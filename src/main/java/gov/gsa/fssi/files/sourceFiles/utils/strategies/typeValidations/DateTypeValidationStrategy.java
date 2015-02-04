package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import java.util.Date;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.TypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.DateHelper;

public class DateTypeValidationStrategy implements TypeValidationStrategy {

	@Override
	public void validate(SchemaField field, Data data) {
		System.out.println(data.getData());
		if(data.getData() != null && !data.getData().isEmpty() && !data.getData().equals("")){
			String dateFormatString = DateHelper.FORMAT_YYYY_MM_DD;
			if(field.getFormat() != null && !field.getFormat().isEmpty() && !field.getFormat().equals("")){
				dateFormatString = field.getFormat();
			}
			Date date = DateHelper.getDate(data.getData(), dateFormatString);			
			if(date == null){
				data.setStatus(File.STATUS_FATAL);
				data.setValidatorStatus(File.STATUS_PASS);
			}
			System.out.println(date);
			
		}
		
		if(data.getStatus() == null || data.getStatus().isEmpty() || data.getStatus().equals("")){
			data.setStatus(File.STATUS_PASS);
		}	
			
		if(data.getValidatorStatus() == null || data.getValidatorStatus().isEmpty() || data.getValidatorStatus().equals("")){
			data.setValidatorStatus(File.STATUS_PASS);
		}	
	}

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
