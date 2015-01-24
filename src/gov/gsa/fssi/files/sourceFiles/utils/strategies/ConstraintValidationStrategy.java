package gov.gsa.fssi.files.sourceFiles.utils.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;

/**
 * @author DavidKLarrimore
 *
 */
public interface ConstraintValidationStrategy {
	static Logger logger = LoggerFactory.getLogger(ConstraintValidationStrategy.class);
	static Config config = new Config();	
	public void validate(SchemaField field, FieldConstraint constraint, Data data);
	public boolean isValid(SchemaField field, FieldConstraint constraint, Data data);
}
