package gov.gsa.fssi.fileprocessor;

import java.io.File;
import java.util.ArrayList;

public class FileHelper {

	
	public static ArrayList<String> getFilesFromDirectory(String directoryName){
		return getFilesFromDirectory(directoryName, null);
	}	
	
	public static ArrayList<String> getFilesFromDirectory(String directoryName, String whitelist){
		ArrayList<String> fileList = new ArrayList<String>();
		//System.out.println("Envoking FileHelper.getFilesFromDirectory");	
		//System.out.println("     Looking in \""+ directoryName + "\"...");	
		
		
		File folder = new File(directoryName);
		File[] listOfFiles = folder.listFiles();
		int fileCount = 0;
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
			    String fileExtension = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
			    
			    if(file.isDirectory()){
			    	//System.out.println("          Ignoring " + file.getName() + " Because it is a directory");	    
			    }else if(whitelist != null && whitelist != "" && !whitelist.contains(fileExtension)){
			    	//System.out.println("          Ignoring " + file.getName() + " Because it is not in whitelist");	 
			    }else if(whitelist != null && whitelist != "" && whitelist.contains(fileExtension)){
			    	//System.out.println("          Found " + file.getName());	 
			    	fileList.add(file.getName());
				    fileCount++;
			    }else{
			    	//System.out.println("          Found " + file.getName());	 
			    	fileList.add(file.getName());
				    fileCount++;
			    }
			}

		}
		
	    System.out.println("     Found " + fileCount + " files in \""+ directoryName + "\"");			    

		return fileList;
	}		    
}
