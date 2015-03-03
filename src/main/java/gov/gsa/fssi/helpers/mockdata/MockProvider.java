package main.java.gov.gsa.fssi.helpers.mockdata;

import main.java.gov.gsa.fssi.files.providers.Provider;

public class MockProvider {

	public static Provider make() {
		Provider provider = new Provider();
		return provider;
	}

	public static Provider make(String providerName, String providerIdentifier) {
		Provider provider = new Provider();
		provider.setProviderName(providerName);
		provider.setProviderIdentifier(providerIdentifier);
		return provider;
	}

	public static Provider make(String providerName, String providerIdentifier,
			String schemaName) {
		Provider provider = new Provider();
		provider.setProviderName(providerName);
		provider.setProviderIdentifier(providerIdentifier);
		provider.setSchemaName(schemaName);
		return provider;
	}

	public static Provider make(String providerName, String providerIdentifier,
			String schemaName, String fileOutputType) {
		Provider provider = new Provider();
		provider.setProviderName(providerName);
		provider.setProviderIdentifier(providerIdentifier);
		provider.setSchemaName(schemaName);
		provider.setFileOutputType(fileOutputType);
		return provider;
	}

	public static Provider make(String providerName, String providerIdentifier,
			String schemaName, String fileOutputType, String providerEmail) {
		Provider provider = new Provider();
		provider.setProviderName(providerName);
		provider.setProviderIdentifier(providerIdentifier);
		provider.setSchemaName(schemaName);
		provider.setFileOutputType(fileOutputType);
		provider.setProviderEmail(providerEmail);
		
		return provider;
	}	
}
