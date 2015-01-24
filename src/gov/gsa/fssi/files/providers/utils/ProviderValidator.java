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
	
	public static ArrayList<Provider> initializeProviders() {	
		ArrayList<Provider> providers = new ArrayList<Provider>();
	    
	    logger.debug("Starting initializeProviders('{}')", config.getProperty(Config.PROVIDERS_DIRECTORY));
		
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(config.getProperty(Config.PROVIDERS_DIRECTORY), ".xlsx");
		ProviderLoaderContext context = new ProviderLoaderContext();
		for (String fileName : fileNames) {
			context.setProviderLoaderStrategy(new ExcelProviderLoaderStrategy());
			context.load(fileName, providers);
		}			
		//logger.info("Completed Provider setup. Added " + providers.size() + " Providers");	
		return providers;
	}

	
	public void validateProvider(ArrayList<Provider> providers, Provider newProvider){
		//We need to check for duplicative Providers.
		boolean unique = true;
		for (Provider provider : providers) {
			if(provider.getProviderIdentifier().equals(newProvider.getProviderIdentifier())){
				//logger.debug("{} - {}", newProvider.getProviderIdentifier(), provider.getProviderIdentifier());
				unique = false;
			}
		}
		if(unique == false){
			logger.warn("Found provider with same identifier '{}' in '{}'. Providers must be unique", newProvider.getProviderIdentifier());	
			//failCounter++;
		}else{
			providers.add(newProvider);	
			//passCounter++;
		}
	}
	public static void printAllProviders(ArrayList<Provider> providers){
		for(Provider provider: providers){
			provider.print();
		}
	}
}