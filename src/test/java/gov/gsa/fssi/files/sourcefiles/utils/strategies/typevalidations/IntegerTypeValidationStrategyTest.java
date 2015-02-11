package test.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.TypeValidationContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.IntegerTypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.mockdata.MockData;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchemaField;

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

		SchemaField field = MockSchemaField.make("INTEGER",
				SchemaField.TYPE_INTEGER);
		Data data = MockData.make();

		context.validate(field, data);
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not catch error",
				0, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not make failure",
				true, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testGoodInteger() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("INTEGER",
				SchemaField.TYPE_INTEGER);
		Data data = MockData.make("1234567");

		context.validate(field, data);
		// data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not catch error",
				0, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not make failure",
				true, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testNumber() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("INTEGER",
				SchemaField.TYPE_INTEGER);
		Data data = MockData.make("123.45");

		context.validate(field, data);
		// data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not catch error",
				2, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testBadInteger() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("INTEGER",
				SchemaField.TYPE_INTEGER);
		Data data = MockData.make("value");

		context.validate(field, data);
		// data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not catch error",
				3, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new IntegerTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("INTEGER",
				SchemaField.TYPE_INTEGER);
		Data data = MockData.make("12345");
		data.setStatus(2);
		data.setMaxErrorLevel(2);

		context.validate(field, data);
		// data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not catch error",
				2, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - IntegerTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

	// TODO: Test Negative Numbers

}
