package main.java.gov.gsa.fssi.fileprocessor;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.ProvidersBuilder;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.utils.SchemasBuilder;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.SourceFileBuilder;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.SourceFileLoggerContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.loggers.BasicTextSourceFileLoggerStrategy;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main class for the FSSI File Processor Project.
 * 
 * @author David Larrimore
 * @version 0.1
 */
public class Main {
	public static void main(String[] args) {
		logger.info("*******************************");		
		logger.info("Starting FSSI File Processor");
		logger.info("Version 1.2-20140305.1");
		logger.info("*******************************");	
		
		logger.info("Validating Config");
		
		boolean validConfig = true;
		
		if(!FileHelper.isDirectory(config
				.getProperty(Config.PROVIDERS_DIRECTORY))){
			logger.error("'{}' is not a valid Providers Directory", config.getProperty(Config.PROVIDERS_DIRECTORY));
			validConfig = false;
		}
		
		if(!FileHelper.isDirectory(config
				.getProperty(Config.SCHEMAS_DIRECTORY))){
			logger.error("'{}' is not a valid Schemas Directory", config.getProperty(Config.SCHEMAS_DIRECTORY));
			validConfig = false;
		}
		
		if(!FileHelper.isDirectory(config
				.getProperty(Config.SOURCEFILES_DIRECTORY))){
			logger.error("'{}' is not a valid Providers Directory", config.getProperty(Config.SOURCEFILES_DIRECTORY));
			logger.error("");
			validConfig = false;
		}
		
		if(!FileHelper.isDirectory(config
				.getProperty(Config.LOGS_DIRECTORY))){
			logger.error("'{}' is not a valid Logs Directory", config.getProperty(Config.LOGS_DIRECTORY));
			logger.error("");
			validConfig = false;
		}
		
		if(validConfig){
			List<Provider> providers = new ArrayList<Provider>();
			if(FileHelper.isDirectory(config
					.getProperty(Config.PROVIDERS_DIRECTORY))){
				logger.info("Building Providers");
				ProvidersBuilder providersBuilder = new ProvidersBuilder();
				providers = providersBuilder.build(config
						.getProperty(Config.PROVIDERS_DIRECTORY));
				printAllProviders(providers);
			}else{
				logger.error("'{}' directory is not a valid directory, unable to process providers", config.getProperty(Config.PROVIDERS_DIRECTORY));
			}
	
			
			List<Schema> schemas = new ArrayList<Schema>();
			if(FileHelper.isDirectory(config
					.getProperty(Config.SCHEMAS_DIRECTORY))){
					SchemasBuilder schemasBuilder = new SchemasBuilder();
					schemas = schemasBuilder.build(config
							.getProperty(Config.SCHEMAS_DIRECTORY));
					printAllSchemas(schemas);			
			}else{
				logger.error("'{}' directory is not a valid directory, unable to process schemas", config.getProperty(Config.SCHEMAS_DIRECTORY));
			}
			
	
			if(FileHelper.isDirectory(config.getProperty(Config.SOURCEFILES_DIRECTORY))){
				for (String fileName : FileHelper.getFilesFromDirectory(
						config.getProperty(Config.SOURCEFILES_DIRECTORY), ".csv")) {
					SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
					SourceFile sourceFile = sourceFileBuilder.build(
							config.getProperty(Config.SOURCEFILES_DIRECTORY), fileName,
							config.getProperty(Config.EXPORT_MODE), config.getProperty(Config.PROVIDER_MODE), schemas, providers);
					if (sourceFile != null) {
						if (sourceFile.getStatus())
							sourceFile.export(config
									.getProperty(Config.STAGED_DIRECTORY));
						else
							logger.error(
									"File '{}' is in Error state and is being ignored for exporting",
									fileName);
					} else
						logger.error(
								"File '{}' is in null and is being ignored for exporting",
								fileName);
	
					if(FileHelper.isDirectory(config.getProperty(Config.LOGS_DIRECTORY))){
						SourceFileLoggerContext context = new SourceFileLoggerContext();
						context.setSourceFileLoggerStrategy(new BasicTextSourceFileLoggerStrategy());
						context.log(config.getProperty(Config.LOGS_DIRECTORY), sourceFile,
								config.getProperty(Config.LOGGING_LEVEL));
					} else
						logger.error(
								"logs_directory '{}' is not a valid directory. Logs cannot be created",
								config.getProperty(Config.LOGS_DIRECTORY));
	
				}
				
			}else{
				logger.error("'{}' directory is not a valid directory, unable to process souce files", config.getProperty(Config.SOURCEFILES_DIRECTORY));
			}
			
		}else logger.error("File Processor could run because required config directories could not be found.");
		logger.info("Completed FSSI File Processor");
	}

	/**
	 * Prints all Providers
	 * 
	 * @param providers
	 *            an ArrayList of Provider class objects
	 */
	public static void printAllProviders(List<Provider> providers) {
		for (Provider provider : providers) {
			provider.print();
		}
	}

	/**
	 * Prints all Schemas
	 * 
	 * @param schemas
	 *            an ArrayList of Schema class objects
	 */
	public static void printAllSchemas(List<Schema> schemas) {
		for (Schema schema : schemas) {
			schema.printAll();
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	static Config config = new Config();

}
