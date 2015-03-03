package main.java.gov.gsa.fssi.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads and manages global configuration Currently, the config file
 * (config.properties) needs to be in the root directory of the project. If you
 * do not provide a config file it will use the default locations under
 * "/working/". See globals for listing of default locations
 *
 * @author David Larrimore
 *
 */
public class Config {
	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	private Properties prop = null;
	public static final String LOGGING_LEVEL_ALL = "all";
	public static final String LOGGING_LEVEL_WARNING = "warning";
	public static final String LOGGING_LEVEL_ERROR = "error";
	public static final String LOGGING_LEVEL_FATAL = "fatal";

	/**
	 * @deprecated
	 */
	@Deprecated
	public static final String WORKING_DIRECTORY = "working_directory";
	/**
	 * SourceFiles Directory denotes where all of the "source files" are located
	 * that you wish to load and process
	 */
	public static final String SOURCEFILES_DIRECTORY = "sourcefiles_directory";
	/**
	 * Schemas Directory is where schemas are stored
	 */
	public static final String SCHEMAS_DIRECTORY = "schemas_directory";
	/**
	 * This Directory was originally designed to handle "Capture" activities
	 * with semi-structured and unstructured data sets. Not in use.
	 * 
	 * @deprecated
	 */
	@Deprecated
	public static final String DATAMAPS_DIRECTORY = "datamaps_directory";
	/**
	 * business facing logs, not system logs.
	 */
	public static final String LOGS_DIRECTORY = "logs_directory";
	/**
	 * Location of files where provider information is stored
	 */
	public static final String PROVIDERS_DIRECTORY = "providers_directory";
	/**
	 * Directory where all post processed files are placed.
	 */
	public static final String STAGED_DIRECTORY = "staged_directory";
	/**
	 * Export mode manages how the application organizes the file. See valid
	 * export modes below:
	 * 
	 * @see EXPORT_MODE_EXPLODE
	 * @see EXPORT_MODE_IMPLODE
	 */
	public static final String EXPORT_MODE = "export_mode";
	/**
	 * Not currently in use
	 * 
	 * @deprecated
	 */
	@Deprecated
	public static final String VALIDATION_MODE = "validation_mode";
	/**
	 * @see main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.organizers.ExplodeSourceFileOrganizerStrategy#organize
	 */
	public static final String EXPORT_MODE_EXPLODE = "explode";
	/**
	 * @see main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.organizers.ImplodeSourceFileOrganizerStrategy#organize
	 */
	public static final String EXPORT_MODE_IMPLODE = "implode";
	/**
	 * Logging level defines the lowest level at which issues will be logged.
	 * for example, the logging level of "warning" will show "warning", "error",
	 * and "fatal". if undefined, it defaults to showing all errors.
	 */
	public static final String LOGGING_LEVEL = "logging_level";
	
	/**
	 * The provider mode option tells the program whether or not to allow files
	 * that do not have a provider to be processed and staged. This is useful in a production
	 * setting where you want to catch when files are named poorly, or must be processed by a
	 * certain schema.<p>
	 * 
	 * Options:<br>
	 *      strict = A providers file is required and files will fail if the provider is not found.<br>
	 *      debug = A provider file is not required and Files without providers will process normally, but will accept system defaults
	 * 
	 */	
	public static final String PROVIDER_MODE = "provider_mode";
	public static final String PROVIDER_MODE_STRICT = "strict";
	public static final String PROVIDER_MODE_DEBUG = "debug";	
	public static final String DEFAULT_PROVIDER_MODE = PROVIDER_MODE_STRICT;
	
	
	public static final String DEFAULT_WORKING_DIRECTORY = "./working/";
	public static final String DEFAULT_SOURCEFILES_DIRECTORY = "./working/srcfiles/";
	public static final String DEFAULT_SCHEMAS_DIRECTORY = "./working/schemas/";
	public static final String DEFAULT_LOGS_DIRECTORY = "./working/logs/";
	public static final String DEFAULT_PROVIDERS_DIRECTORY = "./working/providers/";
	public static final String DEFAULT_STAGED_DIRECTORY = "./working/staged/";
	public static final String DEFAULT_EXPORT_MODE = "implode";
	public static final String DEFAULT_VALIDATION_MODE = "";
	public static final String DEFAULT_PROPFILE_NAME = "config.properties";
	public static final String DEFAULT_LOGGING_LEVEL = LOGGING_LEVEL_WARNING;

