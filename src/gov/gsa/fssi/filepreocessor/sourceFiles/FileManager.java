package gov.gsa.fssi.filepreocessor.sourceFiles;

import gov.gsa.fssi.fileprocessor.FileHelper;

import java.io.File;
import java.util.ArrayList;


/**
 * The purpose of this class is to quickly load files.
 * 
 * @author davidlarrimore
 *
 */
public class FileManager {
	
	
	/**
	 * The purpose of this function is just to prep file processing. We are not actually loading data yet
	 * @param sourceFileDirectory
	 */
	public static ArrayList<SourceFile> initializeSourceFiles(String sourceFileDirectory) {	
		ArrayList<SourceFile> sourceFiles = new ArrayList<SourceFile>();
		
		System.out.println("Finding Source files to process");
		System.out.println("----------------------------");
		
		
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(sourceFileDirectory, null);
		
		File folder = new File(sourceFileDirectory);
		File[] listOfFiles = folder.listFiles();
		int fileCount = 0;
		
		
		//Loop through files in sourceFileDirectory and populate SourceFile objects
		for (File file : listOfFiles) {
			if (file.isFile()) {
			    fileCount++;
			    
			    if(file.getName().toUpperCase().contains(".XLSX") || file.getName().toUpperCase().contains(".XLS") || file.getName().toUpperCase().contains(".CSV") || file.getName().toUpperCase().contains(".XML")){
			    	//System.out.println("     Added " + file.getName() + " to processing list");
			    	
			    	SourceFile newSourceFile = new SourceFile();	
			    	newSourceFile.setFileName(file.getName());
			    	int startOfExtension = (int)file.getName().lastIndexOf(".")+1;
			    	newSourceFile.setFileExtension(file.getName().substring(startOfExtension, file.getName().length()));
			    	newSourceFile.setStatus("Open");
			        
			    	sourceFiles.add(newSourceFile);	
			        
			    }else{
			    	//System.out.println("     Ignoring " + file.getName() + " Because it is probably not a Source file.");			    	
			    }
			    
		      } else if (file.isDirectory()) {
		        //System.out.println("     Ignoring " + file.getName() + " Because it is a Directory.");
		      }

		}
		
	    System.out.println("...found " + fileCount + " files in sourcefiles directory.");			    
		System.out.println(" ");
		
		return sourceFiles;
		
	}	
	
}
