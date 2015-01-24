package gov.gsa.fssi.files.sourceFiles.utils.validators.constraint;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

public class MinimumConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if(field.getType().equals(SchemaField.TYPE_ANY) || field.getType().equals(SchemaField.TYPE_STRING)){
			if(data.getData().length() < Integer.parseInt(constraint.getValue())) data.setStatus(constraint.getLevel());
		}
	}
}
