package gov.gsa.fssi.files.sourceFiles.utils.validators.strategies;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.validators.DataValidationStrategy;

public class TypeDataValidationStrategy implements DataValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if(data.getData().contains(constraint.getValue())) data.setStatus(constraint.getLevel());
	}

}
