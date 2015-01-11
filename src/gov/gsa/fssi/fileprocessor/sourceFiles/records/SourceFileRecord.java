package gov.gsa.fssi.fileprocessor.sourceFiles.records;

import gov.gsa.fssi.fileprocessor.sourceFiles.records.datas.Data;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author davidlarrimore
 *
 */
public class SourceFileRecord {
	static Logger logger = LoggerFactory.getLogger(SourceFileRecord.class);	
	private int rowIndex = 0;
	private ArrayList<Data> datas = new ArrayList<Data>();	
	private String status = null;
	
	public static String STATUS_LOADED = "loaded";		
	public static String STATUS_ERROR = "error";	
	public static String STATUS_WARNING = "warning";
	public static String STATUS_PASS = "pass";		
	
	public ArrayList<Data> getDatas() {
		return datas;
	}
	public Data getDataByHeaderIndex(int headerIndex) {
		for (Data data : this.datas) {
			if(data.getHeaderIndex() == headerIndex){
				return data;
			}
		}
		return null;	
	}
	public void setDatas(ArrayList<Data> datas) {
		this.datas = datas;
	}
	public void addData(Data data) {
		this.datas.add(data);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the row
	 */
	public int getRowIndex() {
		return rowIndex;
	}
	/**
	 * @param row the row to set
	 */
	public void setRowIndex(Integer row) {
		this.rowIndex = row;
	}
	
	public void print(){
		
		logger.debug(" Row: {} Status: {} Data: {}",  this.getRowIndex(), this.getStatus(), this.printDatas());
		//printDatas();
	}
	
	private ArrayList<String> printDatas() {
		ArrayList<String> row = new ArrayList<String>();
		for (Data data : this.getDatas()) {
			row.add(data.getHeaderIndex()+" = "+(data == null? "" : data.getData()));
		}
		return row;
	}
}
