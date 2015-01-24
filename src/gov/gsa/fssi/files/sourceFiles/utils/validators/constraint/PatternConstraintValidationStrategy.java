package gov.gsa.fssi.files.sourceFiles.utils.validators.constraint;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

public class PatternConstraintValidationStrategy implements ConstraintValidationStrategy {

	@Override
	public void validate(SchemaField field, FieldConstraint constraint, Data data) {
		//String match = ".*"+data.getData()+".*";
		
		Pattern pattern = Pattern.compile(constraint.getValue());
		Matcher matcher = pattern.matcher(data.getData());
		if(!matcher.matches()) data.setStatus(constraint.getLevel());
		
//		if(!data.getStatus().matches(constraint.getValue())) data.setStatus(constraint.getLevel());
	}

}
