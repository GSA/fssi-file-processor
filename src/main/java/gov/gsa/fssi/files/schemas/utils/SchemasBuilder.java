package main.java.gov.gsa.fssi.files.schemas.utils;

import java.util.ArrayList;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.utils.contexts.SchemaLoaderContext;
import main.java.gov.gsa.fssi.files.schemas.utils.strategies.loaders.XMLSchemaLoaderStrategy;
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
	static Logger logger = LoggerFactory.getLogger(SchemasBuilder.class);
	static Config config = new Config();	  
	
	public ArrayList<Schema> build() {
	    logger.debug("Starting initializeSchemas('{}')", config.getProperty(Config.SCHEMAS_DIRECTORY));		
		
	    ArrayList<Schema> schemas = new ArrayList<Schema>();	
	    ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(config.getProperty(Config.SCHEMAS_DIRECTORY), ".xml");
		for (String fileName : fileNames) {
			SchemaBuilder schemaBuilder = new SchemaBuilder();
			Schema schema = schemaBuilder.build(fileName);
			
			if(schema.getStatusLevel() != null && schema.getStatusLevel().equals(Schema.STATUS_ERROR)){ //We currently prevent invalid schemas from being loaded
				logger.error("Schema '{}' from file '{}' not being added to schemas because it is in error state", schema.getName(), schema.getFileName());
			}else if(isDuplicateSchemaName(schemas, schema)){ //duplicate schema names can screw up a lot.
				logger.error("Schema '{}' from file '{}' is a duplicate, it will not be added", schema.getName(), schema.getFileName());
			}else{
				logger.info("Completed Schema setup. Added " + schemas.size() + " Schemas");	
				schemas.add(schema);				
			}
			
		}
		return schemas;		
	}
	

	private static boolean isDuplicateSchemaName(ArrayList<Schema> schemas, Schema newSchema){
		for(Schema schema: schemas){
			if(schema.getName().equals(newSchema.getName())){
				logger.error("Schema '{}' from file '{}' is a duplicate from file '{}", schema.getName(), newSchema.getFileName(), schema.getFileName());
				return true;
			}
				
		}
		return false;
	}
	
}
