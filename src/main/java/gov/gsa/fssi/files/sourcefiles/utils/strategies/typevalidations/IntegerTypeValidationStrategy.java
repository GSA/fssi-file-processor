package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.TypeValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.organizers.ImplodeSourceFileOrganizerStrategy;

public class IntegerTypeValidationStrategy implements TypeValidationStrategy {
	static final Logger logger = LoggerFactory.getLogger(IntegerTypeValidationStrategy.class);
	@Override
	public void validate(SchemaField field, Data data) {
		if (data != null) {
			if (data.getData() != null && !data.getData().isEmpty()) {
				Integer integer = null;
				Double number = null;

				try {
					integer = Integer.parseInt(data.getData());
				} catch (NumberFormatException e) {
					if (logger.isDebugEnabled())
						logger.debug(
								"Received error '{}' when trying to convert '{}' to integer",
								e.getMessage(), data.getData());
				}

				if (integer == null) { // Attempting to see if Number
					try {
						number = Double.valueOf(data.getData());
					} catch (NumberFormatException e) {
						if (logger.isDebugEnabled())
							logger.debug(
									"Received error '{}' when trying to convert '{}' to Number",
									e.getMessage(), data.getData());
					}
					
					/*
					 *  If we find out that it is a "Number" (aka has a float)
					 *  then it is just an error....otherwise its a fatal
					 */
					if (number == null)
						data.addValidationResult(false, 3,
								"Type(" + field.getType() + ")"); // Fatal
					else
						data.addValidationResult(false, 2,
								"Type(" + field.getType() + ")"); // Error
				} else
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
