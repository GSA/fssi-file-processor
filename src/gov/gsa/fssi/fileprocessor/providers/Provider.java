package gov.gsa.fssi.fileprocessor.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is our provider object. the provider object controls how each file is identified and processed.
 * 
 * 
 * @author David Larrimore
 * 
 */
public class Provider {
	static Logger logger = LoggerFactory.getLogger(Provider.class);
	private String providerId = null;
	private String providerName = null;	
	private String providerIdentifier = null;
	private String providerEmail = "StrategicSourcing@gsa.gov";		
	private String fileOutputType = null;

	
	/**
	 * @return the contactEmail
	 */
	public String getProviderEmail() {
		return providerEmail;
	}

	/**
	 * @param contactEmail the contactEmail to set
	 */
	public void setProviderEmail(String contactEmail) {
		this.providerEmail = contactEmail;
	}		
	
	/**
	 * Constructor Method
	 */
	public Provider() {
	}
	
	
	public Provider(String name) {
		this.providerId = name;
	}
		
	
	
	/**
	 * @return the providerId
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * @return the providerIdentifier
	 */
	public String getProviderIdentifier() {
		return providerIdentifier;
	}

	/**
	 * @param providerIdentifier the providerIdentifier to set
	 */
	public void setProviderIdentifier(String providerIdentifier) {
		this.providerIdentifier = providerIdentifier;
	}

	/**
	 * @return the providerName
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @param providerName the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * @return the fileOutputType
	 */
	public String getFileOutputType() {
		return fileOutputType;
	}

	/**
	 * @param fileOutputType the fileOutputType to set
	 */
	public void setFileOutputType(String fileOutputType) {
		this.fileOutputType = fileOutputType;
	}
	
	public void print(){
		logger.debug("Id: '{}' Name: '{}' Identifier: '{}' Email: '{}' OutputType '{}'", this.getProviderId(), this.getProviderName(), this.getProviderIdentifier(), this.getProviderEmail(), this.getFileOutputType());	
	}
	
	
}
