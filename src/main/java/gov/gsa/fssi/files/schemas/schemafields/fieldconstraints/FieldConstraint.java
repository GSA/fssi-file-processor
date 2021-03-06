package main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints;

import java.util.Date;
import java.util.HashMap;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This object manages elements inside of the Schema.
 * 
 * @author David Larrimore
 *
 */
public class FieldConstraint {
	private static final Logger logger = LoggerFactory
			.getLogger(SchemaField.class);

	/**
	 * This denotes as to whether or not the Schema Field Constraint is valid
	 * and can be used
	 */
	private String validatorStatusLevel = null;

	// List of constraint types
	/**
	 * A boolean value which indicates whether a field must have a value in
	 * every row of the table. An empty string is considered to be a missing
	 * value.
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
	 * A regular expression that can be used to test field values. If the
	 * regular expression matches then the value is valid. Values will be
	 * treated as a string of characters. It is recommended that values of this
	 * field conform to the standard XML Schema regular expression syntax.
	 * 
	 * @see <a href="http://www.w3.org/TR/xmlschema-2/#regexs">standard XML
	 *      Schema regular expression syntax</a>
	 * @see <a href="http://www.regular-expressions.info/xml.html">Regular
	 *      Expressions</a>
	 */
	public static final String TYPE_PATTERN = "pattern";
	/**
	 * specifies a minimum value for a field. This is different to minLength
	 * which checks number of characters. A minimum value constraint checks
	 * whether a field value is greater than or equal to the specified value.
	 * The range checking depends on the type of the field. E.g. an integer
	 * field may have a minimum value of 100; a date field might have a minimum
	 * date. If a minimum value constraint is specified then the field
	 * descriptor MUST contain a type key
	 */
	public static final String TYPE_MINIMUM = "minimum";
	/**
	 * specifies a maximum value for a field. This is different to maxLength
	 * which checks number of characters. A maximum value constraint checks
	 * whether a field value is less than or equal to the specified value. The
	 * range checking depends on the type of the field. E.g. an integer field
	 * may have a maximum value of 100; a date field might have a maximum date.
	 * If a maximum value constraint is specified then the field descriptor MUST
	 * contain a type key
	 */
	public static final String TYPE_MAXIMUM = "maximum";

	// maximum � as above, but specifies a maximum value for a field.

	// List of Constraint Options which come from the constraints attributes
	public static final String OPTION_EFFECTIVEDATE = "effectiveDate";
	public static final String OPTION_LEVEL = "level";

	// List of Constraint Levels
	public static final String LEVEL_FATAL = "fatal";
	public static final String LEVEL_ERROR = "error";
	public static final String LEVEL_WARNING = "warning";
	public static final String LEVEL_DEBUG = "debug";

	private String type = "";
	private String value = "";
	private String levelName = "";
	private int level = 0;
	private Date effectiveDate = null;
	private HashMap<String, String> options = new HashMap<String, String>();

	// TODO:Add support for option "IgnoreCase" that will ignore case during
	// validation

	
	/**
	 * Default Constructor
	 */
	public FieldConstraint(){
		
	}
	
	
	/**
	 * Copy Constructor
	 * @param fieldConstraint
	 */
	public FieldConstraint(FieldConstraint fieldConstraint) {
		if(fieldConstraint.effectiveDate != null) this.effectiveDate = new Date(fieldConstraint.effectiveDate.getTime());

		this.level = fieldConstraint.level;
		if(fieldConstraint.levelName != null) this.levelName = new String(fieldConstraint.levelName);
		
		if(fieldConstraint.type != null) this.type = new String(fieldConstraint.type);
		if(fieldConstraint.validatorStatusLevel != null) this.validatorStatusLevel = new String(fieldConstraint.validatorStatusLevel);
		if(fieldConstraint.value != null) this.value = new String(fieldConstraint.value);
		
		if(fieldConstraint.options != null){
			this.options.putAll(new HashMap<String, String>(fieldConstraint.options));		
		}
	}

	public void addOption(String key, String value) {
		this.options.put(key, value);
	}

	public Date getEffectiveDate() {
		return (effectiveDate == null ? null
				: new Date(effectiveDate.getTime()));
	}

	public int getLevel() {
		return level;
	}

	public String getLevelName() {
		return levelName;
	}

	public HashMap<String, String> getOptions() {
		return options;
	}

	public String getOptionValue(String key) {
		return options.get(key);
	}

	public String getRuleText() {
		return this.getType() + "(" + this.getValue() + ")";
	}

	public String getType() {
		return type;
	}

	public String getValidatorStatusLevel() {
		return validatorStatusLevel;
	}

	public String getValue() {
		return value;
	}

	public void print() {
		logger.debug(
				"          Constraint Type:'{}' Value:'{}' Level:'{}' EffectiveDate:'{}' Options:{}",
				this.getType(),
				this.getValue(),
				this.getLevelName(),
				(this.getEffectiveDate() == null ? "" : this.getEffectiveDate()),
				this.getOptions());
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = new Date(effectiveDate.getTime());
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLevel(String levelName) {
		this.level = getErrorLevel(levelName);
	}

	public static int getErrorLevel(String levelName) {
		if (levelName.equalsIgnoreCase(File.STATUS_FATAL)) {
			return 3;
		} else if (levelName.equalsIgnoreCase(File.STATUS_ERROR)) {
			return 2;
		} else if (levelName.equalsIgnoreCase(File.STATUS_WARNING)) {
			return 1;
		}
		return 0;

	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public void setLoadStatusLevel(int loadStatusLevel) {
		this.level = loadStatusLevel;
	}


	public void setOptions(HashMap<String, String> options) {
		this.options = options;
	}

	public void setType(String name) {
		this.type = name;
	}

	public void setValidatorStatusLevel(String validatorStatusLevel) {
		this.validatorStatusLevel = validatorStatusLevel;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
