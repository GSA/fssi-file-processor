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
	
	/**
	 * Valid "Export Modes" include: implode, explode
	 */
	public static String EXPORT_MODE = "export_mode";	
	public static String VALIDATION_MODE = "validation_mode";	
	
	
	
	
	public static String DEFAULT_WORKING_DIRECTORY = "./working/";
	public static String DEFAULT_SOURCEFILES_DIRECTORY = "./working/srcfiles/";
	public static String DEFAULT_SCHEMAS_DIRECTORY = "./working/schemas/";	
	public static String DEFAULT_DATAMAPS_DIRECTORY = "./working/schemas/";		
	public static String DEFAULT_LOGS_DIRECTORY = "./working/logs/";			
	public static String DEFAULT_PROVIDERS_DIRECTORY = "./working/providers/";	
	public static String DEFAULT_STAGED_DIRECTORY = "./working/staged/";	
	public static String DEFAULT_EXPORT_MODE = "implode";
	public static String DEFAULT_VALIDATION_MODE = "";
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
		prop.setProperty(WORKING_DIRECTORY, DEFAULT_WORKING_DIRECTORY);
		prop.setProperty(SOURCEFILES_DIRECTORY, DEFAULT_SOURCEFILES_DIRECTORY);		
		prop.setProperty(SCHEMAS_DIRECTORY, DEFAULT_SCHEMAS_DIRECTORY);				
		prop.setProperty(DATAMAPS_DIRECTORY, DEFAULT_DATAMAPS_DIRECTORY);	
		prop.setProperty(LOGS_DIRECTORY, DEFAULT_LOGS_DIRECTORY);		
		prop.setProperty(PROVIDERS_DIRECTORY, DEFAULT_PROVIDERS_DIRECTORY);	
		prop.setProperty(STAGED_DIRECTORY, DEFAULT_STAGED_DIRECTORY);		
		prop.setProperty(EXPORT_MODE, DEFAULT_EXPORT_MODE);		
		prop.setProperty(VALIDATION_MODE, DEFAULT_VALIDATION_MODE);		
		
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
		
		if(!prop.containsKey(WORKING_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default", WORKING_DIRECTORY);
			prop.put(WORKING_DIRECTORY, DEFAULT_WORKING_DIRECTORY);
		}
		
		if(!prop.containsKey(SOURCEFILES_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default", SOURCEFILES_DIRECTORY);
			prop.put(SOURCEFILES_DIRECTORY, DEFAULT_SOURCEFILES_DIRECTORY);
		}
		
		if(!prop.containsKey(SCHEMAS_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default", SCHEMAS_DIRECTORY);
			prop.put(SCHEMAS_DIRECTORY, DEFAULT_SCHEMAS_DIRECTORY);
		}		
		
		if(!prop.containsKey(DATAMAPS_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default", DATAMAPS_DIRECTORY);
			prop.put(DATAMAPS_DIRECTORY, DEFAULT_DATAMAPS_DIRECTORY);
		}	
		
		if(!prop.containsKey(LOGS_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default", LOGS_DIRECTORY);
			prop.put(LOGS_DIRECTORY, DEFAULT_LOGS_DIRECTORY);
		}			
		
		if(!prop.containsKey(PROVIDERS_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default", PROVIDERS_DIRECTORY);
			prop.put(PROVIDERS_DIRECTORY, DEFAULT_PROVIDERS_DIRECTORY);
		}			
		
		if(!prop.containsKey(STAGED_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default", STAGED_DIRECTORY);
			prop.put(STAGED_DIRECTORY, DEFAULT_STAGED_DIRECTORY);
		}		
		
		if(!prop.containsKey(EXPORT_MODE)){
			logger.warn("No '{}' property found in config file, loading default", EXPORT_MODE);
			prop.put(EXPORT_MODE, DEFAULT_EXPORT_MODE);
		}				
		
		if(!prop.containsKey(VALIDATION_MODE)){
			logger.warn("No '{}' property found in config file, loading default", VALIDATION_MODE);
			prop.put(VALIDATION_MODE, DEFAULT_VALIDATION_MODE);
		}				
		return prop;
	}
	
}