package gov.gsa.fssi.files.sourceFiles;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.File;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.SourceFileValidator;
import gov.gsa.fssi.files.sourceFiles.utils.contexts.SourceFileExporterContext;
import gov.gsa.fssi.files.sourceFiles.utils.contexts.SourceFileLoaderContext;
import gov.gsa.fssi.files.sourceFiles.utils.contexts.SourceFileOrganizerContext;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.exporters.CSVSourceFileExporterStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.exporters.ExcelSourceFileExporterStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.loaders.CSVSourceFileLoaderStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.organizers.ExplodeSourceFileOrganizerStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.organizers.ImplodeSourceFileOrganizerStrategy;
import gov.gsa.fssi.helpers.DateHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is our file object. It is an abstract class that forms the structure of all source files
 * Whether it be xls, xlsx, csv, etc...
 * 
 * @author David Larrimore
 * 
 */
public class SourceFile extends File{
	static Logger logger = LoggerFactory.getLogger(SourceFile.class);
	static Config config = new Config();	    
	
	private Schema schema = null;
	private Provider provider = null;
	private Date ReportingPeriod = null;
	private Integer totalRecords = 0;
	private Integer totalProcessedRecords = 0;
	private Integer totalNullRecords = 0;
	private Integer totalEmptyRecords = 0;
	private Map<Integer, String> headers = new HashMap<Integer, String>();
	private ArrayList<SourceFileRecord> records = new ArrayList<SourceFileRecord>();
	
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
	 * 
	 */
	public void setReportingPeriodUsingFileNameParts(){
		if(this.getFileNameParts() == null || this.getFileNameParts().isEmpty()){
			logger.error("File has no fileNameParts, which means we cannot discern a provider or schema. we can process the file no farther");
			this.setLoadStatusLevel(STATUS_ERROR);
		}else{
			
			for(String fileNamePart: this.getFileNameParts()){
				//Checking to see if fileNamePart is all numbers and meets the length restrictions. if so Attempt to process as date
				if(fileNamePart.matches("[0-9]+") && (fileNamePart.length() == 6 || fileNamePart.length() == 8)){
					Date date = new Date();
					//if 6 digits, we attempt to get a Reporting Period in mmYYYY format, example 072015 is July 2015
					if(fileNamePart.length() == 6){
						date = DateHelper.getDate(fileNamePart, DateHelper.FORMAT_MMYYYY);
					//if 8 digits, we attempt to get a Reporting Period in MMddyyyy format, example 07252015 is July 25, 2015	
					}else if(fileNamePart.length() == 8){
						date = DateHelper.getDate(fileNamePart, DateHelper.FORMAT_MMDDYYYY);
					}
					if(date != null){
						logger.info("Processed date as '{}'",date.toString());
						Date todaysDate = DateHelper.getTodaysDate();
						Date minimumDate = DateHelper.getDate("012000", DateHelper.FORMAT_MMYYYY);
						
						if(date.compareTo(todaysDate) > 0){
							logger.error("ReportingPeriod '{}' found in FileName is later than current date. Please check file name", date.toString());
							this.setLoadStatusLevel(STATUS_ERROR);
						}else if(date.compareTo(minimumDate) < 0){
							logger.error("ReportingPeriod '{}' found in FileName is before the year 2000 and may be inacurate. Please check file name", date.toString());
							this.setLoadStatusLevel(STATUS_ERROR);				
						}else{
							logger.info("Successfully added Reporting Period '{}'", date.toString());
							this.setReportingPeriod(date);
							break;
						}

					}
				}

			}
		}
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
	public Map<Integer, String> getHeaders() {
		return headers;
	}
	/**
	 * @param map the headers to set
	 */
	public void setHeaders(Map<Integer, String> map) {
		this.headers = map;
	}
	/**
	 * @param map the headers to set
	 */
	public void addHeader(Integer key, String value) {
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
	public void removeRecord(int index) {
		this.records.remove(index);
	}	
	
	public SourceFile(String fileName) {
		super(fileName);
		this.setReportingPeriodUsingFileNameParts();
	}
		
	
	public void validate(){
		SourceFileValidator validator = new SourceFileValidator();
		validator.validate(this);
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
		
		logger.debug("FileName '{}' FileExtension: '{}' Status: '{}' Headers (Size): '{}' Provider: '{}' Schema: '{}'", this.getFileName(), this.getFileExtension(), this.getLoadStatusLevel(), this.getHeaders().size(), providerString, schemaString);
	}
	
	/**
	 * 
	 */
	public void printAll(){
		String providerString = null;
		if (!(this.getProvider() == null)){
			providerString = this.getProvider().getProviderName() + " - " + this.getProvider().getProviderIdentifier();
		}
		
		String schemaString = null;
		if (!(this.getSchema() == null)){
			schemaString = this.schema.getName();
		}		
		
		logger.debug("FileName '{}' FileExtension: '{}' Status: '{}' Headers (Size): '{}' Provider: '{}' Schema: '{}'", this.getFileName(), this.getFileExtension(), this.getLoadStatusLevel(), this.getHeaders().size(), providerString, schemaString);
		printRecords();	
	}
	/**
	 * 
	 */
	private void printRecords() {
		for (SourceFileRecord sourceFileRecord : this.getRecords()) {
			sourceFileRecord.print();
		}
	}	
	
	/**
	 * @param sourceFile
	 */
	public void load() {
		SourceFileLoaderContext context = new SourceFileLoaderContext();
		switch(this.getFileExtension().toLowerCase()){
		case FILETYPE_CSV:
			logger.info("Loading file {} as a '{}'", this.getFileName(), this.getFileExtension()); 
			context.setSourceFileLoaderStrategy(new CSVSourceFileLoaderStrategy());
			break;		 
		default:
			logger.warn("Could not load file '{}' as a '{}'", this.getFileName(), this.getFileExtension());	
			this.setLoadStatusLevel(STATUS_ERROR);
			break;
		}
		
		if(!this.getLoadStatusLevel().equals(STATUS_ERROR)){
			context.load(this.getFileName(), this);
		}
	}
	
	/**
	 * This method processes a file against its schema
	 */
	public void organize() {
		SourceFileOrganizerContext context = new SourceFileOrganizerContext();
		if(this.getSchema() != null){
		switch(config.getProperty(Config.EXPORT_MODE)){
			case Config.EXPORT_MODE_EXPLODE:
				context.setSourceFileOrganizerStrategy(new ExplodeSourceFileOrganizerStrategy());
				break;
			case Config.EXPORT_MODE_IMPLODE:
				context.setSourceFileOrganizerStrategy(new ImplodeSourceFileOrganizerStrategy());
				break;
			default:
				logger.warn("No Export Mode provided, defaulting to Implode");
				context.setSourceFileOrganizerStrategy(new ImplodeSourceFileOrganizerStrategy());
				break;
		}
		context.organize(this);
		}else{
			logger.info("No schema was found for file {}. Ignoring sourceFile schema organizing", this.getFileName());
		}
	}	
	
	public void export() {
		 SourceFileExporterContext context = new SourceFileExporterContext();
		if (this.getRecords() != null){
		 switch(this.getProvider().getFileOutputType().toLowerCase()){
		 	case SourceFile.FILETYPE_CSV:
		 		context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());
		 		break;
		 	case SourceFile.FILETYPE_XLS:
		 		context.setSourceFileExporterStrategy(new ExcelSourceFileExporterStrategy());
		 		break;
		 	case SourceFile.FILETYPE_XLSX:
		 		context.setSourceFileExporterStrategy(new ExcelSourceFileExporterStrategy());
		 		break;		 		
		 	default:
		 		logger.warn("We cannot currently export to a '{}'. defaulting to '{}'", this.getProvider().getFileOutputType(), SourceFile.FILETYPE_CSV);
		 		context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());
		 		break;
		 }
		 context.export(this);
		}else{
			logger.error("Cannot export sourceFile '{}'. No data found", this.getFileName());
		}
	}
	
}
