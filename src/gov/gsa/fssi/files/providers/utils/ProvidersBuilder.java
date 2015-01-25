package gov.gsa.fssi.files.providers.utils;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.utils.contexts.ProviderLoaderContext;
import gov.gsa.fssi.files.providers.utils.strategies.loaders.ExcelProviderLoaderStrategy;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.helpers.FileHelper;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This method builds an ArrayList of Schemas
 * 
 * @author davidlarrimore
 *
 */
public class ProvidersBuilder {
	static Logger logger = LoggerFactory.getLogger(ProvidersBuilder.class);
	static Config config = new Config();	  
	
	public ArrayList<Provider> build() {
	    logger.debug("Starting Provider Builder", config.getProperty(Config.PROVIDERS_DIRECTORY));
	    
	    ArrayList<Provider> providers = new ArrayList<Provider>();
	    
	    //First we load the providers
	    ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(config.getProperty(Config.PROVIDERS_DIRECTORY), ".xlsx, .xls");
		for (String fileName : fileNames) {
			logger.info("Loading providers from '{}'", fileName);
			ProviderLoaderContext context = new ProviderLoaderContext();
			context.setProviderLoaderStrategy(new ExcelProviderLoaderStrategy());
			context.load(fileName, providers);
			logger.info("Loaded '{}' providers from '{}'", providers.size(), fileName);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Printing loaded providers from '{}'", config.getProperty(Config.PROVIDERS_DIRECTORY));
			for(Provider provider: providers){
				provider.print();
			}
		}
		
		//Now we validate all providers
		ProviderValidator providerValidator = new ProviderValidator();
		providerValidator.validateAll(providers);
		
		if(logger.isDebugEnabled()){
			logger.debug("Printing validated providers from '{}'", config.getProperty(Config.PROVIDERS_DIRECTORY));
			for(Provider provider: providers){
				provider.print();
			}
		}

		return providers;		
	}
}
