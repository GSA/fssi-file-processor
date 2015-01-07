package gov.gsa.fssi.fileprocessor.schemas.schemaElements;

import gov.gsa.fssi.fileprocessor.schemas.schemaFields.SchemaField;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This object manages elements inside of the Schema. 
 * 
 * @author David Larrimore
 *
 */
public class SchemaElement {
	static Logger logger = LoggerFactory.getLogger(SchemaField.class);
	
	private String name = null;
	private String value = null;	
	private HashMap<String, String> options = new HashMap<String, String>();
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	public void print() {
		logger.debug("          Constraint '{}' Value: '{}' Options: {}",  this.getName(),  this.getValue(),  this.getOptions());
	}	
	
}
