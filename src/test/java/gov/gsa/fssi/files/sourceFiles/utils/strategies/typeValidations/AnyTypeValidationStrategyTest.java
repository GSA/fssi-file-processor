package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.TypeValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.typeValidations.AnyTypeValidationStrategy;
import org.junit.Assert;
import org.junit.Test;

import test.java.gov.gsa.fssi.mockData.MockData;
import test.java.gov.gsa.fssi.mockData.MockSchemaField;

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
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not catch error", File.STATUS_PASS, data.getStatusLevel());
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not make failure", File.STATUS_PASS, data.getValidatorStatus());		
	}

	
	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		TypeValidationContext context = new TypeValidationContext();
		context.setTypeValidationStrategy(new AnyTypeValidationStrategy());
		
		SchemaField field = MockSchemaField.make("ANY", SchemaField.TYPE_ANY);
		Data data = MockData.make();
		data.setValidatorStatus(File.STATUS_FAIL);
		data.setStatusLevel(File.STATUS_ERROR);
		
		context.validate(field, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not catch error", File.STATUS_ERROR, data.getStatusLevel());
		Assert.assertEquals("failure - AnyTypeValidationStrategy did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}
		
	
}
