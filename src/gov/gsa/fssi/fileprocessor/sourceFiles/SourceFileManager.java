package gov.gsa.fssi.fileprocessor.sourceFiles;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.fileprocessor.helpers.FileHelper;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.schemas.Schema;

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
		ArrayList<Integer> badFiles = new ArrayList<Integer>();
		
		//Loop 
		for ( SourceFile sourceFile : sourceFiles) {
			for ( Provider provider : providers) {
				if(sourceFile.getFileName().toUpperCase().contains(provider.getProviderIdentifier().toUpperCase())){
					sourceFile.setProvider(provider);
				}
			}
			
			//We couldn't find a provider, we can process this file no longer.
			if(sourceFile.getProvider() == null){
				logger.warn("No Provider found for file '{}' (Array Index #{}). File will be ignored", sourceFile.getFileName(), sourceFiles.indexOf(sourceFile));
				badFiles.add(sourceFiles.indexOf(sourceFile));
				sourceFile.setStatus(SourceFile.STATUS_IGNORED);
			}
		}
		
		//Reversing Order to prevent removing good files
		Collections.reverse(badFiles);
		
		//Removing any bad files
		if (badFiles.size() > 0){
			for (Integer integer : badFiles) {
				logger.warn("Removing sourceFile with index of '{}'", integer);
				sourceFiles.remove(integer.intValue());
			}
		}
	}	
	
	
	/**
	 * @param schemas
	 * @param sourceFiles
	 */
	public static void validateSourceFileSchemas(ArrayList<Schema> schemas,
		ArrayList<SourceFile> sourceFiles) {
		ArrayList<Integer> badFiles = new ArrayList<Integer>();
		//Loop 
		for ( SourceFile sourceFile : sourceFiles) {
			
			//We do not processed the ignored files
			if (!sourceFile.getStatus().equals(SourceFile.STATUS_IGNORED)){
				Provider provider = sourceFile.getProvider();
				for ( Schema schema : schemas) {
					if(provider.getProviderName().toUpperCase().equals(schema.getName().toUpperCase())){
						sourceFile.setSchema(schema);
					}
				}
				
				//We couldn't find a provider.
				if(sourceFile.getSchema() == null){
					logger.warn("No Schema found for file '{}' (Array Index #{}).", sourceFile.getFileName(), sourceFiles.indexOf(sourceFile));
					//logger.warn("No Schema found for file '{}' (Array Index #{}). File has been marked for removal", sourceFile.getFileName(), sourceFiles.indexOf(sourceFile));
					//badFiles.add(sourceFiles.indexOf(sourceFile));
				}
			}
			
			//Reversing Order to prevent removing good files
			Collections.reverse(badFiles);
			
			//Removing any bad files
			if (badFiles.size() > 0){
				for (Integer integer : badFiles) {
					logger.warn("Removing sourceFile with index of '{}'", integer);
					sourceFiles.remove(integer.intValue());
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
			sourceFile.processSourceFile();
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
			sourceFile.outputStagedSourceFile();
		}
	}
		
}
