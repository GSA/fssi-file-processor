package test.gov.gsa.fssi.mockData;

import gov.gsa.fssi.files.providers.Provider;

public class MockProvider{
	
	public static Provider make(){
		Provider provider = new Provider();
		return provider;
	}
	
	public static Provider make(String providerName, String providerIdentifier, String schemaName, String fileOutputType){
		Provider provider = new Provider();
		provider.setProviderName(providerName);
		provider.setProviderIdentifier(providerIdentifier);
		provider.setSchemaName(schemaName);
		provider.setFileOutputType(fileOutputType);
		return provider;		
	}
	
	public static Provider make(String providerName, String providerIdentifier, String schemaName){
		Provider provider = new Provider();
		provider.setProviderName(providerName);
		provider.setProviderIdentifier(providerIdentifier);
		provider.setSchemaName(schemaName);
		return provider;		
	}	
	
	public static Provider make(String providerName, String providerIdentifier){
		Provider provider = new Provider();
		provider.setProviderName(providerName);
		provider.setProviderIdentifier(providerIdentifier);
		return provider;		
	}	
}
