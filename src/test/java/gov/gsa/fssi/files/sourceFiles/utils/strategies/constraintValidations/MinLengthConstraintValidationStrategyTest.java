package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MinLengthConstraintValidationStrategy;
import main.java.gov.gsa.fssi.helpers.mockData.MockData;
import main.java.gov.gsa.fssi.helpers.mockData.MockFieldConstraint;
import main.java.gov.gsa.fssi.helpers.mockData.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class MinLengthConstraintValidationStrategyTest {

	/**
	 * This should test to make sure that the validator is passing strings with a length above maximum
	 */
	@Test
	public void testGreaterThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "4", 2);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MinLengthConstraintValidationStrategy did not catch error", fieldConstraint.getLevel(), data.getMaxErrorLevel());
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy did not pass", true, data.getStatus());					
	}
	
	
	/**
	 * This should test to make sure that the validator is failing strings with a length below maximum
	 */
	@Test
	public void testLessThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "6", 2);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getMaxErrorLevel());
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy did not make failure", false, data.getStatus());				
	}	

	
	/**
	 * This should test to make sure that the validator is failing strings with a length below maximum
	 */
	@Test
	public void testWarning() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "6", 1);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getMaxErrorLevel());
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy did not make failure", true, data.getStatus());				
	}		
	
	
	/**
	 * This should test to make sure that the validator is passing strings with the same length
	 */
	@Test
	public void testEqual() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "5", 2);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MinLengthConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getMaxErrorLevel());
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy did not pass", true, data.getStatus());				
	}	
	
	
	
	
	
	
}
