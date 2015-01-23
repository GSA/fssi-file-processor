package gov.gsa.fssi.files.sourceFiles.utils.validators.strategies;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.validators.DataValidationStrategy;

public class MaxLengthDataValidationStrategy implements DataValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		if(data.getData().length() > Integer.parseInt(constraint.getValue())) data.setStatus(constraint.getLevel());
	}

}
