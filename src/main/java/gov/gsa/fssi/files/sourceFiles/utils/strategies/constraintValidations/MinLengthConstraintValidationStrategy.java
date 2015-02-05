package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;

public class MinLengthConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if(data.getData().length() < Integer.parseInt(constraint.getValue())){
			data.setStatusLevel(constraint.getLevel());
			data.setValidatorStatus(File.STATUS_FAIL);
		}
		
		if(data.getValidatorStatus() == null || data.getValidatorStatus().isEmpty() || data.getValidatorStatus().equals("")){
			data.setValidatorStatus(File.STATUS_PASS);
		}		
	}

	@Override
	public boolean isValid(SchemaField field, FieldConstraint constraint,
			Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
