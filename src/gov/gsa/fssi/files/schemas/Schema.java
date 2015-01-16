package gov.gsa.fssi.files.schemas;

import gov.gsa.fssi.files.File;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;

import java.util.ArrayList;

/**
 * The Schema object is ment to mimic the JSON table schema located here: http://dataprotocols.org/json-table-schema/
 * 
 * @author DavidKLarrimore
 *
 */
public class Schema extends File{

	private String name = null;
	private String providerName = null;	
	private String version = null;

	private ArrayList<String> primaryKeys = null;
	private ArrayList<String> foreignKeys = null;	
	private ArrayList<SchemaField> fields = new ArrayList<SchemaField>();	
	
	
	/**
	 * @param fileName
	 */
	public Schema(String fileName) {
		super();
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
	 * @return the provider
	 */
	public String getProviderName() {
		return providerName;
	}
	/**
	 * @param provider the provider to set
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
	 * @param version the version to set
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
	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<SchemaField> fields) {
		this.fields = fields;
	}
	/**
	 * @param sourceFileHeaderName
	 * @return
	 */
	public String getFieldName(String sourceFileHeaderName){
		for(SchemaField field: this.getFields()){
			if(field.getName().toUpperCase().trim().equals(sourceFileHeaderName.toUpperCase().trim())){
				logger.info("Source File Header '{}' matches field {}",sourceFileHeaderName.toUpperCase(), field.getName());
				return field.getName().trim().toUpperCase();
			}
			for(String alias:field.getAlias()){
				if(alias.toUpperCase().trim().equals(sourceFileHeaderName.trim().toUpperCase())){
					logger.info("Source File Header '{}' matches alias in field {}",sourceFileHeaderName.toUpperCase(), field.getName());
					return field.getName().trim().toUpperCase();					
				}
			}
		}
		logger.info("Header field name '{}' not found in schema {}",sourceFileHeaderName.trim().toUpperCase(), this.getName());
		return sourceFileHeaderName.trim().toUpperCase();
	}
	/**
	 * @param alias
	 * @return
	 */
	public boolean isSchemaField(String sourceFileHeaderName){
		for(SchemaField field: this.getFields()){
			if(field.getName().toUpperCase().equals(sourceFileHeaderName.toUpperCase())){
				logger.info("Source File Header '{}' is in Schema {}",sourceFileHeaderName.toUpperCase(), this.getName());
				return true;
			}
			for(String alias:field.getAlias()){
				if(alias.toUpperCase().equals(sourceFileHeaderName.toUpperCase())){
					logger.info("Source File Header '{}' is in Schema {}",sourceFileHeaderName.toUpperCase(), this.getName());
					return true;					
				}
			}
		}
		logger.info("Source File Header '{}' is NOT in Schema {}",sourceFileHeaderName.toUpperCase(), this.getName());
		return false;
	}

	public void print(){
		logger.debug("Name: '{}' Version: '{}' Provider: '{}' ", this.getName(), this.getVersion(), this.getProviderName());
	}
	
	public void printAll(){
		logger.debug("Name: '{}' Version: '{}' Provider: '{}' ", this.getName(), this.getVersion(), this.getProviderName());
		printSchemaFields();	
	}
	
	private void printSchemaFields() {
		for (SchemaField field : this.getFields()) {
			field.print();
		}
	}
	
}
