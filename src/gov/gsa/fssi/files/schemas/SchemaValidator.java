package gov.gsa.fssi.files.schemas;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.files.ValidatorStatus;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.helpers.DateHelper;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaValidator {
	static Logger logger = LoggerFactory.getLogger(SchemaValidator.class);
	static Config config = new Config();	    

	/**
	 * @param schema
	 * @return
	 */
	public static Schema validate(Schema schema) {
		logger.info("Started schema validation for schema '{}'", schema.getName());	
		Schema newSchema = schema;

		
		if(newSchema.getName() == null || schema.getName().isEmpty()){
			logger.error("Schema does not have a name");
			newSchema.setValidatorStatusLevel(ValidatorStatus.ERROR);
		}
		
		newSchema.setFields(validateFields(newSchema));
		
		if(newSchema.getFields() == null || schema.getFields().isEmpty()){
			logger.warn("Schema '{}' does not have any fields", schema.getName());
			newSchema.setValidatorStatusLevel(ValidatorStatus.WARNING);
		}	
		
		if(newSchema.getValidatorStatusLevel() == null){
			newSchema.setValidatorStatusLevel(ValidatorStatus.PASS);
		}
		
		return newSchema;
	}


	/**
	 * This method checks naming for duplicate field naming
	 * @param schema
	 */
	private static ArrayList<SchemaField> validateFields(Schema schema) {
		Schema newSchema = new Schema();
		for(SchemaField field:schema.getFields()){
			SchemaField newField = field;
			if(newField.getName() == null || newField.getName().isEmpty()){
				logger.warn("Field has no name, removing");
			}else if(newSchema.getFieldNames().contains(newField.getName())){
				logger.warn("Field '{}' is a duplicate, removing", field.getName());
			}else{
				if(field.getAlias() != null && !field.getAlias().isEmpty()){
					newField.setAlias(validateFieldAlias(newSchema, newField));
				}
				if(newField.getType() == null || newField.getType().isEmpty()){
					logger.info("Setting default type 'ANY' for field '{}'", newField.getName());
					newField.setType(SchemaField.TYPE_ANY);
				}
				
				if(newField.getConstraints() != null && !newField.getConstraints().isEmpty()){
					newField.setConstraints(validateFieldConstraints(newSchema, newField));	
				}
				
				//TODO: Validate Constraints
				//TODO: Validate format	to make sure it is compatible with type
				newSchema.addField(newField);
			}
		}
		
		return newSchema.getFields();
	}	


	/**
	 * This method checks naming for duplicate alias'
	 * @param schemaField
	 */
	private static ArrayList<String> validateFieldAlias(Schema newSchema, SchemaField newField) {
		ArrayList<String> newAliasList = new ArrayList<String>();
		for(String alias:newField.getAlias()){
			String newAlias = alias.toUpperCase().trim();
			if(newField.getName().equals(newAlias)){
				logger.warn("Alias '{}' is the same as the field itself, it can be ignored", newAlias);
			}else if(newAliasList.contains(newAlias)){
				logger.warn("Alias '{}' is a duplicate of an existing alias, it can be ignored", newAlias);
			}else{
				boolean dupeCheck = false;
				//we need to check other fields to see if the alias already exists.
				//They need to be unique to prevent unintended processing
				for(SchemaField field:newSchema.getFields()){
					if(field.getName().equals(newAlias)){
						logger.warn("Alias '{}' is a duplicate of another fields name, it can be ignored", newAlias);	
						dupeCheck = true;
					}if(field.getAlias().contains(newAlias)){
						logger.warn("Alias '{}' is a duplicate of another alias in field '{}', it can be ignored", newAlias, field.getName());	
						dupeCheck = true;	
					}
				}
				
				if (dupeCheck == false){
					newAliasList.add(newAlias);
				}
			}
		}
		return newAliasList;
	}	
	
	
	private static ArrayList<FieldConstraint> validateFieldConstraints(Schema newSchema, SchemaField newField) {
		ArrayList<FieldConstraint> fieldConstraints = new ArrayList<FieldConstraint>();
		for(FieldConstraint constraint:newField.getConstraints()){
			FieldConstraint newConstraint = constraint;
			
			
			if(newConstraint.getType() == null || newConstraint.getType().isEmpty()){
				logger.error("Constraint requires a type. ignoring");	
			}else if (!isValidType(newConstraint.getType())){
				logger.warn("'{}' is an invalid type, ignoring",newConstraint.getType());
			}else{
				newConstraint.setLevel(newConstraint.getOptionValue(FieldConstraint.OPTION_LEVEL));
				
				//Setting Effective Date
				if(newConstraint.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE) != null){
					newConstraint.setEffectiveDate(DateHelper.getDate(newConstraint.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE), DateHelper.FORMAT_YYYY_MM_DD));	
				}

				if(newConstraint.getLevel() == null || newConstraint.getLevel().isEmpty()){
					logger.warn("No level provided for constraint '{}', defaulting to error",newConstraint.getType());
					newConstraint.setLevel(FieldConstraint.LEVEL_ERROR);
				}
				
				//TODO:process Options
				
				if(!isValidOptionLevel(newConstraint.getLevel())){
					logger.warn("invalid level provided for constraint '{}', defaulting to error",newConstraint.getType());
					newConstraint.setLevel(FieldConstraint.LEVEL_ERROR);
				}
				
				
				fieldConstraints.add(newConstraint);
			}
			
		}
		return fieldConstraints;
	}

		
		public static void printAllSchemas(ArrayList<Schema> schemas){
			for(Schema schema: schemas){
				schema.printAll();
			}
		}

		
		
		/**
		 * @param string
		 * @return
		 */
		public static boolean isValidType(String string){
			//TODO: use java java.lang.reflect.Field to iterate through globals to generate ArrayList
			ArrayList<String> validTypes = new ArrayList<String>();
			validTypes.add(FieldConstraint.TYPE_REQUIRED);
			validTypes.add(FieldConstraint.TYPE_MINLENGTH);
			validTypes.add(FieldConstraint.TYPE_MAXLENGTH);
			validTypes.add(FieldConstraint.TYPE_PATTERN);
			validTypes.add(FieldConstraint.TYPE_MINIMUM);
			validTypes.add(FieldConstraint.TYPE_MAXIMUM);
			
			for(String type: validTypes){
				if (type.trim().toUpperCase().equals(string.trim().toUpperCase())){
					return true;
				}
			}
			
			return false;
		}
		
		/**
		 * @param string
		 * @return
		 */
		public static boolean isValidOption(String string){
			//TODO: use java java.lang.reflect.Field to iterate through globals to generate ArrayList
			ArrayList<String> validList = new ArrayList<String>();
			validList.add(FieldConstraint.OPTION_EFFECTIVEDATE);
			validList.add(FieldConstraint.OPTION_LEVEL);
			
			for(String type: validList){
				if (type.trim().toUpperCase().equals(string.trim().toUpperCase())){
					return true;
				}
			}
			
			return false;
		}	
		
		/**
		 * @param string
		 * @return
		 */
		public static boolean isValidOptionLevel(String string){
			//TODO: use java java.lang.reflect.Field to iterate through globals to generate ArrayList
			ArrayList<String> validList = new ArrayList<String>();
			validList.add(FieldConstraint.LEVEL_ERROR);
			validList.add(FieldConstraint.LEVEL_WARNING);
			validList.add(FieldConstraint.LEVEL_DEBUG);
			
			for(String type: validList){
				if (type.trim().toUpperCase().equals(string.trim().toUpperCase())){
					return true;
				}
			}
			
			return false;
		}
				
		
}
