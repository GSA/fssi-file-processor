package test.java.gov.gsa.fssi.files.sourcefiles;

import java.util.List;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.ProvidersBuilder;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.utils.SchemasBuilder;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.SourceFileBuilder;

import org.junit.Assert;
import org.junit.Test;

public class SourceFileLoadTest {
	Config config = new Config("./testfiles/", "config.properties");
	private static final String SOURCEFILENAME = "goodfileschematest_012015.csv";

	@Test
	public void loadSourceFile() {
		
		SourceFile sourceFile = new SourceFile(SOURCEFILENAME);
		SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
		sourceFileBuilder.load(sourceFile, config.getProperty(Config.SOURCEFILES_DIRECTORY));

		Assert.assertEquals("failure - loadSourceFile recordCount", 6,
				sourceFile.recordCount());
		Assert.assertEquals("failure - loadSourceFile getSourceHeaders", 35,
				sourceFile.getSourceHeaders().size());

		int totalDataCount = 0;
		for (SourceFileRecord record : sourceFile.getRecords()) {
			for (Data data : record.getDatas()) {
				if (!data.getData().isEmpty())
					totalDataCount++;
			}
		}
		Assert.assertEquals("failure - loadSourceFile totalDataCount", 204,
				totalDataCount);
	}

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void loadSourceFileUsingBuilder() {
		ProvidersBuilder providersBuilder = new ProvidersBuilder();
		List<Provider> providers = providersBuilder.build(config
				.getProperty(Config.PROVIDERS_DIRECTORY));

		SchemasBuilder schemasBuilder = new SchemasBuilder();
		List<Schema> schemas = schemasBuilder.build(config
				.getProperty(Config.SCHEMAS_DIRECTORY));

		SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
		SourceFile sourceFile = sourceFileBuilder.build(
				config.getProperty(Config.SOURCEFILES_DIRECTORY),
				SOURCEFILENAME, Config.EXPORT_MODE_IMPLODE, Config.PROVIDER_MODE_DEBUG, schemas, providers);
		
		
		
		Assert.assertEquals("failure - loadSourceFile recordCount", 6,sourceFile.recordCount());
		/*
		 * This needs to be 50 because we are performing an implode and there are 35 fields in the source file
		 * but there are an additional 15 fields that are in the schema.
		 */
		Assert.assertEquals("failure - loadSourceFile getSourceHeaders", 50,sourceFile.getSourceHeaders().size());

		int totalDataCount = 0;
		for (SourceFileRecord record : sourceFile.getRecords()) {
			for (Data data : record.getDatas()) {
				if (!data.getData().isEmpty())
					totalDataCount++;
			}
		}

		Assert.assertEquals("failure - loadSourceFile totalDataCount", 204,
				totalDataCount);

	}

	@Test
	public void mapProviderToSourceFile() {
		ProvidersBuilder providersBuilder = new ProvidersBuilder();
		List<Provider> providers = providersBuilder.build(config
				.getProperty(Config.PROVIDERS_DIRECTORY));

		SourceFile sourceFile = new SourceFile(SOURCEFILENAME);

		SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
		sourceFileBuilder.mapProviderToSourceFile(providers, sourceFile);
		Assert.assertNotNull(sourceFile.getProvider());
	}

	@Test
	public void mapSchemaToSourceFile() {
		ProvidersBuilder providersBuilder = new ProvidersBuilder();
		List<Provider> providers = providersBuilder.build(config
				.getProperty(Config.PROVIDERS_DIRECTORY));

		SchemasBuilder schemasBuilder = new SchemasBuilder();
		List<Schema> schemas = schemasBuilder.build(config
				.getProperty(Config.SCHEMAS_DIRECTORY));

		SourceFile sourceFile = new SourceFile(SOURCEFILENAME);

		SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
		sourceFileBuilder.mapProviderToSourceFile(providers, sourceFile);
		sourceFileBuilder.mapSchemaToSourceFile(schemas, sourceFile);
		Assert.assertNotNull(sourceFile.getSchema());
	}

	@Test
	public void mapSourceFileFieldsToSchemaTest() {
		ProvidersBuilder providersBuilder = new ProvidersBuilder();
		List<Provider> providers = providersBuilder.build(config
				.getProperty(Config.PROVIDERS_DIRECTORY));
		SchemasBuilder schemasBuilder = new SchemasBuilder();
		List<Schema> schemas = schemasBuilder.build(config
				.getProperty(Config.SCHEMAS_DIRECTORY));
		SourceFile sourceFile = new SourceFile(SOURCEFILENAME);
		SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
		sourceFileBuilder.mapProviderToSourceFile(providers, sourceFile);
		sourceFileBuilder.load(sourceFile, config.getProperty(Config.SOURCEFILES_DIRECTORY));
		sourceFileBuilder.mapSchemaToSourceFile(schemas, sourceFile);
		sourceFileBuilder.personalizeSourceFileSchema(sourceFile);

		
		Assert.assertEquals("failure - personalizeSourceFileSchema recordCount", 35, sourceFile.getSourceHeaders().size());
		sourceFileBuilder.mapSourceFileFieldsToSchema(sourceFile);
		int totalMatchedSchemaFieldCount = 0;
		for (SchemaField field : sourceFile.getSchema().getFields()) {
			if (field.getHeaderIndex() > -1)
				totalMatchedSchemaFieldCount++;
		}
		Assert.assertEquals(
				"failure - mapSourceFileFieldsToSchemaTest totalMatchedSchemaFieldCount",
				35, totalMatchedSchemaFieldCount);

		Assert.assertEquals(
				"failure - mapSourceFileFieldsToSchemaTest recordCount", 35,
				sourceFile.getSourceHeaders().size());
		int totalDataCount = 0;
		for (SourceFileRecord record : sourceFile.getRecords()) {
			for (Data data : record.getDatas()) {
				if (!data.getData().isEmpty())
					totalDataCount++;
			}
		}
		Assert.assertEquals(
				"failure - mapSourceFileFieldsToSchemaTest totalDataCount",204, totalDataCount);
	}

}
