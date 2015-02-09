package main.java.gov.gsa.fssi.files.sourceFiles.utils;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.TypeValidationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The purpose of this class is to handle the automated processing of all source files.
 *
 * @author davidlarrimore
 *
 */
public class SourceFileValidator{    
	static Logger logger = LoggerFactory.getLogger(SourceFileValidator.class);
	
	
	public void validate(SourceFile sourceFile){
		logger.info("Starting sourceFile validation for file '{}'", sourceFile.getFileName());
		
		if(sourceFile.getSchema() == null){
			logger.error("Cannot validate file '{}', no Schema", sourceFile.getFileName());	
		}else if(sourceFile.getLoadStage().equals(SourceFile.STATUS_ERROR)){
			logger.error("file '{}', had a load error, cannot validate", sourceFile.getFileName());
		}else{
			for(SourceFileRecord sourceFileRecord: sourceFile.getRecords()){
				for(SchemaField field:sourceFile.getSchema().getFields()){
					Data data = sourceFileRecord.getDataByHeaderIndex(field.getHeaderIndex());
					if(data != null){
					 TypeValidationContext typeContext = new TypeValidationContext();
					 typeContext.validate(field, data);
						if(data.getMaxErrorLevel() < 2 && data.getStatus() == true){
							for(FieldConstraint constraint:field.getConstraints()){
							//Already in error state, we can ignore
							//if(data.getStatus() == null || !data.getStatus().equals(FieldConstraint.LEVEL_ERROR)){
								ConstraintValidationContext context = new ConstraintValidationContext();
								context.validate(field, constraint, data); //Validate Constraint	
								if(logger.isDebugEnabled() && data.getMaxErrorLevel() > 0){
									logger.debug("Row {} - Field '{}' validation {}: '{}' = {}, Value = '{}'", sourceFileRecord.getRowIndex(), field.getName(), constraint.getLevelName(), constraint.getType(), constraint.getValue(), data.getData());	
								}
							//}
							}
						}
						sourceFileRecord.setMaxErrorLevel(data.getMaxErrorLevel());
						sourceFileRecord.setStatus(data.getMaxErrorLevel());
					}
				}
			}
		}
	}
	
}