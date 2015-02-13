package test.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.loggers;

import java.io.File;
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

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class BasicTextSourceFileLoggerStrategyTest {
	Config config = new Config("./testfiles/", "config.properties");

	/**
	 * 
	 */
	@Test
	@Ignore
	public void createsFile() {
		try {
			String fileName = FileHelper.getFullPath(
					config.getProperty(Config.LOGS_DIRECTORY),
					"goodfileschematest_012015.log");
			File file = new File(fileName);
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed for file: "
						+ fileName);
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		ProvidersBuilder providersBuilder = new ProvidersBuilder();
		List<Provider> providers = providersBuilder.build(config
				.getProperty(Config.PROVIDERS_DIRECTORY));

		SchemasBuilder schemasBuilder = new SchemasBuilder();
		List<Schema> schemas = schemasBuilder.build(config
				.getProperty(Config.SCHEMAS_DIRECTORY));

		SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
		SourceFile sourceFile = sourceFileBuilder.build(
				config.getProperty(Config.SOURCEFILES_DIRECTORY),
				"goodfileschematest_012015.csv", Config.EXPORT_MODE_IMPLODE,
				schemas, providers);

		SourceFileLoggerContext context = new SourceFileLoggerContext();
		context.setSourceFileLoggerStrategy(new BasicTextSourceFileLoggerStrategy());
		context.log(config.getProperty(Config.LOGS_DIRECTORY), sourceFile, config.getProperty(Config.LOGGING_LEVEL));

		try {
			String fileName = FileHelper.getFullPath(
					config.getProperty(Config.LOGS_DIRECTORY),
					"goodfileschematest_012015.log");
			File file = new File(fileName);
			Assert.assertTrue(file.delete());
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

	}
}
