package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface TypeValidationStrategy {
	Logger logger = LoggerFactory.getLogger(TypeValidationStrategy.class);
	Config config = new Config();	
	void validate(SchemaField field, Data data);
	boolean isValid(SchemaField field, Data data);
}
