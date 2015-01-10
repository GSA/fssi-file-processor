package gov.gsa.fssi.fileprocessor.sourceFiles;

import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.fileprocessor.sourceFiles.records.SourceFileRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	private Integer totalRecords = 0;
	private Integer totalProcessedRecords = 0;
	private Integer totalNullRecords = 0;
	private Integer totalEmptyRecords = 0;
	private Map<String, Integer> headers = new HashMap<String,Integer>();
	private ArrayList<SourceFileRecord> records = new ArrayList<SourceFileRecord>();
	public static String STATUS_INITIALIZED = "initialized";	
	public static String STATUS_STAGED = "staged";
	public static String STATUS_IGNORED = "ignored";	
	public static String STATUS_ERROR = "error";
	public static String STATUS_WARNING = "warnings";		
	public static String STATUS_LOADED = "loaded";			
	
	/**
	 * @return
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	/**
	 * @param totalRecords
	 */
	public void incrementTotalRecords() {
		this.totalRecords ++;
	}
	/**
	 * @return
	 */
	public Integer getTotalProcessedRecords() {
		return totalProcessedRecords;
	}
	/**
	 * @param totalProcessedRecords
	 */
	public void setTotalProcessedRecords(Integer totalProcessedRecords) {
		this.totalProcessedRecords = totalProcessedRecords;
	}
	/**
	 * @param totalProcessedRecords
	 */
	public void incrementTotalProcessedRecords() {
		this.totalProcessedRecords ++;
	}	
	/**
	 * @return
	 */
	public Integer getTotalNullRecords() {
		return totalNullRecords;
	}
	/**
	 * @param totalNullRecords
	 */
	public void setTotalNullRecords(Integer totalNullRecords) {
		this.totalNullRecords = totalNullRecords;
	}
	/**
	 * @param totalNullRecords
	 */
	public void incrementTotalNullRecords() {
		this.totalNullRecords++;
	}	
	/**
	 * @return
	 */
	public Integer getTotalEmptyRecords() {
		return totalEmptyRecords;
	}
	/**
	 * @param totalEmptyRecords
	 */
	public void setTotalEmptyRecords(Integer totalEmptyRecords) {
		this.totalEmptyRecords = totalEmptyRecords;
	}
	/**
	 * 
	 */
	public void incrementTotalEmptyRecords() {
		this.totalEmptyRecords ++;
	}	
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
	public Map<String, Integer> getHeaders() {
		return headers;
	}
	/**
	 * @param map the headers to set
	 */
	public void setHeaders(Map<String, Integer> map) {
		this.headers = map;
	}
	/**
	 * @param map the headers to set
	 */
	public void addHeader(String key, Integer value) {
		this.headers.put(key, value);
	}
	/**
	 * @param map the headers to set
	 */
	public void removeHeader(String key) {
		this.headers.remove(key);
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
	/**
	 * @param record
	 */
	public void addRecord(SourceFileRecord record) {
		this.records.add(record);
	}	
	/**
	 * @param record
	 */
	public long recordCount() {
		return this.records.size();
	}	

	/**
	 * @param index
	 */
	public void deleteRecord(int index) {
		this.records.remove(index);
	}	
	
	/**
	 * 
	 */
	public SourceFile() {
	
	}
	
	/**
	 * 
	 */
	public void print(){
		String providerString = null;
		if (!(this.getProvider() == null)){
			providerString = this.getProvider().getProviderName() + " - " + this.getProvider().getProviderIdentifier();
		}
		
		String schemaString = null;
		if (!(this.getSchema() == null)){
			schemaString = this.schema.getName();
		}		
		
		logger.debug("FileName '{}' FileExtension: '{}' Status: '{}' Headers (Size): '{}' Provider: '{}' Schema: '{}'", this.getFileName(), this.getFileExtension(), this.getStatus(), this.getHeaders().size(), providerString, schemaString);
		printRecords();	
	}
	
	private void printRecords() {
		for (SourceFileRecord sourceFileRecord : this.getRecords()) {
			sourceFileRecord.print();
		}
	}
}
