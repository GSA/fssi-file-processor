package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;

/**
 * @author DavidKLarrimore
 *
 */
public interface ConstraintValidationStrategy {
	boolean isValid(SchemaField field, FieldConstraint constraint, Data data);

	void validate(SchemaField field, FieldConstraint constraint, Data data);
}
