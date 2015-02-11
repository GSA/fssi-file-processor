package main.java.gov.gsa.fssi.files.sourcefiles.records.datas;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.results.ValidationResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Data {
	private static final Logger logger = LoggerFactory.getLogger(SourceFileRecord.class);
	private String dataValue = ""; // TODO: turn this into a generic Object
	private Integer headerIndex = 0;
	private int maxErrorLevel = 0;
	private boolean status = true;
	private List<ValidationResult> validationResults = new ArrayList<ValidationResult>();

	/**
	 * @return the validationResults
	 */
	public List<ValidationResult> getValidationResults() {
		return validationResults;
	}

	/**
	 * @param index
	 * @return the validationResult based upon provided index
	 */
	public ValidationResult getValidationResult(int index) {
		return validationResults.get(index);
	}

	/**
	 * @param validationResults
	 *            the validationResults to set
	 */
	public void setValidationResults(
			ArrayList<ValidationResult> validationResults) {
		for (ValidationResult validationResult : validationResults) {
			this.setMaxErrorLevel(validationResult.getErrorLevel());
			this.setStatus(validationResult.getErrorLevel());
		}
		this.validationResults = validationResults;
	}

	/**
	 * @param validationResults
	 *            the validationResults to add
	 */
	public void addValidationResult(ValidationResult validationResult) {
		this.setMaxErrorLevel(validationResult.getErrorLevel());
		this.setStatus(validationResult.getErrorLevel());
		this.validationResults.add(validationResult);
	}

	/**
	 * @param status
	 * @param errorLevel
	 */
	public void addValidationResult(boolean status, int errorLevel, String rule) {
		this.addValidationResult(new ValidationResult(status, errorLevel, rule));
	}

	/**
	 * @return the validatorStatus
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * This sets the overall Pass/Fail status of the Data object. Once it is
	 * fail (false), it cannot change back
	 * 
	 * @param validatorStatus
	 *            the validatorStatus to set
	 */
	public void setStatus(int errorLevel) {
		if (this.getStatus() == true && errorLevel > 1)
			this.status = false;
	}

	public void setStatus(boolean status) {
		if (this.getStatus())
			this.status = status;
	}

	public Data() {
	}

	public Data(Integer headerIndex) {
		this.headerIndex = headerIndex;
	}

	public Data(Integer headerIndex, String data) {
		this.headerIndex = headerIndex;
		this.dataValue = data;
	}

	/**
	 * @return
	 */
	public String getData() {
		return dataValue;
	}

	/**
	 * @param data
	 */
	public void setData(String data) {
		this.dataValue = data;
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
	public int getMaxErrorLevel() {
		return maxErrorLevel;
	}

	/**
	 * @return
	 */
	public String getErrorLevelName(int errorLevel) {
		String name = null;
		if (errorLevel <= 3) {
			switch (errorLevel) {
			case 0:
				name = File.STATUS_PASS;
				break;
			case 1:
				name = File.STATUS_WARNING;
				break;
			case 2:
				name = File.STATUS_ERROR;
				break;
			case 3:
				name = File.STATUS_FATAL;
				break;
			default:
				break;
			}
		}
		return name;
	}

	/**
	 * @param status
	 */
	public void setMaxErrorLevel(int errorLevel) {
		if (errorLevel > this.maxErrorLevel)
			this.maxErrorLevel = errorLevel;
	}

	public void print() {
		logger.debug(" Data: {} Max Status: {}, ", this.getData(),
				this.getMaxErrorLevel());
	}

}
