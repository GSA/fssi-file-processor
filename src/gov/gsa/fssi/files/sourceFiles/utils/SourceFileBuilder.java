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
public class SourceFileBuilder {
	static Config config = new Config();	    
	static Logger logger = LoggerFactory.getLogger(SourceFileBuilder.class);
	
	/**
	 * The purpose of this function is just to prep file processing. We are not actually loading data yet
	 * @param sourceFileDirectory
	 */
	public SourceFile build(String fileName, ArrayList<Schema> schemas,ArrayList<Provider> providers) {	
		SourceFile sourceFile = new SourceFile(fileName);
    	mapProviderToSourceFile(providers, sourceFile);
    	
    	if(sourceFile.getProvider() == null){
			logger.error("Could not find Provider for file '{}'. Ignoring", fileName);
			sourceFile.setStatusLevel(SourceFile.STATUS_ERROR);
			return null;
    	}
    	
    	//Map Schema to SourceFile
    	if(!sourceFile.getStatusLevel().equals(SourceFile.STATUS_ERROR) && sourceFile.getProvider().getSchemaName() != null && !sourceFile.getProvider().getSchemaName().isEmpty()){
    		logger.warn("Attemping to map Schema to SourceFile '{}'", sourceFile.getFileName());
    		mapSchemaToSourceFile(schemas, sourceFile);	
    		
    	}else{
    		logger.warn("Provider '{}' for SourceFile '{}' does not have a Schema", sourceFile.getProvider().getProviderName(), fileName);
    	}
    	
    	//Provider noted a schema, but couldn't find it
    	if((sourceFile.getProvider().getSchemaName() != null || !sourceFile.getProvider().getSchemaName().isEmpty()) && sourceFile.getSchema() == null){
    		logger.error("Provider '{}' for sourceFile '{}' noted schema '{}', but it could not be found", sourceFile.getProvider().getProviderName(), sourceFile.getFileName(), sourceFile.getProvider().getSchemaName());
    	}else if(sourceFile.getSchema() == null){
    		logger.error("No schema for file '{}', ignoring Schema processing activitie", fileName);
    	}else{
    		personalizeSourceFileSchema(sourceFile); 
    	}

		//Load File
		if (!sourceFile.getStatusLevel().equals(SourceFile.STATUS_ERROR)){
		    logger.info("Loading SourceFile '{}'", sourceFile.getFileName());	
		    sourceFile.load();
		    logger.info("Completed loading SourceFile '{}'", sourceFile.getFileName());	
		}
		
		//Organize file based upon schema
		if (!sourceFile.getLoadStatusLevel().equals(SourceFile.STATUS_ERROR) && sourceFile.getSchema() != null && sourceFile.getRecords() != null){
		    logger.info("Processing SourceFile '{}'", sourceFile.getFileName());	
			sourceFile.organize();
		    logger.info("Completed Processing SourceFile '{}'", sourceFile.getFileName());	
		}
		
		//Validate file based upon schema
		if (!sourceFile.getStatusLevel().equals(SourceFile.STATUS_ERROR) && sourceFile.getSchema() != null && sourceFile.getRecords() != null){
		    logger.info("Validating SourceFile '{}'", sourceFile.getFileName());	
		   sourceFile.validate();
		    logger.info("Completed validating SourceFile '{}'", sourceFile.getFileName());	
		}
		
		return sourceFile;
	}
	
	/**
	 * @param providers
	 * @param sourceFile
	 */
	public void mapProviderToSourceFile(ArrayList<Provider> providers, SourceFile sourceFile) {
		if(sourceFile.getStatusLevel() == null || !sourceFile.getStatusLevel().equals(SourceFile.STATUS_ERROR)){
			logger.info("Attempting to map Provider to file {}", sourceFile.getFileName());
			for (Provider provider : providers) {
				for(String fileNamePart:sourceFile.getFileNameParts()){
					//logger.debug("does '{}' equal '{}'", fileNamePart, provider.getProviderIdentifier());
					if(provider.getProviderIdentifier().toUpperCase().matches(fileNamePart.toUpperCase().trim().toUpperCase())){
						logger.info("Mapped provider {} - {} to file '{}'", provider.getProviderName(), provider.getProviderIdentifier(),sourceFile.getFileName());
						sourceFile.setProvider(provider);
						break;					
					}
				}
			}
		}
		if (sourceFile.getProvider() == null){
			logger.error("Could not find provider for file: '{}'", sourceFile.getFileName());
			sourceFile.setStatusLevel(SourceFile.STATUS_ERROR);
		}else{
			logger.info("Mapped Provider '{}' successfully", sourceFile.getProvider().getProviderIdentifier());
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
					sourceFile.setStatusLevel(SourceFile.STATUS_WARNING);
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
    	if(sourceFile.getSchema() == null){
    		logger.error("SourceFile '{}' does not have a schema, unable to personalize", sourceFile.getFileName());
    	}else if(sourceFile.getStatusLevel().equals(SourceFile.STATUS_ERROR)){
       		logger.error("SourceFile '{}' is in error status, unable to personalize", sourceFile.getFileName());
    	}else if(sourceFile.getReportingPeriod() == null){
       		logger.error("SourceFile '{}' does not have a reporting period, unable to personalize", sourceFile.getFileName());
    	}else{   		
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
    	}
	}
	
	
	
	
}