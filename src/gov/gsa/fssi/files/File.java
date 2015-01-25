package gov.gsa.fssi.files;

import gov.gsa.fssi.config.Config;
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
	 * This constructor class takes a file name and uses it to initialize the basic elements of a SourceFile
	 * @param fileName - This should be in name.ext format.
	 */
	public File(String fileName) {
		logger.info("Constructing SourceFile using fileName '{}'", fileName);
		this.setFileName(fileName);
		int startOfExtension = fileName.lastIndexOf(".")+1;
		this.setFileExtension(fileName.substring(startOfExtension, fileName.length()));
		this.setLoadStatusLevel(STATUS_LOADED);
		//defaulted to underscore
		this.setFileNameParts(SEPARATOR_UNDERSCORE);
	}
	
	public File() {
	}
	
	/**
	 * This Method sets fileNameParts based upon input file name.
	 */
	public void setFileNameParts(byte filePartSeparator) {
		ArrayList<String> fileNameParts = new ArrayList<String>();
		
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
		
		this.setFileNameParts(fileNameParts);
	}	

	private String fileName = null;
	private String fileExtension = null;
	private ArrayList<String> fileNameParts = new ArrayList<String>();
	//private String status = null;
	protected static Logger logger = LoggerFactory.getLogger(File.class);
	protected static Config config = new Config();	    
	public String getLoadStatusLevel() {
		return loadStatusLevel;
	}
	public void setLoadStatusLevel(String loadStatusLevel) {
		this.loadStatusLevel = loadStatusLevel;
	}
	public String getLoadStatusMessage() {
		return loadStatusMessage;
	}
	public void setLoadStatusMessage(String loadStatusMessage) {
		this.loadStatusMessage = loadStatusMessage;
	}
	public String getValidatorStatusMessage() {
		return validatorStatusMessage;
	}

	public void setValidatorStatusMessage(String validatorStatusMessage) {
		this.validatorStatusMessage = validatorStatusMessage;
	}

	public String getValidatorStatusLevel() {
		return validatorStatusLevel;
	}

	public void setValidatorStatusLevel(String validatorStatusLevel) {
		this.validatorStatusLevel = validatorStatusLevel;
	}

	private String loadStatusLevel = null;
	private String loadStatusMessage = null;
	private String validatorStatusMessage = null;
	private String validatorStatusLevel = null;
	public static final byte SEPARATOR_UNDERSCORE = '_';
	public static final byte SEPARATOR_DASH = '-';
	public static final byte SEPARATOR_COMMA = ',';
	public static final byte SEPARATOR_PIPE = '|';
	public static final byte SEPARATOR_TILDE = '~';
	public static final byte SEPARATOR_FORWARDSLASH = '/';
	public static final byte SEPARATOR_BACKSLASH = '\\';
	public static final String FILETYPE_CSV = "csv";
	public static final String FILETYPE_XLSX = "xlsx";
	public static final String FILETYPE_XLS = "xls";
	public static final String FILETYPE_XML = "xml";
	public static final String STATUS_ERROR = "error";
	public static final String STATUS_WARNING = "warning";
	public static final String STATUS_LOADED = "loaded";
	public static final String STATUS_PASS = "pass";
}
