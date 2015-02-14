package main.java.gov.gsa.fssi.files.providers.utils;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.files.providers.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The purpose of this class is to quickly validate the loaded providers.
 * 
 * @author davidlarrimore
 *
 */
public class ProviderValidator {
	private static final Logger logger = LoggerFactory
			.getLogger(ProviderValidator.class);

	/**
	 * This accepts a provider and validates it.
	 * 
	 * @param provider
	 *            of Provider class
	 */
	public void validate(Provider provider) {
		// Certain fields are required.
		if (provider.getProviderName() == null
				|| provider.getProviderIdentifier() == null) {
			logger.warn("Provider found Without all of the required fields (Name, Identifier)...ignoring");
			provider.setValidatorStatus(false);
			provider.setStatus(false);
			provider.setMaxErrorLevel(3);
			provider.addValidatorStatusMessage("Provider found Without all of the required fields (Name, Identifier)...ignoring");
		}
	}

	/**
	 * This accepts an arraylist of providers and validates them. Note: It has a
	 * first-come-first-serve approach to validating duplicates
	 * 
	 * @param providers
	 *            ArrayList<Provider> of Provider class
	 */
	public void validateAll(List<Provider> providers) {
		ArrayList<Provider> deleteProvidersList = new ArrayList<Provider>();
		// We need to check for duplicative Providers.

		for (Provider provider : providers) {
			boolean unique = true;
			validate(provider);
			if (provider.getStatus()) {
				// Checking for unique identifier
				for (Provider newProvider : deleteProvidersList) {
					if (provider.getProviderIdentifier().equals(
							newProvider.getProviderIdentifier())) {
						unique = false;
					}
				}
				if (unique == false) {
					logger.warn("Found duplicate provider '{}'. removing",
							provider.getProviderIdentifier());
					deleteProvidersList.add(provider);
				}
			} else {
				deleteProvidersList.add(provider);
			}
		}
		// Deleting providers
		for (Provider provider : deleteProvidersList) {
			providers.remove(provider);
		}
	}
}