package test.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.ConstraintValidationContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations.PatternConstraintValidationStrategy;
import main.java.gov.gsa.fssi.helpers.mockdata.MockData;
import main.java.gov.gsa.fssi.helpers.mockdata.MockFieldConstraint;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchemaField;

import org.junit.Assert;
import org.junit.Test;

public class PatternConstraintValidationStrategyTest {

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testNull() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_PATTERN, "Yes|No", 2);
		SchemaField field = MockSchemaField.make("PATTERN",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make();

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest did not catch error",
				0, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest did not make failure",
				true, data.getStatus());
	}

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testOrPatternExactMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_PATTERN, "Yes|No", 2);
		SchemaField field = MockSchemaField.make("PATTERN",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("Yes");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest caughtError",
				0, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest did not pass",
				true, data.getStatus());
	}

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testOrPatterMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_PATTERN, "Yes|No", 2);
		SchemaField field = MockSchemaField.make("PATTERN",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("YES");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest caughtError",
				0, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest did not pass",
				true, data.getStatus());
	}

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testOrPatterNoMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_PATTERN, "Yes|No", 2);
		SchemaField field = MockSchemaField.make("PATTERN",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("YESS");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest caughtError",
				fieldConstraint.getLevel(), data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest did not pass",
				false, data.getStatus());
	}

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testREGEXPatterMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_PATTERN, "([A-Z])\\w+", 2);
		SchemaField field = MockSchemaField.make("PATTERN",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("YES");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest caughtError",
				0, data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest did not pass",
				true, data.getStatus());
	}

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testREGEXPatterNoMatch() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_PATTERN, "([A-Z])\\w+", 2);
		SchemaField field = MockSchemaField.make("PATTERN",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("123345");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest caughtError",
				fieldConstraint.getLevel(), data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest did not pass",
				false, data.getStatus());
	}

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void testREGEXPatterWarning() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_PATTERN, "([A-Z])\\w+", 1);
		SchemaField field = MockSchemaField.make("PATTERN",
				SchemaField.TYPE_STRING, fieldConstraint);
		Data data = MockData.make("123345");

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest caughtError",
				fieldConstraint.getLevel(), data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - PatternConstraintValidationStrategyTest did not pass",
				true, data.getStatus());
	}

	/**
	 * 
	 */
	@Test
	public void testAlreadyFailed() {
		ConstraintValidationContext context = new ConstraintValidationContext();
		context.setDataValidationStrategy(new PatternConstraintValidationStrategy());

		FieldConstraint fieldConstraint = MockFieldConstraint.make(
				FieldConstraint.TYPE_PATTERN, "true", 2);
		SchemaField field = MockSchemaField.make("ANY", SchemaField.TYPE_ANY);
		Data data = MockData.make();
		data.setStatus(2);
		data.setMaxErrorLevel(2);

		context.validate(field, fieldConstraint, data);
		// data.setStatus(2);
		Assert.assertEquals(
				"failure - AnyTypeValidationStrategy did not catch error", 2,
				data.getMaxErrorLevel());
		Assert.assertEquals(
				"failure - AnyTypeValidationStrategy did not make failure",
				false, data.getStatus());
	}

}
