package gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.TypeValidationStrategy;

public class StringTypeValidationStrategy implements TypeValidationStrategy {

	@Override
	public void validate(SchemaField field, Data data) {
		//if(data.getData().contains(constraint.getValue())) data.setStatus(constraint.getLevel());
	}

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
