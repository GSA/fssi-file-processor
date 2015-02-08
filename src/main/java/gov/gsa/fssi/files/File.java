package main.java.gov.gsa.fssi.files;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the File Superclass that serves at the parent for all files, regardless of its purpose
 * @author DavidKLarrimore
 *
 */
public class File {
	protected static Logger logger = LoggerFactory.getLogger(File.class);	    
	/**
	 * @return current fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName String fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return current fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}
	/**
	 * @param fileExtension String fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	/**
	 * @param filepart String filepart to add
	 */
	public void addFileNameParts(String filepart) {
		this.fileNameParts.add(filepart);
	}
	/**
	 * @return current fileNameParts
	 */
	public ArrayList<String> getFileNameParts() {
		return fileNameParts;
	}
	/**
	 * @param fileParts ArrayList<String> fileParts to set
	 */
	public void setFileNameParts(ArrayList<String> fileParts) {
		this.fileNameParts = fileParts;
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
		this.setLoadStatusLevel(STATUS_LOADED);
		this.setValidatorStatusLevel(STATUS_LOADED);
		this.setStatusLevel(STATUS_INITIALIZED);
		//defaulted to underscore
		this.setFileNameParts(SEPARATOR_UNDERSCORE);
	}
	
	/**
	 * Blank Constructor
	 */
	public File() {
	}
	
	/**
	 * This method breaks apart the files fileName into descrete parts and pops them into an ArrayList. requires a byte, like '_' an underscore or '-' dash to step through the name and break things apart.
	 * @param filePartSeparator byte filePartSeparator
	 */
	public void setFileNameParts(byte filePartSeparator) {
		ArrayList<String> newfileNameParts = new ArrayList<String>();
		 
		if(fileName == null || fileName.isEmpty()){
			logger.warn("FileName was empty or null, unable to set FileNameParts");
		}else{
			String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
			boolean loopQuit = false;
			
			logger.debug("Attempting to get file parts from fileName '{}'", fileName);
			while (!loopQuit){
				logger.debug("fileNameWithoutExtension: {}", fileNameWithoutExtension);
				if(fileNameWithoutExtension.contains("_")){
					logger.debug("Adding File Part: '{}'", fileNameWithoutExtension.substring(0, fileNameWithoutExtension.indexOf("_")));
					newfileNameParts.add(fileNameWithoutExtension.substring(0, fileNameWithoutExtension.indexOf("_")));
					fileNameWithoutExtension = fileNameWithoutExtension.substring(fileNameWithoutExtension.indexOf("_")+1,fileNameWithoutExtension.length());	
				}else{
					logger.debug("Adding File Part: '{}'", fileNameWithoutExtension);
					newfileNameParts.add(fileNameWithoutExtension);
					loopQuit = true;
				}	
			}
			this.setFileNameParts(newfileNameParts);
			logger.info("FileName '{}' had the following filename parts: {}", fileName, newfileNameParts);
		}
	}	
	/**
	 * @return current loadStatusLevel
	 */
	public String getLoadStatusLevel() {
		return loadStatusLevel;
	}
	/**
	 * @param loadStatusLevel String loadStatusLevel to set
	 */
	public void setLoadStatusLevel(String loadStatusLevel) {
		this.loadStatusLevel = loadStatusLevel;
	}
	/**
	 * @return current loadStatusMessage
	 */
	public String getLoadStatusMessage() {
		return loadStatusMessage;
	}
	public void setLoadStatusMessage(String loadStatusMessage) {
		this.loadStatusMessage = loadStatusMessage;
	}
	/**
	 * @return current validatorStatusMessage
	 */
	public String getValidatorStatusMessage() {
		return validatorStatusMessage;
	}
	/**
	 * @param validatorStatusMessage String validatorStatusMessage to set
	 */
	public void setValidatorStatusMessage(String validatorStatusMessage) {
		this.validatorStatusMessage = validatorStatusMessage;
	}
	/**
	 * @return current validatorStatusLevel
	 */
	public String getValidatorStatusLevel() {
		return validatorStatusLevel;
	}
	/**
	 * @param validatorStatusLevel String validatorStatusLevel to set
	 */
	public void setValidatorStatusLevel(String validatorStatusLevel) {
		this.validatorStatusLevel = validatorStatusLevel;
	}
	/**
	 * @return current statusLevel
	 */
	public String getStatusLevel() {
		return statusLevel;
	}
	/**
	 * @param statusLevel String statusLevel to set
	 */
	public void setStatusLevel(String statusLevel) {
		this.statusLevel = statusLevel;
	}
	/**
	 * @return current exportStatusMessage
	 */
	public String getExportStatusMessage() {
		return exportStatusMessage;
	}
	/**
	 * @param exportStatusMessage String exportStatusMessage to set
	 */
	public void setExportStatusMessage(String exportStatusMessage) {
		this.exportStatusMessage = exportStatusMessage;
	}
	/**
	 * @return current exportStatusLevel
	 */
	public String getExportStatusLevel() {
		return exportStatusLevel;
	}
	/**
	 * @param exportStatusLevel String exportStatusLevel to set
	 */
	public void setExportStatusLevel(String exportStatusLevel) {
		this.exportStatusLevel = exportStatusLevel;
	}

	/**
	 * Full filename including file extension.....Example: "filename.txt"
	 */
	private String fileName = null;
	private String fileExtension = null;
	/**
	 * Individual parts of a files Name. For example the file "example_oo2.txt" consists of the "parts" [exmaple,002]
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte filePartSeparator)
	 */
	private ArrayList<String> fileNameParts = new ArrayList<String>();
	private String loadStatusLevel = null;
	private String loadStatusMessage = null;
	private String validatorStatusMessage = null;
	private String validatorStatusLevel = null;
	private String exportStatusMessage = null;
	private String exportStatusLevel = null;
	/**
	 * Overall Status of File
	 */
	private String statusLevel = null;	
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte filePartSeparator)
	 */
	public static final byte SEPARATOR_UNDERSCORE = '_';
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte filePartSeparator)
	 */
	public static final byte SEPARATOR_DASH = '-';
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte filePartSeparator)
	 */	
	public static final byte SEPARATOR_COMMA = ',';
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte filePartSeparator)
	 */	
	public static final byte SEPARATOR_PIPE = '|';
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte filePartSeparator)
	 */	
	public static final byte SEPARATOR_TILDE = '~';
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte filePartSeparator)
	 */	
	public static final byte SEPARATOR_FORWARDSLASH = '/';
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte filePartSeparator)
	 */	
	public static final byte SEPARATOR_BACKSLASH = '\\';
	public static final String FILETYPE_CSV = "csv";
	public static final String FILETYPE_XLSX = "xlsx";
	public static final String FILETYPE_XLS = "xls";
	public static final String FILETYPE_XML = "xml";
	/**
	 * A status of "fatal" means that an unrecoverable issue was found. The file MUST stop processing
	 */
	public static final String STATUS_FATAL = "fatal";
	/**
	 * A status of "error" means that the file is able to be processed but contains issues that MUST be addressed.
	 */
	public static final String STATUS_ERROR = "error";
	/**
	 * A status of "warning" means that the file is able to be addressed but contains issues.
	 */
	public static final String STATUS_WARNING = "warning";
	public static final String STATUS_LOADED = "loaded";
	public static final String STATUS_PASS = "pass";
	public static final String STATUS_VALIDATED = "validated";
	public static final String STATUS_EXPORTED = "exported";
	public static final String STATUS_INITIALIZED = "initialized";
	public static final String STATUS_FAIL = "failed";
}
