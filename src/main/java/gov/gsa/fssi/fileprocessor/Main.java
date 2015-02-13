package main.java.gov.gsa.fssi.fileprocessor;

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
		logger.info("Starting FSSI File Processor");

		logger.info("Building Providers");
		ProvidersBuilder providersBuilder = new ProvidersBuilder();
		List<Provider> providers = providersBuilder.build(config
				.getProperty(Config.PROVIDERS_DIRECTORY));
		printAllProviders(providers);

		logger.info("Building Schemas");
		SchemasBuilder schemasBuilder = new SchemasBuilder();
		List<Schema> schemas = schemasBuilder.build(config
				.getProperty(Config.SCHEMAS_DIRECTORY));
		printAllSchemas(schemas);

		for (String fileName : FileHelper.getFilesFromDirectory(
				config.getProperty(Config.SOURCEFILES_DIRECTORY), ".csv")) {
			SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
			SourceFile sourceFile = sourceFileBuilder.build(
					config.getProperty(Config.SOURCEFILES_DIRECTORY), fileName,
					config.getProperty(Config.EXPORT_MODE), schemas, providers);
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

			SourceFileLoggerContext context = new SourceFileLoggerContext();
			context.setSourceFileLoggerStrategy(new BasicTextSourceFileLoggerStrategy());
			context.log(config.getProperty(Config.LOGS_DIRECTORY), sourceFile, config.getProperty(Config.LOGGING_LEVEL));
		}

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
