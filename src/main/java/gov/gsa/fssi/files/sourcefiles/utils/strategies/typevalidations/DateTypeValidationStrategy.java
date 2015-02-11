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
							"Type(" + field.getType() + ")"); // Fatal error,
																// could not
																// find date
				} else if (date.compareTo(DateHelper.getMinDate()) < 0) {
					if (logger.isDebugEnabled())
						logger.debug(
								"Field '{}' Date '{}' is before our accessible threshold ({}) for a date",
								field.getName(), data.getData(),
								DateHelper.getMinDate());
					data.addValidationResult(false, 2,
							"Type(" + field.getType() + ")"); // Only an error
																// if out of
																// bounds
				} else if (date.compareTo(DateHelper.getMaxDate()) > 0) {
					if (logger.isDebugEnabled())
						logger.debug(
								"Field '{}' Date '{}' is past our accessible threshold ({}) for a date",
								field.getName(), data.getData(),
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

	@Override
	public boolean isValid(SchemaField field, Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
