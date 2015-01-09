package gov.gsa.fssi.fileprocessor.sourceFiles.records.datas;

import gov.gsa.fssi.fileprocessor.sourceFiles.records.SourceFileRecord;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Data {
	static Logger logger = LoggerFactory.getLogger(SourceFileRecord.class);
	private String data = null;
	private Integer headerIndex = 0;
	private String status = null;
	private String statusMessage = null;
	public static String STATUS_LOADED = "loaded";	
	public static String STATUS_ERROR = "error";	
	public static String STATUS_WARNING = "warning";
	public static String STATUS_PASS = "pass";	
	
	
	/**
	 * @return
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return
	 */
	public Integer getHeaderIndex() {
		return headerIndex;
	}
	/**
	 * @param headerIndex
	 */
	public void setHeaderIndex(Integer headerIndex) {
		this.headerIndex = headerIndex;
	}
	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return
	 */
	public String getStatusMessage() {
		return statusMessage;
	}
	/**
	 * @param error
	 */
	public void setStatusMessage(String error) {
		this.statusMessage = error;
	}

	public void print(){
		if(this.status.equals(STATUS_PASS)){
			logger.debug(" Data: {} Status: {}", this.getData(), this.getStatus());
		}else{
			logger.debug(" Data: {} Status: {}, ", this.getData(), this.getStatus(), this.getStatusMessage());
		}
	}
	
}
