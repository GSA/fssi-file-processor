package gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;

public class PatternConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		//String match = ".*"+data.getData()+".*";
		
		Pattern pattern = Pattern.compile(constraint.getValue(), Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(data.getData());
		if(!matcher.matches()) data.setStatus(constraint.getLevel());
		
		//if(!data.getStatus().matches(constraint.getValue())) data.setStatus(constraint.getLevel());
	}

	@Override
	public boolean isValid(SchemaField field, FieldConstraint constraint,
			Data data) {
		// TODO Auto-generated method stub
		return false;
	}

}
