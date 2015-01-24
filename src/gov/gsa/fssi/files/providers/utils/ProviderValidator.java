package gov.gsa.fssi.files.providers.utils;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.utils.contexts.ProviderLoaderContext;
import gov.gsa.fssi.files.providers.utils.strategies.loader.ExcelProviderLoaderStrategy;
import gov.gsa.fssi.helpers.FileHelper;

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
	
	
	public void validateProvider(ArrayList<Provider> providers){
		ArrayList<Provider> newProviders = new ArrayList<Provider>();
		//We need to check for duplicative Providers.
		boolean unique = true;
		for (Provider provider : providers) {
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
		//commiting all of the changes
		providers = newProviders;
	}
	
	
	public static void printAllProviders(ArrayList<Provider> providers){
		for(Provider provider: providers){
			provider.print();
		}
	}
	
	
	
	
	
	
}