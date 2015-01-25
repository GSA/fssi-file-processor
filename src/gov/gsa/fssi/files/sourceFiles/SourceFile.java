package gov.gsa.fssi.files.sourceFiles;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.File;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.SourceFileValidator;
import gov.gsa.fssi.files.sourceFiles.utils.contexts.SourceFileLoaderContext;
import gov.gsa.fssi.files.sourceFiles.utils.exporters.SourceFileExporterCSV;
import gov.gsa.fssi.files.sourceFiles.utils.exporters.SourceFileExporterExcel;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.loaders.CSVSourceFileLoaderStrategy;
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
	public void setReportingPeriod(){
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
		this.setReportingPeriod();
	}
		
	/**
	 * This method fixes the column header names (Key) to match the Schema.
	 */
	public void updateHeadersNamesToMatchSchema(){
		if(this.getSchema() != null){
			logger.info("Atempting to map field names from Schema {} to File {}", this.getSchema().getName(), this.getFileName());
			Map<Integer, String> thisHeader = this.getHeaders();
			//HashMap<Integer, String> newHeader = new HashMap<Integer, String>();
			Iterator<?> thisHeaderIterator = thisHeader.entrySet().iterator();
			while (thisHeaderIterator.hasNext()) {
				Map.Entry<Integer, String> thisHeaderPairs = (Map.Entry<Integer, String>)thisHeaderIterator.next();
				String sourceFileFieldName = thisHeaderPairs.getValue().toString().trim().toUpperCase();
				this.addHeader((Integer)thisHeaderPairs.getKey(), (this.getSchema().getFieldAndAliasNames().contains(sourceFileFieldName))? this.getSchema().getFieldName(sourceFileFieldName): sourceFileFieldName);
			}
			logger.info("Headers have been updated");	
		}else{
			logger.info("No schema was found for file {}. Will not Map Schema Fields to Header", this.getFileName());
		}
	}
	/**
	 * This method adds missing headers and re-organizes the data to put schema headers first
	 */
	public void explodeSourceFileToSchema(){
		logger.info("Exploding sourceFile '{}' to Schema '{}'", this.getFileName(), this.getSchema().getName());
		HashMap<Integer, String> newHeader = new HashMap<Integer, String>();	
		//This is our count to determine location of each header
		Integer headerCounter = 0;
		
		//First, lets add all of the fields from our Schema, they always go first
		for (SchemaField field : this.getSchema().getFields()) {
			newHeader.put(headerCounter, field.getName());
			field.setHeaderIndex(headerCounter);
			headerCounter ++;
		}
		
		//Second, prep this sourcefiles 
		this.updateHeadersNamesToMatchSchema();
		//logger.debug("{}", this.getHeaders());
		//logger.debug("{}", newHeader);
		//Now we iterate through the existing header and add any additional fields as well as create our "Translation Map"
		Map<Integer, String> thisHeader = this.getHeaders();
		//The headerTranslationMap object translates the old headerIndex, to the new header index.
		//Key = Old Index, Value = New Index
		HashMap<Integer, Integer> headerTranslationMap = new HashMap<Integer, Integer>();
		
		Iterator<?> sourceFileHeaderIterator = thisHeader.entrySet().iterator();
		
		while (sourceFileHeaderIterator.hasNext()) {
			boolean foundColumn = false;
			Map.Entry<Integer, String> thisHeaderPairs = (Map.Entry)sourceFileHeaderIterator.next();
			Iterator<?> newHeaderIterator = newHeader.entrySet().iterator();
			//We need to check to see if the header is already in the index. If so, we need to put the index in the translation map
			while (newHeaderIterator.hasNext()) {
				Map.Entry<Integer, String> newHeaderPairs = (Map.Entry)newHeaderIterator.next();
				if (newHeaderPairs.getValue().toString().toUpperCase().equals(thisHeaderPairs.getValue().toString().toUpperCase())){
					logger.info("Mapping header field '{} - {}' to new index {}", thisHeaderPairs.getValue().toString().toUpperCase(), thisHeaderPairs.getKey(), newHeaderPairs.getKey());
					headerTranslationMap.put(thisHeaderPairs.getKey(), newHeaderPairs.getKey());
					foundColumn = true;
				}
			}
			
			if(!foundColumn){
				logger.info("Source field '{} - {}' was not in new header, adding to newHeader index '{} - {}'", thisHeaderPairs.getValue().toString().toUpperCase(), thisHeaderPairs.getKey(), thisHeaderPairs.getValue(), headerCounter);
				headerTranslationMap.put(thisHeaderPairs.getKey(), headerCounter);
				newHeader.put(headerCounter, thisHeaderPairs.getValue().toString().toUpperCase());
				headerCounter ++;
			}	
		}               
	
		//Now we reset the HeaderIndex's in the data object
		logger.info("Old Header:{}", this.getHeaders());
		this.setHeaders(newHeader);
		logger.debug("New Header: {}", this.getHeaders());
		logger.debug("Header Translation (Old/New): {}", headerTranslationMap);
		for (SourceFileRecord sourceFileRecord : records) {
			//sourceFileRecord.print();
			for (Data data : sourceFileRecord.getDatas()) {
					data.setHeaderIndex(headerTranslationMap.get(data.getHeaderIndex()));
			}
			//sourceFileRecord.print();
			
			//Now we fill in the blanks
			Iterator<?> sourceFileHeaderIterator2 = this.getHeaders().entrySet().iterator();
			while (sourceFileHeaderIterator2.hasNext()){
				Map.Entry<Integer, String> newHeaderPairs = (Map.Entry)sourceFileHeaderIterator2.next();
				Data data = sourceFileRecord.getDataByHeaderIndex(newHeaderPairs.getKey());
				if(data == null){
					sourceFileRecord.addData(new Data(newHeaderPairs.getKey(), ""));
				}
			}
		}
		
		
	}
	
	
	/**
	 * This method maps a sourceFile to its schema and then conforms the file/data to the schema format 
	 * We delete any data that is no longer necessary
	 */	
	public void implodeSourceFileToSchema(){
		logger.info("Imploding sourceFile '{}' to Schema '{}'", this.getFileName(), this.getSchema().getName());		
		HashMap<Integer, String> newHeader = new HashMap<Integer, String>();	
		//This is our count to determine location of each header
		Integer headerCounter = 0;
		
		//First, lets add all of the fields from our Schema, they always go first
		for (SchemaField field : this.getSchema().getFields()) {
			newHeader.put(headerCounter, field.getName());
			field.setHeaderIndex(headerCounter);
			headerCounter ++;
		}
		
		//Second, prep this sourcefiles 
		this.updateHeadersNamesToMatchSchema();
		//logger.debug("{}", this.getHeaders());
		//logger.debug("{}", newHeader);
		//Now we iterate through the existing header and add any additional fields as well as create our "Translation Map"
		Map<Integer, String> thisHeader = this.getHeaders();
		//The headerTranslationMap object translates the old headerIndex, to the new header index.
		//Key = Old Index, Value = New Index
		HashMap<Integer, Integer> headerTranslationMap = new HashMap<Integer, Integer>();
		
		Iterator<?> sourceFileHeaderIterator = thisHeader.entrySet().iterator();
		ArrayList<Integer> deleteFieldDataList = new ArrayList<Integer>();
		
		while (sourceFileHeaderIterator.hasNext()) {
			boolean foundColumn = false;
			Map.Entry<Integer, String> thisHeaderPairs = (Map.Entry)sourceFileHeaderIterator.next();
			Iterator<?> newHeaderIterator = newHeader.entrySet().iterator();
			//We need to check to see if the header is already in the index. If so, we need to put the index in the translation map
			while (newHeaderIterator.hasNext()) {
				Map.Entry<Integer, String> newHeaderPairs = (Map.Entry)newHeaderIterator.next();
				if (newHeaderPairs.getValue().toString().toUpperCase().equals(thisHeaderPairs.getValue().toString().toUpperCase())){
					logger.info("Mapping header field '{} - {}' to new index {}", thisHeaderPairs.getValue().toString().toUpperCase(), thisHeaderPairs.getKey(), newHeaderPairs.getKey());
					headerTranslationMap.put(thisHeaderPairs.getKey(), newHeaderPairs.getKey());
					foundColumn = true;
				}
			}
			
			if(!foundColumn){
				logger.info("Source field field '{} - {}' was not in Schema, adding to adding to delete list", thisHeaderPairs.getValue().toString().toUpperCase(), thisHeaderPairs.getKey(), thisHeaderPairs.getValue(), headerCounter);
				deleteFieldDataList.add(thisHeaderPairs.getKey());
			}
			
		}         
		
		
		//Now we delete the old data. We do this before we re-stack the headers
		logger.info("Deleting data from the following headers: {}", deleteFieldDataList);
		for(SourceFileRecord sourceFileRecord: this.getRecords()){
			for(Integer deleteField : deleteFieldDataList){
				sourceFileRecord.deleteDataByHeaderIndex(deleteField);
			}
		}

		
		//Now we reset the HeaderIndex's in the data object
		logger.info("Old Header:{}", this.getHeaders());
		this.setHeaders(newHeader);
		logger.debug("New Header: {}", this.getHeaders());
		
		logger.debug("Header Translation (Old/New): {}", headerTranslationMap);
		for (SourceFileRecord sourceFileRecord : this.records) {
			//sourceFileRecord.print();
			for (Data data : sourceFileRecord.getDatas()) {
					data.setHeaderIndex(headerTranslationMap.get(data.getHeaderIndex()));
			}
			//sourceFileRecord.print();
		}
		
		
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
	public void processToSchema() {
		//processing sourcefile for export
		if(this.getSchema() != null){
			if(config.getProperty(Config.EXPORT_MODE) != null && config.getProperty(Config.EXPORT_MODE).equals("explode")){
				logger.info("Export mode set to explode. Exploding file");
				this.explodeSourceFileToSchema();					
			}else if(config.getProperty(Config.EXPORT_MODE) != null && config.getProperty(Config.EXPORT_MODE).equals("implode")){
				logger.info("Export mode set to implode. Imploding file");
				this.implodeSourceFileToSchema();	
			}else{
				logger.info("No export node provided. leaving file as-is");
				//sourceFile.implodeSourceFileToSchema();	
			}	

		}else{
			logger.info("No schema was found for file {}. Ignoring sourceFile schema processing", this.getFileName());
		}
	}	
	
	public void export() {
		 if (this.getRecords() != null){
			if(this.getProvider().getFileOutputType().toUpperCase().equals("CSV")){
				logger.info("Exporting file '{}' as a CSV", this.getFileName());
				SourceFileExporterCSV exporter = new SourceFileExporterCSV();
				exporter.export(this);	
			}else if(this.getProvider().getFileOutputType().toUpperCase().equals("XML")){
				//logger.info("Exporting File {} as a 'XML'", sourceFile.getFileExtension());	
				logger.error("Cannot export sourceFile '{}' as XML. We don't currently handle XML output at this point", this.getFileName());
				this.setLoadStatusLevel(STATUS_ERROR);
			}else if(this.getProvider().getFileOutputType().toUpperCase().equals("XLS")){
				logger.info("Exporting file '{}' as a XLS", this.getFileName());
				SourceFileExporterExcel exporter = new SourceFileExporterExcel();
				exporter.export(this);
			}else if(this.getProvider().getFileOutputType().toUpperCase().equals("XLSX")){
				logger.info("Exporting file '{}' as a XLSX", this.getFileName());
				SourceFileExporterExcel exporter = new SourceFileExporterExcel();
				exporter.export(this);	
			}else{
				logger.warn("I'm sorry, we cannot export a file as a '{}' defaulting to 'CSV'", this.getFileExtension());
				SourceFileExporterCSV exporter = new SourceFileExporterCSV();
				exporter.export(this);	
			} 
		}else{
			logger.error("Cannot export sourceFile '{}'. No data found", this.getFileName());
		}
	}
	
}
