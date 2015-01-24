package gov.gsa.fssi.files.sourceFiles.utils.validators.constraint;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

/**
 * @author DavidKLarrimore
 *
 */
public interface ConstraintValidationStrategy {
	public void validate(SchemaField field, FieldConstraint constraint, Data data);
}
