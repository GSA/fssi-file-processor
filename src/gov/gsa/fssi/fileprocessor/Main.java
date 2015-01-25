package gov.gsa.fssi.fileprocessor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.utils.ProviderValidator;
import gov.gsa.fssi.files.providers.utils.contexts.ProviderLoaderContext;
import gov.gsa.fssi.files.providers.utils.strategies.loaders.ExcelProviderLoaderStrategy;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.utils.SchemaValidator;
import gov.gsa.fssi.files.schemas.utils.loaders.SchemaXMLLoader;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.utils.SourceFilePreProcessor;
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
	    ArrayList<Provider> providers = loadProviders();
	    
	    printAllProviders(providers);
	    
		ArrayList<Schema> schemas = loadSchemas();
		
		printAllSchemas(schemas);
		
		ArrayList<SourceFile> sourceFiles = new ArrayList<SourceFile>();
		ingestProcessAndExportSourceFiles(providers, schemas, sourceFiles);	    
	    logger.info("Completed FSSI File Processor");	
	}

	/**
	 * The purpose of this function is to process a all files through the entire process from ingestion to processing to validation and finally to output.
	 * @param sourceFileDirectory
	 */
	public static void ingestProcessAndExportSourceFiles(ArrayList<Provider> providers, ArrayList<Schema> schemas, ArrayList<SourceFile> sourceFiles) {	
		SourceFilePreProcessor sourceFilePreProcessor = new SourceFilePreProcessor();
		sourceFilePreProcessor.preProcessAll(schemas, providers, sourceFiles);
		for(SourceFile sourceFile: sourceFiles){
			ingestProcessAndExportSourceFile(providers, schemas, sourceFile);			
		}
	}

	/**
	 * The purpose of this function is to process a single file through the entire process from ingestion to processing to validation and finally to output.
	 * @param providers
	 * @param schemas
	 * @param sourceFile
	 */
	private static void ingestProcessAndExportSourceFile(ArrayList<Provider> providers, ArrayList<Schema> schemas, SourceFile sourceFile) {
		logger.debug("Processing sourceFile '{}'", sourceFile.getFileName());	
		if (!sourceFile.getLoadStatusLevel().equals(SourceFile.STATUS_ERROR)){
		    logger.info("Loading SourceFile '{}'", sourceFile.getFileName());	
		    sourceFile.load();
		    logger.info("Completed loading SourceFile '{}'", sourceFile.getFileName());	
		}
		
		if (!sourceFile.getLoadStatusLevel().equals(SourceFile.STATUS_ERROR)){
		    logger.info("Processing SourceFile '{}'", sourceFile.getFileName());	
			sourceFile.organize();
		    logger.info("Completed Processing SourceFile '{}'", sourceFile.getFileName());	
		}	
		
		if (!sourceFile.getLoadStatusLevel().equals(SourceFile.STATUS_ERROR) && sourceFile.getSchema() != null){
		    logger.info("Validating SourceFile '{}'", sourceFile.getFileName());	
		   sourceFile.validate();
		    logger.info("Completed validating SourceFile '{}'", sourceFile.getFileName());	
		}
		
		if (!sourceFile.getLoadStatusLevel().equals(SourceFile.STATUS_ERROR)){
		    logger.info("Outputting SourceFile '{}'", sourceFile.getFileName());	
		    sourceFile.export();
		    logger.info("Completed Outputting SourceFile '{}'", sourceFile.getFileName());	
		}
	}	
	
	public static ArrayList<Schema> loadSchemas() {
	    logger.debug("Starting initializeSchemas('{}')", config.getProperty(Config.SCHEMAS_DIRECTORY));		
		
	    ArrayList<Schema> schemas = new ArrayList<Schema>();	
	    ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(config.getProperty(Config.SCHEMAS_DIRECTORY), ".xml");
		
		for (String fileName : fileNames) {
			
			SchemaXMLLoader schemaLoader = new SchemaXMLLoader();
			SchemaValidator schemaValidator = new SchemaValidator();
			Schema schema = new Schema(fileName);
			
			schemaLoader.load(schema);
			if(logger.isDebugEnabled()){
				logger.info("Printing '{}' Schema that has been loaded", schema.getName());
				schema.printAll();
			}
			
			schemaValidator.validate(schema);
			if(logger.isDebugEnabled()){
				logger.info("Printing '{}' Schema that has been validated", schema.getName());
				schema.printAll();
			}
			
			if(schema.getValidatorStatusLevel().equals(Schema.STATUS_ERROR)){ //We currently prevent invalid schemas from being loaded
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
	
	public static ArrayList<Provider> loadProviders(){
	    logger.debug("Loading Providers");
		ArrayList<Provider> providers = new ArrayList<Provider>();
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(config.getProperty(Config.PROVIDERS_DIRECTORY), ".xlsx");
		ProviderLoaderContext context = new ProviderLoaderContext();
		for (String fileName : fileNames) {
			context.setProviderLoaderStrategy(new ExcelProviderLoaderStrategy());
			context.load(fileName, providers);
		}	
		
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validateAll(providers);   
		
		return providers;
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
