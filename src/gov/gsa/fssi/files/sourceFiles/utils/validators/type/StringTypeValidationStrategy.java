package gov.gsa.fssi.files.sourceFiles.utils.validators.type;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

public class StringTypeValidationStrategy implements TypeValidationStrategy {

	@Override
	public void validate(SchemaField field, Data data) {
		//if(data.getData().contains(constraint.getValue())) data.setStatus(constraint.getLevel());
	}

}
