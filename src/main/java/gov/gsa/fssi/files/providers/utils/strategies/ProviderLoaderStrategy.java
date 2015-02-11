package main.java.gov.gsa.fssi.files.providers.utils.strategies;

import java.util.List;

import main.java.gov.gsa.fssi.files.providers.Provider;

/**
 * @author DavidKLarrimore
 *
 */
public interface ProviderLoaderStrategy {
	void load(String directory, String fileName, List<Provider> providers);
}
