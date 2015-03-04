package test.java.gov.gsa.fssi.providers;

import java.util.List;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.ProvidersBuilder;
import main.java.gov.gsa.fssi.files.providers.utils.strategies.loaders.ExcelProviderLoaderStrategy;
import main.java.gov.gsa.fssi.helpers.mockdata.MockProvider;

import org.junit.Assert;
import org.junit.Test;

public class ProviderLoadTest {
	Config config = new Config("./testfiles/", "config.properties");

	/**
	 * This should test to make sure that we are catching required Fields
	 */
	@Test
	public void loadFromExcelFile() {
		ProvidersBuilder providersBuilder = new ProvidersBuilder();
		List<Provider> providers = providersBuilder.build(config
				.getProperty(Config.PROVIDERS_DIRECTORY));

		Assert.assertEquals("failure - ProviderLoadTest getProviderName",
				"TEST",providers.get(0).getProviderName());
		Assert.assertEquals("failure - ProviderLoadTest getProviderIdentifier",
				"TEST", providers.get(0).getProviderIdentifier());
		Assert.assertEquals("failure - ProviderLoadTest getFileOutputType",
				"csv",providers.get(0).getFileOutputType());
		Assert.assertEquals("failure - ProviderLoadTest count", 3,
				providers.size());
		

	}
	
	
}
