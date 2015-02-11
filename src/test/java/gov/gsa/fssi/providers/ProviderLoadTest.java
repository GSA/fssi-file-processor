package test.java.gov.gsa.fssi.providers;

import java.util.ArrayList;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.ProvidersBuilder;
import main.java.gov.gsa.fssi.helpers.mockdata.MockProvider;

import org.junit.Assert;
import org.junit.Test;

public class ProviderLoadTest {
	Config config = new Config("./testfiles/", "config.properties");

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void load() {
		ProvidersBuilder providersBuilder = new ProvidersBuilder();
		ArrayList<Provider> providers = providersBuilder.build(config
				.getProperty(Config.PROVIDERS_DIRECTORY));
		Provider mockProvider = MockProvider.make("TEST", "TEST", "", "CSV");
		// Assert.assertNotNull("Test file missing",
		// getClass().getResource("/sample.txt"));

		Assert.assertEquals("failure - ProviderLoadTest getProviderName",
				providers.get(0).getProviderName(),
				mockProvider.getProviderName());
		Assert.assertEquals("failure - ProviderLoadTest getProviderIdentifier",
				providers.get(0).getProviderIdentifier(),
				mockProvider.getProviderIdentifier());
		Assert.assertEquals("failure - ProviderLoadTest getFileOutputType",
				providers.get(0).getFileOutputType(),
				mockProvider.getFileOutputType());
		Assert.assertEquals("failure - ProviderLoadTest count", 3,
				providers.size());

	}
}
