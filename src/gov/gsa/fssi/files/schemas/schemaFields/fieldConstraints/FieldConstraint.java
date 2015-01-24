package gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.helpers.LoaderStatus;
import gov.gsa.fssi.helpers.ValidatorStatus;

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
	private LoaderStatus builderStatus = new LoaderStatus();
	private ValidatorStatus validatorStatus = new ValidatorStatus();	
	
	//List of constraint types
	/**
	 * A boolean value which indicates whether a field must have a value in every row of the table. An empty string is considered to be a missing value.
	 */
	public static final String TYPE_REQUIRED = "required"; 
	
	/**
	 * An integer that specifies the minimum number of characters for a value
	 */
	public static final String TYPE_MINLENGTH = "minLength"; 
	/**
	 * An integer that specifies the maximum number of characters for a value
	 */
	public static final String TYPE_MAXLENGTH = "maxLength";
	/**
	 * A regular expression that can be used to test field values. If the regular expression matches then the value is valid. Values will be treated as a string of characters. It is recommended that values of this field conform to the standard XML Schema regular expression syntax.
	 * @see <a href="http://www.w3.org/TR/xmlschema-2/#regexs">standard XML Schema regular expression syntax</a>
	 * @see <a href="http://www.regular-expressions.info/xml.html">Regular Expressions</a>
	 */
	public static final String TYPE_PATTERN = "pattern";
	/**
	 * specifies a minimum value for a field. This is different to minLength which checks number of characters. A minimum value constraint checks whether a field value is greater than or equal to the specified value. The range checking depends on the type of the field. E.g. an integer field may have a minimum value of 100; a date field might have a minimum date. If a minimum value constraint is specified then the field descriptor MUST contain a type key
	 */
	public static final String TYPE_MINIMUM = "minimum";
	/**
	 * specifies a maximum value for a field. This is different to maxLength which checks number of characters. A maximum value constraint checks whether a field value is less than or equal to the specified value. The range checking depends on the type of the field. E.g. an integer field may have a maximum value of 100; a date field might have a maximum date. If a maximum value constraint is specified then the field descriptor MUST contain a type key
	 */
	public static final String TYPE_MAXIMUM = "maximum";
	

//	maximum � as above, but specifies a maximum value for a field.
	
	//List of Constraint Options which come from the constraints attributes
	public static final String OPTION_EFFECTIVEDATE = "effectiveDate";
	public static final String OPTION_LEVEL = "level";
	
	//List of Constraint Levels
	public static final String LEVEL_ERROR = "error";
	public static final String LEVEL_WARNING = "warning";
	public static final String LEVEL_DEBUG = "debug";
	
	private String type = null;
	private String value = null;	
	private String level = null;
	private Date effectiveDate = null;
	private HashMap<String, String> options = new HashMap<String, String>();
	//TODO:Add support for option "IgnoreCase" that will ignore case during validation
	
	public String getType() {
		return type;
	}
	public void setType(String name) {
		this.type = name;
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
		logger.debug("          Constraint Type:'{}' Value:'{}' Level:'{}' EffectiveDate:'{}' Options:{}",  this.getType(),  this.getValue(), this.getLevel(), this.getEffectiveDate(), this.getOptions());
	}
	/**
	 * @return
	 */
	public ValidatorStatus getValidatorStatus() {
		return validatorStatus;
	}
	/**
	 * @return
	 */
	public String getValidatorStatusMessage(){
		return validatorStatus.getStatusMessage();
	}
	/**
	 * @return
	 */
	public String getValidatorStatusLevel(){
		return validatorStatus.getLevel();
	}	
	/**
	 * @param buildStatus
	 */
	public void setValidatorStatus(ValidatorStatus validatorStatus) {
		this.validatorStatus = validatorStatus;
	}
	/**
	 * @param level
	 */
	public void setValidatorStatusLevel(String level) {
		this.validatorStatus.setLevel(level);
	}
	/**
	 * @param level
	 */
	public void setValidatorStatusError() {
		this.validatorStatus.setLevel(ValidatorStatus.ERROR);
	}	
	/**
	 * @param message
	 */
	public void setValidatorStatusMessage(String message) {
		this.validatorStatus.setStatusMessage(message);
	}	
	/**
	 * @return
	 */
	public LoaderStatus getBuilderStatus() {
		return builderStatus;
	}
	/**
	 * @return
	 */
	public String getBuilderStatusMessage(){
		return builderStatus.getStatusMessage();
	}
	/**
	 * @return
	 */
	public String getBuilderStatusLevel(){
		return builderStatus.getLevel();
	}	
	/**
	 * @param buildStatus
	 */
	public void setBuilderStatus(LoaderStatus buildStatus) {
		this.builderStatus = buildStatus;
	}
	/**
	 * @param level
	 */
	public void setBuilderStatusError() {
		this.builderStatus.setLevel(LoaderStatus.ERROR);
	}	
	/**
	 * @param level
	 */
	public void setBuilderStatusLevel(String level) {
		this.builderStatus.setLevel(level);
	}
	/**
	 * @param message
	 */
	public void setBuilderStatusMessage(String message) {
		this.builderStatus.setStatusMessage(message);
	}
	/**
	 * This constructor class takes a file name and uses it to initialize the basic elements of a SourceFile
	 * @param fileName - This should be in name.ext format.
	 */
	
}
