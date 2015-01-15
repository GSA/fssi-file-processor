package gov.gsa.fssi.fileprocessor.schemas.schemaFields;

import gov.gsa.fssi.fileprocessor.schemas.schemaFields.fieldConstraints.FieldConstraint;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaField {
	static Logger logger = LoggerFactory.getLogger(SchemaField.class);
	
	
	private String name = null;
	private String title = null;	
	private String type = null;		
	private String format = null;		
	private String description = null;
	private ArrayList<FieldConstraint> constraints = new ArrayList<FieldConstraint>();	
	private ArrayList<String> alias = new ArrayList<String>();		
	
	public static String TYPE_STRING = "string"; //a string (of arbitrary length)
	public static String TYPE_NUMBER = "number"; //a number including floating point numbers.
	public static String TYPE_INTEGER = "integer"; //an integer.
	public static String TYPE_DATE = "date"; //a date. This MUST be in ISO6801 format YYYY-MM-DD or, if not, a format field must be provided describing the structure.
	public static String TYPE_TIME = "time"; //a time without a date
	public static String TYPE_DATETIME = "datetime";//a time without a date
	public static String TYPE_BOOLEAN = "boolean"; //a date-time. This MUST be in ISO 8601 format of YYYY-MM-DDThh:mm:ssZ in UTC time or, if not, a format field must be provided.
	//public static String TYPE_BINARY = "binary"; //a boolean value (1/0, true/false).
	//public static String TYPE_OBJECT = "object"; //(alias xml) an XML-encoded object
	//public static String TYPE_GEOPOINT = "geopoint"; //has one of the following structures: "longitude, latitude"
	public static String TYPE_ARRAY = "array"; //an array in "value,value,value" format
	public static String TYPE_ANY = "any"; //value of field may be any type
	
	
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
}
