package main.java.gov.gsa.fssi.files.providers.utils.strategies;

import java.util.ArrayList;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.providers.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface ProviderLoaderStrategy {
	Logger logger = LoggerFactory.getLogger(ProviderLoaderStrategy.class);
	Config config = new Config();	    
	
	void load(String fileName, ArrayList<Provider> providers);
}
