package gov.gsa.fssi.files.sourceFiles.utils.validators;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

/**
 * @author DavidKLarrimore
 *
 */
public interface DataValidationStrategy {
	public void validate(SchemaField field, FieldConstraint constraint, Data data);
}
