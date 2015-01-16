package gov.gsa.fssi.files;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
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
	
	/**
	 * @return
	 */
	public void file() {
		
	}
	
	
	/**
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @return
	 */
	public String getFileExtension() {
		return fileExtension;
	}
	
	/**
	 * @param fileExtension
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	/**
	 * @param filepart
	 */
	public void addFileNameParts(String filepart) {
		this.fileNameParts.add(filepart);
	}
	/**
	 * @return the fileParts
	 */
	/**
	 * @return
	 */
	public ArrayList<String> getFileNameParts() {
		return fileNameParts;
	}
	/**
	 * @param fileParts the fileParts to set
	 */
	/**
	 * @param fileParts
	 */
	public void setFileNameParts(ArrayList<String> fileParts) {
		this.fileNameParts = fileParts;
	}

	/**
	 * This Method sets fileNameParts based upon input file name.
	 */
	/**
	 * @param fileName
	 */
	public void setFileNameParts(String fileName) {
		if(fileName == null || fileName.isEmpty()){
			logger.warn("FileName was empty or null, unable to set FileNameParts");
		}else{
			this.setFileNameParts(FileHelper.setFileNameParts(fileName, FileHelper.SEPARATOR_UNDERSCORE));
		}
	}	
	
	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
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
		this.setFileNameParts(this.getFileName());
	}
	
	public File() {
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
