package gov.gsa.fssi.fileprocessor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.utils.ProviderValidator;
import gov.gsa.fssi.files.providers.utils.ProvidersBuilder;
import gov.gsa.fssi.files.providers.utils.contexts.ProviderLoaderContext;
import gov.gsa.fssi.files.providers.utils.strategies.loaders.ExcelProviderLoaderStrategy;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.utils.SchemaValidator;
import gov.gsa.fssi.files.schemas.utils.SchemasBuilder;
import gov.gsa.fssi.files.schemas.utils.contexts.SchemaLoaderContext;
import gov.gsa.fssi.files.schemas.utils.strategies.loaders.XMLSchemaLoaderStrategy;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.utils.SourceFileBuilder;
import gov.gsa.fssi.files.sourceFiles.utils.SourceFilesBuilder;
import gov.gsa.fssi.helpers.FileHelper;




/**
 * This is the main class for the FSSI File Processor Project.
 * 
 * @author David Larrimore
 * @version 0.1
 */
public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	static Config config = new Config();	    
	
	public static void main(String[] args) {
	    logger.info("Starting FSSI File Processor");
	    
	    logger.info("Building Providers");
	    ProvidersBuilder providersBuilder = new ProvidersBuilder();
	    ArrayList<Provider> providers = providersBuilder.build();
	    printAllProviders(providers);
	    
	    logger.info("Building Schemas");
	    SchemasBuilder schemasBuilder = new SchemasBuilder();
		ArrayList<Schema> schemas = schemasBuilder.build();
		printAllSchemas(schemas);
		
		for (String fileName : FileHelper.getFilesFromDirectory(config.getProperty(Config.SOURCEFILES_DIRECTORY), ".csv")) {
	    	SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
	    	SourceFile sourceFile = sourceFileBuilder.build(fileName, schemas, providers);   
	    	if(sourceFile != null && !sourceFile.getStatusLevel().equals(SourceFile.STATUS_ERROR)){
	    		sourceFile.export();
	    	}
		}
		
	    logger.info("Completed FSSI File Processor");	
	}

	public static void printAllProviders(ArrayList<Provider> providers){
		for(Provider provider: providers){
			provider.print();
		}
	}
	
	
	public static void printAllSchemas(ArrayList<Schema> schemas){
		for(Schema schema: schemas){
			schema.printAll();
		}
	}
	
}
