package gov.gsa.fssi.fileprocessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
 
/**
 * This class loads and manages global configuration
 * Currently, the config file (config.properties) needs to be in the 
 * root directory of the class path (/src)
 * 
 * @author David Larrimore
 * 
 */
public class Config {
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void getPropValues() throws IOException {
		Properties prop = new Properties();
		String propFileName = "config.properties";
 
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
		//System.out.println(prop.getProperty("working_directory"));
		this.prop = prop;
	}
}