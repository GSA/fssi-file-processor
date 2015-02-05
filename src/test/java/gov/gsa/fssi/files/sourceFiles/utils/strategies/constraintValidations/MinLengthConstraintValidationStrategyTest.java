package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MinLengthConstraintValidationStrategy;

import org.junit.Assert;
import org.junit.Test;

import test.java.gov.gsa.fssi.mockData.MockData;
import test.java.gov.gsa.fssi.mockData.MockFieldConstraint;
import test.java.gov.gsa.fssi.mockData.MockSchemaField;

public class MinLengthConstraintValidationStrategyTest {

	/**
	 * This should test to make sure that the validator is passing strings with a length above maximum
	 */
	@Test
	public void testGreaterThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "4", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MinLengthConstraintValidationStrategy did not catch error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());					
	}
	
	
	/**
	 * This should test to make sure that the validator is failing strings with a length below maximum
	 */
	@Test
	public void testLessThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "6", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy did not make failure", File.STATUS_FAIL, data.getValidatorStatus());				
	}	

	/**
	 * This should test to make sure that the validator is passing strings with the same length
	 */
	@Test
	public void testEqual() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "5", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MinLengthConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - MinLengthConstraintValidationStrategy did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	
	
	
	
	
	
	
}
