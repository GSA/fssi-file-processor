package test.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts.ConstraintValidationContext;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MaxLengthConstraintValidationStrategy;
import main.java.gov.gsa.fssi.helpers.mockData.MockData;
import main.java.gov.gsa.fssi.helpers.mockData.MockFieldConstraint;
import main.java.gov.gsa.fssi.helpers.mockData.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class MaxLengthConstraintValidationStrategyTest {

	/**
	 * This should test to make sure that the validator is failing strings with
	 * a length above maximum
	 */
	@Test
	public void testGreaterThanConstraint() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_MAXLENGTH, "4", 2);
		SchemaField field = MockSchemaField.make("NUMBER",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - MaxLengthConstraintValidationStrategy did not catch error",
				fieldConstraint.getLevel(), data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - MaxLengthConstraintValidationStrategy did not make failure",
				false, data.getStatus());
		Assert.assertEquals(
				"failure - MaxLengthConstraintValidationStrategy did not make failure",
				false, data.getValidationResult(0).getStatus());
	}

	/**
	 * This should test to make sure that the validator is passing strings with
	 * a length below maximum
	 */
	@Test
	public void testLessThanConstraint() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_MAXLENGTH, "6", 2);
		SchemaField field = MockSchemaField.make("NUMBER",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - MaxLengthConstraintValidationStrategy caught error",
				0, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - MaxLengthConstraintValidationStrategy did not make failure",
				true, data.getStatus());
	}

	/**
	 * This should test to make sure that the validator is passing strings with
	 * a length below maximum
	 */
	@Test
	public void testEqual() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_MAXLENGTH, "5", 2);
		SchemaField field = MockSchemaField.make("NUMBER",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("12345");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - MaxLengthConstraintValidationStrategy caught error",
				0, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - MaxLengthConstraintValidationStrategy did not make failure",
				true, data.getStatus());
		Assert.assertEquals(
				"failure - MaxLengthConstraintValidationStrategy did not make failure",
				true, data.getValidationResult(0).getStatus());
	}

}
