package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import java.util.Date;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;
import main.java.gov.gsa.fssi.helpers.DateHelper;

public class MaximumConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if (field.getType().equals(SchemaField.TYPE_STRING)){
			validateString(field, constraint, data);
		}else if (field.getType().equals(SchemaField.TYPE_INTEGER)){
			validateInteger(field, constraint, data);
		}else if (field.getType().equals(SchemaField.TYPE_NUMBER)){
			validateNumber(field, constraint, data);
		}else if (field.getType().equals(SchemaField.TYPE_DATE)){
			validateDate(field, constraint, data);
		}else{
			validateString(field, constraint, data);
		}
		
		if(data.getValidatorStatus() == null || data.getValidatorStatus().isEmpty() || data.getValidatorStatus().equals("")){
			data.setValidatorStatus(File.STATUS_PASS);
		}
	}

	@Override
	public boolean isValid(SchemaField field, FieldConstraint constraint,Data data) {
		// TODO Auto-generated method stub
		
		
		return true;
	}
	
	
	/**
	 * Validate String or Any type fields
	 * @param field
	 * @param constraint
	 * @param data
	 */
	private void validateString(SchemaField field, FieldConstraint constraint, Data data){
		if(data.getData().length() > Integer.parseInt(constraint.getValue())){
			data.setStatus(constraint.getLevel());
			data.setValidatorStatus(File.STATUS_FAIL);
		}
	}
	
	
	/**
	 * Validate Integer or Number
	 * @param field
	 * @param constraint
	 * @param data
	 */
	private void validateInteger(SchemaField field, FieldConstraint constraint, Data data){
		if(Integer.parseInt(data.getData()) > Integer.parseInt(constraint.getValue())){
			data.setStatus(constraint.getLevel());
			data.setValidatorStatus(File.STATUS_FAIL);
		}
	}	
	
	
	/**
	 * Validate Integer or Number
	 * @param field
	 * @param constraint
	 * @param data
	 */
	private void validateNumber(SchemaField field, FieldConstraint constraint, Data data){
		if(Float.valueOf(data.getData()) > Float.valueOf(constraint.getValue())){
			data.setStatus(constraint.getLevel());
			data.setValidatorStatus(File.STATUS_FAIL);
		}
	}	

	
	/**
	 * Validate Date
	 * @param field
	 * @param constraint
	 * @param data
	 */
	private void validateDate(SchemaField field, FieldConstraint constraint, Data data){
		//In order to do a date compare, we need to get turn the dataDate and constraintDate into....well...dates
		Date dataDate  = null;
		Date constraintDate  = DateHelper.getDate(constraint.getValue(), DateHelper.FORMAT_YYYY_MM_DD);
		
		if(field.getFormat() != null){ //We start by checking for the date format
			dataDate = DateHelper.getDate(data.getData(), field.getFormat());				
		}else{ //if all else fails, we default to 'yyyy-MM-dd'
			dataDate = DateHelper.getDate(data.getData(), DateHelper.FORMAT_YYYY_MM_DD);				
		}
				
		if(dataDate != null && constraintDate != null){
		  if(dataDate.compareTo(constraintDate) > 0){
			  //logger.debug("'{}' - '{}'", dataDate, constraintDate);
			  data.setStatus(constraint.getLevel());
			  data.setValidatorStatus(File.STATUS_FAIL);
		  }
		}
		
	//Everything else is either Any, String, or unknown....we use a simple length comparison
	}
}