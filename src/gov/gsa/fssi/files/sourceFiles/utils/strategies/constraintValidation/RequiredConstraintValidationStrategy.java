package gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;

public class RequiredConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if(constraint.getValue().toUpperCase().equals("TRUE")){
			if(data == null || data.getData() == null || data.getData().equals("") || data.getData().isEmpty()) data.setStatus(constraint.getLevel());
		}
	}

	@Override
	public boolean isValid(SchemaField field, FieldConstraint constraint,
			Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
