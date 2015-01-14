package gov.gsa.fssi.helpers;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
	static Logger logger = LoggerFactory.getLogger(FileHelper.class);
	//static Config config = new Config();
	public static byte SEPARATOR_UNDERSCORE = '_';
	public static byte SEPARATOR_DASH = '-';
	public static byte SEPARATOR_COMMA = ',';
	public static byte SEPARATOR_PIPE = '|';
	public static byte SEPARATOR_TILDE = '~';
	public static byte SEPARATOR_FORWARDSLASH = '/';
	public static byte SEPARATOR_BACKSLASH = '\\';
	
	public static ArrayList<String> getFilesFromDirectory(String directoryName){
		return getFilesFromDirectory(directoryName, null);
	}	
	
	public static ArrayList<String> getFilesFromDirectory(String directoryName, String whitelist){
		ArrayList<String> fileList = new ArrayList<String>();
		logger.info("getFilesFromDirectory Looking in '{}' for '{}' files.", directoryName, whitelist);	
		
		
		File folder = new File(directoryName);
		File[] listOfFiles = folder.listFiles();
		int fileCount = 0;
		int totalFileCount = 0;
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
				totalFileCount ++;
				String fileExtension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
			    if(file.isDirectory()){
			    	logger.info("Ignoring '{}' because it is a directory", whitelist);	    
			    }else if(whitelist != null && whitelist != "" && !whitelist.contains(fileExtension)){
			    	logger.info("Ignoring '{}' because it is not in whitelist", file.getName());	 
			    }else if(whitelist != null && whitelist != "" && whitelist.contains(fileExtension)){
			    	logger.info("Added '{}' to ArrayList", file.getName());	 	 
			    	fileList.add(file.getName());
				    fileCount++;
			    }else{
			    	logger.info("Added '{}' to ArrayList", file.getName());	 
			    	fileList.add(file.getName());
				    fileCount++;
			    }
			}

		}
		
	    logger.info("Added {} out of {} files", fileCount, totalFileCount);			    

		return fileList;
	}		
	
	
	
	public static String buildNewFileName(String oldFileName, String newExtension){
		return oldFileName.substring(0, oldFileName.lastIndexOf('.') + 1)  + newExtension.toLowerCase();
	}
	
	
	/**
	 * This Method sets fileNameParts based upon input file name.
	 * This 
	 */
	public static ArrayList<String> setFileNameParts(String fileName, byte filePartSeparator) {
		ArrayList<String> fileNameParts = new ArrayList<String>();
		
		if(fileName == null || fileName.isEmpty()){
			logger.warn("FileName was empty or null, unable to set FileNameParts");
		}else{
			String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
			boolean loopQuit = false;
			
			logger.debug("Attemtping to get file parts from fileName '{}'", fileName);
			while (!loopQuit){
				logger.debug("fileNameWithoutExtension: {}", fileNameWithoutExtension);
				if(fileNameWithoutExtension.contains("_")){
					logger.debug("Adding File Part: '{}'", fileNameWithoutExtension.substring(0, fileNameWithoutExtension.indexOf("_")));
					fileNameParts.add(fileName.substring(0, fileNameWithoutExtension.indexOf("_")));
					fileNameWithoutExtension = fileNameWithoutExtension.substring(fileNameWithoutExtension.indexOf("_")+1,fileNameWithoutExtension.length());	
				}else{
					logger.debug("Adding File Part: '{}'", fileNameWithoutExtension);
					fileNameParts.add(fileNameWithoutExtension);
					loopQuit = true;
				}	
			}
			logger.info("FileName '{}' had the following filename parts: {}", fileName, fileNameParts);
		}
		
		return fileNameParts;
	}	
}
