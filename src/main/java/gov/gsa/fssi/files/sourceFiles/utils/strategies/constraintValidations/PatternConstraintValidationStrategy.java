package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;

public class PatternConstraintValidationStrategy implements
		ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint,
			Data data) {
		if (data != null) {
			if (data.getData() != null && !data.getData().isEmpty()) {
				Pattern pattern = Pattern.compile(constraint.getValue(),
						Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(data.getData());
				if (!matcher.matches()) {
					data.addValidationResult(false, constraint.getLevel(),
							constraint.getRuleText());
				} else
					data.addValidationResult(true, 0, constraint.getRuleText());
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
