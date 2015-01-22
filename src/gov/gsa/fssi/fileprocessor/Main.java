package gov.gsa.fssi.fileprocessor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.LoaderStatus;
import gov.gsa.fssi.files.ValidatorStatus;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.ProviderManager;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.utils.loaders.SchemaXMLLoader;
import gov.gsa.fssi.files.schemas.utils.validators.SchemaValidator;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.utils.validators.SourceFilePreProcessor;
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
		ArrayList<Provider> providers = ProviderManager.initializeProviders();
		ProviderManager.printAllProviders(providers);
	    ArrayList<Schema> schemas = initializeSchemas();
		ingestProcessAndExportSourceFiles(providers, schemas);	    
	    logger.info("Completed FSSI File Processor");	
	}

	/**
	 * The purpose of this function is to process a all files through the entire process from ingestion to processing to validation and finally to output.
	 * @param sourceFileDirectory
	 */
	public static void ingestProcessAndExportSourceFiles(ArrayList<Provider> providers, ArrayList<Schema> schemas) {	
		for ( SourceFile sourceFile : SourceFilePreProcessor.preProcess()) {
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
		if (!sourceFile.getLoaderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Mapping Provider to SourceFile '{}'", sourceFile.getFileName());	
			validateSourceFileProvider(providers, sourceFile);	
		    logger.info("Completed Mapping Provider to SourceFile '{}'", sourceFile.getFileName());			
		}
		if (!sourceFile.getLoaderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Mapping Schema to SourceFile '{}'", sourceFile.getFileName());	
		    validateSourceFileSchema(schemas, sourceFile); 
		    logger.info("Completed Mapping Schema to SourceFile '{}'", sourceFile.getFileName());	
		}
		if (!sourceFile.getLoaderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Loading SourceFile '{}'", sourceFile.getFileName());	
		    sourceFile.load();
		    logger.info("Completed loading SourceFile '{}'", sourceFile.getFileName());	
		}
		
		if (!sourceFile.getLoaderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Processing SourceFile '{}'", sourceFile.getFileName());	
			sourceFile.processToSchema();
		    logger.info("Completed Processing SourceFile '{}'", sourceFile.getFileName());	
		}	
		
		if (!sourceFile.getLoaderStatusLevel().equals(LoaderStatus.ERROR) && sourceFile.getSchema() != null){
		    logger.info("Validating SourceFile '{}'", sourceFile.getFileName());	
		   // sourceFile.validate();
		    logger.info("Completed validating SourceFile '{}'", sourceFile.getFileName());	
		}
		
		if (!sourceFile.getLoaderStatusLevel().equals(LoaderStatus.ERROR)){
		    logger.info("Outputting SourceFile '{}'", sourceFile.getFileName());	
		    sourceFile.export();
		    logger.info("Completed Outputting SourceFile '{}'", sourceFile.getFileName());	
		}
	}	
	
	public static ArrayList<Schema> initializeSchemas() {
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
			
			if(schema.getValidatorStatus().equals(ValidatorStatus.ERROR)){ //We currently prevent invalid schemas from being loaded
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
	
	/**
	 * @param providers
	 * @param sourceFile
	 */
	public static void validateSourceFileProvider(
			ArrayList<Provider> providers, SourceFile sourceFile) {
		if(!sourceFile.getLoaderStatusLevel().equals(LoaderStatus.ERROR)){
			logger.info("Attempting to map Provider to file {}", sourceFile.getFileName());
			for (Provider provider : providers) {
				if(sourceFile.getFileName().toUpperCase().contains(provider.getProviderIdentifier().toUpperCase())){
					logger.info("Mapped provider {} - {} to file '{}'", provider.getProviderName(), provider.getProviderIdentifier(),sourceFile.getFileName());
					sourceFile.setProvider(provider);
					sourceFile.setLoaderStatusLevel(LoaderStatus.MAPPED);
				}
			}
		}
		if (sourceFile.getProvider() == null){
			logger.error("Could not find provider for file: '{}'", sourceFile.getFileName());
			sourceFile.setLoaderStatusLevel(LoaderStatus.ERROR);
		}
	}	
	

	/**
	 * @param schemas
	 * @param sourceFile
	 */
	public static void validateSourceFileSchema(ArrayList<Schema> schemas,
			SourceFile sourceFile) {
		logger.info("Attempting to map Schema to file {}", sourceFile.getFileName());
		if (!sourceFile.getLoaderStatusLevel().equals(LoaderStatus.ERROR)){
			Provider provider = sourceFile.getProvider();
			for ( Schema schema : schemas) {
				if(provider.getProviderName().toUpperCase().equals(schema.getName().toUpperCase())){
					logger.info("Mapped schema {} to file '{}'", schema.getName(), sourceFile.getFileName());
					Schema newSchema = SourceFilePreProcessor.personalizeSourceFileSchema(sourceFile.getReportingPeriod(), schema);
					logger.info("Printing personalized '{}' schema", newSchema.getName());
					newSchema.printAll();
					sourceFile.setSchema(newSchema);
				}
			}
			if (sourceFile.getSchema() == null){
				logger.error("Could not find schema for file: '{}'", sourceFile.getFileName());
				sourceFile.setValidatorStatusLevel(ValidatorStatus.WARNING);
			}
		}
	}		
	
}
