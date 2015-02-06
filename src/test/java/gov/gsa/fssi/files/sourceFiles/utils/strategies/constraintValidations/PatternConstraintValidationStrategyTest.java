package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.PatternConstraintValidationStrategy;
import org.junit.Assert;
import org.junit.Test;

import test.java.gov.gsa.fssi.mockData.MockData;
import test.java.gov.gsa.fssi.mockData.MockFieldConstraint;
import test.java.gov.gsa.fssi.mockData.MockSchemaField;

public class PatternConstraintValidationStrategyTest {

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testNull() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_PATTERN, "Yes|No", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("PATTERN", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make();
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest did not catch error",File.STATUS_PASS, data.getStatusLevel());
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest did not make failure", File.STATUS_PASS, data.getValidatorStatus());		
	}

	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testOrPatternExactMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_PATTERN, "Yes|No", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("PATTERN", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("Yes");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest caughtError", File.STATUS_PASS, data.getStatusLevel());
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	
	
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testOrPatterMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_PATTERN, "Yes|No", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("PATTERN", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("YES");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest caughtError", File.STATUS_PASS, data.getStatusLevel());
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	
	
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testOrPatterNoMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_PATTERN, "Yes|No", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("PATTERN", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("YESS");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest caughtError", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest did not pass", File.STATUS_FAIL, data.getValidatorStatus());				
	}	
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testREGEXPatterMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_PATTERN, "([A-Z])\\w+", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("PATTERN", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("YES");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest caughtError", File.STATUS_PASS, data.getStatusLevel());
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}		

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testREGEXPatterNoMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_PATTERN, "([A-Z])\\w+", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("PATTERN", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("123345");
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest caughtError", fieldConstraint.getLevel(), data.getStatusLevel());
		Assert.assertEquals("failure - PatternConstraintValidationStrategyTest did not pass", File.STATUS_FAIL, data.getValidatorStatus());				
	}		
	
	
	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_PATTERN, "true", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("ANY", SchemaField.TYPE_ANY);
		Data data = MockData.make();
		data.setValidatorStatus(File.STATUS_FAIL);
		data.setStatusLevel(File.STATUS_ERROR);
		
		context.validate(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not catch error", File.STATUS_ERROR, data.getStatusLevel());
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}

}
