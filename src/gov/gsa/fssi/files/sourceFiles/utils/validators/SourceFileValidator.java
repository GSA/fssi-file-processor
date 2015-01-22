package gov.gsa.fssi.files.sourceFiles.utils.validators;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.LoaderStatus;
import gov.gsa.fssi.files.ValidatorStatus;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
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
public class SourceFileValidator{
	static Config config = new Config();	    
	static Logger logger = LoggerFactory.getLogger(SourceFileValidator.class);
	
	
	public void validate(SourceFile sourceFile){
		logger.info("Starting sourceFile validation for file '{}'", sourceFile.getFileName());
		
		if(sourceFile.getSchema() == null){
			logger.error("Cannot validate file '{}', no Schema", sourceFile.getFileName());	
		}else if(sourceFile.getLoaderStatus().equals(LoaderStatus.ERROR)){
			logger.error("file '{}', had a load error, cannot validate", sourceFile.getFileName());
		}else{
			for(SourceFileRecord sourceFileRecord: sourceFile.getRecords()){
				for(SchemaField field:sourceFile.getSchema().getFields()){
					Data data = sourceFileRecord.getDataByHeaderIndex(field.getHeaderIndex());
					if(data != null){
						//logger.debug("HeaderIndex - '{}'", field.getHeaderIndex());
						//TODO: Validate Type
						//TODO: Validate Format
					
						//Validate Constraints
						for(FieldConstraint constraint:field.getConstraints()){
							switch (constraint.getType()){
							case FieldConstraint.TYPE_MAXIMUM:
								break;
							case FieldConstraint.TYPE_MINIMUM:
								break;
							case FieldConstraint.TYPE_MAXLENGTH:
								if(data.getData().length() > Integer.parseInt(constraint.getValue())) data.setStatus(constraint.getLevel());
								break;		
							case FieldConstraint.TYPE_MINLENGTH:
								break;
							case FieldConstraint.TYPE_PATTERN:
								if(data.getData().contains(constraint.getValue())) data.setStatus(constraint.getLevel());
								break;		
							case FieldConstraint.TYPE_REQUIRED:
								if(constraint.getValue().toUpperCase().equals("TRUE")){
									if(data == null || data.getData() == null || data.getData().equals("") || data.getData().isEmpty()) data.setStatus(constraint.getLevel());
								}
								break;				
							default:
								break;
							}
							if(data.getStatus().equals(FieldConstraint.LEVEL_ERROR) ||data.getStatus().equals(FieldConstraint.LEVEL_WARNING)){
								logger.debug("Row {} - Field '{}' validation {}: '{}' = {}, Value = '{}'", sourceFileRecord.getRowIndex(), field.getName(), constraint.getLevel().toUpperCase(), constraint.getType(), constraint.getValue(), data.getData());	
							}
						}
					}
				}
			}
		}
	}
	
	
	
	public void validateConstraint(Data data, SchemaField field){
	}
}