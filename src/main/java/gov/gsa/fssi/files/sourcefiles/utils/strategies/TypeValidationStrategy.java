package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;

/**
 * @author DavidKLarrimore
 *
 */
public interface TypeValidationStrategy {
	boolean isValid(SchemaField field, Data data);
	void validate(SchemaField field, Data data);
}
