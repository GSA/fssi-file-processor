package gov.gsa.fssi.fileprocessor.providers;

import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.fileprocessor.schemas.fields.Field;

import java.util.ArrayList;

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
	private String providerIdentifier = null;
	private String providerName = null;
	private String fileIdentifier = null;
	private String fileInputType = null;
	private String fileOutputType = null;
	private String schema = null;
	private String dataMappingTemplate = null;
	private String schemaValidation = null;
	private String sourceType = null;
	private String providerEmail = "StrategicSourcing@gsa.gov";	
	
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
	 * @return the schemaValidation
	 */
	public String getSchemaValidation() {
		return schemaValidation;
	}


	/**
	 * @param schemaValidation the schemaValidation to set
	 */
	public void setSchemaValidation(String schemaValidation) {
		this.schemaValidation = schemaValidation;
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
	 * @return the fileIdentifier
	 */
	public String getFileIdentifier() {
		return fileIdentifier;
	}

	/**
	 * @param fileIdentifier the fileIdentifier to set
	 */
	public void setFileIdentifier(String fileIdentifier) {
		this.fileIdentifier = fileIdentifier;
	}

	/**
	 * @return the fileInputType
	 */
	public String getFileInputType() {
		return fileInputType;
	}

	/**
	 * @param fileInputType the fileInputType to set
	 */
	public void setFileInputType(String fileInputType) {
		this.fileInputType = fileInputType;
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

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @return the dataMappingTemplate
	 */
	public String getDataMappingTemplate() {
		return dataMappingTemplate;
	}

	/**
	 * @param dataMappingTemplate the dataMappingTemplate to set
	 */
	public void setDataMappingTemplate(String dataMappingTemplate) {
		this.dataMappingTemplate = dataMappingTemplate;
	}

	/**
	 * @return the sourceType
	 */
	public String getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	public void printProvider(){
		logger.debug("printProvider: '{}' Name: '{}' Provider: '{}' Identifier: '{}' Email: {}", this.getProviderId(), this.getProviderName(), this.getProviderIdentifier(), this.getProviderEmail());

	}
	
	
}
