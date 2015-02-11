package main.java.gov.gsa.fssi.files.sourceFiles.records.datas.results;

import main.java.gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationResult {
	static Logger logger = LoggerFactory.getLogger(SourceFileRecord.class);

	public ValidationResult() {

	}

	public ValidationResult(boolean status, int errorLevel, String rule) {
		this.setErrorLevel(errorLevel);
		this.setStatus(status);
		this.setRule(rule);
	}

	/**
	 * @return current level
	 */
	public int getErrorLevel() {
		return errorLevel;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setErrorLevel(int errorLevel) {
		this.errorLevel = errorLevel;
	}

	/**
	 * @return current status
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return current rule
	 */
	public String getRule() {
		return rule;
	}

	/**
	 * @param rule
	 *            the rule to set
	 */
	public void setRule(String rule) {
		this.rule = rule;
	}

	private int errorLevel = 0;
	/**
	 * Pass or Fail
	 */
	private boolean status = true;
	private String rule = null;
}
