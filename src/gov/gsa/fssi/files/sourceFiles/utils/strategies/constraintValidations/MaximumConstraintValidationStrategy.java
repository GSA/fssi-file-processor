package gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import java.util.Date;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;
import gov.gsa.fssi.helpers.DateHelper;

public class MaximumConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		
		//If no data is here, we don't do anything
		if(data.getData() != null && !data.getData().equals("")){
			if(field.getType().equals(SchemaField.TYPE_DATE)){
				//In order to do a date compare, we need to get turn the dataDate and constraintDate into....well...dates
				Date dataDate  = null;
				Date constraintDate  = DateHelper.getDate(constraint.getValue(), DateHelper.FORMAT_YYYY_MM_DD);
				
				if(field.getFormat() != null){ //We start by checking for the date format
					dataDate = DateHelper.getDate(data.getData(), field.getFormat());				
				}else{ //if all else fails, we default to 'yyyy-MM-dd'
					dataDate = DateHelper.getDate(data.getData(), DateHelper.FORMAT_YYYY_MM_DD);				
				}
				
				if(dataDate == null){
					
				}else if(constraintDate == null){
						//TODO:
				}else{
				  if(dataDate.compareTo(constraintDate) > 1) data.setStatus(constraint.getLevel());
				}
				//DateHelper.getDate(data.getData(), dateFormat)
				
			//Everything else is either Any, String, or unknown....we use a simple length comparison
			}else{
				if(data.getData().length() > Integer.parseInt(constraint.getValue())) data.setStatus(constraint.getLevel());
			}	
		}
	}

	@Override
	public boolean isValid(SchemaField field, FieldConstraint constraint,Data data) {
		// TODO Auto-generated method stub
		
		
		return true;
	}
}
