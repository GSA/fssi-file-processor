package test.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import org.junit.Assert;

import gov.gsa.fssi.files.File;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.RequiredConstraintValidationStrategy;

import org.junit.Test;

import test.gov.gsa.fssi.mockData.MockData;
import test.gov.gsa.fssi.mockData.MockFieldConstraint;
import test.gov.gsa.fssi.mockData.MockSchemaField;

public class RequiredConstraintValidationStrategyTest {

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testRequiredWhenNull() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new RequiredConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_REQUIRED, "true", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("REQUIRED", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make();
		
		context.validateConstraint(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", fieldConstraint.getLevel(), data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_FAIL, data.getValidatorStatus());		
	}

	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testRequiredWhenEmpty() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new RequiredConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_REQUIRED, "true", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("REQUIRED", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("");
		
		context.validateConstraint(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not catch error", fieldConstraint.getLevel(), data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not make failure", File.STATUS_FAIL, data.getValidatorStatus());			
	}	
	
	
	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testRequiredWhenPopulated() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new RequiredConstraintValidationStrategy());
		
		FieldConstraint fieldConstraint = MockFieldConstraint.make(FieldConstraint.TYPE_REQUIRED, "true", FieldConstraint.LEVEL_ERROR);	
		SchemaField field = MockSchemaField.make("REQUIRED", SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("Value");
		
		context.validateConstraint(field, fieldConstraint, data);
		//data.setStatus(FieldConstraint.LEVEL_ERROR);
		Assert.assertNotEquals("failure - RequiredConstraintValidationStrategyTest caughtError", fieldConstraint.getLevel(), data.getStatus());
		Assert.assertEquals("failure - RequiredConstraintValidationStrategyTest did not pass", File.STATUS_PASS, data.getValidatorStatus());				
	}	

}
