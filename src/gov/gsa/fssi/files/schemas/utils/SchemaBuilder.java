package gov.gsa.fssi.files.schemas.utils;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.utils.contexts.SchemaLoaderContext;
import gov.gsa.fssi.files.schemas.utils.strategies.loaders.XMLSchemaLoaderStrategy;
import gov.gsa.fssi.helpers.FileHelper;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This method builds a single Schema
 * 
 * @author davidlarrimore
 *
 */
public class SchemaBuilder {
	static Logger logger = LoggerFactory.getLogger(SchemaBuilder.class);
	static Config config = new Config();	  
	
	public Schema build(String fileName) {
		SchemaLoaderContext context = new SchemaLoaderContext();
		context.setSchemaLoaderStrategy(new XMLSchemaLoaderStrategy());
		SchemaValidator schemaValidator = new SchemaValidator();
		Schema schema = new Schema(fileName);
		
		context.load(schema);
		if(logger.isDebugEnabled()){
			logger.info("Printing '{}' Schema that has been loaded", schema.getName());
			schema.printAll();
		}
		
		schemaValidator.validate(schema);
		if(logger.isDebugEnabled()){
			logger.info("Printing '{}' Schema that has been validated", schema.getName());
			schema.printAll();
		}
		return schema;
	}
	
}
