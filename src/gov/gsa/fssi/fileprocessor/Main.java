package gov.gsa.fssi.fileprocessor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.files.LoaderStatus;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.ProviderManager;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.SchemaLoader;
import gov.gsa.fssi.files.schemas.SchemaValidator;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.SourceFileManager;
import gov.gsa.fssi.helpers.FileHelper;




/**
 * This is the main class for the FSSI File Processor Project
 * 
 * @author David Larrimore
 * @version 0.1
 */
public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	static Config config = new Config();	    
	
	public static void main(String[] args) {
	    logger.info("Starting FSSI File Processor");
		ArrayList<Provider> providers = ProviderManager.initializeProviders();
		ProviderManager.printAllProviders(providers);
	    ArrayList<Schema> schemas = initializeSchemas();
	    SchemaLoader.printAllSchemas(schemas);
		//ArrayList<SourceFile> sourceFiles = SourceFileManager.initializeSourceFiles();
		//ingestProcessAndExportSourceFiles(providers, schemas, sourceFiles);	    
	    logger.info("Completed FSSI File Processor");	
	}

	/**
	 * The purpose of this function is to process a all files through the entire process from ingestion to processing to validation and finally to output.
	 * @param sourceFileDirectory
	 */
	public static void ingestProcessAndExportSourceFiles(ArrayList<Provider> providers, ArrayList<Schema> schemas, ArrayList<SourceFile> sourceFiles) {	
	    for ( SourceFile sourceFile : sourceFiles) {
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
		if (!sourceFile.getBuilderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Mapping Provider to SourceFile '{}'", sourceFile.getFileName());	
			SourceFileManager.validateSourceFileProvider(providers, sourceFile);	
		    logger.info("Completed Mapping Provider to SourceFile '{}'", sourceFile.getFileName());			
		}
		if (!sourceFile.getBuilderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Mapping Schema to SourceFile '{}'", sourceFile.getFileName());	
		    SourceFileManager.validateSourceFileSchema(schemas, sourceFile); 
		    logger.info("Completed Mapping Schema to SourceFile '{}'", sourceFile.getFileName());	
		}
		if (!sourceFile.getBuilderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Ingesting SourceFile '{}'", sourceFile.getFileName());	
			sourceFile.ingest();
		    logger.info("Completed Ingesting SourceFile '{}'", sourceFile.getFileName());	
		}
		
		if (!sourceFile.getBuilderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Processing SourceFile '{}'", sourceFile.getFileName());	
			sourceFile.processToSchema();
		    logger.info("Completed Processing SourceFile '{}'", sourceFile.getFileName());	
		}	
		
		if (!sourceFile.getBuilderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Outputting SourceFile '{}'", sourceFile.getFileName());	
		    sourceFile.outputStagedSourceFile();
		    logger.info("Completed Outputting SourceFile '{}'", sourceFile.getFileName());	
		}
	}	
	
	public static ArrayList<Schema> initializeSchemas() {
	    logger.debug("Starting initializeSchemas('{}')", config.getProperty(Config.SCHEMAS_DIRECTORY));		
		
	    ArrayList<Schema> schemas = new ArrayList<Schema>();	
	    ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(config.getProperty(Config.SCHEMAS_DIRECTORY), ".xml");
		
		for (String fileName : fileNames) {
			SchemaLoader schemaBuilder = new SchemaLoader();
			Schema schema = schemaBuilder.load(fileName);
			schema = SchemaValidator.validate(schema);
			
			if(schema == null){
				logger.error("No schema was returned from schemaBuilder. Most likely a file IO issue.");
			}else{
				schemas.add(schema);
			}
		}
		logger.info("Completed Schema setup. Added " + schemas.size() + " Schemas");
			
		return schemas;		
	}
	
	
}
