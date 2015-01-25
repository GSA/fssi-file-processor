package gov.gsa.fssi.files.providers.utils;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.utils.contexts.ProviderLoaderContext;
import gov.gsa.fssi.files.providers.utils.strategies.loader.ExcelProviderLoaderStrategy;
import gov.gsa.fssi.helpers.FileHelper;
import gov.gsa.fssi.helpers.ValidatorStatus;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * The purpose of this class is to quickly validate the loaded providers.
 * 
 * @author davidlarrimore
 *
 */
public class ProviderValidator {
	static Logger logger = LoggerFactory.getLogger(ProviderValidator.class);
	static Config config = new Config();	    
	
	
	public void validateAll(ArrayList<Provider> providers){
		ArrayList<Provider> newProviders = new ArrayList<Provider>();
		//We need to check for duplicative Providers.

		for (Provider provider : providers) {
			boolean unique = true;
			validate(provider);
			if(!provider.getValidatorStatus().equals(ValidatorStatus.ERROR)){
				for(Provider newProvider:newProviders){
					if(provider.getProviderIdentifier().equals(newProvider.getProviderIdentifier())){
						//logger.debug("{} - {}", newProvider.getProviderIdentifier(), provider.getProviderIdentifier());
						unique = false;
					}
				}
				if(unique == false){
					logger.warn("Found duplicate provider '{}'. removing", provider.getProviderIdentifier());	
				}else{
					newProviders.add(provider);	
				}	
			}
		}
		//commiting all of the changes
		providers = newProviders;
	}
	
	

	public void validate(Provider provider){
		//Certain fields are required.
		if (provider.getProviderId() == null || provider.getProviderName() == null || provider.getProviderIdentifier() == null){
			logger.error("Provider found Without all of the required fields (ID, Name, Identifier)...ignoring");
			provider.setValidatorStatusError();
		}else{
			provider.setValidatorStatusPass();			
		}
	}	
}