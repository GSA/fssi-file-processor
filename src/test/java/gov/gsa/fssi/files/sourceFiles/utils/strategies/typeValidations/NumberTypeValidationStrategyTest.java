package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.TypeValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations.NumberTypeValidationStrategy;

import org.junit.Assert;
import org.junit.Test;

import test.java.gov.gsa.fssi.mockData.MockData;
import test.java.gov.gsa.fssi.mockData.MockSchemaField;

public class NumberTypeValidationStrategyTest {

	/**
	 * 
	 */
	@Test
	public void testNull() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new NumberTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER);
		Data data = MockData.make();
		
		context.validate(field, data);
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not catch error", 0, data.getMaxErrorLevel());
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not make failure", true, data.getStatus());		
	}

	/**
	 * 
	 */
	@Test
	public void test2DigitFloat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new NumberTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER);
		Data data = MockData.make("1234.54");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not catch error", 0, data.getMaxErrorLevel());
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not make failure", true, data.getStatus());		
	}
	
	

	/**
	 * 
	 */
	@Test
	public void test10DigitFloat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new NumberTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER);
		Data data = MockData.make("1234.541234677");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not catch error", 0, data.getMaxErrorLevel());
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not make failure", true, data.getStatus());		
	}	
	
	/**
	 * 
	 */
	@Test
	public void testInteger() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new NumberTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER);
		Data data = MockData.make("123");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not catch error", 0, data.getMaxErrorLevel());
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not make failure", true, data.getStatus());		
	}	
	
	/**
	 * 
	 */
	@Test
	public void testBadNumber() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new NumberTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER);
		Data data = MockData.make("value");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not catch error", 3, data.getMaxErrorLevel());
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not make failure", false, data.getStatus());		
	}
	
	
	
	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new NumberTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("NUMBER", SchemaField.TYPE_NUMBER);
		Data data = MockData.make("12345.345");
		data.setStatus(2);
		data.setMaxErrorLevel(2);
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not catch error", 2, data.getMaxErrorLevel());
		Assert.assertEquals("failure - NumberTypeValidationStrategy did not make failure", false, data.getStatus());		
	}
	
}
