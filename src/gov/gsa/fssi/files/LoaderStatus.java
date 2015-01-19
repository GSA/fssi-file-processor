package gov.gsa.fssi.files;

public class LoaderStatus {
	public static final String ERROR = "error";
	public static final String INITIALIZED = "initialized";	
	public static final String LOADED = "loaded";	
	public static final String MAPPED = "mapped";		
	public static final String PROCESSED = "processed";		
	public static final String VALIDATED = "validated";		
	public static final String STAGED = "staged";
	private String level;
	private String statusMessage;
	
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public LoaderStatus(){
		
	}
	
	
}
