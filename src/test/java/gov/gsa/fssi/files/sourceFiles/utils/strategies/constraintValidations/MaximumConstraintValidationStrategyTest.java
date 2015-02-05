package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MaximumConstraintValidationStrategy;

import org.junit.Assert;
import org.junit.Test;

import test.java.gov.gsa.fssi.mockData.MockData;
import test.java.gov.gsa.fssi.mockData.MockFieldConstraint;
import test.java.gov.gsa.fssi.mockData.MockSchemaField;

public class MaximumConstraintValidationStrategyTest {

	/**
	 * This should test to make sure that the validator is failing strings with a length above maximum
	 */
	@Test
	public void testStringGreaterThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "4", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not catch error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not make failure", File.STATUS_FAIL, data.getValidatorStatus());
	}
	
	/**
	 * This should test to make sure that the validator is passing strings with a length below maximum
	 */
	@Test
	public void testStringLessThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "6", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaximumConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());		
	}	

	/**
	 * This should test to make sure that the validator is passing strings with a length below maximum
	 */
	@Test
	public void testStringEqual() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "5", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaximumConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	
	
	

	
	/**
	 * This should test to make sure that the validator is failing dates later than maximum
	 */
	@Test
	public void testDateGreaterThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "2015-01-01", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE, fieldConstraint);
		Data data = MockData.make("2015-01-02");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not catch error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}
	
	/**
	 * This should test to make sure that the validator is passing dates before maximum
	 */
	@Test
	public void testDateLessThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "2015-01-01", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE, fieldConstraint);
		Data data = MockData.make("2014-12-31");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaximumConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());			
	}	

	/**
	 * This should test to make sure that the validator is passing dates same as maximum
	 */
	@Test
	public void testDateEqual() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "2015-01-01", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE, fieldConstraint);
		Data data = MockData.make("2015-01-01");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaximumConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());			
	}	
	
	

	
	/**
	 * This should test to make sure that the validator is failing dates later than maximum
	 */
	@Test
	public void testNumberGreaterThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "999.99", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER, fieldConstraint);
		Data data = MockData.make("9999.99");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not catch error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not make failure", File.STATUS_FAIL, data.getValidatorStatus());	
	}
	
	/**
	 * This should test to make sure that the validator is passing dates before maximum
	 */
	@Test
	public void testNumberLessThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "999.99", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER, fieldConstraint);
		Data data = MockData.make("999.99");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaximumConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	

	/**
	 * This should test to make sure that the validator is passing dates same as maximum
	 */
	@Test
	public void testNumberEqual() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "999.99", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER, fieldConstraint);
		Data data = MockData.make("99.99");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaximumConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	
	
	

	
	/**
	 * This should test to make sure that the validator is failing dates later than maximum
	 */
	@Test
	public void testIntegerGreaterThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "999", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_INTEGER, fieldConstraint);
		Data data = MockData.make("9999");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not catch error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not make failure", File.STATUS_FAIL, data.getValidatorStatus());			
	}
	
	/**
	 * This should test to make sure that the validator is passing dates before maximum
	 */
	@Test
	public void testIntegerLessThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "999", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_INTEGER, fieldConstraint);
		Data data = MockData.make("999");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaximumConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	

	/**
	 * This should test to make sure that the validator is passing dates same as maximum
	 */
	@Test
	public void testIntegerEqual() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXIMUM, "999", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_INTEGER, fieldConstraint);
		Data data = MockData.make("99");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaximumConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MaximumConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	
				
	
	
			
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
