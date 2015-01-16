package gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;

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
	/**
	 * A boolean value which indicates whether a field must have a value in every row of the table. An empty string is considered to be a missing value.
	 */
	public static String TYPE_REQUIRED = "required"; 
	
	/**
	 * An integer that specifies the minimum number of characters for a value
	 */
	public static String TYPE_MINLENGTH = "minLength"; 
	/**
	 * An integer that specifies the maximum number of characters for a value
	 */
	public static String TYPE_MAXLENGTH = "maxLength";
	/**
	 * A regular expression that can be used to test field values. If the regular expression matches then the value is valid. Values will be treated as a string of characters. It is recommended that values of this field conform to the standard XML Schema regular expression syntax.
	 * @see <a href="http://www.w3.org/TR/xmlschema-2/#regexs">standard XML Schema regular expression syntax</a>
	 * @see <a href="http://www.regular-expressions.info/xml.html">Regular Expressions</a>
	 */
	public static String TYPE_PATTERN = "pattern";
	/**
	 * specifies a minimum value for a field. This is different to minLength which checks number of characters. A minimum value constraint checks whether a field value is greater than or equal to the specified value. The range checking depends on the type of the field. E.g. an integer field may have a minimum value of 100; a date field might have a minimum date. If a minimum value constraint is specified then the field descriptor MUST contain a type key
	 */
	public static String TYPE_MINIMUM = "minimum";
	/**
	 * specifies a maximum value for a field. This is different to maxLength which checks number of characters. A maximum value constraint checks whether a field value is less than or equal to the specified value. The range checking depends on the type of the field. E.g. an integer field may have a maximum value of 100; a date field might have a maximum date. If a maximum value constraint is specified then the field descriptor MUST contain a type key
	 */
	public static String TYPE_MAXIMUM = "maximum";
	

//	maximum – as above, but specifies a maximum value for a field.
	
	//List of Constraint Options which come from the constraints attributes
	public static String OPTION_EFFECTIVEDATE = "effectiveDate";
	public static String OPTION_LEVEL = "level";
	
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
		validTypes.add(TYPE_MINLENGTH);
		validTypes.add(TYPE_MAXLENGTH);
		validTypes.add(TYPE_PATTERN);
		validTypes.add(TYPE_MINIMUM);
		validTypes.add(TYPE_MAXIMUM);
		
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
