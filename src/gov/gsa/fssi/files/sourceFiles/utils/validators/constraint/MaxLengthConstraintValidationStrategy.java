package gov.gsa.fssi.files.sourceFiles.utils.validators.constraint;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

public class MaxLengthConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if(data.getData().length() > Integer.parseInt(constraint.getValue())) data.setStatus(constraint.getLevel());
	}

}
