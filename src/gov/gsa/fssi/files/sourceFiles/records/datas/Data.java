package gov.gsa.fssi.files.sourceFiles.records.datas;

import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Data {
	static Logger logger = LoggerFactory.getLogger(SourceFileRecord.class);
	private String data = ""; //TODO: turn this into a generic Object
	private Integer headerIndex = 0;
	private String status = "";
	private String statusMessage = "";
	private String validatorStatus = "";
	private String validatorStatusMessage = "";
	
	
	
	/**
	 * @return the validatorStatus
	 */
	public String getValidatorStatus() {
		return validatorStatus;
	}

	/**
	 * @param validatorStatus the validatorStatus to set
	 */
	public void setValidatorStatus(String validatorStatus) {
		this.validatorStatus = validatorStatus;
	}

	/**
	 * @return the validatorStatusMessage
	 */
	public String getValidatorStatusMessage() {
		return validatorStatusMessage;
	}

	/**
	 * @param validatorStatusMessage the validatorStatusMessage to set
	 */
	public void setValidatorStatusMessage(String validatorStatusMessage) {
		this.validatorStatusMessage = validatorStatusMessage;
	}

	public Data() {
		super();
	}
	
	public Data(Integer headerIndex) {
		super();
		this.headerIndex = headerIndex;
	}
	
	public Data(Integer headerIndex, String data) {
		super();
		this.headerIndex = headerIndex;
		this.data = data;
	}
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
			logger.debug(" Data: {} Status: {}, ", this.getData(), this.getStatus(), this.getStatusMessage());
	}
	
}
