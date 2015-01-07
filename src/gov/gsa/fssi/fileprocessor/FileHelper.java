package gov.gsa.fssi.fileprocessor;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
	static Logger logger = LoggerFactory.getLogger(FileHelper.class);
	//static Config config = new Config();	    
	
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
	
	
	
	public static String buildFileName(String oldFileName, String newExtension){
		return oldFileName.substring(0, oldFileName.lastIndexOf('.') + 1)  + newExtension.toLowerCase();
	}
}
