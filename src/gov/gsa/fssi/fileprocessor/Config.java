package gov.gsa.fssi.fileprocessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * This class loads and manages global configuration
 * Currently, the config file (config.properties) needs to be in the 
 * root directory of the project. If you do not provide a config file
 * it will use the default locations under "/working/". See globals 
 * for listing of default locations
 * 
 * @author David Larrimore
 * 
 */
public class Config {
	static Logger logger = LoggerFactory.getLogger(Config.class);
	private Properties prop = null;

	public static String WORKING_DIRECTORY = "working_directory";
	public static String SOURCEFILES_DIRECTORY = "sourcefiles_directory";
	public static String SCHEMAS_DIRECTORY = "schemas_directory";	
	public static String DATAMAPS_DIRECTORY = "datamaps_directory";		
	public static String LOGS_DIRECTORY = "logs_directory";			
	public static String PROVIDERS_DIRECTORY = "providers_directory";	
	public static String STAGED_DIRECTORY = "staged_directory";	
	public static String EXPORT_MODE = "export_mode";	
	
	public static String DEFAULT_WORKING_DIRECTORY = "./working/";
	public static String DEFAULT_SOURCEFILES_DIRECTORY = "./working/srcfiles/";
	public static String DEFAULT_SCHEMAS_DIRECTORY = "./working/schemas/";	
	public static String DEFAULT_DATAMAPS_DIRECTORY = "./working/schemas/";		
	public static String DEFAULT_LOGS_DIRECTORY = "./working/logs/";			
	public static String DEFAULT_PROVIDERS_DIRECTORY = "./working/providers/";	
	public static String DEFAULT_STAGED_DIRECTORY = "./working/staged/";	
	public static String DEFAULT_PROPFILE_NAME = "config.properties";
	
	/**
	 * @return the property
	 */
	public String getProperty(String propName) {
		return this.prop.getProperty(propName);
		//return "test";
	}


	/**
	 * @param property the property to set
	 */
	public void setProperty(String key, String value) {
		this.prop.setProperty(key, value);
	}


	public Config() {
		try {
			getPropValues();
		} catch (IOException e) {
			logger.error("Could not load config file '{}'. Received following error: {}", DEFAULT_PROPFILE_NAME, e.getMessage());
			logger.info("Loading default config settings");
			getDefaultPropValue();
		} 
	}
	
	
	/**
	 * @throws IOException
	 */
	public void getPropValues() throws IOException {
		Properties prop = new Properties();
 
		InputStream inputStream = new FileInputStream(DEFAULT_PROPFILE_NAME);
		prop.load(inputStream);
		
		
		validatePropFile(prop);
		
		this.prop = prop;
	}
	
	
	/**
	 * This method is only envoked if no config.properties file is provided. 
	 * This loads in default values..
	 */
	private void getDefaultPropValue() {
		Properties prop = new Properties();
		prop.setProperty("working_directory", DEFAULT_WORKING_DIRECTORY);
		prop.setProperty("sourcefiles_directory", DEFAULT_SOURCEFILES_DIRECTORY);		
		prop.setProperty("schemas_directory", DEFAULT_SCHEMAS_DIRECTORY);				
		prop.setProperty("datamaps_directory", DEFAULT_DATAMAPS_DIRECTORY);	
		prop.setProperty("logs_directory", DEFAULT_LOGS_DIRECTORY);		
		prop.setProperty("providers_directory", DEFAULT_PROVIDERS_DIRECTORY);	
		prop.setProperty("staged_directory", DEFAULT_STAGED_DIRECTORY);			
		
		this.prop = prop;
	}
	
	/**
	 * This method validates provided properties files
	 * and makes sure that the required properties have been provided.
	 * 
	 * @param prop
	 * @return
	 */
	private Properties validatePropFile(Properties prop){
		
		if(!prop.containsKey("working_directory")){
			logger.warn("No 'working_directory' property found in config file, loading default");
			prop.put("working_directory", DEFAULT_WORKING_DIRECTORY);
		}
		
		if(!prop.containsKey("sourcefiles_directory")){
			logger.warn("No 'sourcefiles_directory' property found in config file, loading default");
			prop.put("working_directory", DEFAULT_SOURCEFILES_DIRECTORY);
		}
		
		if(!prop.containsKey("schemas_directory")){
			logger.warn("No 'schemas_directory' property found in config file, loading default");
			prop.put("schemas_directory", DEFAULT_SCHEMAS_DIRECTORY);
		}		
		
		if(!prop.containsKey("datamaps_directory")){
			logger.warn("No 'datamaps_directory' property found in config file, loading default");
			prop.put("datamaps_directory", DEFAULT_DATAMAPS_DIRECTORY);
		}	
		
		if(!prop.containsKey("logs_directory")){
			logger.warn("No 'logs_directory' property found in config file, loading default");
			prop.put("logs_directory", DEFAULT_LOGS_DIRECTORY);
		}			
		
		if(!prop.containsKey("providers_directory")){
			logger.warn("No 'providers_directory' property found in config file, loading default");
			prop.put("providers_directory", DEFAULT_PROVIDERS_DIRECTORY);
		}			
		
		if(!prop.containsKey("staged_directory")){
			logger.warn("No 'staged_directory' property found in config file, loading default");
			prop.put("staged_directory", DEFAULT_STAGED_DIRECTORY);
		}		
		
		return prop;
	}
	
}