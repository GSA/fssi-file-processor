package gov.gsa.fssi.files.providers.utils.strategies;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.utils.ProviderValidator;

/**
 * @author DavidKLarrimore
 *
 */
public interface ProviderLoaderStrategy {
	static Logger logger = LoggerFactory.getLogger(ProviderValidator.class);
	static Config config = new Config();	    
	
	public void load(String fileName, ArrayList<Provider> providers);
}
