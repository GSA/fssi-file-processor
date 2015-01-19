package gov.gsa.fssi.files.sourceFiles;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.files.BuilderStatus;
import gov.gsa.fssi.files.File;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.helpers.DateHelper;
import gov.gsa.fssi.helpers.FileHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
			this.setBuilderStatusLevel(BuilderStatus.ERROR);
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
							this.setBuilderStatusLevel(BuilderStatus.ERROR);
						}else if(date.compareTo(minimumDate) < 0){
							logger.error("ReportingPeriod '{}' found in FileName is before the year 2000 and may be inacurate. Please check file name", date.toString());
							this.setBuilderStatusLevel(BuilderStatus.ERROR);				
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
				this.addHeader((Integer)thisHeaderPairs.getKey(), (this.getSchema().isSchemaField(sourceFileFieldName)? this.getSchema().getFieldName(sourceFileFieldName): sourceFileFieldName));
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
		
		logger.debug("FileName '{}' FileExtension: '{}' Status: '{}' Headers (Size): '{}' Provider: '{}' Schema: '{}'", this.getFileName(), this.getFileExtension(), this.getBuilderStatusLevel(), this.getHeaders().size(), providerString, schemaString);
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
		
		logger.debug("FileName '{}' FileExtension: '{}' Status: '{}' Headers (Size): '{}' Provider: '{}' Schema: '{}'", this.getFileName(), this.getFileExtension(), this.getBuilderStatusLevel(), this.getHeaders().size(), providerString, schemaString);
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
	 * This method ingests a CSV file into a sourceFile Object
	 * It checks for Null/Empty records
	 * 
	 * @param sourceFile
	 */
	private void loadSourceFileObjectsFromCSV() {
		 try {
			Reader in = new FileReader(config.getProperty(Config.SOURCEFILES_DIRECTORY) + this.getFileName());
			final CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
			
			//Converting Apache Commons CSV header map from <String, Integer> to <Integer,String>
			Map<String, Integer> parserHeaderMap = parser.getHeaderMap();
			Iterator<?> parserHeaderMapIterator = parserHeaderMap.entrySet().iterator();
			while (parserHeaderMapIterator.hasNext()) {
				Map.Entry pairs = (Map.Entry)parserHeaderMapIterator.next();
				this.addHeader((Integer)pairs.getValue(), pairs.getKey().toString());
			}
			
			//logger.info("{}",parser.getHeaderMap());
			
			for (final CSVRecord csvRecord : parser) {
				this.incrementTotalRecords();
				
				SourceFileRecord thisRecord = new SourceFileRecord();
				
				//Ignoring null rows
				if (csvRecord.size() > 1 && this.getHeaders().size() > 1){
					Iterator<?> headerIterator = this.getHeaders().entrySet().iterator();
					while (headerIterator.hasNext()) {
						Map.Entry pairs = (Map.Entry)headerIterator.next();
						Data data = new Data();
						try {
							data.setData(csvRecord.get(pairs.getValue().toString()).trim());
							data.setHeaderIndex((Integer)pairs.getKey());
							data.setStatus(Data.STATUS_LOADED);
							thisRecord.addData(data);
						} catch (IllegalArgumentException e) {
							//logger.error("Failed to process record '{} - {}' in file '{}'", pairs.getKey().toString(), pairs.getValue().toString(), sourceFile.getFileName());
							logger.error("{}", e.getMessage());
							data.setStatus(Data.STATUS_ERROR);
						}
						
					}
					
					//Checking to see if any data was in the row. if so, we consider this an Empty Record
					boolean emptyRowCheck = false;
					for (Data data : thisRecord.getDatas()) {
						if(data.getData() == null || data.getData().isEmpty() || data.getData().equals("")){
							emptyRowCheck = true;
						}else{
							emptyRowCheck = false;
							break;
						}
					}
					
					if(emptyRowCheck == false){
						thisRecord.setStatus(SourceFileRecord.STATUS_LOADED);
						this.addRecord(thisRecord);
					}else{
						this.incrementTotalEmptyRecords();
					}

				}else{
					//logger.debug("row {} in file '{}' had no data, ignoring.", recordCount, sourceFile.getFileName());
					this.incrementTotalNullRecords();
				}
		    }
			
			if (this.getTotalEmptyRecords()+this.getTotalNullRecords() > 0){
				logger.warn("Only {} out of {} rows processed from {}. Null Rows: {} Empty Records: {}", this.recordCount(), this.getTotalRecords(), this.getFileName(), this.getTotalNullRecords(), this.getTotalEmptyRecords());
			}else{
				logger.info("All {} Records successfully processed in {}", this.getTotalRecords(), this.getFileName());
			}
			this.setBuilderStatusLevel(BuilderStatus.LOADED);
			parser.close();
		} catch (FileNotFoundException e) {
			logger.error("There was an FileNotFoundException error with file {}", this.getFileName());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("There was an IOException error with file {}", this.getFileName());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("There was an IllegalArgumentException error with file {}", this.getFileName());
			e.printStackTrace();
		}
	}	

	/**
	 * @param sourceFile
	 */
	public void ingest() {
		if(this.getFileExtension().toUpperCase().equals("CSV")){
			logger.info("Loading file {} as a 'CSV'", this.getFileName()); 
			this.loadSourceFileObjectsFromCSV();
		}else if(this.getFileExtension().toUpperCase().equals("XML")){
			 logger.info("Loading File {} as a 'XML'", this.getFileExtension());					
		}else if(this.getFileExtension().toUpperCase().equals("XLSX")){
			 logger.info("Loading File {} as a 'XLSX'", this.getFileExtension());						
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

	
	public void processToSchema(Schema schema) {
		this.setSchema(schema);
		this.processToSchema();
	}	
	
	
	public void outputStagedSourceFile() {
		 if (this.getRecords() != null){
			if(this.getProvider().getFileOutputType().toUpperCase().equals("CSV")){
				this.outputAsCSV();
			}else if(this.getProvider().getFileOutputType().toUpperCase().equals("XML")){
				//logger.info("Exporting File {} as a 'XML'", sourceFile.getFileExtension());	
				logger.error("Cannot export sourceFile '{}' as XML. We don't currently handle XML output at this point", this.getFileName());
				this.setBuilderStatusError();
			}else if(this.getProvider().getFileOutputType().toUpperCase().equals("XLS")){
				this.outputAsExcel();
			}else if(this.getProvider().getFileOutputType().toUpperCase().equals("XLSX")){
				this.outputAsExcel();				
			}else{
				logger.warn("I'm sorry, we cannot export a file as a '{}' defaulting to 'CSV'", this.getFileExtension());
				outputAsCSV();				
			} 
		}else{
			logger.error("Cannot export sourceFile '{}'. No data found", this.getFileName());
		}
	}

	/**
	 * This Method takes our SourceFile data and exports it to Excel format
	 * @param stagedDirectory
	 * @param newFileName
	 */
	private void outputAsExcel() {
		logger.info("Exporting File {} as a 'XLS'", this.getFileName());
		FileOutputStream out;
		try {
			out = new FileOutputStream(config.getProperty(Config.STAGED_DIRECTORY) + FileHelper.buildNewFileName(this.getFileName(), this.getProvider().getFileOutputType()));

		// create a new workbook
		Workbook wb = (this.getProvider().getFileOutputType().toUpperCase().equals("XLSX") ? new XSSFWorkbook() : new HSSFWorkbook());
		// create a new sheet
		Sheet s = wb.createSheet();
		// declare a row object reference
		Row r = null;
		// declare a cell object reference
		Cell c = null;

		//creating header row
		r = s.createRow(0);

		for(int i=0; i < this.getHeaders().size();i++){
			c = r.createCell(i);
			c.setCellValue(this.getHeaders().get(i));
		}
		
		int counter = 0;
		
		//Now lets put some data in there....
		for (SourceFileRecord sourceFileRecord : this.getRecords()) {
			counter ++;	
			r = s.createRow(counter);
			
			ArrayList<Data> records = sourceFileRecord.getDatas(); 	
			for (Data data : records) {
				//logger.debug("{}", data.getHeaderIndex());
				c = r.createCell((int)data.getHeaderIndex());
				c.setCellValue(data.getData());
			}
		}
		
		// write the workbook to the output stream
		// close our file (don't blow out our file handles
			wb.write(out);
			out.close();
		} catch (IOException e) {
			logger.error("There was an IOException error '{}' with file {}. ", this.getFileName(), e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * @param string
	 * @param sourceFile
	 */
	private void outputAsCSV() {
		logger.info("Exporting File {} as a 'CSV'", this.getFileName()); 
		//Delimiter used in CSV file
		String newFileName = FileHelper.buildNewFileName(this.getFileName(), this.getProvider().getFileOutputType());
		String newLineSeparator = "\n";
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		
		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(newLineSeparator);
				
		try {
			//initialize FileWriter object
			fileWriter = new FileWriter(config.getProperty(Config.STAGED_DIRECTORY) + newFileName);
			//initialize CSVPrinter object 
		    csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
		    
			List<String> csvHeaders = new ArrayList<String>();
			//Writing Headers
			Map<Integer,String> headerMap = this.getHeaders(); 	
			Iterator<?> headerMapIterator = headerMap.entrySet().iterator();
			while (headerMapIterator.hasNext()) {
				Map.Entry headerMapPairs = (Map.Entry)headerMapIterator.next();
				csvHeaders.add(headerMapPairs.getValue().toString());
			}
		    
		    //Create CSV file header
		    csvFilePrinter.printRecord(csvHeaders);
			
		    //Writing Data
			for (SourceFileRecord sourceFileRecord : this.getRecords()) {
				List<String> csvRecord = new ArrayList<String>();
				for(int i = 0;i < this.getHeaders().size();i++){
					if(sourceFileRecord.getDataByHeaderIndex(i)!= null && sourceFileRecord.getDataByHeaderIndex(i).getData() != null){
						//sourceFileRecord.print();
						csvRecord.add(sourceFileRecord.getDataByHeaderIndex(i).getData());						
					}else{
						csvRecord.add("");
					}	
				}	
		        csvFilePrinter.printRecord(csvRecord);
			}
			logger.info("{} Created Successfully. {} Records processed", this.getFileName(), this.recordCount());
			
		} catch (Exception e) {
			logger.error("Received Exception '{}' while processing {}", e.getMessage(), this.getFileName());
			//e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				logger.error("Received IOException '{}' while flushing/closing fileWriter for {}", e.getMessage() ,this.getFileName());
//		        e.printStackTrace();
			}
		}
	}	
	
}
