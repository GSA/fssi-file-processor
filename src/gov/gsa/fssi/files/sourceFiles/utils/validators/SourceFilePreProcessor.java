package gov.gsa.fssi.files.sourceFiles.utils.validators;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.LoaderStatus;
import gov.gsa.fssi.files.ValidatorStatus;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.helpers.FileHelper;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The purpose of this class is to handle the automated processing of all source files.
 *
 * @author davidlarrimore
 *
 */
public class SourceFilePreProcessor {
	static Config config = new Config();	    
	static Logger logger = LoggerFactory.getLogger(SourceFilePreProcessor.class);
	
	/**
	 * The purpose of this function is just to prep file processing. We are not actually loading data yet
	 * @param sourceFileDirectory
	 */
	public static ArrayList<SourceFile> preProcess() {	
	    logger.debug("Starting initializeSourceFiles('{}')", config.getProperty(Config.SOURCEFILES_DIRECTORY));		
		ArrayList<SourceFile> sourceFiles = new ArrayList<SourceFile>();	
	
		//Loop through files in sourceFileDirectory and populate SourceFile objects
		for (String fileName : FileHelper.getFilesFromDirectory(config.getProperty(Config.SOURCEFILES_DIRECTORY), ".csv ")) {
	    	sourceFiles.add(new SourceFile(fileName));		        
		}		    
		return sourceFiles;
	}
	
	/**
	 * This method is important to trim the fat from Schemas and personalize
	 * it for each file based upon its effective date. It compares the effective dates and only
	 * 
	 * @param effectiveDate
	 * @param schema
	 * @return
	 */
	public static Schema personalizeSourceFileSchema(Date effectiveDate, Schema schema){
		logger.info("Personalizing schema '{}' to effectiveDate '{}'", schema.getName(), effectiveDate);
		Schema newSchema = schema;
		ArrayList<SchemaField> newFields = new ArrayList<SchemaField>();
		for(SchemaField field: schema.getFields()){
			logger.info("Personalizing field '{}' to effectiveDate '{}'", field.getName(), effectiveDate);
			SchemaField newField = field;
			ArrayList<FieldConstraint> newConstraints = new ArrayList<FieldConstraint>();
			for(FieldConstraint constraint:newField.getConstraints()){
				FieldConstraint newConstraint = constraint;
				logger.info("Analyzing constraint '{}' - '{}'", newConstraint.getType(), newConstraint.getEffectiveDate());
				boolean addNewConstraint = true;
				 if(newConstraint.getEffectiveDate() == null){
					logger.info("Constraint '{}' does not have an effective date", constraint.getType());
				 }else if(effectiveDate.compareTo(newConstraint.getEffectiveDate()) >= 0){
					logger.warn("The files effectiveDate '{}' is after the constraint '{}' effectiveDate '{}'", effectiveDate, constraint.getType(), constraint.getEffectiveDate());
				 }else if(effectiveDate.compareTo(newConstraint.getEffectiveDate()) < 0){
					logger.info("The files effectiveDate '{}' is before or equal to the constraint '{}' effectiveDate '{}'. This can be ignored.", effectiveDate, constraint.getType(), constraint.getEffectiveDate());
					addNewConstraint = false;
				}	
				logger.info("Checking for duplicate constraint", constraint.getType());
				if(addNewConstraint){
					for (FieldConstraint constraintCheck: newConstraints){
						if(constraintCheck.getType().equals(newConstraint.getType())){
							logger.info("found duplicate constraint, comparing dates", constraint.getType());
							if(newConstraint.getEffectiveDate() == null && constraintCheck.getEffectiveDate() != null){
								logger.info("newConstraint '{}' does not have an effective date, but constraintCheck does. This can be ignored", constraint.getType());	
								addNewConstraint = false;
							}else if(newConstraint.getEffectiveDate() != null && constraintCheck.getEffectiveDate() == null){
								logger.info("newConstraint '{}' has an effective date, but constraintCheck does not. Switching", constraint.getType());	
								newConstraints.remove(constraintCheck);
								newConstraints.add(newConstraint);
								addNewConstraint = false;
							}else if(newConstraint.getEffectiveDate().compareTo(constraintCheck.getEffectiveDate()) <= 0){
								logger.info("newConstraint is after constraintCheck. switching", constraint.getType());	
								newConstraints.remove(constraintCheck);
								newConstraints.add(newConstraint);
								addNewConstraint = false;
							}else{
								logger.info("newConstraints already has the most recent constraint");
								addNewConstraint = false;
							}
						}
					}
					
				}
				if(addNewConstraint){
					newConstraints.add(newConstraint);						
				}
			}
			newField.setConstraints(newConstraints);
			newFields.add(newField);
		}
		newSchema.setFields(newFields);
		return newSchema;
	}
	
}
