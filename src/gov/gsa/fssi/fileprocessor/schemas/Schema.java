package gov.gsa.fssi.fileprocessor.schemas;

import gov.gsa.fssi.fileprocessor.schemas.fields.Field;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Schema {
	static Logger logger = LoggerFactory.getLogger(Schema.class);
	private String name = null;
	private String providerName = null;	
	private String version = null;
	private String effectiveReportingPeriod = null;
	private ArrayList<Field> fields = new ArrayList<Field>();	
	
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
	 * @return the effectiveReportingPeriod
	 */
	public String getEffectiveReportingPeriod() {
		return effectiveReportingPeriod;
	}
	/**
	 * @param effectiveReportingPeriod the effectiveReportingPeriod to set
	 */
	public void setEffectiveReportingPeriod(String effectiveReportingPeriod) {
		this.effectiveReportingPeriod = effectiveReportingPeriod;
	}
	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	public void print(){
		ArrayList<Field> fields = this.getFields();
		logger.debug("Name: '{}' Version: '{}' Provider: '{}' EffectiveReportingPeriod: '{}' ", this.getName(), this.getVersion(), this.getProviderName(), this.getEffectiveReportingPeriod());
		printSchemaFields(fields);	
	}
	
	private void printSchemaFields(ArrayList<Field> fields) {
		for (Field field : fields) {
			field.print();
		}
	}
	
	
}
