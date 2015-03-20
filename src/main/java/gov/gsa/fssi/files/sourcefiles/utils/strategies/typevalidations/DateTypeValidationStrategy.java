package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations;

import java.util.Date;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.TypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.DateHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTypeValidationStrategy implements TypeValidationStrategy {
	private static final Logger logger = LoggerFactory
			.getLogger(DateTypeValidationStrategy.class);

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(SchemaField field, Data data) {
		if (data != null) {
			if (data.getData() != null && !data.getData().isEmpty()) {
				String dateFormatString = DateHelper.FORMAT_YYYY_MM_DD;
				if (field.getFormat() != null && !field.getFormat().isEmpty()
						&& !field.getFormat().equals(""))
					dateFormatString = field.getFormat();
				Date date = DateHelper
						.getDate(data.getData(), dateFormatString);

				if (date == null) {
					data.addValidationResult(false, 3,
							"Type(" + field.getType() + "-[" + dateFormatString + "])");
				} else if (date.before(DateHelper.getMinDate())) {
					if (logger.isDebugEnabled())
						logger.debug(
								"Field '{}' Date '{}' was parsed to '{}' which is before our accessible threshold ({}) for a date",
								field.getName(), data.getData(), date,
								DateHelper.getMinDate());
					data.addValidationResult(false, 2,
							"Type(" + field.getType() + ")"); // Only an error
																// if out of
																// bounds
				} else if (date.after(DateHelper.getMaxDate())) {
					if (logger.isDebugEnabled())
						logger.debug(
								"Field '{}' Date '{}' was parsed to '{}' which is past our accessible threshold ({}) for a date",
								field.getName(), data.getData(), date,
								DateHelper.getMaxDate());
					data.addValidationResult(false, 2,
							"Type(" + field.getType() + ")"); // Only an error
																// if out of
																// bounds
				} else
					data.addValidationResult(true, 0, "Type(" + field.getType()
							+ ")");

			} else
				data.addValidationResult(true, 0, "Type(" + field.getType()
						+ ")");
		}
	}

}
