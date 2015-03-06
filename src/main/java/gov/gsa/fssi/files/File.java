package main.java.gov.gsa.fssi.files;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the File Superclass that serves at the parent for all files,
 * regardless of its purpose
 * 
 * @author DavidKLarrimore
 *
 */
public class File {
	/**
	 * @return
	 */
	private static final Logger logger = LoggerFactory.getLogger(File.class);
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	public static final String SEPARATOR_PERIOD = ".";
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	public static final String SEPARATOR_UNDERSCORE = "_";
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	public static final String SEPARATOR_DASH = "-";
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	public static final String SEPARATOR_COMMA = ",";
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	public static final String SEPARATOR_PIPE = "|";
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	public static final String SEPARATOR_TILDE = "~";
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	public static final String SEPARATOR_FORWARDSLASH = "/";
	/**
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	public static final String SEPARATOR_BACKSLASH = "\\";
	public static final String FILETYPE_CSV = "csv";
	public static final String FILETYPE_XLSX = "xlsx";
	public static final String FILETYPE_XLS = "xls";
	public static final String FILETYPE_XML = "xml";
	/**
	 * A status of "fatal" means that an unrecoverable issue was found. The file
	 * MUST stop processing
	 */
	public static final String STATUS_FATAL = "fatal";
	/**
	 * A status of "error" means that the file is able to be processed but
	 * contains issues that MUST be addressed.
	 */
	public static final String STATUS_ERROR = "error";
	/**
	 * A status of "warning" means that the file is able to be addressed but
	 * contains issues.
	 */
	public static final String STATUS_WARNING = "warning";
	public static final String STATUS_FAIL = "failed";

	public static final String STATUS_PASS = "pass";
	public static final String STAGE_LOADING = "loading";
	public static final String STAGE_LOADED = "loaded";
	public static final String STAGE_VALIDATED = "validated";
	public static final String STAGE_EXPORTED = "exported";

	public static final String STAGE_INITIALIZED = "initialized";
	/**
	 * Full filename including file extension.....Example: "filename.txt"
	 */
	private String fileName = null;
	private String fileExtension = null;
	/**
	 * Individual parts of a files Name. For example the file "example_oo2.txt"
	 * consists of the "parts" [exmaple,002]
	 * 
	 * @see main.java.gov.gsa.fssi.files.File#setFileNameParts(byte
	 *      filePartSeparator)
	 */
	private List<String> fileNameParts = new ArrayList<String>();
	private String loadStage = STAGE_INITIALIZED;
	private boolean status = true;
	private int maxErrorLevel = 0;
	private boolean loadStatus = true;
	private boolean validatorStatus = true;
	private boolean exportStatus = true;
	private List<String> loadStatusMessages = new ArrayList<String>();
	private List<String> validatorStatusMessages = new ArrayList<String>();
	private List<String> exportStatusMessages = new ArrayList<String>();
	
	/**
	 * Blank Constructor
	 */
	public File() {
	}
	
	public File(File file){
		this.exportStatus = file.exportStatus;
		
		if(file.exportStatusMessages != null){
			for(String exportStatusMessage: file.exportStatusMessages){
				this.exportStatusMessages.add(new String(exportStatusMessage));
			}
		}
		
		if(file.fileExtension != null) this.fileExtension = new String(file.fileExtension);
		
		if(file.fileName != null) this.fileName = new String(file.fileName);
		
		if(file.fileNameParts != null){
			for(String fileNamePart: file.fileNameParts){
				this.fileNameParts.add(new String(fileNamePart));
			}
		}		
		
		if(file.loadStage != null) this.loadStage = new String(file.loadStage);
		
		this.loadStatus = file.loadStatus;
		
		if(file.loadStatusMessages != null){
			for(String loadStatusMessage: file.loadStatusMessages){
				this.loadStatusMessages.add(new String(loadStatusMessage));
			}
		}
		
		this.maxErrorLevel = file.maxErrorLevel;
		this.status = file.status;
		this.validatorStatus = file.validatorStatus;

		if(file.validatorStatusMessages != null){
			for(String validatorStatusMessage: file.validatorStatusMessages){
				this.validatorStatusMessages.add(new String(validatorStatusMessage));
			}
		}		
	}
	
	
	/**
	 * This constructor class takes a file name and uses it to initialize the
	 * basic elements of a SourceFile
	 * 
	 * @param fileName
	 *            - This should be in name.ext format.
	 */
	public File(String fileName) {
		logger.info("Constructing SourceFile using fileName '{}'", fileName);
		this.setFileName(fileName);
		int startOfExtension = fileName.lastIndexOf(".") + 1;
		this.setFileExtension(fileName.substring(startOfExtension,
				fileName.length()));
		this.setLoadStage(STAGE_LOADED);
		// defaulted to underscore
		this.setFileNameParts(SEPARATOR_UNDERSCORE);
	}

