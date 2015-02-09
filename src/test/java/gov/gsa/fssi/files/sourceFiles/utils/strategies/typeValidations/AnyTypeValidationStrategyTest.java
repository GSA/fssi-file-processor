package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.TypeValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations.AnyTypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.mockData.MockData;
import main.java.gov.gsa.fssi.helpers.mockData.MockSchemaField;

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
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not catch error", 0, data.getMaxErrorLevel());
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not make failure", true, data.getStatus());		
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
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not catch error", 2, data.getMaxErrorLevel());
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not make failure", false, data.getStatus());		
	}
		
	
}
