package main.java.gov.gsa.fssi.files.sourceFiles.records;

import java.util.ArrayList;

import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;

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
	private boolean status = true;
	private int maxErrorLevel = 0;

	public ArrayList<Data> getDatas() {
		return datas;
	}

	public Data getData(int index) {
		return datas.get(index);
	}

	public Data getDataByHeaderIndex(int headerIndex) {
		for (Data data : this.datas) {
			if (data.getHeaderIndex() == headerIndex) {
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

	public void deleteData(Data data) {
		this.datas.remove(this.datas.indexOf(data));
	}

	public void deletDataByIndex(int dataIndex) {
		this.datas.remove(dataIndex);
	}

	/**
	 * 
	 * @param headerIndex
	 */
	public void deleteDataByHeaderIndex(int headerIndex) {
		Integer dataIndex = null;
		// First we generate the list of objects to delete
		for (Data data : this.getDatas()) {
			if (data.getHeaderIndex() == headerIndex) {
				dataIndex = this.datas.indexOf(data);
			}
		}

		// Now we delete the object
		if (dataIndex != null) {
			// logger.info("found data with index {} which is headerIndex {}",
			// dataIndex, headerIndex);
			deletDataByIndex(dataIndex);
		}
	}

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

	/**
	 * @return the row
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRowIndex(Integer row) {
		this.rowIndex = row;
	}

	public void print() {
		logger.debug(" Row: {} Status: {} Data Elements: {}",
				this.getRowIndex(), this.getStatus(), this.getDatas().size());
	}

	public void printAll() {
		logger.debug(" Row: {} Status: {} Data: {}", this.getRowIndex(),
				this.getStatus(), this.printDatas());
	}

	private ArrayList<String> printDatas() {
		ArrayList<String> row = new ArrayList<String>();
		for (Data data : this.getDatas()) {
			row.add(data.getHeaderIndex() + " = "
					+ (data.getData() == null ? "" : data.getData()));
		}
		return row;
	}

	/**
	 * @return
	 */
	public int getMaxErrorLevel() {
		return maxErrorLevel;
	}

	/**
	 * @param status
	 */
	public void setMaxErrorLevel(int errorLevel) {
		if (errorLevel > this.maxErrorLevel)
			this.maxErrorLevel = errorLevel;
		this.setStatus(errorLevel);
	}

}
