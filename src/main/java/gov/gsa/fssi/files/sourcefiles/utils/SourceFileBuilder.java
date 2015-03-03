package main.java.gov.gsa.fssi.files.sourcefiles.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The purpose of this class is to handle the automated processing of all source
 * files.
 *
 * @author davidlarrimore
 *
 */
public class SourceFileBuilder {
	static Logger logger = LoggerFactory.getLogger(SourceFileBuilder.class);

	/**
	 * The purpose of this function is just to prep file processing. We are not
	 * actually loading data yet
	 * 
	 * @param sourceFileDirectory
	 */
	public SourceFile build(String directory, String fileName,
			String exportMode, String providerMode, List<Schema> schemas, List<Provider> providers) {
		SourceFile sourceFile = new SourceFile(fileName);
		mapProviderToSourceFile(providers, sourceFile);

		if (sourceFile.getProvider() == null && Config.PROVIDER_MODE_STRICT.equalsIgnoreCase(providerMode)) {
			logger.error("No provider was found and provider mode was set to 'Strict' Mode. File cannot be processed");
			sourceFile.addLoadStatusMessage("No provider was found and provider mode was set to 'Strict' Mode. File cannot be processed");
			sourceFile.setMaxErrorLevel(3);
		}else if (sourceFile.getProvider() == null) {
			logger.error("Could not find Provider for file '{}'. Will use defaults", fileName);
		} else if (!sourceFile.getStatus()) {
			logger.error(
					"File is in error state, will not process schema activities",
					fileName);			
		} else {
			// Map Schema to SourceFile
			if (sourceFile.getStatus()
					&& sourceFile.getProvider().getSchemaName() != null
					&& !sourceFile.getProvider().getSchemaName().isEmpty()) {
				logger.warn("Attemping to map Schema to SourceFile '{}'",
						sourceFile.getFileName());
				mapSchemaToSourceFile(schemas, sourceFile);

			} else {
				logger.warn(
						"Provider '{}' for SourceFile '{}' does not have a Schema",
						sourceFile.getProvider().getProviderName(), fileName);
			}

			// Provider noted a schema, but couldn't find it
			if (sourceFile.getStatus() && sourceFile.getSchema() != null && sourceFile.getReportingPeriod() == null) {
				logger.error(
						"sourceFile '{}' noted schema '{}', but has no reporting period. Please add a valid reporting period",
						sourceFile .getFileName(), sourceFile.getSchema().getName());
				sourceFile.addLoadStatusMessage("SourceFile noted schema '"+ sourceFile.getSchema().getName()+"' but has no reporting period. Please add a valid reporting period to the file name");
				sourceFile.setMaxErrorLevel(3);
			}else if (sourceFile.getStatus() && sourceFile.getSchema() != null) {
				personalizeSourceFileSchema(sourceFile);
			} else if (sourceFile.getStatus() && sourceFile.getSchema() == null && sourceFile.getProvider().getSchemaName() != null && !sourceFile.getProvider().getSchemaName().isEmpty()) {
				logger.error(
						"Provider '{}' for sourceFile '{}' noted schema '{}', but it could not be found",
						sourceFile.getProvider().getProviderName(), sourceFile
								.getFileName(), sourceFile.getProvider()
								.getSchemaName());
				sourceFile.addLoadStatusMessage("Schema could not be found.");
				sourceFile.setMaxErrorLevel(3);
			} else if (sourceFile.getStatus()) {
				logger.error(
						"No schema for file '{}', ignoring Schema processing activities",
						fileName);
			}

		}

		// Load File
		if (sourceFile.getStatus()) {
			logger.info("Loading SourceFile '{}'", sourceFile.getFileName());
			sourceFile.load(directory);
			logger.info("Completed loading SourceFile '{}'",
					sourceFile.getFileName());
		}

		logger.info("File '{}' is in state '{}' and status '{}' after loading",
				sourceFile.getFileName(),
				File.getErrorLevelName(sourceFile.getMaxErrorLevel()),
				sourceFile.getStatusName());

		// Organize file based upon schema
		if (sourceFile.getStatus() && sourceFile.getSchema() != null
				&& sourceFile.getRecords() != null) {
			logger.info("Mapping SourceFile '{}' fields to Schema '{}'",
					sourceFile.getFileName(), sourceFile.getSchema().getName());
			mapSourceFileFieldsToSchema(sourceFile);
			logger.info("Completed Mapping");
			logger.info("Organizing SourceFile '{}'", sourceFile.getFileName());
			sourceFile.organize(exportMode);
			logger.info("Completed Organizing SourceFile '{}'",
					sourceFile.getFileName());
		}

		logger.info(
				"File '{}' is in state '{}' and status '{}' after organizing",
				sourceFile.getFileName(),
				File.getErrorLevelName(sourceFile.getMaxErrorLevel()),
				sourceFile.getStatusName());

		// Validate file based upon schema
		if (sourceFile.getStatus() && sourceFile.getSchema() != null
				&& sourceFile.getRecords() != null) {
			logger.info("Validating SourceFile '{}'", sourceFile.getFileName());
			sourceFile.validate();
			logger.info("Completed validating SourceFile '{}'",
					sourceFile.getFileName());
		}

		logger.info(
				"File '{}' is in state '{}' and status '{}' after validation",
				sourceFile.getFileName(),
				File.getErrorLevelName(sourceFile.getMaxErrorLevel()),
				sourceFile.getStatusName());
		return sourceFile;
	}

