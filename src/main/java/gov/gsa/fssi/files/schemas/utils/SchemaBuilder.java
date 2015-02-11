package main.java.gov.gsa.fssi.files.schemas.utils;

import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.utils.contexts.SchemaLoaderContext;
import main.java.gov.gsa.fssi.files.schemas.utils.strategies.loaders.XMLSchemaLoaderStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This method builds a single Schema
 * 
 * @author davidlarrimore
 *
 */
public class SchemaBuilder {
	private static final Logger logger = LoggerFactory
			.getLogger(SchemaBuilder.class);

	public Schema build(String directory, String fileName) {
		SchemaLoaderContext context = new SchemaLoaderContext();
		context.setSchemaLoaderStrategy(new XMLSchemaLoaderStrategy());
		SchemaValidator schemaValidator = new SchemaValidator();
		Schema schema = new Schema(fileName);

		context.load(directory, schema);
		if (logger.isDebugEnabled()) {
			logger.info("Printing '{}' Schema that has been loaded",
					schema.getName());
			schema.printAll();
		}

		schemaValidator.validate(schema);
		if (logger.isDebugEnabled()) {
			logger.info("Printing '{}' Schema that has been validated",
					schema.getName());
			schema.printAll();
		}
		return schema;
	}

}
