package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface ConstraintValidationStrategy {
	Logger logger = LoggerFactory.getLogger(ConstraintValidationStrategy.class);

	void validate(SchemaField field, FieldConstraint constraint, Data data);

	boolean isValid(SchemaField field, FieldConstraint constraint, Data data);
}
