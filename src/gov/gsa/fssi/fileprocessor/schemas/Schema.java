package gov.gsa.fssi.fileprocessor.schemas;

import gov.gsa.fssi.fileprocessor.schemas.fields.Field;

import java.util.ArrayList;

public class Schema {
	
	private String name = null;
	private String provider = null;	
	private String version = null;
	private String effectiveReportingPeriod = null;
	private String endingReportingPeriod = null;	
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
	public String getProvider() {
		return provider;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
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
	 * @return the endingReportingPeriod
	 */
	public String getEndingReportingPeriod() {
		return endingReportingPeriod;
	}
	/**
	 * @param endingReportingPeriod the endingReportingPeriod to set
	 */
	public void setEndingReportingPeriod(String endingReportingPeriod) {
		this.endingReportingPeriod = endingReportingPeriod;
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

	public void printlnSchema(){
		ArrayList<Field> fields = this.getFields();
		
		System.out.println("Printing out Schema \"" + this.getName() + "\"");
		System.out.println("----------------------------");
		System.out.println("Version: " + this.getVersion());
		System.out.println("Provider: " + this.getProvider());
		System.out.println("Effective Reporting Period: " + this.getEffectiveReportingPeriod());
		System.out.println("Ending Reporting Period: " + this.getEndingReportingPeriod());
		
		System.out.println("Field List");
		for (Field field : fields) {
			System.out.println("     Name: " + field.getName());
			System.out.println("     Description: " + field.getDescription());
			System.out.println("     Alias: " + field.getAlias());
			System.out.println("     Constraints: " + field.getConstraints());
			System.out.println(" ");
			
		}
		System.out.println(" ");
		
	}
	
}
