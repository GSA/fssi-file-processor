package gov.gsa.fssi.filepreocessor.sourceFiles;

import gov.gsa.fssi.fileprocessor.FileHelper;
import gov.gsa.fssi.fileprocessor.Main;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.fileprocessor.schemas.fields.Field;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The purpose of this class is to quickly load files.
 * 
 * @author davidlarrimore
 *
 */
public class SourceFileManager {
	
	//Status Types.
	public static String INITIALIZED = "initialized";
	public static String LOADED = "loaded";	
	public static String PREPARED = "prepared";		
	public static String COMPLETED = "completed";
	public static String ERROR = "error";			
	
	static Logger logger = LoggerFactory.getLogger(SourceFileManager.class);
	
	/**
	 * The purpose of this function is just to prep file processing. We are not actually loading data yet
	 * @param sourceFileDirectory
	 */
	public static ArrayList<SourceFile> initializeSourceFiles(String sourceFileDirectory) {	
	    logger.debug("Starting initializeSourceFiles('{}')", sourceFileDirectory);		
	    
		ArrayList<SourceFile> sourceFiles = new ArrayList<SourceFile>();	
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(sourceFileDirectory, ".csv ");
		
		File folder = new File(sourceFileDirectory);
		File[] listOfFiles = folder.listFiles();
		int fileCount = 0;
		int totalFileCount = 0;
		
		
		//Loop through files in sourceFileDirectory and populate SourceFile objects
		for (String fileName : fileNames) {
			    fileCount++;
		    	SourceFile newSourceFile = new SourceFile();	
		    	newSourceFile.setFileName(fileName);
		    	int startOfExtension = (int)fileName.lastIndexOf(".")+1;
		    	newSourceFile.setFileExtension(fileName.substring(startOfExtension, fileName.length()));
		    	newSourceFile.setStatus(SourceFileManager.INITIALIZED);
		    	//newSourceFile.setProvider(getProvider(fileName.substring(0, (int)fileName.indexOf("_"))));	
			    sourceFiles.add(newSourceFile);	
			        
		}		    
		
		return sourceFiles;
	}	
	
	
	
	
	
	
	private static Provider getProvider(String providerIdentifier){
		logger.info("Attempting to find Provider using identifier '{}'", providerIdentifier);
		return null;
	}
	
	private static Schema getSchema(String providerIdentifier, Date reportingPeriod){
		logger.info("Attempting to find Schema using Name '{}'", providerIdentifier);
		return null;
	}	
}
