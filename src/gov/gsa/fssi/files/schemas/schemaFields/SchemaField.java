package gov.gsa.fssi.files.schemas.schemaFields;

import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaField {
	static Logger logger = LoggerFactory.getLogger(SchemaField.class);
	
	
	private String name = null;
	private String title = null;	
	private String type = null;		
	private String format = null;	
	private String status = null;
	private String description = null;
	private ArrayList<FieldConstraint> constraints = new ArrayList<FieldConstraint>();	
	private ArrayList<String> alias = new ArrayList<String>();	
	
	public static String STATUS_ERROR = "error";
	public static String STATUS_WARNING = "warning";
	public static String STATUS_VALIDATED = "validated";
	
	/**
	 * a string (of arbitrary length)
	 */
	public static String TYPE_STRING = "string"; 
	/**
	 * a number including floating point numbers.
	 */
	public static String TYPE_NUMBER = "number";
	/**
	 * an integer.
	 */
	public static String TYPE_INTEGER = "integer";
	/**
	 * a date. This MUST be in ISO6801 format YYYY-MM-DD or, if not, a format field must be provided describing the structure.
	 */
	public static String TYPE_DATE = "date"; 
	/**
	 * a time without a date
	 */
	public static String TYPE_TIME = "time";
	/**
	 * a date-time. This MUST be in ISO 8601 format of YYYY-MM-DDThh:mm:ssZ in UTC time or, if not, a format field must be provided.
	 */
	public static String TYPE_DATETIME = "datetime";//a time without a date
	/**
	 * a boolean value (1/0, true/false).
	 */
	public static String TYPE_BOOLEAN = "boolean"; 
	
	//public static String TYPE_BINARY = "binary"; //a boolean value (1/0, true/false).
	//public static String TYPE_OBJECT = "object"; //(alias xml) an XML-encoded object
	
	/**
	 * has one of the following structures: "longitude, latitude
	 */
	public static String TYPE_GEOPOINT = "geopoint";
	
	/**
	 * an array in "value,value,value" format
	 */
	public static String TYPE_ARRAY = "array";
	/**
	 * value of field may be any type
	 */
	public static String TYPE_ANY = "any"; 
	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}	
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @param format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the constraints
	 */
	public ArrayList<FieldConstraint> getConstraints() {
		return constraints;
	}
	/**
	 * @param constraintMap the constraints to set
	 */
	public void setConstraints(ArrayList<FieldConstraint> constraintMap) {
		this.constraints = constraintMap;
	}
	/**
	 * @param constraint
	 */
	public void addConstraint(FieldConstraint constraint) {
		this.constraints.add(constraint);
	}	
	/**
	 * @return the alias
	 */
	public ArrayList<String> getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(ArrayList<String> alias) {
		this.alias = alias;
	}
	/**
	 * @param alias
	 */
	public void addAlias(String alias) {
		this.alias.add(alias);
	}
	public void removeAlias(String alias){
		int index = this.alias.indexOf(alias);
		this.alias.remove(index);
	}
	/**
	 * Print Field
	 */
	public void print() {
		logger.debug("     Field '{}' Title: '{}' Type: '{}' Description: '{}' Format: '{}' Alias: {}}",  this.getName(), this.getTitle(), this.getType(), this.getDescription(), this.getFormat(), this.getAlias());
		printConstraints();
	}
	
	private void printConstraints(){
		for (FieldConstraint constraint : this.getConstraints()) {
			constraint.print();
		}
	}
	
	/**
	 * Validates that a string is a valid SchemaFieldType 
	 * @param string to compare
	 * @return true or false
	 */
	public boolean isValidType(String e){
		ArrayList<String> arrayList = buildTypeArray();
		return (arrayList.contains(e)? true:false);
	}
	/**
	 * @return
	 */
	private ArrayList<String> buildTypeArray() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(TYPE_STRING);
		arrayList.add(TYPE_NUMBER);
		arrayList.add(TYPE_INTEGER);
		arrayList.add(TYPE_DATE);
		arrayList.add(TYPE_TIME);
		arrayList.add(TYPE_DATETIME);
		arrayList.add(TYPE_BOOLEAN);
		arrayList.add(TYPE_GEOPOINT);
		arrayList.add(TYPE_ARRAY);
		arrayList.add(TYPE_ANY);
		return arrayList;
	}
	
	
}
