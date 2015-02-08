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
	protected static final Logger logger = LoggerFactory.getLogger(File.class);	    
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
		this.setLoadStage(STAGE_LOADED);
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
	public String getLoadStage() {
		return loadStage;
	}
	/**
	 * @param loadStatusLevel String loadStatusLevel to set
	 */
	public void setLoadStage(String loadStatusLevel) {
		this.loadStage = loadStatusLevel;
	}
	/**
	 * @return current loadStatusMessage
	 */
	public ArrayList<String> getLoadStatusMessage() {
		return this.loadStatusMessages;
	}
	public void setLoadStatusMessage(ArrayList<String> loadStatusMessages) {
		this.loadStatusMessages = loadStatusMessages;
	}
	public void addLoadStatusMessage(String loadStatusMessage) {
		this.loadStatusMessages.add(loadStatusMessage);
	}	
	/**
	 * @return current validatorStatusMessage
	 */
	public ArrayList<String> getValidatorStatusMessage() {
		return this.validatorStatusMessages;
	}
	/**
	 * @param validatorStatusMessage String validatorStatusMessage to set
	 */
	public void setValidatorStatusMessage(ArrayList<String> validatorStatusMessages) {
		this.validatorStatusMessages = validatorStatusMessages;
	}
	public void addValidatorStatusMessage(String validatorStatusMessage) {
		this.validatorStatusMessages.add(validatorStatusMessage);
	}	
	/**
	 * @return current validatorStatusLevel
	 */
	public boolean getValidatorStatus() {
		return validatorStatus;
	}
	/**
	 * @return current validatorStatusMessage
	 */
	public String getValidatorStatusName() {
		if(this.getValidatorStatus()) return STATUS_PASS;
		else return STATUS_FAIL;
	}	
	/**
	 * @param validatorStatus boolean validatorStatus to set
	 */
	public void setValidatorStatus(boolean validatorStatus) {
		this.setStatus(validatorStatus);
		if(this.getValidatorStatus()) this.validatorStatus = validatorStatus;		
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
	public boolean getExportStatus() {
		return exportStatus;
	}
	/**
	 * @param exportStatusLevel String exportStatusLevel to set
	 */
	public void setExportStatusLevel(boolean exportStatus) {
		this.setStatus(exportStatus);
		if(this.getExportStatus()) this.exportStatus = exportStatus;
	}
	public boolean getLoadStatus() {
		return loadStatus;
	}
	public void setLoadStatus(boolean loadStatus) {
		this.setStatus(loadStatus);
		if(this.getLoadStatus()) this.loadStatus = loadStatus;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		if(this.getStatus()) this.status = status;
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
	private String loadStage = STAGE_INITIALIZED;
	private boolean status = true;
	private boolean loadStatus = true;
	private boolean validatorStatus = true;
	private boolean exportStatus = false;	
	private ArrayList<String> loadStatusMessages = null;
	private ArrayList<String> validatorStatusMessages = null;

	private String exportStatusMessage = null;

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
	public static final String STATUS_FAIL = "failed";
	public static final String STATUS_PASS = "pass";
	
	public static final String STAGE_LOADED = "loaded";
	public static final String STAGE_VALIDATED = "validated";
	public static final String STAGE_EXPORTED = "exported";
	public static final String STAGE_INITIALIZED = "initialized";

}
