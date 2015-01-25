package gov.gsa.fssi.files.sourceFiles.utils;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.helpers.FileHelper;
import java.util.ArrayList;
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
	public void preProcessAll(ArrayList<Schema> schemas,ArrayList<Provider> providers,ArrayList<SourceFile> sourceFiles) {	
		//Loop through files in sourceFileDirectory and populate SourceFile objects
		for (String fileName : FileHelper.getFilesFromDirectory(config.getProperty(Config.SOURCEFILES_DIRECTORY), ".csv")) {
	    	SourceFile newSourceFile = new SourceFile(fileName);
	    	mapProviderToSourceFile(providers, newSourceFile);
	    	logger.info("'{}'", newSourceFile.getProvider().getProviderIdentifier());
	    	mapSchemaToSourceFile(schemas, newSourceFile);
	    	if(newSourceFile.getSchema() != null){
	    		personalizeSourceFileSchema(newSourceFile);
	    	}
			sourceFiles.add(newSourceFile);		        
		}	
	}
	
	/**
	 * @param providers
	 * @param sourceFile
	 */
	public void mapProviderToSourceFile(ArrayList<Provider> providers, SourceFile sourceFile) {
		if(!sourceFile.getLoadStatusLevel().equals(SourceFile.STATUS_ERROR)){
			logger.info("Attempting to map Provider to file {}", sourceFile.getFileName());
			for (Provider provider : providers) {
				if(sourceFile.getFileName().toUpperCase().contains(provider.getProviderIdentifier().toUpperCase())){
					logger.info("Mapped provider {} - {} to file '{}'", provider.getProviderName(), provider.getProviderIdentifier(),sourceFile.getFileName());
					sourceFile.setProvider(provider);
					//sourceFile.setLoaderStatusLevel(LoaderStatus.MAPPED);
				}
			}
		}
		if (sourceFile.getProvider() == null){
			logger.error("Could not find provider for file: '{}'", sourceFile.getFileName());
			sourceFile.setLoadStatusLevel(SourceFile.STATUS_ERROR);
		}
	}		
	
	
	
	/**
	 * @param schemas
	 * @param sourceFile
	 */
	public void mapSchemaToSourceFile(ArrayList<Schema> schemas,SourceFile sourceFile) {
		logger.info("Attempting to map Schema to file {}", sourceFile.getFileName());
		if (!sourceFile.getLoadStatusLevel().equals(SourceFile.STATUS_ERROR)){
			if(sourceFile.getProvider().getSchemaName() != null){
				for (Schema schema : schemas) {
					if(sourceFile.getProvider().getSchemaName().toUpperCase().equals(schema.getName().toUpperCase())){
						sourceFile.setSchema(schema);
					}
				}
				if (sourceFile.getSchema() == null){
					logger.error("Could not find schema for file: '{}'", sourceFile.getFileName());
					sourceFile.setValidatorStatusLevel(SourceFile.STATUS_WARNING);
				}
			}
		}
	}		
	
	
	/**
	 * This method is important to trim the fat from Schemas and personalize
	 * it for each file based upon its effective date. It compares the effective dates and only
	 * 
	 * @param effectiveDate
	 * @param schema
	 * @return
	 */
	public void personalizeSourceFileSchema(SourceFile sourceFile){
    	if(sourceFile.getSchema() != null){
			logger.info("Personalizing schema '{}' to effectiveDate '{}'", sourceFile.getSchema().getName(), sourceFile.getReportingPeriod());
			Schema newSchema = sourceFile.getSchema();
			ArrayList<SchemaField> newFields = new ArrayList<SchemaField>();
			for(SchemaField field: sourceFile.getSchema().getFields()){
				logger.info("Personalizing field '{}' to effectiveDate '{}'", field.getName(), sourceFile.getReportingPeriod());
				SchemaField newField = field;
				ArrayList<FieldConstraint> newConstraints = new ArrayList<FieldConstraint>();
				for(FieldConstraint constraint:newField.getConstraints()){
					FieldConstraint newConstraint = constraint;
					logger.info("Analyzing constraint '{}' - '{}'", newConstraint.getType(), newConstraint.getEffectiveDate());
					boolean addNewConstraint = true;
					 if(newConstraint.getEffectiveDate() == null){
						logger.info("Constraint '{}' does not have an effective date", constraint.getType());
					 }else if(sourceFile.getReportingPeriod().compareTo(newConstraint.getEffectiveDate()) >= 0){
						logger.warn("The files effectiveDate '{}' is after the constraint '{}' effectiveDate '{}'", sourceFile.getReportingPeriod(), constraint.getType(), constraint.getEffectiveDate());
					 }else if(sourceFile.getReportingPeriod().compareTo(newConstraint.getEffectiveDate()) < 0){
						logger.info("The files effectiveDate '{}' is before or equal to the constraint '{}' effectiveDate '{}'. This can be ignored.", sourceFile.getReportingPeriod(), constraint.getType(), constraint.getEffectiveDate());
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
			sourceFile.setSchema(newSchema);
    	}else{
    		logger.error("SourceFile '{}' does not have a schema, unable to process", sourceFile.getFileName());
    	}
	}
	
	
	
	
}
