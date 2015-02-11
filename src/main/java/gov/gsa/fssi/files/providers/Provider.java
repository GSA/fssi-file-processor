package main.java.gov.gsa.fssi.files.providers;

import main.java.gov.gsa.fssi.files.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is our provider object. the provider object controls how each file is
 * identified and processed.
 * 
 * @author David Larrimore
 * 
 */
public class Provider extends File {
	private static final Logger logger = LoggerFactory
			.getLogger(Provider.class);
	private String schemaName = null;

	/**
	 * id of the provider, which uniquely identifies the provider. Not currently
	 * used
	 */
	private String providerId = null;
	/**
	 * Name of the provider, which has a one-to-many relationship with the
	 * providerIdentifier
	 */
	private String providerName = null;
	/**
	 * Unique provider Identifier. This must be unique
	 */
	private String providerIdentifier = null;
	private String providerEmail = "StrategicSourcing@gsa.gov";
	private String fileOutputType = null;

	/**
	 * Generic Constructor
	 */
	public Provider() {
	}

	/**
	 * Constructor
	 * 
	 * @param fileName
	 *            String fileName
	 * @see main.java.gov.gsa.fssi.files.File#File(String)
	 */
	public Provider(String fileName) {
		super(fileName);
	}

	/**
	 * @return current providerEmail
	 */
	public String getProviderEmail() {
		return providerEmail;
	}

	/**
	 * @param contactEmail
	 *            String contactEmail to set
	 */
	public void setProviderEmail(String contactEmail) {
		this.providerEmail = contactEmail;
	}

	/**
	 * @return current providerId
	 */
	public String getProviderId() {
		return providerId;
	}

	/**
	 * @param providerId
	 *            the providerId to set
	 */
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	/**
	 * @return current providerIdentifier
	 */
	public String getProviderIdentifier() {
		return providerIdentifier;
	}

	/**
	 * @param providerIdentifier
	 *            the providerIdentifier to set
	 */
	public void setProviderIdentifier(String providerIdentifier) {
		this.providerIdentifier = providerIdentifier;
	}

	/**
	 * @return current providerName
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @param providerName
	 *            the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * @return current fileOutputType
	 */
	public String getFileOutputType() {
		return fileOutputType;
	}

	/**
	 * @return current schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @param schemaName
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * @param fileOutputType
	 *            the fileOutputType to set
	 */
	public void setFileOutputType(String fileOutputType) {
		this.fileOutputType = fileOutputType;
	}

	/**
	 * Print this Provider using logger.debug
	 */
	public void print() {
		logger.debug(
				"Id: '{}' Name: '{}' Identifier: '{}' Email: '{}' OutputType '{}' SchemaName '{}'",
				this.getProviderId(), this.getProviderName(),
				this.getProviderIdentifier(), this.getProviderEmail(),
				this.getFileOutputType(), this.getSchemaName());
	}

}
