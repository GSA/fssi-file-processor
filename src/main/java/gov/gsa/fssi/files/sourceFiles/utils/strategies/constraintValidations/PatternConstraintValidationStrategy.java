package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;

public class PatternConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if(data.getStatusLevel() == null || (!data.getStatusLevel().equals(File.STATUS_ERROR) && !data.getStatusLevel().equals(File.STATUS_FATAL))){		
			if(data.getData() != null && !data.getData().isEmpty() && !data.getData().equals("")){	
				Pattern pattern = Pattern.compile(constraint.getValue(), Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(data.getData());
				if(!matcher.matches()) {
					data.setStatusLevel(constraint.getLevel());
					data.setValidatorStatus(File.STATUS_FAIL);
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
	public boolean isValid(SchemaField field, FieldConstraint constraint,
			Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
