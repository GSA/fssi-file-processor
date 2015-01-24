package gov.gsa.fssi.files.sourceFiles.utils.validators.constraint;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

public class RequiredConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if(constraint.getValue().toUpperCase().equals("TRUE")){
			if(data == null || data.getData() == null || data.getData().equals("") || data.getData().isEmpty()) data.setStatus(constraint.getLevel());
		}
	}

}
