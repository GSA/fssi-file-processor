package main.java.gov.gsa.fssi.files.schemas;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Schema object is ment to mimic the JSON table schema located here:
 * http://dataprotocols.org/json-table-schema/
 * 
 * @author DavidKLarrimore
 *
 */
public class Schema extends File {
	private static final Logger logger = LoggerFactory.getLogger(Schema.class);
	private String name = null;
	private String providerName = null;
	private String version = null;
	private List<String> primaryKeys = null;
	private List<String> foreignKeys = null;
	private List<SchemaField> fields = new ArrayList<SchemaField>();

	/**
	 * @param fileName
	 */
	public Schema() {
		super();
	}

	public Schema(Schema schema){
		super(schema);
		
		setExportStatus(schema.getExportStatus());
		setValidatorStatus(schema.getValidatorStatus());
		setStatus(schema.getStatus());
		setMaxErrorLevel(schema.getMaxErrorLevel());
		
		
		if(schema.fields != null){
			for(SchemaField field:schema.fields) this.fields.add(new SchemaField(field));
		}
		if(schema.name != null) this.name = new String(schema.name);
		if(schema.providerName != null) this.providerName = new String(schema.providerName);
		
		if(schema.foreignKeys != null){
			for(String foreignKey:schema.foreignKeys) this.foreignKeys.add(new String(foreignKey));
		}
		if(schema.primaryKeys != null){
			for(String primaryKey:schema.primaryKeys) this.primaryKeys.add(new String(primaryKey));
		}
		
		if(schema.version != null) this.version = new String(schema.version);
	}
	
	/**
	 * @param fileName
	 * @see File#File(String)
	 */
	public Schema(String fileName) {
		super(fileName);
	}

	/**
	 * @param fields
	 */
	public void addField(SchemaField field) {
		this.fields.add(field);
	}

	/**
	 * @param string
	 */
	public void addForeignKey(String string) {
		this.foreignKeys.add(string);
	}

	/**
	 * @param string
	 */
	public void addPrimaryKey(String string) {
		this.primaryKeys.add(string);
	}

	public SchemaField getField(String fieldName) {
		for (SchemaField field : this.getFields()) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	public List<String> getFieldAndAliasNames() {
		List<String> aliasNames = new ArrayList<String>();
		for (SchemaField field : this.getFields()) {
			aliasNames.add(field.getName());
			for (String alias : field.getAlias()) {
				aliasNames.add(alias);
			}
		}
		return aliasNames;
	}

	public String getFieldName(int headerIndex) {
		for (SchemaField field : this.getFields()) {
			if (field.getHeaderIndex() == headerIndex) {
				return field.getName();
			}
		}
		return null;
	}

	/**
	 * @param e
	 * @return
	 */
	public String getFieldName(String e) {
		for (SchemaField field : this.getFields()) {
			if (field.getName().toUpperCase().trim()
					.equals(e.toUpperCase().trim())) {
				logger.info("Source File Header '{}' matches field {}",
						e.toUpperCase(), field.getName());
				return field.getName().trim().toUpperCase();
			}
			for (String alias : field.getAlias()) {
				if (alias.toUpperCase().trim().equalsIgnoreCase(e.trim())) {
					logger.info(
							"Source File Header '{}' matches alias in field {}",
							e.toUpperCase(), field.getName());
					return field.getName().trim().toUpperCase();
				}
			}
		}
		logger.info("Header field name '{}' not found in schema {}", e.trim()
				.toUpperCase(), this.getName());
		return null;
	}

	public List<String> getFieldNames() {
		List<String> fieldNames = new ArrayList<String>();
		for (SchemaField field : this.getFields()) {
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}

	/**
	 * @return the fields
	 */
	public List<SchemaField> getFields() {
		return fields;
	}

	/**
	 * @return
	 */
	public List<String> getForeignKeys() {
		return foreignKeys;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public List<String> getPrimaryKeys() {
		return primaryKeys;
	}

	/**
	 * @return the provider
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	public void print() {
		logger.debug(
				"Name: '{}' Version: '{}' Build Status:'{}' Validator Status: '{}'",
				this.getName(), this.getVersion(), this.getLoadStage(),
				this.getValidatorStatusName());
	}

	public void printAll() {
		printSchemaFields();
	}

	private void printSchemaFields() {
		for (SchemaField field : this.getFields()) {
			field.print();
		}
	}

	public void removeField(int index) {
		this.fields.remove(index);
	}

	public void removeField(SchemaField schemaField) {
		this.fields.remove(schemaField);
	}

	public void removeField(String fieldName) {
		if (this.getField(fieldName) != null) {
			this.removeField(this.getField(fieldName));
		} else {
			logger.error("Could not remove field '{}' from Schema '{}'",
					fieldName, this.getName());
		}
	}

	/**
	 * @param list
	 */
	public void setFields(List<SchemaField> list) {
		this.fields = list;
	}

	/**
	 * @param foreignKeys
	 */
	public void setForeignKeys(List<String> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param primaryKey
	 */
	public void setPrimaryKeys(List<String> primaryKey) {
		this.primaryKeys = primaryKey;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProviderName(String provider) {
		this.providerName = provider;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	public SchemaField getSchemaFieldFromName(String fieldName){
		for(SchemaField field:getFields()){
			if(field.isField(fieldName)) return field;
		}
		return null;
	}
	
	public boolean isSchemaFieldFromName(String fieldName){
		for(SchemaField field:getFields()){
			if(field.isField(fieldName)) return true;
		}
		return false;
	}	
	
	
    @Override
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
