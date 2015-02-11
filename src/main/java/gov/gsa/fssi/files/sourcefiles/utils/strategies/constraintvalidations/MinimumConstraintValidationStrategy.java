package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.ConstraintValidationStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinimumConstraintValidationStrategy
		implements
			ConstraintValidationStrategy {
	private static final Logger logger = LoggerFactory
			.getLogger(MinimumConstraintValidationStrategy.class);
	@Override
	public void validate(SchemaField field, FieldConstraint constraint,
			Data data) {
		if (data != null) {
			if (data.getData() != null && !data.getData().isEmpty()) {
				if (field.getType().equals(SchemaField.TYPE_ANY)
						|| field.getType().equals(SchemaField.TYPE_STRING)) {
					if (data.getData().length() < Integer.parseInt(constraint
							.getValue()))
						data.addValidationResult(false, constraint.getLevel(),
								constraint.getRuleText());
				} else {
					data.addValidationResult(true, 0, constraint.getRuleText());
				}
			} else
				data.addValidationResult(true, 0, constraint.getRuleText());
		}
	}

	@Override
	public boolean isValid(SchemaField field, FieldConstraint constraint,
			Data data) {
		// TODO Auto-generated method stub
		return false;
	}
}
