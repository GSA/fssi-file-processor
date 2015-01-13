package gov.gsa.fssi.fileprocessor.schemas.schemaFields.fieldConstraints;

import gov.gsa.fssi.fileprocessor.schemas.schemaFields.SchemaField;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This object manages elements inside of the Schema. 
 * 
 * @author David Larrimore
 *
 */
public class FieldConstraint {
	static Logger logger = LoggerFactory.getLogger(SchemaField.class);
	
	//List of constraint types
	public static String TYPE_REQUIRED = "REQUIRED";
	public static String TYPE_DATATYPE = "DATATYPE";
	public static String TYPE_LENGTH = "LENGTH";
	public static String TYPE_CUSTOM = "CUSTOM";
	public static String TYPE_MATCH_LIST = "MATCHLIST";
	public static String TYPE_POSITIVE_NUMBER = "POSITIVENUMBER";
	public static String TYPE_NEGATIVE_NUMBER = "NEGATIVENUMBER";
	
	//List of Constraint Options which come from the constraints attributes
	public static String OPTION_EFFECTIVEDATE = "EFFECTIVEDATE";
	public static String OPTION_LEVEL = "LEVEL";
	
	//List of Constraint Levels
	public static String LEVEL_ERROR = "ERROR";
	public static String LEVEL_WARNING = "WARNING";
	public static String LEVEL_DEBUG = "DEBUG";
	
	private String constraintType = null;
	private String value = null;	
	private String level = null;
	private Date effectiveDate = new Date();
	private HashMap<String, String> options = new HashMap<String, String>();
	
	
	/**
	 * @param string
	 * @return
	 */
	public boolean isValidType(String string){
		//TODO: use java java.lang.reflect.Field to iterate through globals to generate ArrayList
		ArrayList<String> validTypes = new ArrayList<String>();
		validTypes.add(TYPE_REQUIRED);
		validTypes.add(TYPE_DATATYPE);
		validTypes.add(TYPE_LENGTH);
		validTypes.add(TYPE_CUSTOM);
		validTypes.add( TYPE_MATCH_LIST);
		validTypes.add(TYPE_POSITIVE_NUMBER);
		validTypes.add(TYPE_NEGATIVE_NUMBER);
		
		for(String type: validTypes){
			if (type.trim().toUpperCase().equals(string.trim().toUpperCase())){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @param string
	 * @return
	 */
	public boolean isValidOption(String string){
		//TODO: use java java.lang.reflect.Field to iterate through globals to generate ArrayList
		ArrayList<String> validList = new ArrayList<String>();
		validList.add(OPTION_EFFECTIVEDATE);
		validList.add(OPTION_LEVEL);
		
		for(String type: validList){
			if (type.trim().toUpperCase().equals(string.trim().toUpperCase())){
				return true;
			}
		}
		
		return false;
	}	
	
	/**
	 * @param string
	 * @return
	 */
	public boolean isValidOptionLevel(String string){
		//TODO: use java java.lang.reflect.Field to iterate through globals to generate ArrayList
		ArrayList<String> validList = new ArrayList<String>();
		validList.add(LEVEL_ERROR);
		validList.add(LEVEL_WARNING);
		validList.add(LEVEL_DEBUG);
		
		for(String type: validList){
			if (type.trim().toUpperCase().equals(string.trim().toUpperCase())){
				return true;
			}
		}
		
		return false;
	}
	
	public String getConstraintType() {
		return constraintType;
	}
	public void setConstraintType(String name) {
		this.constraintType = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public HashMap<String, String> getOptions() {
		return options;
	}
	public String getOptionValue(String key) {
		return options.get(key);
	}
	public String getOptionValue(int index) {
		return options.get(index);
	}	
	public void setOptions(HashMap<String, String> options) {
		this.options = options;
	}
	public void addOption(String key, String value) {
		this.options.put(key, value);
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void print() {
		logger.debug("          Constraint '{}' Value: '{}' Options: {}",  this.getConstraintType(),  this.getValue(),  this.getOptions());
	}	
	
}
