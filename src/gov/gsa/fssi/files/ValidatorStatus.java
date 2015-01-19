package gov.gsa.fssi.files;

public class ValidatorStatus {
	public static final String ERROR = "error";
	public static final String WARNING = "warning";
	public static final String PASS = "pass";	
	
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
	
	public ValidatorStatus(){
		
	}
	
	
}
