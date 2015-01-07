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
			logger.error("Unable to load config file");
			e.printStackTrace();
		}
	}
	
	
	public void getPropValues() throws IOException {
		Properties prop = new Properties();
		String propFileName = "config.properties";
 
		InputStream inputStream = new FileInputStream(propFileName);
		prop.load(inputStream);
		
		this.prop = prop;
	}
}