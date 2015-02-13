package main.java.gov.gsa.fssi.files.schemas.utils;

import java.io.File;

import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.utils.contexts.SchemaLoaderContext;
import main.java.gov.gsa.fssi.files.schemas.utils.strategies.loaders.XMLSchemaLoaderStrategy;
import main.java.gov.gsa.fssi.helpers.FileHelper;

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
		File file = FileHelper.getFile(directory+fileName);
		Schema schema = new Schema();
		
		if(file.isFile()){
			SchemaLoaderContext context = new SchemaLoaderContext();
			context.setSchemaLoaderStrategy(new XMLSchemaLoaderStrategy());
			SchemaValidator schemaValidator = new SchemaValidator();
			schema = new Schema(fileName);
	
			context.load(file, schema);
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
		}else{
			logger.error("Could not build Schema from file '{}', no fileName was set", directory + fileName);
			schema.setMaxErrorLevel(3);
			schema.addLoadStatusMessage(directory + fileName +" is not a File.");	
			return schema;
		}
	}

}
