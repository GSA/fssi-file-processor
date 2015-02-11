package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.TypeValidationStrategy;

public class NumberTypeValidationStrategy implements TypeValidationStrategy {

	@Override
	public void validate(SchemaField field, Data data) {
		if (data != null) {
			if (!data.getData().isEmpty() && !data.getData().equals("")) {
				// if(logger.isDebugEnabled()) logger.debug("'{}'",
				// data.getData());
				Double number = null;
				try {
					number = Double.valueOf(data.getData());
				} catch (NumberFormatException e) {
					if (logger.isDebugEnabled())
						logger.debug(
								"Received error '{}' when trying to convert '{}' to Number",
								e.getMessage(), data.getData());
					// e.printStackTrace();
				}

				// logger.debug("'{}'", number);
				if (number == null)
					data.addValidationResult(false, 3,
							"Type(" + field.getType() + ")"); // Fatal
				else
					data.addValidationResult(true, 0, "Type(" + field.getType()
							+ ")");
			} else
				data.addValidationResult(true, 0, "Type(" + field.getType()
						+ ")");
		}
	}

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
