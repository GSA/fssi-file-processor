package gov.gsa.fssi.files.sourceFiles.utils.strategies;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

/**
 * @author DavidKLarrimore
 *
 */
public interface TypeValidationStrategy {
	public void validate(SchemaField field, Data data);
	public boolean isValid(SchemaField field, Data data);
}
