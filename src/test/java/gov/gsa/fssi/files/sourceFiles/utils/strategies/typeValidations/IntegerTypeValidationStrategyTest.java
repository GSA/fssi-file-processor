package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.TypeValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations.IntegerTypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.mockData.MockData;
import main.java.gov.gsa.fssi.helpers.mockData.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class IntegerTypeValidationStrategyTest {

	/**
	 * 
	 */
	@Test
	public void testNull() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("INTEGER", SchemaField.TYPE_INTEGER);
		Data data = MockData.make();
		
		context.validate(field, data);
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not catch error", 0, data.getMaxErrorLevel());
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not make failure", true, data.getStatus());		
	}

	/**
	 * 
	 */
	@Test
	public void testGoodInteger() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("INTEGER", SchemaField.TYPE_INTEGER);
		Data data = MockData.make("1234567");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not catch error",0, data.getMaxErrorLevel());
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not make failure", true, data.getStatus());		
	}
	
	/**
	 * 
	 */
	@Test
	public void testNumber() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("INTEGER", SchemaField.TYPE_INTEGER);
		Data data = MockData.make("123.45");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not catch error", 2, data.getMaxErrorLevel());
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not make failure", false, data.getStatus());		
	}	
	
	/**
	 * 
	 */
	@Test
	public void testBadInteger() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("INTEGER", SchemaField.TYPE_INTEGER);
		Data data = MockData.make("value");
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not catch error", 3, data.getMaxErrorLevel());
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not make failure", false, data.getStatus());		
	}
	
	
	
	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("INTEGER", SchemaField.TYPE_INTEGER);
		Data data = MockData.make("12345");
		data.setStatus(2);
		data.setMaxErrorLevel(2);
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not catch error", 2, data.getMaxErrorLevel());
		Assert.assertEquals("failure - IntegerTypeValidationStrategy did not make failure", false, data.getStatus());		
	}
	
	//TODO: Test Negative Numbers
	
}
