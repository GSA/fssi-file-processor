package gov.gsa.fssi.filepreocessor.sourceFiles;

import gov.gsa.fssi.filepreocessor.sourceFiles.records.SourceFileRecord;

import java.util.ArrayList;


/**
 * This is our file object. It is an abstract class that forms the structure of all source files
 * Whether it be xls, xlsx, csv, etc...
 * 
 * 
 * @author David Larrimore
 * 
 */
public class SourceFile{
	private String fileName = null;
	private String fileExtension = null;	
	private String status = null;
	private ArrayList<String> headers = new ArrayList<String>();
	private ArrayList<SourceFileRecord> records = new ArrayList<SourceFileRecord>();	
	
	
	/**
	 * @return the headers
	 */
	public ArrayList<String> getHeaders() {
		return headers;
	}


	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(ArrayList<String> headers) {
		this.headers = headers;
	}


	/**
	 * @return the records
	 */
	public ArrayList<SourceFileRecord> getRecords() {
		return records;
	}


	/**
	 * @param records the records to set
	 */
	public void setRecords(ArrayList<SourceFileRecord> records) {
		this.records = records;
	}

	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * @return the fileExtension
	 */
	public String getFileExtension() {
		return fileExtension;
	}


	/**
	 * @param fileExtension the fileExtension to set
	 */
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void addRecord(SourceFileRecord record) {
		this.records.add(record);
	}	

	public void deleteRecord(int index) {
		this.records.remove(index);
	}	
	
	
	public SourceFile() {
	
	}

	
}
