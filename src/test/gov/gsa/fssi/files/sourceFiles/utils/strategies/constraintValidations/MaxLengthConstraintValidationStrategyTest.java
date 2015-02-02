package test.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import org.junit.Assert;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MaxLengthConstraintValidationStrategy;
import org.junit.Test;

import test.gov.gsa.fssi.mockData.MockData;
import test.gov.gsa.fssi.mockData.MockFieldConstraint;
import test.gov.gsa.fssi.mockData.MockSchemaField;

public class MaxLengthConstraintValidationStrategyTest {

	/**
	 * This should test to make sure that the validator is failing strings with a length above maximum
	 */
	@Test
	public void testGreaterThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "4", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validateConstraint(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - MaxLengthConstraintValidationStrategy did not catch error", fieldConstraint.getLevel(), data.getStatus());
	}
	
	
	/**
	 * This should test to make sure that the validator is passing strings with a length below maximum
	 */
	@Test
	public void testLessThan() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "6", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validateConstraint(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaxLengthConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatus());
	}	

	/**
	 * This should test to make sure that the validator is passing strings with a length below maximum
	 */
	@Test
	public void testEqual() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_MAXLENGTH, "5", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");
		
		context.validateConstraint(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - MaxLengthConstraintValidationStrategy caught error", fieldConstraint.getLevel(), data.getStatus());
	}	
	
	
	
	
	
	
}
