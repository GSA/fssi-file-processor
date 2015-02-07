package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.TypeValidationStrategy;

public class IntegerTypeValidationStrategy implements TypeValidationStrategy {

	@Override
	public void validate(SchemaField field, Data data) {
		if(data != null){
			if(!data.getData().isEmpty() && !data.getData().equals("")){
				Integer integer = null;
				Double number = null;
				
				try {
					integer = Integer.parseInt(data.getData());
				} catch (NumberFormatException e) {
					if(logger.isDebugEnabled()) logger.debug("Received error '{}' when trying to convert '{}' to integer", e.getMessage(), data.getData());
					//e.printStackTrace();
				}
				
				//Attempting to see if Number
				if(integer == null){
					try {
						number = Double.valueOf(data.getData()); 
					} catch (NumberFormatException e) {
						if(logger.isDebugEnabled()) logger.debug("Received error '{}' when trying to convert '{}' to Number", e.getMessage(), data.getData());
						//e.printStackTrace();
					}
					//if(logger.isDebugEnabled()) logger.debug("'{}'", number);
					//If we find out that it is a "Number" (aka has a float) then it is just an error....otherwise its a fatal
					if(number == null) data.addValidationResult(false, 3, "Type(Integer)");	//Fatal
					else data.addValidationResult(false, 2, "Type(Integer)"); //Error	
				}else data.addValidationResult(true, 0, "Type(Integer)");	
			}else data.addValidationResult(true, 0, "Type(Integer)");			
			
			
		}		
	}

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
