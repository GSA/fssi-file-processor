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
 * root directory of the class path (/src)
 * 
 * @author David Larrimore
 * 
 */
public class Config {
	static Logger logger = LoggerFactory.getLogger(Config.class);
	private Properties prop = null;
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
		} finally{
			logger.info("Loading default config settings");
			getDefaultPropValue();
		}
	}
	
	
	public void getPropValues() throws IOException {
		Properties prop = new Properties();
 
		InputStream inputStream = new FileInputStream(DEFAULT_PROPFILE_NAME);
		prop.load(inputStream);
		
		this.prop = prop;
	}
	
	public void getDefaultPropValue() {
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
}