package gov.gsa.fssi.files;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.fileprocessor.sourceFiles.SourceFile;
import gov.gsa.fssi.helpers.FileHelper;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the File Superclass that serves at the parent for all files, regardless of its purpose
 * @author DavidKLarrimore
 *
 */
public class File {
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileExtension() {
		return fileExtension;
	}
	
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	public void addFileNameParts(String filepart) {
		this.fileNameParts.add(filepart);
	}
	/**
	 * @return the fileParts
	 */
	public ArrayList<String> getFileNameParts() {
		return fileNameParts;
	}
	/**
	 * @param fileParts the fileParts to set
	 */
	public void setFileNameParts(ArrayList<String> fileParts) {
		this.fileNameParts = fileParts;
	}
	/**
	 * This Method sets fileNameParts based upon the sourceFiles file name. If filename is not set, then filenameparts will be empty
	 */
	public void setFileNameParts() {
		if(fileName == null || this.fileName.isEmpty()){
			logger.warn("FileName is not set, unable to set FileNameParts");
		}else{
			this.setFileNameParts(this.fileName);
		}
	}
	/**
	 * This Method sets fileNameParts based upon input file name.
	 */
	public void setFileNameParts(String fileName) {
		if(fileName == null || fileName.isEmpty()){
			logger.warn("FileName was empty or null, unable to set FileNameParts");
		}else{
			this.setFileNameParts(FileHelper.setFileNameParts(fileName, FileHelper.SEPARATOR_UNDERSCORE));
		}
	}	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * This constructor class takes a file name and uses it to initialize the basic elements of a SourceFile
	 * @param fileName - This should be in name.ext format.
	 */
	public File(String fileName) {
		logger.info("Constructing SourceFile using fileName '{}'", fileName);
		this.setFileName(fileName);
		int startOfExtension = fileName.lastIndexOf(".")+1;
		this.setFileExtension(fileName.substring(startOfExtension, fileName.length()));
		this.setStatus(SourceFile.STATUS_INITIALIZED);
		this.setFileNameParts();
	}
	
	public String fileName = null;
	private String fileExtension = null;
	private ArrayList<String> fileNameParts = new ArrayList<String>();
	private String status = null;
	protected static Logger logger = LoggerFactory.getLogger(File.class);
	protected static Config config = new Config();	    
	public static String STATUS_ERROR = "error";
	public static String STATUS_WARNING = "warning";
	public static String STATUS_INITIALIZED = "initialized";	
	public static String STATUS_LOADED = "loaded";	
	public static String STATUS_MAPPED = "mapped";		
	public static String STATUS_PROCESSED = "processed";		
	public static String STATUS_VALIDATED = "validated";		
	public static String STATUS_STAGED = "staged";
}
