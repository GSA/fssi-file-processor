package main.java.gov.gsa.fssi.files.providers.utils;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.contexts.ProviderLoaderContext;
import main.java.gov.gsa.fssi.files.providers.utils.strategies.loaders.ExcelProviderLoaderStrategy;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is our ProvidersBuilder object. It is used to build providers.
 * 
 * @author davidlarrimore
 *
 */
public class ProvidersBuilder {
	private static final Logger logger = LoggerFactory.getLogger(ProvidersBuilder.class);

	/**
	 * This is the main method for ProvidersBuilder that reads all files from
	 * the "config.getProperty(Config.PROVIDERS_DIRECTORY))", validates them,
	 * and returns an ArrayList of Provider objects.
	 * 
	 * @return ArryList<Provider> of providers
	 */
	public List<Provider> build(String directory) {
		logger.debug("Starting Provider Builder", directory);

		List<Provider> providers = new ArrayList<Provider>();

		// First we load the providers
		List<String> fileNames = FileHelper.getFilesFromDirectory(
				directory, ".xlsx, .xls");
		for (String fileName : fileNames) {
			logger.info("Loading providers from '{}'", fileName);
			ProviderLoaderContext context = new ProviderLoaderContext();
			context.setProviderLoaderStrategy(new ExcelProviderLoaderStrategy());
			context.load(directory, fileName, providers);
			logger.info("Loaded '{}' providers from '{}'", providers.size(),
					fileName);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Printing loaded providers from '{}'", directory);
			for (Provider provider : providers) {
				provider.print();
			}
		}

		// Now we validate all providers
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validateAll(providers);

		if (logger.isDebugEnabled()) {
			logger.debug("Printing validated providers from '{}'", directory);
			for (Provider provider : providers) {
				provider.print();
			}
		}

		return providers;
	}
}