	/**
	 * @param filepart
	 *            String filepart to add
	 */
	public void addFileNameParts(String filepart) {
		this.fileNameParts.add(filepart);
	}

	public void addLoadStatusMessage(String loadStatusMessage) {
		this.loadStatusMessages.add(loadStatusMessage);
	}

	public void addValidatorStatusMessage(String validatorStatusMessage) {
		this.validatorStatusMessages.add(validatorStatusMessage);
	}

	/**
	 * @return current exportStatusLevel
	 */
	public boolean getExportStatus() {
		return exportStatus;
	}

	/**
	 * @return current exportStatusMessage
	 */
	public List<String> getExportStatusMessages() {
		return exportStatusMessages;
	}

	/**
	 * @return current fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}

	/**
	 * @return current fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return current fileNameParts
	 */
	public List<String> getFileNameParts() {
		return fileNameParts;
	}

	/**
	 * @return current loadStatusLevel
	 */
	public String getLoadStage() {
		return loadStage;
	}

	public boolean getLoadStatus() {
		return loadStatus;
	}

	/**
	 * @return current loadStatusMessage
	 */
	public List<String> getLoadStatusMessage() {
		return this.loadStatusMessages;
	}

	/**
	 * @return
	 */
	public int getMaxErrorLevel() {
		return maxErrorLevel;
	}

	public boolean getStatus() {
		return status;
	}

