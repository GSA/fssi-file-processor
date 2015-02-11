package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.constraintvalidations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.ConstraintValidationStrategy;

public class PatternConstraintValidationStrategy implements
		ConstraintValidationStrategy {
	static final Logger logger = LoggerFactory.getLogger(PatternConstraintValidationStrategy.class);
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
