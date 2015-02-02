package main.java.gov.gsa.fssi.files.providers.utils.strategies;

import java.util.ArrayList;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.ProviderValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface ProviderLoaderStrategy {
	static Logger logger = LoggerFactory.getLogger(ProviderValidator.class);
	static Config config = new Config();	    
	
	public void load(String fileName, ArrayList<Provider> providers);
}
