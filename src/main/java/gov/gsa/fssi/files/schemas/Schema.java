package main.java.gov.gsa.fssi.files.schemas;

import java.util.ArrayList;

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
	private ArrayList<String> primaryKeys = null;
	private ArrayList<String> foreignKeys = null;
	private ArrayList<SchemaField> fields = new ArrayList<SchemaField>();

	/**
	 * @param fileName
	 */
	public Schema() {
		super();
	}

	/**
	 * @param fileName
	 * @see File#File(String)
	 */
	public Schema(String fileName) {
		super(fileName);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the provider
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProviderName(String provider) {
		this.providerName = provider;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return
	 */
	public ArrayList<String> getPrimaryKeys() {
		return primaryKeys;
	}

	/**
	 * @param primaryKey
	 */
	public void setPrimaryKeys(ArrayList<String> primaryKey) {
		this.primaryKeys = primaryKey;
	}

	/**
	 * @param string
	 */
	public void addPrimaryKey(String string) {
		this.primaryKeys.add(string);
	}

	/**
	 * @return
	 */
	public ArrayList<String> getForeignKeys() {
		return foreignKeys;
	}

	/**
	 * @param foreignKeys
	 */
	public void setForeignKeys(ArrayList<String> foreignKeys) {
		this.foreignKeys = foreignKeys;
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
	 * @param string
	 */
	public void addForeignKey(String string) {
		this.foreignKeys.add(string);
	}

	/**
	 * @return the fields
	 */
	public ArrayList<SchemaField> getFields() {
		return fields;
	}

	public SchemaField getField(String fieldName) {
		for (SchemaField field : this.getFields()) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * @param fields
	 */
	public void setFields(ArrayList<SchemaField> fields) {
		this.fields = fields;
	}

	/**
	 * @param fields
	 */
	public void addField(SchemaField field) {
		this.fields.add(field);
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
				if (alias.toUpperCase().trim().equals(e.trim().toUpperCase())) {
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

	public ArrayList<String> getFieldNames() {
		ArrayList<String> fieldNames = new ArrayList<String>();
		for (SchemaField field : this.getFields()) {
			fieldNames.add(field.getName());
		}
		return fieldNames;
	}

	public String getFieldName(int headerIndex) {
		for (SchemaField field : this.getFields()) {
			if (field.getHeaderIndex() == headerIndex) {
				return field.getName();
			}
		}
		return null;
	}

	public ArrayList<String> getFieldAndAliasNames() {
		ArrayList<String> aliasNames = new ArrayList<String>();
		for (SchemaField field : this.getFields()) {
			aliasNames.add(field.getName());
			for (String alias : field.getAlias()) {
				aliasNames.add(alias);
			}
		}
		return aliasNames;
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

}
