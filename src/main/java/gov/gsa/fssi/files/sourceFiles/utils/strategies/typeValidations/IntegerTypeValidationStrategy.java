package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.TypeValidationStrategy;

public class IntegerTypeValidationStrategy implements TypeValidationStrategy {

	@Override
	public void validate(SchemaField field, Data data) {
		if(data.getStatusLevel() == null || (!data.getStatusLevel().equals(File.STATUS_ERROR) && !data.getStatusLevel().equals(File.STATUS_FATAL))){
			if(data.getData() == null || data.getData().isEmpty() || data.getData().equals("")){
				
			}else{
				Integer integer = null;
				Double number = null;
				
				try {
					integer = Integer.parseInt(data.getData());
				} catch (NumberFormatException e) {
					logger.debug("Received error '{}' when trying to convert '{}' to integer", e.getMessage(), data.getData());
					//e.printStackTrace();
				}
				
				//Attempting to see if Number
				if(integer == null){
					try {
						number = Double.valueOf(data.getData()); 
					} catch (NumberFormatException e) {
						logger.debug("Received error '{}' when trying to convert '{}' to Number", e.getMessage(), data.getData());
						//e.printStackTrace();
					}
					
					logger.debug("'{}'", number);
					if(number == null){
						data.setValidatorStatus(File.STATUS_FAIL);
						data.setStatusLevel(File.STATUS_FATAL);
					}else{
						data.setValidatorStatus(File.STATUS_FAIL);
						data.setStatusLevel(File.STATUS_ERROR);
						
						//TODO: Need to keep highest level of failure in-tact
					}
				}else{
					data.setValidatorStatus(File.STATUS_PASS);	
					data.setStatusLevel(File.STATUS_PASS);
					
					//TODO: Need to keep highest level of failure in-tact
				}
			}
				
			
			if(data.getStatusLevel() == null || data.getStatusLevel().isEmpty() || data.getStatusLevel().equals("")){
				data.setStatusLevel(File.STATUS_PASS);
			}
			if(data.getValidatorStatus() == null || data.getValidatorStatus().isEmpty() || data.getValidatorStatus().equals("")){
				data.setValidatorStatus(File.STATUS_PASS);
			}	
		}
	}

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
