package main.java.gov.gsa.fssi.files.schemas.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.helpers.DateHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class validates CSV File validator schemas You give it a Schema object,
 * and then it goes through it, validating it removing bad or duplicate fields,
 * constraints, and alias'. all of this ends with a completely rebuild schema
 * that is sent back to the requester.
 * 
 * @author davidlarrimore
 *
 */
public class SchemaValidator {
	private static final Logger logger = LoggerFactory
			.getLogger(SchemaValidator.class);

	/**
	 * @param string
	 * @return
	 */
	public boolean isValidLevel(String string) {
		// TODO: use java java.lang.reflect.Field to iterate through globals to
		// generate ArrayList
		List<String> validList = new ArrayList<String>();
		validList.add(FieldConstraint.LEVEL_FATAL);
		validList.add(FieldConstraint.LEVEL_ERROR);
		validList.add(FieldConstraint.LEVEL_WARNING);
		validList.add(FieldConstraint.LEVEL_DEBUG);

		for (String type : validList) {
			if (type.trim().equalsIgnoreCase(string.trim())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @param string
	 * @return
	 */
	public boolean isValidOption(String string) {
		// TODO: use java java.lang.reflect.Field to iterate through globals to
		// generate ArrayList
		List<String> validList = new ArrayList<String>();
		validList.add(FieldConstraint.OPTION_EFFECTIVEDATE);
		validList.add(FieldConstraint.OPTION_LEVEL);

		for (String type : validList) {
			if (type.trim().equalsIgnoreCase(string.trim())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @param string
	 * @return
	 */
	public boolean isValidType(String string) {
		// TODO: use java java.lang.reflect.Field to iterate through globals to
		List<String> validTypes = new ArrayList<String>();
		validTypes.add(FieldConstraint.TYPE_REQUIRED);
		validTypes.add(FieldConstraint.TYPE_MINLENGTH);
		validTypes.add(FieldConstraint.TYPE_MAXLENGTH);
		validTypes.add(FieldConstraint.TYPE_PATTERN);
		validTypes.add(FieldConstraint.TYPE_MINIMUM);
		validTypes.add(FieldConstraint.TYPE_MAXIMUM);

		for (String type : validTypes) {
			if (type.trim().equalsIgnoreCase(string.trim())) {
				return true;
			}
		}

		return false;
	}

	public void printAllSchemas(List<Schema> schemas) {
		for (Schema schema : schemas) {
			schema.printAll();
		}
	}

	/**
	 * This is the only public facing method. you send it a schema, it validates
	 * it and sends one back.
	 * 
	 * @param schema
	 * @return
	 */
	public void validate(Schema schema) {
		logger.info("Started schema validation for schema '{}'",
				schema.getName());

		if (schema.getName() == null || "".equals(schema.getName())) {
			logger.error("Schema in file '{}' does not have a name",schema.getFileName());
			schema.addValidatorStatusMessage("Schema in file '" + schema.getFileName() + "' does not have a name");
			schema.setMaxErrorLevel(3);
			schema.setValidatorStatus(false);
		}

		
		if (schema.getFields() == null || schema.getFields().isEmpty()) {
			logger.error("Schema '{}' in file '{}' does not have any fields",schema.getName(), schema.getFileName());
			schema.addValidatorStatusMessage("Schema in file '" + schema.getFileName() + "' does not have any fields");			
			schema.setMaxErrorLevel(3);
			schema.setValidatorStatus(false);
		}else{
			schema.setFields(validateFields(schema));			
		}
	}

	public void validateAll(List<Schema> schemas) {
		for (Schema schema : schemas) {
			validate(schema);
		}
	}

	/**
	 * This method checks naming for duplicate alias'
	 * 
	 * @param schemaField
	 */
	private List<String> validateFieldAliass(Schema newSchema,
			SchemaField newField) {
		List<String> newAliasList = new ArrayList<String>();
		for (String alias : newField.getAlias()) {
			validateFieldAlias(newSchema, newField, newAliasList, alias);
		}
		return newAliasList;
	}

	private void validateFieldAlias(Schema newSchema, SchemaField newField,
			List<String> newAliasList, String alias) {
		String newAlias = alias.toUpperCase().trim();
		if (newField.getName().equals(newAlias)) {
			logger.warn(
					"Alias '{}' is the same as the field itself, it can be ignored", newAlias);
		} else if (newAliasList.contains(newAlias)) {
			logger.warn(
					"Alias '{}' is a duplicate of an existing alias, it can be ignored", newAlias);
		} else {
			boolean dupeCheck = false;
			// we need to check other fields to see if the alias already
			// exists.
			// They need to be unique to prevent unintended processing
			for (SchemaField field : newSchema.getFields()) {
				if (field.getName().equals(newAlias)) {
					logger.warn("Alias '{}' is a duplicate of another fields name, it can be ignored",newAlias);
					dupeCheck = true;
				}
				if (field.getAlias().contains(newAlias)) {
					logger.warn("Alias '{}' is a duplicate of another alias in field '{}', it can be ignored",newAlias, field.getName());
					dupeCheck = true;
				}
			}

			if (dupeCheck == false) {
				newAliasList.add(newAlias);
			}
		}
	}

	private List<FieldConstraint> validateFieldConstraints(SchemaField newField) {
		List<FieldConstraint> fieldConstraints = new ArrayList<FieldConstraint>();
		for (FieldConstraint constraint : newField.getConstraints()) {
			FieldConstraint newConstraint = constraint;

			if (newConstraint.getType() == null
					|| newConstraint.getType().isEmpty()) {
				logger.warn("No constraint type provided, constraints requires a type. ignoring.");
			} else if (!isValidType(newConstraint.getType())) {
				logger.warn("'{}' is an invalid type, ignoring",
						newConstraint.getType());
			} else {

				// Checking default level value. Level cannot be null
				if (newConstraint.getLevelName() == null
						|| newConstraint.getLevelName().isEmpty()
						|| !isValidLevel(newConstraint.getLevelName())) {
					logger.warn(
							"Level in '{} - {}' is null, checking to options",
							newField.getName(), newConstraint.getType());

					// checking options
					if (newConstraint
							.getOptionValue(FieldConstraint.OPTION_LEVEL) != null
							&& isValidLevel(newConstraint
									.getOptionValue(FieldConstraint.OPTION_LEVEL))) {
						logger.info(
								"Found good level in '{} - {}' options, using that",
								newConstraint.getType(),
								newConstraint.getValue());
						newConstraint.setLevelName(newConstraint
								.getOptionValue(FieldConstraint.OPTION_LEVEL));
						newConstraint.setLevel(newConstraint
								.getOptionValue(FieldConstraint.OPTION_LEVEL));
					} else {
						logger.warn(
								"No good level in in '{} - {}' found, defaulting to error",
								newConstraint
										.getOptionValue(FieldConstraint.OPTION_LEVEL),
								newConstraint.getType());
						newConstraint.setLevelName(FieldConstraint.LEVEL_ERROR);
						newConstraint.setLevel(FieldConstraint.LEVEL_ERROR);
					}
				} else {
					logger.warn(
							"No good level found in in '{} - {}' , defaulting to error",
							newField.getName(), newConstraint.getType());
					newConstraint.setLevelName(FieldConstraint.LEVEL_ERROR);
					newConstraint.setLevel(FieldConstraint.LEVEL_ERROR);
				}

				// Checking default effectiveDate value
				if (newConstraint.getEffectiveDate() == null) {
					logger.info(
							"Effective Date in in '{} - {}' is null, checking to options",
							newField.getName(), newConstraint.getType());
					// checking options
					if (newConstraint
							.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE) != null) {
						Date newDate = DateHelper
								.getDate(
										newConstraint
												.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE),
										DateHelper.FORMAT_YYYY_MM_DD);
						if (newDate != null) {
							logger.info(
									"Found good effectiveDate in options, using that",
									newConstraint.getType());
							newConstraint.setEffectiveDate(newDate);
						} else {
							logger.error(
									"Could not convert date '{}' using yyyy-MM-dd format.",
									newConstraint
											.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE));
						}
					} else {
						logger.info("No date was provided in options either");
					}
				}

				fieldConstraints.add(newConstraint);
			}
		}
		return fieldConstraints;
	}

	/**
	 * This method checks naming for duplicate field naming
	 * 
	 * @param schema
	 */
	private List<SchemaField> validateFields(Schema schema) {
		Schema newSchema = new Schema();
		for (SchemaField field : schema.getFields()) {
			validateField(schema, newSchema, field);
		}

		return newSchema.getFields();
	}

	private void validateField(Schema schema, Schema newSchema,
			SchemaField field) {
		SchemaField newField = field;
		if (newField.getName() == null || newField.getName().isEmpty()) {
			logger.error("Field has no name, removing");
			schema.addValidatorStatusMessage("Schema '" + schema.getName() + "' has a field with no name");			
			schema.setMaxErrorLevel(3);
			schema.setValidatorStatus(false);
		} else if (newSchema.getFieldNames().contains(newField.getName())) {
			logger.error("Field '{}' is a duplicate",field.getName());
			schema.addValidatorStatusMessage("Schema '" + schema.getName() + "' has a duplicate field '" + field.getName() + "'");	
			schema.setMaxErrorLevel(3);
			schema.setValidatorStatus(false);
		} else {
			if (field.getAlias() != null && !field.getAlias().isEmpty()) {
				newField.setAlias(validateFieldAliass(newSchema, newField));
			}
			if (newField.getType() == null || newField.getType().isEmpty()) {
				logger.info("Setting default type 'ANY' for field '{}'",
						newField.getName());
				newField.setType(SchemaField.TYPE_ANY);
			}
			
			/**
			 * Added code to support type error level
			 */
			// checking options for valid type level
			if (newField.getTypeOptionValue(SchemaField.OPTION_LEVEL) != null
					&& isValidLevel(newField.getTypeOptionValue(SchemaField.OPTION_LEVEL))) {
				logger.info("Found good level in '{}' options, using that",newField.getName());
				newField.setTypeErrorLevel(newField.getTypeOptionValue(SchemaField.OPTION_LEVEL));
			} else {
				logger.warn("No good level in '{}' options, defaulting to '{}'",newField.getName());
				newField.setTypeErrorLevel(Config.DEFAULT_DEFAULT_ERROR_LEVEL);
			}
	
			if (newField.getConstraints() != null
					&& !newField.getConstraints().isEmpty()) {
				newField.setConstraints(validateFieldConstraints(newField));
			}

			// TODO: Validate format to make sure it is compatible with type
			newSchema.addField(newField);
		}
	}

}
