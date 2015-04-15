package test.java.gov.gsa.fssi.schemas;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.schemas.utils.SchemaValidator;
import main.java.gov.gsa.fssi.helpers.mockdata.MockFieldConstraint;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchema;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class SchemaValidatorTest {
	Config config = new Config("./testfiles/", "config.properties");

	/**
	 *
	 */
	@Test
	public void noNameOrFields() {
		SchemaValidator schemaValidator = new SchemaValidator();
		Schema schema = MockSchema.make("TEST1");
		schemaValidator.validate(schema);
		Assert.assertEquals("failure - SchemaValidatorTest", false, schema.getStatus());
		Assert.assertEquals("failure - SchemaValidatorTest", false, schema.getValidatorStatus());
		Assert.assertEquals("failure - SchemaValidatorTest", 1, schema.getValidatorStatusMessages().size());			
	}	
	
	
	/**
	 * 
	 */
	@Test
	public void basicSchema() {
		SchemaValidator schemaValidator = new SchemaValidator();
		FieldConstraint constraint = MockFieldConstraint.make("required", "true");
		SchemaField field = MockSchemaField.make("TEST", "string", constraint);
		Schema schema = MockSchema.make("TEST1", field);
		schemaValidator.validate(schema);
		Assert.assertEquals("failure - SchemaValidatorTest", true,schema.getStatus());
		Assert.assertEquals("failure - SchemaValidatorTest", true,schema.getValidatorStatus());
	}
	
	
	
	/**
	 * 
	 */
	@Test
	public void noFieldNamex1() {
		SchemaValidator schemaValidator = new SchemaValidator();
		FieldConstraint constraint = MockFieldConstraint.make("required", "true");
		SchemaField field = MockSchemaField.make("", "string", constraint);
		
		Schema schema = MockSchema.make("TEST1", field);
		schemaValidator.validate(schema);
		Assert.assertEquals("failure - SchemaValidatorTest", false,schema.getStatus());
		Assert.assertEquals("failure - SchemaValidatorTest", false,schema.getValidatorStatus());
		Assert.assertEquals("failure - SchemaValidatorTest", 1, schema.getValidatorStatusMessages().size());			
	}

	/**
	 * 
	 */
	@Test
	public void noFieldNamexn() {
		SchemaValidator schemaValidator = new SchemaValidator();
		FieldConstraint constraint = MockFieldConstraint.make("required", "true");
		List<SchemaField> fields = new ArrayList<SchemaField>();
		
		fields.add(MockSchemaField.make("", "string", constraint));
		fields.add(MockSchemaField.make("TEST", "string", constraint));	
		
		Schema schema = MockSchema.make("TEST1", fields);
		schemaValidator.validate(schema);
		Assert.assertEquals("failure - SchemaValidatorTest", false, schema.getStatus());
		Assert.assertEquals("failure - SchemaValidatorTest", false, schema.getValidatorStatus());
		Assert.assertEquals("failure - SchemaValidatorTest", 1, schema.getValidatorStatusMessages().size());			
	}	
	
	
	/**
	 * Fail on Duplicate fields
	 */
	@Test
	public void duplicateFieldNames() {
		SchemaValidator schemaValidator = new SchemaValidator();
		FieldConstraint constraint = MockFieldConstraint.make("required", "true");
		List<SchemaField> fields = new ArrayList<SchemaField>();
		
		fields.add(MockSchemaField.make("TEST", "string", constraint));
		fields.add(MockSchemaField.make("TEST", "number", constraint));	
		
		Schema schema = MockSchema.make("TEST1", fields);
		schemaValidator.validate(schema);
		
		Assert.assertEquals("failure - SchemaValidatorTest", false, schema.getStatus());		
		Assert.assertEquals("failure - SchemaValidatorTest", 1, schema.getFields().size());
		Assert.assertEquals("failure - SchemaValidatorTest", "string", schema.getField("TEST").getType());
		Assert.assertEquals("failure - SchemaValidatorTest", 1, schema.getValidatorStatusMessages().size());		
	}		
	

	/**
	 * 
	 */
	@Test
	public void noSchemaLevelProvided() {
		SchemaValidator schemaValidator = new SchemaValidator();
		FieldConstraint constraint = MockFieldConstraint.make("required", "true");
		SchemaField field = MockSchemaField.make("FIELD_1", "string", constraint);
		//field.setFormatErrorLevel(2);
		Schema schema = MockSchema.make("TEST1", field);
		schemaValidator.validate(schema);
		Assert.assertEquals("failure - SchemaValidatorTest", SchemaField.getTypeErrorLevel(Config.DEFAULT_DEFAULT_ERROR_LEVEL),schema.getField("FIELD_1").getTypeErrorLevel());			
	}	
	
	
	
	/**
	 * 
	 */
	@Test
	public void schemaLevelProvided() {
		SchemaValidator schemaValidator = new SchemaValidator();
		FieldConstraint constraint = MockFieldConstraint.make("required", "true");
		SchemaField field = MockSchemaField.make("FIELD_1", "string", constraint);
		field.addTypeOption(SchemaField.OPTION_LEVEL, FieldConstraint.LEVEL_ERROR);
		Schema schema = MockSchema.make("TEST1", field);
		schemaValidator.validate(schema);
		Assert.assertEquals("failure - SchemaValidatorTest", 2,schema.getField("FIELD_1").getTypeErrorLevel());			
	}	
}
