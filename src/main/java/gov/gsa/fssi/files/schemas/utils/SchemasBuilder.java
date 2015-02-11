package main.java.gov.gsa.fssi.files.schemas.utils;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This method builds an ArrayList of Schemas
 * 
 * @author davidlarrimore
 *
 */
public class SchemasBuilder {
	private static final Logger logger = LoggerFactory.getLogger(SchemasBuilder.class);

	public List<Schema> build(String directory) {
		logger.debug("Starting initializeSchemas('{}')", directory);

		List<Schema> schemas = new ArrayList<Schema>();
		List<String> fileNames = FileHelper.getFilesFromDirectory(
				directory, ".xml");
		for (String fileName : fileNames) {
			SchemaBuilder schemaBuilder = new SchemaBuilder();
			Schema schema = schemaBuilder.build(directory, fileName);

			if (!schema.getStatus()) { // We currently prevent invalid schemas
										// from being loaded
				logger.error(
						"Schema '{}' from file '{}' not being added to schemas because it is in error state",
						schema.getName(), schema.getFileName());
			} else if (isDuplicateSchemaName(schemas, schema)) { // duplicate
																	// schema
																	// names can
																	// screw up
																	// a lot.
				logger.error(
						"Schema '{}' from file '{}' is a duplicate, it will not be added",
						schema.getName(), schema.getFileName());
			} else {
				logger.info("Completed Schema setup. Added " + schemas.size()
						+ " Schemas");
				schemas.add(schema);
			}

		}
		return schemas;
	}

	private static boolean isDuplicateSchemaName(List<Schema> schemas,
			Schema newSchema) {
		for (Schema schema : schemas) {
			if (schema.getName().equals(newSchema.getName())) {
				logger.error(
						"Schema '{}' from file '{}' is a duplicate from file '{}",
						schema.getName(), newSchema.getFileName(),
						schema.getFileName());
				return true;
			}

		}
		return false;
	}

}
