package gov.gsa.fssi.sources;

/**
 * @author David Larrimore
 * 
 */
public class Source {
	private String name = "Contract";
	private String sourceIdentifier = null;
	private String sourceType = null;			
	
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSourceIdentifier() {
		return sourceIdentifier;
	}

	public void setSourceIdentifier(String programIdentifier) {
		this.sourceIdentifier = programIdentifier;
	}

	public Source() {
	}
	
	public Source(String name) {
		this.name = name;
	}
	
	
		
}
