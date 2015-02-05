package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.TypeValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations.DateTypeValidationStrategy;
import org.junit.Assert;
import org.junit.Test;

import test.java.gov.gsa.fssi.mockData.MockData;
import test.java.gov.gsa.fssi.mockData.MockSchemaField;

public class DateTypeValidationStrategyTest {

	/**
	 * 
	 */
	@Test
	public void testNull() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make();
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", File.STATUS_PASS, data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_PASS, data.getValidatorStatus());		
	}

	/**
	 * 
	 */
	@Test
	public void testGoodDateWithoutFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make("2014-05-25");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", File.STATUS_PASS, data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_PASS, data.getValidatorStatus());		
	}
	
	/**
	 * 
	 */
	@Test
	public void testBadDateWithoutFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make("432154123");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", File.STATUS_FATAL, data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}
	
	/**
	 * 
	 */
	@Test
	public void testMaxDateWithoutFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make("4321-54-123");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", File.STATUS_ERROR, data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}
	
	/**
	 * 
	 */
	@Test
	public void testMinDateWithoutFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make("1024-54-123");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", File.STATUS_ERROR, data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}
	
	/**
	 * 
	 */
	@Test
	public void testGoodDateWithFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("REQUIRED", SchemaField.TYPE_DATE);
		field.setFormat("dd-yyyy-MM");
		Data data = MockData.make("12-2014-05");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", File.STATUS_PASS, data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_PASS, data.getValidatorStatus());		
	}
	
	/**
	 * 
	 */
	@Test
	public void testBadDateWithFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("REQUIRED", SchemaField.TYPE_DATE);
		field.setFormat("ddMMyyyy");
		Data data = MockData.make("1234567890");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", File.STATUS_ERROR, data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}	
	
	
	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make();
		data.setValidatorStatus(File.STATUS_FAIL);
		data.setStatus(File.STATUS_ERROR);
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", File.STATUS_ERROR, data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}
	
}