	public String getStatusName() {
		if (status)
			return "Pass";
		return "Fail";
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
	public List<String> getValidatorStatusMessages() {
		return this.validatorStatusMessages;
	}

	/**
	 * @return current validatorStatusMessage
	 */
	public String getValidatorStatusName() {
		if (this.getValidatorStatus())
			return STATUS_PASS;
		else
			return STATUS_FAIL;
	}

	/**
	 * @param exportStatusLevel
	 *            String exportStatusLevel to set
	 */
	public void setExportStatus(boolean exportStatus) {
		this.setStatus(exportStatus);
		if (this.getExportStatus())
			this.exportStatus = exportStatus;
	}

	/**
	 * @param exportStatusMessage
	 *            String exportStatusMessage to set
	 */
	public void setExportStatusMessages(List<String> exportStatusMessages) {
		this.exportStatusMessages = exportStatusMessages;
	}
	
	public void addExportStatusMessages(String exportStatusMessage) {
		this.exportStatusMessages.add(exportStatusMessage);
	}	

	/**
	 * @param fileExtension
	 *            String fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	/**
	 * @param fileName
	 *            String fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param fileParts
	 *            ArrayList<String> fileParts to set
	 */
	public void setFileNameParts(List<String> fileParts) {
		this.fileNameParts = fileParts;
	}

	/**
	 * This method breaks apart the files fileName into descrete parts and pops
	 * them into an ArrayList. requires a byte, like '_' an underscore or '-'
	 * dash to step through the name and break things apart.
	 * 
	 * @param filePartSeparator
	 *            byte filePartSeparator
	 */
	public void setFileNameParts(String filePartSeparator) {

		List<String> newfileNameParts = new ArrayList<String>();

		if (fileName == null || fileName.isEmpty()) {
			logger.warn("FileName was empty or null, unable to set FileNameParts");
		} else {
			String fileNameWithoutExtension = fileName.substring(0,
					fileName.lastIndexOf(SEPARATOR_PERIOD));
			boolean loopQuit = false;
			logger.debug("Setting file name parts using the '{}' separator",
					filePartSeparator);
			logger.info("Attempting to get file parts from fileName '{}'",
					fileName);
			while (!loopQuit) {
				logger.debug("fileNameWithoutExtension: {}",
						fileNameWithoutExtension);
				if (fileNameWithoutExtension.contains(filePartSeparator)) {
					logger.debug("Adding File Part: '{}'",
							fileNameWithoutExtension.substring(0,
									fileNameWithoutExtension
											.indexOf(filePartSeparator)));
					newfileNameParts.add(fileNameWithoutExtension
							.substring(0, fileNameWithoutExtension
									.indexOf(filePartSeparator)));
					fileNameWithoutExtension = fileNameWithoutExtension
							.substring(fileNameWithoutExtension
									.indexOf(filePartSeparator) + 1,
									fileNameWithoutExtension.length());
				} else {
					logger.debug("Adding File Part: '{}'",
							fileNameWithoutExtension);
					newfileNameParts.add(fileNameWithoutExtension);
					loopQuit = true;
				}
			}
			this.setFileNameParts(newfileNameParts);
			logger.info("FileName '{}' had the following filename parts: {}",
					fileName, newfileNameParts);
		}
	}

	/**
	 * @param loadStatusLevel
	 *            String loadStatusLevel to set
	 */
	public void setLoadStage(String loadStatusLevel) {
		this.loadStage = loadStatusLevel;
	}

	public void setLoadStatus(boolean loadStatus) {
		this.setStatus(loadStatus);
		if (this.getLoadStatus())
			this.loadStatus = loadStatus;
	}

	public void setLoadStatusMessage(List<String> loadStatusMessages) {
		this.loadStatusMessages = loadStatusMessages;
	}

	public void setMaxErrorLevel(int errorLevel) {
		setStatus(errorLevel);
		if (errorLevel > this.maxErrorLevel)
			this.maxErrorLevel = errorLevel;
	}

	public void setStatus(boolean status) {
		if (this.getStatus())
			this.status = status;
	}

	/**
	 * This sets the overall Pass/Fail status of the Data object. Once it is
	 * fail (false), it cannot change back
	 * 
	 * @param validatorStatus
	 *            the validatorStatus to set
	 */
	public void setStatus(int errorLevel) {
		if (errorLevel == 3)
			this.status = false;
	}

	/**
	 * @param validatorStatus
	 *            boolean validatorStatus to set
	 */
	public void setValidatorStatus(boolean validatorStatus) {
		this.setStatus(validatorStatus);
		if (this.getValidatorStatus())
			this.validatorStatus = validatorStatus;
	}

	public static String getErrorLevelName(int errorLevel) {
		String name = null;
		if (errorLevel <= 3) {
			switch (errorLevel) {
			case 0:
				name = File.STATUS_PASS;
				break;
			case 1:
				name = File.STATUS_WARNING;
				break;
			case 2:
				name = File.STATUS_ERROR;
				break;
			case 3:
				name = File.STATUS_FATAL;
				break;
			default:
				break;
			}
		}
		return name;
	}
	
	public static char getErrorLevelInitial(int errorLevel) {
		char name = 'P';
		if (errorLevel <= 3) {
			switch (errorLevel) {
			case 0:
				name = 'P';
				break;
			case 1:
				name = 'W';
				break;
			case 2:
				name = 'E';
				break;
			case 3:
				name = 'F';
				break;
			default:
				break;
			}
		}
		return name;
	}	
}
