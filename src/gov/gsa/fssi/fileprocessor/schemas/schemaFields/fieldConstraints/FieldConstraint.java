package gov.gsa.fssi.fileprocessor.schemas.schemaFields.fieldConstraints;

import gov.gsa.fssi.fileprocessor.schemas.schemaFields.SchemaField;

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
	public static String TYPE_REQUIRED = "required";
	public static String TYPE_DATATYPE = "dataType";
	public static String TYPE_LENGTH = "length";
	public static String TYPE_CUSTOM = "custom";
	public static String TYPE_MATCH_LIST = "matchList";
	public static String TYPE_POSITIVE_NUMBER = "positiveNumber";
	public static String TYPE_NEGATIVE_NUMBER = "negativeNumber";
	
	//List of Options which come from the constraints attributes
	public static String OPTION_EFFECTIVEDATE = "effectiveDate";
	public static String OPTION_LEVEL = "level";
	public static String LEVEL_ERROR = "error";
	public static String LEVEL_WARNING = "warning";
	
	private String constraintType = null;
	private String value = null;	
	private String level = null;
	private Date effectiveDate = new Date();
	private HashMap<String, String> options = new HashMap<String, String>();
	
	
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
