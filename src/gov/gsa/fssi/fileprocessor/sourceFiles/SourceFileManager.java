package gov.gsa.fssi.fileprocessor.sourceFiles;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.helpers.FileHelper;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The purpose of this class is to handle the automated processing of all source files.
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
			validateSourceFileProvider(providers, sourceFile);
		}
	}

	/**
	 * @param providers
	 * @param sourceFile
	 */
	public static void validateSourceFileProvider(
			ArrayList<Provider> providers, SourceFile sourceFile) {
		if(!sourceFile.getStatus().equals(SourceFile.STATUS_ERROR)){
			logger.info("Attempting to map Provider to file {}", sourceFile.getFileName());
			for (Provider provider : providers) {
				if(sourceFile.getFileName().toUpperCase().contains(provider.getProviderIdentifier().toUpperCase())){
					logger.info("Mapped provider {} - {} to file '{}'", provider.getProviderName(), provider.getProviderIdentifier(),sourceFile.getFileName());
					sourceFile.setProvider(provider);
					sourceFile.setStatus(SourceFile.STATUS_MAPPED);					
				}
			}
		}
		if (sourceFile.getProvider() == null){
			logger.error("Could not find provider for file: '{}'", sourceFile.getFileName());
			sourceFile.setStatus(SourceFile.STATUS_ERROR);
		}
	}	
	
	
	/**
	 * @param schemas
	 * @param sourceFiles
	 */
	public static void validateSourceFileSchemas(ArrayList<Schema> schemas,
		ArrayList<SourceFile> sourceFiles) {
		for ( SourceFile sourceFile : sourceFiles) {
			validateSourceFileSchema(schemas, sourceFile);
		}	
	}

	/**
	 * @param schemas
	 * @param sourceFile
	 */
	public static void validateSourceFileSchema(ArrayList<Schema> schemas,
			SourceFile sourceFile) {
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
