package gov.gsa.fssi.files.schemas;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.files.ValidatorStatus;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;

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
		
		if(schema.getName() == null || schema.getName().equals("")){
			logger.error("Schema does not have a name");
			schema.setValidatorStatusError();
		}
		
		if(schema.getFields() == null || schema.getFields().isEmpty()){
			logger.warn("Schema '{}' does not have any fields", schema.getName());
			schema.setValidatorStatusLevel(ValidatorStatus.WARNING);
		}		
		
		deDuplicateFieldNames(schema);
		
		
		
		return schema;
	}


	/**
	 * This method checks naming for duplicate field naming
	 * @param schema
	 */
	private static void deDuplicateFieldNames(Schema schema) {
		for(SchemaField field:schema.getFields()){
			int dupeIndex = 0;
			for(SchemaField field2:schema.getFields()){
				if(field2.getName().equals(field.getName()) && !field2.equals(field)){
					dupeIndex = schema.getFields().indexOf(field2);
					logger.warn("Found duplicate field '{}' with index of '{}' in schema '{}'. Marking for deletion", dupeIndex, schema.getName());
				}
			}
			if(dupeIndex != 0){
				schema.removeField(dupeIndex);
			}	
		}
	}	


	/**
	 * This method checks naming for duplicate field naming
	 * @param schema
	 */
	private static void deDuplicateAliasNames(Schema schema) {
		// 
		for(SchemaField field:schema.getFields()){
			int dupeIndex = 0;
			for(SchemaField field2:schema.getFields()){
				if(field2.getName().equals(field.getName()) && !field2.equals(field)){
					dupeIndex = schema.getFields().indexOf(field2);
					logger.warn("Found duplicate field '{}' with index of '{}' in schema '{}'. Marking for deletion", dupeIndex, schema.getName());
				}
			}
			if(dupeIndex != 0){
				schema.removeField(dupeIndex);
			}	
		}
	}	

		/**
		 * @param newAlias
		 * @param field
		 * @return 
		 */
		private static boolean isDuplicateConstraintAlias(SchemaField field, String newAlias) {
			for(String alias: field.getAlias()){
				return (alias.equals(newAlias.trim().toUpperCase())? true:false);
			}
			return false;
		}


		/**
		 * Checks for a duplicate constraint based upon type and option effectivedate
		 * @param field
		 * @param newConstraint
		 * @return
		 */
		private static boolean isDuplicateConstraint(SchemaField field,FieldConstraint newConstraint) {
			for (FieldConstraint constraintCheck : field.getConstraints()) {
				if(newConstraint.getConstraintType().trim().toUpperCase().equals(constraintCheck.getConstraintType().trim().toUpperCase())){
					if(newConstraint.getOptions().containsKey(FieldConstraint.OPTION_EFFECTIVEDATE) && constraintCheck.getOptions().containsKey(FieldConstraint.OPTION_EFFECTIVEDATE)){
						return (newConstraint.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE) == constraintCheck.getOptionValue(FieldConstraint.OPTION_EFFECTIVEDATE)? true:false);
					}else if(!newConstraint.getOptions().containsKey(FieldConstraint.OPTION_EFFECTIVEDATE) && !constraintCheck.getOptions().containsKey(FieldConstraint.OPTION_EFFECTIVEDATE)){
						//logger.debug("Both constraints had no effective date, is duplicate");
						return true;
					}
				}
			}
			return false;
		}
		
		public static void printAllSchemas(ArrayList<Schema> schemas){
			for(Schema schema: schemas){
				schema.printAll();
			}
		}

}
