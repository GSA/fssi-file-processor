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

	public static final String DEFAULT_PROVIDER_NAME = "N/A";
	public static final String DEFAULT_PROVIDER_IDENTIFIER = "N/A";
	public static final String DEFAULT_PROVIDER_EMAIL = "N/A";
	public static final String DEFAULT_FILE_OUTPUT_TYPE = File.FILETYPE_CSV;
	public static final String DEFAULT_SCHEMA_NAME = "N/A";

	private String schemaName = null;

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

	public Provider(Provider provider) {
		if(provider.fileOutputType != null) this.fileOutputType = new String(provider.fileOutputType);
		if(provider.providerEmail != null) this.providerEmail = new String(provider.providerEmail);		
		if(provider.providerIdentifier != null) this.providerIdentifier = new String(provider.providerIdentifier);	
		if(provider.providerName != null) this.providerName = new String(provider.providerName);
		if(provider.schemaName != null) this.schemaName = new String(provider.schemaName);
	}

	/**
	 * @return current fileOutputType
	 */
	public String getFileOutputType() {
		return fileOutputType;
	}

	/**
	 * @return current providerEmail
	 */
	public String getProviderEmail() {
		return providerEmail;
	}

	/**
	 * @return current providerIdentifier
	 */
	public String getProviderIdentifier() {
		return providerIdentifier;
	}

	/**
	 * @return current providerName
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @return current schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * Print this Provider using logger.debug
	 */
	public void print() {
		logger.debug(
				"Name: '{}' Identifier: '{}' Email: '{}' OutputType '{}' SchemaName '{}'",
				this.getProviderName(),
				this.getProviderIdentifier(), this.getProviderEmail(),
				this.getFileOutputType(), this.getSchemaName());
	}

	/**
	 * @param fileOutputType
	 *            the fileOutputType to set
	 */
	public void setFileOutputType(String fileOutputType) {
		this.fileOutputType = fileOutputType;
	}

	/**
	 * @param contactEmail
	 *            String contactEmail to set
	 */
	public void setProviderEmail(String contactEmail) {
		this.providerEmail = contactEmail;
	}


	/**
	 * @param providerIdentifier
	 *            the providerIdentifier to set
	 */
	public void setProviderIdentifier(String providerIdentifier) {
		this.providerIdentifier = providerIdentifier;
	}

	/**
	 * @param providerName
	 *            the providerName to set
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * @param schemaName
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

}
