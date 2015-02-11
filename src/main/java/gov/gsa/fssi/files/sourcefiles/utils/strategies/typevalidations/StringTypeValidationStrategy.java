package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.TypeValidationStrategy;

public class StringTypeValidationStrategy implements TypeValidationStrategy {

	@Override
	public void validate(SchemaField field, Data data) {
		if (data != null) {
			data.addValidationResult(true, 0, "Type(" + field.getType() + ")");
		}
	}

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
