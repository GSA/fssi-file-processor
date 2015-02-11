package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface TypeValidationStrategy {
	Logger logger = LoggerFactory.getLogger(TypeValidationStrategy.class);

	void validate(SchemaField field, Data data);

	boolean isValid(SchemaField field, Data data);
}
