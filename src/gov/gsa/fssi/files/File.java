package gov.gsa.fssi.files;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.sourceFiles.utils.loaders.SourceFileLoader;

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
	 * @return
	 */
	public ValidatorStatus getValidatorStatus() {
		return validatorStatus;
	}
	/**
	 * @return
	 */
	public String getValidatorStatusMessage(){
		return validatorStatus.getStatusMessage();
	}
	/**
	 * @return
	 */
	public String getValidatorStatusLevel(){
		return validatorStatus.getLevel();
	}	
	/**
	 * @param buildStatus
	 */
	public void setValidatorStatus(ValidatorStatus validatorStatus) {
		this.validatorStatus = validatorStatus;
	}
	/**
	 * @param level
	 */
	public void setValidatorStatusLevel(String level) {
		this.validatorStatus.setLevel(level);
	}
	/**
	 * @param level
	 */
	public void setValidatorStatusError() {
		this.validatorStatus.setLevel(ValidatorStatus.ERROR);
	}	
	/**
	 * @param message
	 */
	public void setValidatorStatusMessage(String message) {
		this.validatorStatus.setStatusMessage(message);
	}	
	/**
	 * @return
	 */
	public LoaderStatus getLoaderStatus() {
		return loaderStatus;
	}
	/**
	 * @return
	 */
	public String getLoaderStatusMessage(){
		return loaderStatus.getStatusMessage();
	}
	/**
	 * @return
	 */
	public String getLoaderStatusLevel(){
		return loaderStatus.getLevel();
	}	
	/**
	 * @param buildStatus
	 */
	public void setLoaderStatus(LoaderStatus buildStatus) {
		this.loaderStatus = buildStatus;
	}
	/**
	 * @param level
	 */
	public void setLoaderStatusError() {
		this.loaderStatus.setLevel(LoaderStatus.ERROR);
	}	
	/**
	 * @param level
	 */
	public void setLoaderStatusLevel(String level) {
		this.loaderStatus.setLevel(level);
	}
	/**
	 * @param message
	 */
	public void setLoaderStatusMessage(String message) {
		this.loaderStatus.setStatusMessage(message);
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
		this.loaderStatus.setLevel(LoaderStatus.INITIALIZED);;
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
		
		this.setFileNameParts(fileNameParts);
	}	

	public String fileName = null;
	private String fileExtension = null;
	private ArrayList<String> fileNameParts = new ArrayList<String>();
	//private String status = null;
	protected static Logger logger = LoggerFactory.getLogger(File.class);
	protected static Config config = new Config();	    
	private LoaderStatus loaderStatus = new LoaderStatus();
	private ValidatorStatus validatorStatus = new ValidatorStatus();	
	public static final byte SEPARATOR_UNDERSCORE = '_';
	public static final byte SEPARATOR_DASH = '-';
	public static final byte SEPARATOR_COMMA = ',';
	public static final byte SEPARATOR_PIPE = '|';
	public static final byte SEPARATOR_TILDE = '~';
	public static final byte SEPARATOR_FORWARDSLASH = '/';
	public static final byte SEPARATOR_BACKSLASH = '\\';
}
