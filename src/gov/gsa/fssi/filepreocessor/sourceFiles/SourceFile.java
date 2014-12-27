package gov.gsa.fssi.filepreocessor.sourceFiles;

import gov.gsa.fssi.filepreocessor.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is our file object. It is an abstract class that forms the structure of all source files
 * Whether it be xls, xlsx, csv, etc...
 * 
 * 
 * @author David Larrimore
 * 
 */
public class SourceFile{
	static Logger logger = LoggerFactory.getLogger(SourceFile.class);
	
	private String fileName = null;
	private String fileExtension = null;	
	private String status = null;
	private Schema schema = null;
	private Provider provider = null;
	private Date ReportingPeriod = null;
	private ArrayList<String> headers = new ArrayList<String>();
	private ArrayList<SourceFileRecord> records = new ArrayList<SourceFileRecord>();	
	
	/**
	 * @return the reportingPeriod
	 */
	public Date getReportingPeriod() {
		return ReportingPeriod;
	}


	/**
	 * @param reportingPeriod the reportingPeriod to set
	 */
	public void setReportingPeriod(Date reportingPeriod) {
		ReportingPeriod = reportingPeriod;
	}	
	
	
	/**
	 * @return the schema
	 */
	public Schema getSchema() {
		return schema;
	}


	/**
	 * @param schema the schema to set
	 */
	public void setSchema(Schema schema) {
		this.schema = schema;
	}


	/**
	 * @return the provider
	 */
	public Provider getProvider() {
		return provider;
	}


	/**
	 * @param provider the provider to set
	 */
	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	
	
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

	
	public void printSourceFile(){
		logger.debug("printSourceFile: '{}' FileExtension: '{}' Status: '{}' Headers: '{}' ", this.getFileName(), this.getFileExtension(), this.getStatus(), this.getHeaders());
	}
}
