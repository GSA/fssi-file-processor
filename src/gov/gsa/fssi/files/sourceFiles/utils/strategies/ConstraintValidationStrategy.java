package gov.gsa.fssi.files.sourceFiles.utils.strategies;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

/**
 * @author DavidKLarrimore
 *
 */
public interface ConstraintValidationStrategy {
	public void validate(SchemaField field, FieldConstraint constraint, Data data);
	public boolean isValid(SchemaField field, FieldConstraint constraint, Data data);
}
