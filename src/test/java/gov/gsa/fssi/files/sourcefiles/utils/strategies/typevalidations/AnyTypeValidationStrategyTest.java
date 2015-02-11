package test.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.TypeValidationContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.AnyTypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.mockdata.MockData;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class AnyTypeValidationStrategyTest {

	/**
	 * 
	 */
	@Test
	public void testNull() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new AnyTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("ANY", SchemaField.TYPE_ANY);
		Data data = MockData.make();

		context.validate(field, data);
		Assert.assertEquals(
				"failure - AnyTypeValidationStrategy did not catch error", 0,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - AnyTypeValidationStrategy did not make failure",
				true, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new AnyTypeValidationStrategy());

		SchemaField field = MockSchemaField.make("ANY", SchemaField.TYPE_ANY);
		Data data = MockData.make("value");
		data.setStatus(2);
		data.setMaxErrorLevel(2);

		context.validate(field, data);
		Assert.assertEquals(
				"failure - AnyTypeValidationStrategy did not catch error", 2,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - AnyTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

}
