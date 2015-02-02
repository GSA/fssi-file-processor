package main.java.gov.gsa.fssi.config;

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

	/**
	 * @deprecated
	 */
	public static String WORKING_DIRECTORY = "working_directory";
	
	
	/**
	 * SourceFiles Directory denotes where all of the "source files" are located 
	 * that you wish to load and process
	 */
	public static String SOURCEFILES_DIRECTORY = "sourcefiles_directory";
	
	/**
	 * Schemas Directory is where schemas are stored
	 */
	public static String SCHEMAS_DIRECTORY = "schemas_directory";	
	
	/**
	 * This Directory was originally designed to handle "Capture" activities
	 * with semi-structured and unstructured data sets. Not in use.
	 * @deprecated
	 */
	public static String DATAMAPS_DIRECTORY = "datamaps_directory";		
	/**
	 * business facing logs, not system logs.
	 */
	public static String LOGS_DIRECTORY = "logs_directory";
	
	/**
	 * Location of files where provider information is stored
	 */
	public static String PROVIDERS_DIRECTORY = "providers_directory";	
	
	/**
	 * Directory where all post processed files are placed.
	 */
	public static String STAGED_DIRECTORY = "staged_directory";	
	

	/**
	 * Export mode manages how the application organizes the file. See valid export modes below:
	 * @see EXPORT_MODE_EXPLODE
	 * @see EXPORT_MODE_IMPLODE
	 */
	public static String EXPORT_MODE = "export_mode";
	
	/**
	 * Not currently in use
	 * @deprecated
	 */
	public static String VALIDATION_MODE = "validation_mode";	
	
	/**
	 * @see main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.organizers.ExplodeSourceFileOrganizerStrategy#organize
	 */
	public static final String EXPORT_MODE_EXPLODE = "explode";
	
	/**
	 * @see main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.organizers.ImplodeSourceFileOrganizerStrategy#organize
	 */
	public static final String EXPORT_MODE_IMPLODE = "implode";
	
	
	
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
	 * @param key the property you want to get
	 * @return Properties the property you want
	 * @see java.util.Properties#getProperty()
	 */
	public String getProperty(String key) {
		return this.prop.getProperty(key);
		//return "test";
	}


	/**
	 * @param key the key to be placed into this property list.
	 * @param value the value corresponding to key.
	 * @see java.util.Properties#setProperty(String key, String value)
	 */
	public void setProperty(String key, String value) {
		this.prop.setProperty(key, value);
	}


	/**
	 * Config
	 */
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
	 * Get Property values from 'config.properties' file in project root.
	 * If file is found, it will read, validate,  and then load the values in to the Properties prop object.
	 * @throws IOException
	 */
	public void getPropValues() throws IOException {
		Properties prop = new Properties();
		InputStream inputStream = new FileInputStream(DEFAULT_PROPFILE_NAME);
		prop.load(inputStream); //Attempting to Load File
		validatePropFile(prop); //Now we validate the file
		this.prop = prop;
	}
	
	
	/**
	 * This method is only envoked if no config.properties file is provided. 
	 * This loads in default values.
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
	 * and makes sure that the required properties have been provided. If not, it uses the default values
	 * 
	 * @param Properties prop
	 * @return Properties prop
	 */
	private Properties validatePropFile(Properties prop){
		
		if(!prop.containsKey(WORKING_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", WORKING_DIRECTORY, DEFAULT_WORKING_DIRECTORY);
			prop.put(WORKING_DIRECTORY, DEFAULT_WORKING_DIRECTORY);
		}
		
		if(!prop.containsKey(SOURCEFILES_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", SOURCEFILES_DIRECTORY, DEFAULT_SOURCEFILES_DIRECTORY);
			prop.put(SOURCEFILES_DIRECTORY, DEFAULT_SOURCEFILES_DIRECTORY);
		}
		
		if(!prop.containsKey(SCHEMAS_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", SCHEMAS_DIRECTORY, DEFAULT_SCHEMAS_DIRECTORY);
			prop.put(SCHEMAS_DIRECTORY, DEFAULT_SCHEMAS_DIRECTORY);
		}		
		
		if(!prop.containsKey(DATAMAPS_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", DATAMAPS_DIRECTORY, DEFAULT_DATAMAPS_DIRECTORY);
			prop.put(DATAMAPS_DIRECTORY, DEFAULT_DATAMAPS_DIRECTORY);
		}	
		
		if(!prop.containsKey(LOGS_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", LOGS_DIRECTORY, DEFAULT_LOGS_DIRECTORY);
			prop.put(LOGS_DIRECTORY, DEFAULT_LOGS_DIRECTORY);
		}			
		
		if(!prop.containsKey(PROVIDERS_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", PROVIDERS_DIRECTORY, DEFAULT_PROVIDERS_DIRECTORY);
			prop.put(PROVIDERS_DIRECTORY, DEFAULT_PROVIDERS_DIRECTORY);
		}			
		
		if(!prop.containsKey(STAGED_DIRECTORY)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", STAGED_DIRECTORY, DEFAULT_STAGED_DIRECTORY);
			prop.put(STAGED_DIRECTORY, DEFAULT_STAGED_DIRECTORY);
		}		
		
		if(!prop.containsKey(EXPORT_MODE)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", EXPORT_MODE, DEFAULT_EXPORT_MODE);
			prop.put(EXPORT_MODE, DEFAULT_EXPORT_MODE);
		}				
		
		if(!prop.containsKey(VALIDATION_MODE)){
			logger.warn("No '{}' property found in config file, loading default: '{}'", VALIDATION_MODE, DEFAULT_VALIDATION_MODE);
			prop.put(VALIDATION_MODE, DEFAULT_VALIDATION_MODE);
		}				
		return prop;
	}
	
}