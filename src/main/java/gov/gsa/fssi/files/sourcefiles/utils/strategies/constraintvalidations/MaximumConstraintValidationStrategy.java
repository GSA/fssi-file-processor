package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.ConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.exporters.CSVSourceFileExporterStrategy;
import main.java.gov.gsa.fssi.helpers.DateHelper;

public class MaximumConstraintValidationStrategy implements
		ConstraintValidationStrategy {
	static final Logger logger = LoggerFactory.getLogger(MaximumConstraintValidationStrategy.class);
	@Override
	public void validate(SchemaField field, FieldConstraint constraint,
			Data data) {
		if (data != null) {
			if (data.getData() != null && !data.getData().isEmpty()) {
				if (field.getType().equals(SchemaField.TYPE_STRING)) {
					validateString(constraint, data);
				} else if (field.getType().equals(SchemaField.TYPE_INTEGER)) {
					validateInteger(constraint, data);
				} else if (field.getType().equals(SchemaField.TYPE_NUMBER)) {
					validateNumber(constraint, data);
				} else if (field.getType().equals(SchemaField.TYPE_DATE)) {
					validateDate(field, constraint, data);
				} else {
					validateString(constraint, data);
				}
			} else
				data.addValidationResult(true, 0, constraint.getRuleText());
		}
	}

	@Override
	public boolean isValid(SchemaField field, FieldConstraint constraint,
			Data data) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Validate String or Any type fields
	 * 
	 * @param field
	 * @param constraint
	 * @param data
	 */
	private void validateString(FieldConstraint constraint, Data data) {
		if (data.getData().length() > Integer.parseInt(constraint.getValue())) {
			data.addValidationResult(false, constraint.getLevel(),
					constraint.getRuleText());
		} else {
			data.addValidationResult(true, 0, constraint.getRuleText());
		}
	}

	/**
	 * Validate Integer or Number
	 * 
	 * @param field
	 * @param constraint
	 * @param data
	 */
	private void validateInteger(FieldConstraint constraint, Data data) {
		if (Integer.parseInt(data.getData()) > Integer.parseInt(constraint
				.getValue())) {
			data.addValidationResult(false, constraint.getLevel(),
					constraint.getRuleText());
		} else {
			data.addValidationResult(true, 0, constraint.getRuleText());
		}
	}

	/**
	 * Validate Integer or Number
	 * 
	 * @param field
	 * @param constraint
	 * @param data
	 */
	private void validateNumber(FieldConstraint constraint, Data data) {
		if (Float.valueOf(data.getData()) > Float
				.valueOf(constraint.getValue())) {
			data.addValidationResult(false, constraint.getLevel(),
					constraint.getRuleText());
		} else {
			data.addValidationResult(true, 0, constraint.getRuleText());
		}
	}

	/**
	 * Validate Date
	 * 
	 * @param field
	 * @param constraint
	 * @param data
	 */
	private void validateDate(SchemaField field, FieldConstraint constraint,
			Data data) {
		// In order to do a date compare, we need to get turn the dataDate and
		// constraintDate into....well...dates
		Date dataDate = null;
		Date constraintDate = DateHelper.getDate(constraint.getValue(),
				DateHelper.FORMAT_YYYY_MM_DD);

		if (field.getFormat() != null) { // We start by checking for the date
											// format
			dataDate = DateHelper.getDate(data.getData(), field.getFormat());
		} else { // if all else fails, we default to 'yyyy-MM-dd'
			dataDate = DateHelper.getDate(data.getData(),
					DateHelper.FORMAT_YYYY_MM_DD);
		}

		if (dataDate != null && constraintDate != null) {
			if (dataDate.compareTo(constraintDate) > 0) {
				data.addValidationResult(false, constraint.getLevel(),
						constraint.getRuleText());
			} else {
				data.addValidationResult(true, 0, constraint.getRuleText());
			}
		}

		// Everything else is either Any, String, or unknown....we use a simple
		// length comparison
	}
}
