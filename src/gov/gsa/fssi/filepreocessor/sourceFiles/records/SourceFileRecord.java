package gov.gsa.fssi.filepreocessor.sourceFiles.records;

import java.util.HashMap;

/**
 * @author davidlarrimore
 *
 */
public class SourceFileRecord {
	private int id = 0;
	private HashMap<String,String> data = new HashMap<String,String>();	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return data
	 */
	public HashMap<String, String> getData() {
		return data;
	}

	/**
	 * @param data
	 */
	public void setData(HashMap<String, String> data) {
		this.data = data;
	}

	/**
	 * @param key
	 * @return
	 */
	public String getDataElementValue(String key) {
		return data.get(key);
	}	
	
	
	/**
	 * @param Key
	 * @param Value
	 */
	public void setDataElement(String Key, String Value) {
		this.data.put(Key, Value);
	}		
	

	/**
	 * @param key
	 * @param value
	 */
	public void addDataElement(String key, String value) {
		this.data.put(key, value);
	}	
	

	/**
	 * @param key
	 */
	public void deleteDataElement(int key) {
		this.data.remove(key);
	}	
	
}
