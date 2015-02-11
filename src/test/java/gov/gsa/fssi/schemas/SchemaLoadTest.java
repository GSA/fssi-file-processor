package test.java.gov.gsa.fssi.schemas;

import java.util.List;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.utils.SchemaBuilder;
import main.java.gov.gsa.fssi.files.schemas.utils.SchemasBuilder;
import main.java.gov.gsa.fssi.helpers.mockdata.MockSchema;

import org.junit.Assert;
import org.junit.Test;

public class SchemaLoadTest {
	Config config = new Config("./testfiles/", "config.properties");

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void loadSchema() {
		SchemaBuilder schemaBuilder = new SchemaBuilder();
		Schema schema = schemaBuilder.build(
				config.getProperty(Config.SCHEMAS_DIRECTORY), "test1.xml");
		Schema mockSchema = MockSchema.make("TEST1");

		Assert.assertEquals("failure - loadSchema getName", schema.getName(),
				mockSchema.getName());
	}

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void loadSchemas() {
		SchemasBuilder schemasBuilder = new SchemasBuilder();
		List<Schema> schemas = schemasBuilder.build(config
				.getProperty(Config.SCHEMAS_DIRECTORY));

		Assert.assertEquals("failure - loadSchemas count", 1, schemas.size());
	}

}
