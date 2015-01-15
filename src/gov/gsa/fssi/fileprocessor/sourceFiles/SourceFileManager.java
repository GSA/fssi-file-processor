package gov.gsa.fssi.fileprocessor.sourceFiles;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.helpers.FileHelper;

import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The purpose of this class is to manage the source files and data..
 *
 * @author davidlarrimore
 *
 */
public class SourceFileManager {
	static Config config = new Config();	    
	static Logger logger = LoggerFactory.getLogger(SourceFileManager.class);
	
	/**
	 * The purpose of this function is just to prep file processing. We are not actually loading data yet
	 * @param sourceFileDirectory
	 */
	public static ArrayList<SourceFile> initializeSourceFiles() {	
	    logger.debug("Starting initializeSourceFiles('{}')", config.getProperty(Config.SOURCEFILES_DIRECTORY));		
		ArrayList<SourceFile> sourceFiles = new ArrayList<SourceFile>();	
	
		//Loop through files in sourceFileDirectory and populate SourceFile objects
		for (String fileName : FileHelper.getFilesFromDirectory(config.getProperty(Config.SOURCEFILES_DIRECTORY), ".csv ")) {
	    	sourceFiles.add(new SourceFile(fileName));		        
		}		    
		return sourceFiles;
	}
	
	
	/**
	 * This method maps Providers to SourceFiles. Currently, if it doesn't map, we remove the sourcefile altogether.
	 * 
	 * @param providers
	 * @param sourceFiles
	 */
	public static void validateSourceFileProviders(
		ArrayList<Provider> providers, ArrayList<SourceFile> sourceFiles) {
		for ( SourceFile sourceFile : sourceFiles) {
			if(!sourceFile.getStatus().equals(SourceFile.STATUS_ERROR)){
				logger.info("Attempting to map Provider to file {}", sourceFile.getFileName());
				for (Provider provider : providers) {
					if(sourceFile.getFileName().toUpperCase().contains(provider.getProviderIdentifier().toUpperCase())){
						logger.info("Mapped provider {} - {} to file '{}'", provider.getProviderName(), provider.getProviderIdentifier(),sourceFile.getFileName());
						sourceFile.setProvider(provider);
					}
				}
			}
			if (sourceFile.getProvider() == null){
				logger.error("Could not find provider for file: '{}'", sourceFile.getFileName());
				sourceFile.setStatus(SourceFile.STATUS_ERROR);
			}
		}
	}	
	
	
	/**
	 * @param schemas
	 * @param sourceFiles
	 */
	public static void validateSourceFileSchemas(ArrayList<Schema> schemas,
		ArrayList<SourceFile> sourceFiles) {
		for ( SourceFile sourceFile : sourceFiles) {
			logger.info("Attempting to map Schema to file {}", sourceFile.getFileName());
			if (!sourceFile.getStatus().equals(SourceFile.STATUS_ERROR)){
				Provider provider = sourceFile.getProvider();
				for ( Schema schema : schemas) {
					if(provider.getProviderName().toUpperCase().equals(schema.getName().toUpperCase())){
						logger.info("Mapped schema {} to file '{}'", schema.getName(), sourceFile.getFileName());
						sourceFile.setSchema(schema);
					}
				}
				if (sourceFile.getSchema() == null){
					logger.error("Could not find schema for file: '{}'", sourceFile.getFileName());
					sourceFile.setStatus(SourceFile.STATUS_WARNING);
				}
			}
		}	
	}	

	
	/**
	 * This Function manages the processing of all of the source files.
	 * It loads that data into the SourceFile object and then performs any sort of cleansing
	 * validation, and/or processing on the data.
	 * 
	 * @param sourceFilesDirectory
	 * @param sourceFiles
	 */
	public static void processSourceFiles(ArrayList<SourceFile> sourceFiles) {
		//OK, now we start processing the files one at a time.
		for ( SourceFile sourceFile : sourceFiles) {
			if (!sourceFile.getStatus().equals(SourceFile.STATUS_ERROR)){
			sourceFile.processSourceFile();
			//Starting Data Validation
			logger.info("Starting Data validation on file {}", sourceFile.getFileName()); 
			//END DATA VALIDATION
			}
		}
	}
	
	/**
	 * Based upon the providers defined Output Type, we export the source files to the staged directory.
	 * 
	 * @param string
	 * @param sourceFiles
	 */
	public static void outputStagedSourceFiles(ArrayList<SourceFile> sourceFiles) {
		//OK, now we start processing the files one at a time.
		for ( SourceFile sourceFile : sourceFiles) {	
			if (!sourceFile.getStatus().equals(SourceFile.STATUS_ERROR)){
				//logger.info("Starting ouput of file {}", sourceFile.getFileName());
				sourceFile.outputStagedSourceFile();
			}
		}
	}
		
}
