package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import java.util.Date;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.TypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.DateHelper;

public class DateTypeValidationStrategy implements TypeValidationStrategy {

	@Override
	public void validate(SchemaField field, Data data) {
		if(data != null){
			if(!data.getData().isEmpty() && !data.getData().equals("")){
				String dateFormatString = DateHelper.FORMAT_YYYY_MM_DD;
				
				if(field.getFormat() != null && !field.getFormat().isEmpty() && !field.getFormat().equals("")) dateFormatString = field.getFormat();
				Date date = DateHelper.getDate(data.getData(), dateFormatString);
				
				if(date == null){
					data.addValidationResult(false, 3, "Type(Date)"); //Fatal error, could not find date
				}else if(date.compareTo(DateHelper.getMinDate()) < 0){
					if(logger.isDebugEnabled()) logger.debug("Field '{}' Date '{}' is before our accessible threshold ({}) for a date", field.getName(), data.getData(), DateHelper.getMinDate());
					data.addValidationResult(false, 2, "Type(Date)"); //Only an error if out of bounds
				}else if(date.compareTo(DateHelper.getMaxDate())  > 0){
					if(logger.isDebugEnabled()) logger.debug("Field '{}' Date '{}' is past our accessible threshold ({}) for a date", field.getName(), data.getData(), DateHelper.getMaxDate());
					data.addValidationResult(false, 2, "Type(Date)"); //Only an error if out of bounds		
				}else data.addValidationResult(true, 0, "Type(Date)");
			
			}else data.addValidationResult(true, 0, "Type(Date)");	
		}			
	}

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
