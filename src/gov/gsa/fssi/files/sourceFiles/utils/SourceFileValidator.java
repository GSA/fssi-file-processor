package gov.gsa.fssi.files.sourceFiles.utils;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The purpose of this class is to handle the automated processing of all source files.
 *
 * @author davidlarrimore
 *
 */
public class SourceFileValidator{
	static Config config = new Config();	    
	static Logger logger = LoggerFactory.getLogger(SourceFileValidator.class);
	
	
	public void validate(SourceFile sourceFile){
		logger.info("Starting sourceFile validation for file '{}'", sourceFile.getFileName());
		
		if(sourceFile.getSchema() == null){
			logger.error("Cannot validate file '{}', no Schema", sourceFile.getFileName());	
		}else if(sourceFile.getLoadStatusLevel().equals(SourceFile.STATUS_ERROR)){
			logger.error("file '{}', had a load error, cannot validate", sourceFile.getFileName());
		}else{
			for(SourceFileRecord sourceFileRecord: sourceFile.getRecords()){
				for(SchemaField field:sourceFile.getSchema().getFields()){
					Data data = sourceFileRecord.getDataByHeaderIndex(field.getHeaderIndex());
					if(data != null){
						//logger.debug("HeaderIndex - '{}'", field.getHeaderIndex());
						//TODO: Validate Type
						//context.validateConstraint(field, constraint, data);
						//TODO: Validate Format
						//Already in error state, we can ignore
						if(data.getStatus() == null || !data.getStatus().equals(FieldConstraint.LEVEL_ERROR)){
							for(FieldConstraint constraint:field.getConstraints()){
								//Already in error state, we can ignore
								//if(data.getStatus() == null || !data.getStatus().equals(FieldConstraint.LEVEL_ERROR)){
									ConstraintValidationContext context = new ConstraintValidationContext();
									context.validateConstraint(field, constraint, data); //Validate Constraint	
									if(data.getStatus().equals(FieldConstraint.LEVEL_ERROR) ||data.getStatus().equals(FieldConstraint.LEVEL_WARNING)){
										logger.debug("Row {} - Field '{}' validation {}: '{}' = {}, Value = '{}'", sourceFileRecord.getRowIndex(), field.getName(), constraint.getLevel().toUpperCase(), constraint.getType(), constraint.getValue(), data.getData());	
									}
								//}
							}
						}
					}
				}
			}
		}
	}
	
}