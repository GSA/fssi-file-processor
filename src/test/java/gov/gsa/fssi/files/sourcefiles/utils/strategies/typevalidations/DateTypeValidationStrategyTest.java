package test.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.TypeValidationContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.DateTypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.mockdata.MockData;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class DateTypeValidationStrategyTest {

	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make();
		data.setStatus(2);
		data.setMaxErrorLevel(2);

		context.validate(field, data);
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 2,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testBadDateWithFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("REQUIRED",
				SchemaField.TYPE_DATE);
		field.setFormat("ddMMyyyy");
		Data data = MockData.make("1234567890");

		context.validate(field, data);
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 3,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				false, data.getStatus());
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
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 3,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testGoodDateWithFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("REQUIRED",
				SchemaField.TYPE_DATE);
		field.setFormat("dd-yyyy-MM");
		Data data = MockData.make("12-2014-05");

		context.validate(field, data);
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 0,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				true, data.getStatus());
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
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 0,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				true, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testMaxDateWithoutFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make("2175-11-12");

		context.validate(field, data);
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 2,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testMinDateWithoutFormat() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make("1924-12-12");

		context.validate(field, data);
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 2,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testNotDate() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("DATE", SchemaField.TYPE_DATE);
		Data data = MockData.make("value");

		context.validate(field, data);
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 3,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

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
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not catch error", 0,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy did not make failure",
				true, data.getStatus());
	}

	
	
	/**
	 * 
	 */
	@Test
	public void test2Digitvs1DigitFormats() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new DateTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("REQUIRED",
				SchemaField.TYPE_DATE);
		field.setFormat("Mddyyyy");
		Data data = MockData.make("01242014");

		context.validate(field, data);
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy", 3,data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - DateTypeValidationStrategy", false, data.getStatus());
	}
	
	
	
}
