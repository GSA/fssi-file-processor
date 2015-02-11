package main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.ConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations.MaxLengthConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations.MaximumConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations.MinLengthConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations.MinimumConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations.PatternConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations.RequiredConstraintValidationStrategy;

public class ConstraintValidationContext {
	private ConstraintValidationStrategy strategy;

	// this can be set at runtime by the application preferences
	public void setDataValidationStrategy(ConstraintValidationStrategy strategy) {
		this.strategy = strategy;
	}

	// this can be set at runtime by the application preferences
	public ConstraintValidationStrategy getDataValidationStrategy() {
		return this.strategy;
	}

	// use the strategy
	/**
	 * @param field
	 * @param constraint
	 * @param data
	 */
	public void validate(SchemaField field, FieldConstraint constraint,
			Data data) {
		if (this.getDataValidationStrategy() == null) {
			if (constraint.getType().equals(FieldConstraint.TYPE_MAXIMUM)) {
				this.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
			} else if (constraint.getType()
					.equals(FieldConstraint.TYPE_MINIMUM)) {
				this.setDataValidationStrategy(new MinimumConstraintValidationStrategy());
			} else if (constraint.getType().equals(
					FieldConstraint.TYPE_MAXLENGTH)) {
				this.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());
			} else if (constraint.getType().equals(
					FieldConstraint.TYPE_MINLENGTH)) {
				this.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
			} else if (constraint.getType()
					.equals(FieldConstraint.TYPE_PATTERN)) {
				this.setDataValidationStrategy(new PatternConstraintValidationStrategy());
			} else if (constraint.getType().equals(
					FieldConstraint.TYPE_REQUIRED)) {
				this.setDataValidationStrategy(new RequiredConstraintValidationStrategy());
			}
		}
		strategy.validate(field, constraint, data); // Validate Constraint
	}

	public boolean isValid(SchemaField field, FieldConstraint constraint,
			Data data) {
		return strategy.isValid(field, constraint, data); // is Valid
	}
}