	/**
	 * @param providers
	 * @param sourceFile
	 */
	public void mapProviderToSourceFile(List<Provider> providers,
			SourceFile sourceFile) {
		if (sourceFile.getStatus()) {
			logger.info("Attempting to map Provider to file {}",
					sourceFile.getFileName());
			for (Provider provider : providers) {
				for (String fileNamePart : sourceFile.getFileNameParts()) {

					if (provider
							.getProviderIdentifier()
							.toUpperCase()
							.matches(
									fileNamePart.toUpperCase().trim()
											.toUpperCase())) {
						logger.info("Mapped provider {} - {} to file '{}'",
								provider.getProviderName(),
								provider.getProviderIdentifier(),
								sourceFile.getFileName());
						sourceFile.setProvider(provider);
						break;
					}
				}
			}
			
			if (sourceFile.getProvider() == null) {
				logger.warn("Could not find provider for file: '{}'",
						sourceFile.getFileName());
				sourceFile.setMaxErrorLevel(2);
				sourceFile.addLoadStatusMessage("Could not find provider");
			} else {
				logger.info("Mapped Provider '{}' successfully", sourceFile.getProvider().getProviderIdentifier());
			}
		}
	}

	/**
	 * @param schemas
	 * @param sourceFile
	 */
	public void mapSchemaToSourceFile(List<Schema> schemas,
			SourceFile sourceFile) {
		logger.info("Attempting to map Schema to file {}",
				sourceFile.getFileName());
		if (sourceFile.getStatus()) {
			for (Schema schema : schemas) {
				if (sourceFile.getProvider().getSchemaName()
						.equalsIgnoreCase(schema.getName())) {
					sourceFile.setSchema(schema);
				}
			}
			if (sourceFile.getSchema() == null) {
				logger.error("Could not find schema for file: '{}'",
						sourceFile.getFileName());
				sourceFile.addLoadStatusMessage("Could not find schema for file");
			}
		}
	}

