package main.java.gov.gsa.fssi.files.sourcefiles.utils;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.ConstraintValidationContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.TypeValidationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The purpose of this class is to handle the automated processing of all source
 * files.
 *
 * @author davidlarrimore
 *
 */
public class SourceFileValidator {
	static Logger logger = LoggerFactory.getLogger(SourceFileValidator.class);

	public void validate(SourceFile sourceFile) {
		logger.info(
				"Starting sourceFile validation for file '{}'. it is in '{}' status",
				sourceFile.getFileName(), sourceFile.getStatusName());

		if (sourceFile.getSchema() == null) {
			logger.error("Cannot validate file '{}', it has no Schema",
					sourceFile.getFileName());
		} else if (!sourceFile.getStatus()) {
			logger.error("file '{}' is in '{}' state and cannot be processed",
					sourceFile.getFileName(),
					File.getErrorLevelName(sourceFile.getMaxErrorLevel()));
		} else {
			for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
				for (SchemaField field : sourceFile.getSchema().getFields()) {
					Data data = sourceFileRecord.getDataByHeaderIndex(field
							.getHeaderIndex());
					if (data != null) {
						TypeValidationContext typeContext = new TypeValidationContext();
						typeContext.validate(field, data);
						if (data.getMaxErrorLevel() < 2
								&& data.getStatus() == true) {
							for (FieldConstraint constraint : field
									.getConstraints()) {
								ConstraintValidationContext context = new ConstraintValidationContext();
								context.validate(field, constraint, data); // Validate
																			// Constraint
								if (logger.isDebugEnabled()
										&& data.getMaxErrorLevel() > 0) {
									logger.debug(
											"Row {} - Field '{}' validation {}: '{}' = {}, Value = '{}'",
											sourceFileRecord.getRowIndex(),
											field.getName(),
											constraint.getLevelName(),
											constraint.getType(),
											constraint.getValue(),
											data.getData());
								}
							}
						}
						sourceFileRecord.setMaxErrorLevel(data
								.getMaxErrorLevel());
						sourceFileRecord.setStatus(data.getMaxErrorLevel());
					}
				}
				if (logger.isDebugEnabled())
					logger.debug(
							"sourceFileRecord for Row '{}' is in '{}' State",
							sourceFileRecord.getRowIndex(), File
									.getErrorLevelName(sourceFileRecord
											.getMaxErrorLevel()));
				sourceFile.logError(sourceFileRecord.getMaxErrorLevel());
			}
		}
		if (logger.isDebugEnabled())
			logger.debug("File '{}' is in '{}' State",
					sourceFile.getFileName(),
					File.getErrorLevelName(sourceFile.getMaxErrorLevel()));
		if (!sourceFile.getStatus())
			logger.error("File is now in Fatal State");
	}

}