	/**
	 * Config
	 */
	public Config() {
		try {
			getPropValues("./", DEFAULT_PROPFILE_NAME);
		} catch (IOException e) {
			logger.error(
					"Could not load config file '{}'. Received following error: {}",
					DEFAULT_PROPFILE_NAME, e.getMessage());
			logger.info("Loading default config settings");
			getDefaultPropValue();
		}
	}

	/**
	 * Config
	 */
	public Config(String directory, String fileName) {
		try {
			getPropValues(directory, fileName);
		} catch (IOException e) {
			logger.error(
					"Could not load config file '{}'. Received following error: {}",
					DEFAULT_PROPFILE_NAME, e.getMessage());
			logger.info("Loading default config settings");
			getDefaultPropValue();
		}

	}

	/**
	 * This method is only envoked if no config.properties file is provided.
	 * This loads in default values.
	 */
	private void getDefaultPropValue() {
		Properties prop = new Properties();
		prop.setProperty(SOURCEFILES_DIRECTORY, DEFAULT_SOURCEFILES_DIRECTORY);
		prop.setProperty(SCHEMAS_DIRECTORY, DEFAULT_SCHEMAS_DIRECTORY);
		prop.setProperty(LOGS_DIRECTORY, DEFAULT_LOGS_DIRECTORY);
		prop.setProperty(PROVIDERS_DIRECTORY, DEFAULT_PROVIDERS_DIRECTORY);
		prop.setProperty(STAGED_DIRECTORY, DEFAULT_STAGED_DIRECTORY);
		prop.setProperty(EXPORT_MODE, DEFAULT_EXPORT_MODE);
		prop.setProperty(PROVIDER_MODE, DEFAULT_PROVIDER_MODE);		
		prop.setProperty(LOGGING_LEVEL, DEFAULT_LOGGING_LEVEL);				
		this.prop = prop;
	}

	/**
	 * @param key
	 *            the property you want to get
	 * @return Properties the property you want
	 * @see java.util.Properties#getProperty()
	 */
	public String getProperty(String key) {
		return this.prop.getProperty(key);
	}

	/**
	 * Get Property values from 'config.properties' file in project root. If
	 * file is found, it will read, validate, and then load the values in to the
	 * Properties prop object.
	 * 
	 * @throws IOException
	 */
	public void getPropValues(String directory, String fileName)
			throws IOException {
		boolean foundPropFile = false;
		Properties prop = new Properties();
		String fullFileName = FileHelper.getFullPath(directory, fileName);
		logger.info("Attempting to get configurations from configfile '{}'",fullFileName);
		File file = FileHelper.getFile(fullFileName);
		
		if(!file.isFile()){
			logger.warn("could not find default properties file '{}', scanning home directory", DEFAULT_PROPFILE_NAME);
			List<String> fileNames = FileHelper.getFilesFromDirectory(directory, ".properties");
			for (String thisFileName : fileNames) {
				file = FileHelper.getFile(thisFileName);
				if(file.isFile()) foundPropFile = true;
				fullFileName = file.getAbsolutePath();
			}			
		}else{
			foundPropFile = true;
		}
		
		if(foundPropFile){
			logger.info("found propfile '{}'", fullFileName);
			InputStream inputStream = new FileInputStream(fullFileName);

			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			prop.load(reader); // Attempting to Load File
			validatePropFile(prop); // Now we validate the file
			this.prop = prop;
			reader.close();
			inputStream.close();			
		}else{
			logger.error("Could not find Prop File");
		}
	}

	/**
	 * @param key
	 *            the key to be placed into this property list.
	 * @param value
	 *            the value corresponding to key.
	 * @see java.util.Properties#setProperty(String key, String value)
	 */
	public void setProperty(String key, String value) {
		this.prop.setProperty(key, value);
	}

