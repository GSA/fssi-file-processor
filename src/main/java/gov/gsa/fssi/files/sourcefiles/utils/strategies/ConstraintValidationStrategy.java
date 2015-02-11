package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;

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
