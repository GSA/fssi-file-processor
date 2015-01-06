package gov.gsa.fssi.fileprocessor.schemas;

import gov.gsa.fssi.fileprocessor.schemas.schemaFields.SchemaField;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Schema {
	static Logger logger = LoggerFactory.getLogger(Schema.class);
	private String name = null;
	private String providerName = null;	
	private String version = null;
	private ArrayList<SchemaField> fields = new ArrayList<SchemaField>();	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the provider
	 */
	public String getProviderName() {
		return providerName;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProviderName(String provider) {
		this.providerName = provider;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the fields
	 */
	public ArrayList<SchemaField> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<SchemaField> fields) {
		this.fields = fields;
	}

	public void print(){
		logger.debug("Name: '{}' Version: '{}' Provider: '{}' ", this.getName(), this.getVersion(), this.getProviderName());
		printSchemaFields();	
	}
	
	private void printSchemaFields() {
		for (SchemaField field : this.getFields()) {
			field.print();
		}
	}
	
	
}