	/**
	 * This method validates provided properties files and makes sure that the
	 * required properties have been provided. If not, it uses the default
	 * values
	 * 
	 * @param Properties
	 *            prop
	 * @return Properties prop
	 */
	private Properties validatePropFile(Properties prop) {
		if (!prop.containsKey(SOURCEFILES_DIRECTORY)) {
			logger.warn(
					"No '{}' property found in config file, loading default: '{}'",
					SOURCEFILES_DIRECTORY, DEFAULT_SOURCEFILES_DIRECTORY);
			prop.put(SOURCEFILES_DIRECTORY, DEFAULT_SOURCEFILES_DIRECTORY);
		}
		if (!prop.containsKey(SCHEMAS_DIRECTORY)) {
			logger.warn(
					"No '{}' property found in config file, loading default: '{}'",
					SCHEMAS_DIRECTORY, DEFAULT_SCHEMAS_DIRECTORY);
			prop.put(SCHEMAS_DIRECTORY, DEFAULT_SCHEMAS_DIRECTORY);
		}

		if (!prop.containsKey(LOGS_DIRECTORY)) {
			logger.info(
					"No '{}' property found in config file, loading default: '{}'",
					LOGS_DIRECTORY, DEFAULT_LOGS_DIRECTORY);
			prop.put(LOGS_DIRECTORY, DEFAULT_LOGS_DIRECTORY);
		}

		if (!prop.containsKey(PROVIDERS_DIRECTORY)) {
			logger.info(
					"No '{}' property found in config file, loading default: '{}'",
					PROVIDERS_DIRECTORY, DEFAULT_PROVIDERS_DIRECTORY);
			prop.put(PROVIDERS_DIRECTORY, DEFAULT_PROVIDERS_DIRECTORY);
		}

		if (!prop.containsKey(STAGED_DIRECTORY)) {
			logger.info(
					"No '{}' property found in config file, loading default: '{}'",
					STAGED_DIRECTORY, DEFAULT_STAGED_DIRECTORY);
			prop.put(STAGED_DIRECTORY, DEFAULT_STAGED_DIRECTORY);
		}

		if (!prop.containsKey(EXPORT_MODE)) {
			logger.info(
					"No '{}' property found in config file, loading default: '{}'",
					EXPORT_MODE, DEFAULT_EXPORT_MODE);
			prop.put(EXPORT_MODE, DEFAULT_EXPORT_MODE);
		} else if (!isValidExportMode(prop.getProperty(EXPORT_MODE))) {
			logger.warn(
					"BAD '{}' property found in config file, loading default: '{}'",
					EXPORT_MODE, DEFAULT_EXPORT_MODE);
			prop.put(EXPORT_MODE, DEFAULT_EXPORT_MODE);
		}

		if (!prop.containsKey(LOGGING_LEVEL)) {
			logger.info(
					"No '{}' property found in config file, loading default: '{}'",
					LOGGING_LEVEL, DEFAULT_LOGGING_LEVEL);
			prop.put(LOGGING_LEVEL, DEFAULT_LOGGING_LEVEL);
		} else if (!isValidLoggingLevel(prop.getProperty(LOGGING_LEVEL))) {
			logger.warn(
					"BAD '{}' property found in config file, loading default: '{}'",
					LOGGING_LEVEL, DEFAULT_LOGGING_LEVEL);
			prop.put(LOGGING_LEVEL, DEFAULT_LOGGING_LEVEL);
		}
		
		
		if (!prop.containsKey(PROVIDER_MODE)) {
			logger.info(
					"No '{}' property found in config file, loading default: '{}'",
					PROVIDER_MODE, DEFAULT_PROVIDER_MODE);
			prop.put(PROVIDER_MODE, DEFAULT_PROVIDER_MODE);
		} else if (!isValidProviderMode(prop.getProperty(PROVIDER_MODE))) {
			logger.warn(
					"BAD '{}' property found in config file, loading default: '{}'",
					PROVIDER_MODE, DEFAULT_PROVIDER_MODE);
			prop.put(PROVIDER_MODE, DEFAULT_PROVIDER_MODE);
		}		
		

		return prop;
	}

	public static boolean isValidLoggingLevel(String val) {
		if (val.equalsIgnoreCase(LOGGING_LEVEL_FATAL)) {
			return true;
		} else if (val.equalsIgnoreCase(LOGGING_LEVEL_ERROR)) {
			return true;
		} else if (val.equalsIgnoreCase(LOGGING_LEVEL_WARNING)) {
			return true;
		} else if (val.equalsIgnoreCase(LOGGING_LEVEL_ALL)) {
			return true;
		}
		return false;
	}

	public static boolean isValidExportMode(String val) {
		if (val.equalsIgnoreCase(EXPORT_MODE_IMPLODE)) {
			return true;
		} else if (val.equalsIgnoreCase(EXPORT_MODE_EXPLODE)) {
			return true;
		}
		return false;
	}
	

	public static boolean isValidProviderMode(String val) {
		if (val.equalsIgnoreCase(PROVIDER_MODE_STRICT)) {
			return true;
		} else if (val.equalsIgnoreCase(PROVIDER_MODE_DEBUG)) {
			return true;
		} 
		return false;
	}	
	

}