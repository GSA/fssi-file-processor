package gov.gsa.fssi.fileprocessor.sourceFiles.records;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author davidlarrimore
 *
 */
public class SourceFileRecord {
	static Logger logger = LoggerFactory.getLogger(SourceFileRecord.class);	
	
	private Integer row = null;
	
	//Data contains index to Header and actual row field data
	private HashMap<Integer,String> data = new HashMap<Integer, String>();	
	
	
	/**
	 * @return the row
	 */
	public Integer getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(Integer row) {
		this.row = row;
	}

	/**
	 * @return data
	 */
	public HashMap<Integer, String> getData() {
		return data;
	}

	/**
	 * @param data
	 */
	public void setData(HashMap<Integer, String> data) {
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
	public void setDataElement(Integer Key, String Value) {
		this.data.put(Key, Value);
	}		
	

	/**
	 * @param key
	 * @param value
	 */
	public void addDataElement(Integer key, String value) {
		this.data.put(key, value);
	}	
	

	/**
	 * @param key
	 */
	public void deleteDataElement(int key) {
		this.data.remove(key);
	}	
	
	
	public void print(){
		logger.debug("{}", this.getData());
	}
	
}