	/**
	 * This method takes a populated sourcefile and maps the fields to the
	 * schema header indexes This allows us to easily organize and validate.
	 * 
	 * This method should only be called if the sourceFile has been loaded or at
	 * least has headers
	 * 
	 * @param sourceFile
	 */
	public void mapSourceFileFieldsToSchema(SourceFile sourceFile) {
		if (sourceFile.getSourceHeaders() != null
				|| sourceFile.getSchema() != null) {
			logger.info(
					"Atempting to map field names from File '{}' to Schema '{}'",
					sourceFile.getFileName(), sourceFile.getSchema().getName());
			for (SchemaField field : sourceFile.getSchema().getFields()) {
				List<String> aliasNames = new ArrayList<String>();
				aliasNames.add(field.getName()); 
				aliasNames.addAll(field.getAlias());
				Iterator<?> thisHeaderIterator = sourceFile.getSourceHeaders()
						.entrySet().iterator();
				while (thisHeaderIterator.hasNext()) {
					Map.Entry<Integer, String> thisHeaderPairs = (Map.Entry<Integer, String>) thisHeaderIterator
							.next();
					if (aliasNames.contains(thisHeaderPairs.getValue()
							.toUpperCase())) {
						logger.info(
								"Matched sourcFile field '{} - {}' with Schema field '{}'",
								thisHeaderPairs.getKey(),
								thisHeaderPairs.getValue(), field.getName());
						field.setHeaderIndex(thisHeaderPairs.getKey());
					}
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Printing Fields:");
				for (SchemaField field : sourceFile.getSchema().getFields()) {
					logger.debug("FieldName: '{}' headerIndex: '{}'",
							field.getName(), field.getHeaderIndex());
				}
			}
		} else {
			logger.info(
					"No schema or header found was found for file {}. Will not Map header indexes to schema fields",
					sourceFile.getFileName());
		}
	}

	/**
	 * This method is important to trim the fat from Schemas and personalize it
	 * for each file based upon its effective date. It compares the effective
	 * dates and only
	 * 
	 * @param effectiveDate
	 * @param schema
	 * @return
	 */
	public void personalizeSourceFileSchema(SourceFile sourceFile) {

		if (!sourceFile.getStatus()) {
			logger.error(
					"SourceFile '{}' is in error status, unable to personalize",
					sourceFile.getFileName());
		} else if (sourceFile.getSchema() == null) {
			logger.error(
					"SourceFile '{}' does not have a schema, unable to personalize",
					sourceFile.getFileName());
		} else if (sourceFile.getReportingPeriod() == null) {
			logger.error(
					"SourceFile '{}' does not have a reporting period, unable to personalize",
					sourceFile.getFileName());
		} else {
			logger.info("Personalizing schema '{}' to effectiveDate '{}'",
					sourceFile.getSchema().getName(),
					sourceFile.getReportingPeriod());
			Schema newSchema = sourceFile.getSchema();
			List<SchemaField> newFields = new ArrayList<SchemaField>();
			for (SchemaField field : sourceFile.getSchema().getFields()) {
				logger.info("Personalizing field '{}' to effectiveDate '{}'",
						field.getName(), sourceFile.getReportingPeriod());
				SchemaField newField = field;
				List<FieldConstraint> newConstraints = new ArrayList<FieldConstraint>();
				for (FieldConstraint constraint : newField.getConstraints()) {
					FieldConstraint newConstraint = constraint;
					logger.info("Analyzing constraint '{}' - '{}'",
							newConstraint.getType(),
							newConstraint.getEffectiveDate());
					boolean addNewConstraint = true;
					if (newConstraint.getEffectiveDate() == null) {
						logger.info(
								"Constraint '{}' does not have an effective date",
								constraint.getType());
					} else if (sourceFile.getReportingPeriod().compareTo(
							newConstraint.getEffectiveDate()) >= 0) {
						logger.warn(
								"The files effectiveDate '{}' is after the constraint '{}' effectiveDate '{}'",
								sourceFile.getReportingPeriod(),
								constraint.getType(),
								constraint.getEffectiveDate());
					} else if (sourceFile.getReportingPeriod().compareTo(
							newConstraint.getEffectiveDate()) < 0) {
						logger.info(
								"The files effectiveDate '{}' is before or equal to the constraint '{}' effectiveDate '{}'. This can be ignored.",
								sourceFile.getReportingPeriod(),
								constraint.getType(),
								constraint.getEffectiveDate());
						addNewConstraint = false;
					}
					logger.info("Checking for duplicate constraint",
							constraint.getType());
					if (addNewConstraint) {
						for (FieldConstraint constraintCheck : newConstraints) {
							if (constraintCheck.getType().equals(
									newConstraint.getType())) {
								logger.info(
										"found duplicate constraint, comparing dates",
										constraint.getType());
								if (newConstraint.getEffectiveDate() == null
										&& constraintCheck.getEffectiveDate() != null) {
									logger.info(
											"newConstraint '{}' does not have an effective date, but constraintCheck does. This can be ignored",
											constraint.getType());
									addNewConstraint = false;
								} else if (newConstraint.getEffectiveDate() != null
										&& constraintCheck.getEffectiveDate() == null) {
									logger.info(
											"newConstraint '{}' has an effective date, but constraintCheck does not. Switching",
											constraint.getType());
									newConstraints.remove(constraintCheck);
									newConstraints.add(newConstraint);
									addNewConstraint = false;
								} else if (newConstraint.getEffectiveDate()
										.compareTo(
												constraintCheck
														.getEffectiveDate()) <= 0) {
									logger.info(
											"newConstraint is after constraintCheck. switching",
											constraint.getType());
									newConstraints.remove(constraintCheck);
									newConstraints.add(newConstraint);
									addNewConstraint = false;
								} else {
									logger.info("newConstraints already has the most recent constraint");
									addNewConstraint = false;
								}
							}
						}

					}
					if (addNewConstraint) {
						newConstraints.add(newConstraint);
					}
				}
				newField.setConstraints(newConstraints);
				newFields.add(newField);
			}
			newSchema.setFields(newFields);
			sourceFile.setSchema(newSchema);
		}
	}

}
