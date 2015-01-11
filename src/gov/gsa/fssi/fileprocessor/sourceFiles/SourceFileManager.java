package gov.gsa.fssi.fileprocessor.sourceFiles;

import gov.gsa.fssi.fileprocessor.Config;
import gov.gsa.fssi.fileprocessor.FileHelper;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.fileprocessor.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.fileprocessor.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.fileprocessor.sourceFiles.records.datas.Data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
 * The purpose of this class is to manage the source files and data..
 *
 * @author davidlarrimore
 *
 */
public class SourceFileManager {
	static Config config = new Config();	    
	static Logger logger = LoggerFactory.getLogger(SourceFileManager.class);
	
	/**
	 * The purpose of this function is just to prep file processing. We are not actually loading data yet
	 * @param sourceFileDirectory
	 */
	public static ArrayList<SourceFile> initializeSourceFiles() {	
	    logger.debug("Starting initializeSourceFiles('{}')", config.getProperty("sourcefiles_directory"));		
	    
		ArrayList<SourceFile> sourceFiles = new ArrayList<SourceFile>();	
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(config.getProperty("sourcefiles_directory"), ".csv ");
	
		//Loop through files in sourceFileDirectory and populate SourceFile objects
		for (String fileName : fileNames) {
	    	SourceFile newSourceFile = new SourceFile();	
	    	newSourceFile.setFileName(fileName);
	    	int startOfExtension = fileName.lastIndexOf(".")+1;
	    	newSourceFile.setFileExtension(fileName.substring(startOfExtension, fileName.length()));
	    	newSourceFile.setStatus(SourceFile.STATUS_INITIALIZED);
		    sourceFiles.add(newSourceFile);		        
		}		    
		
		return sourceFiles;
	}	
	
	
	
	/**
	 * This method maps Providers to SourceFiles. Currently, if it doesn't map, we remove the sourcefile altogether.
	 * 
	 * @param providers
	 * @param sourceFiles
	 */
	public static void validateSourceFileProviders(
		ArrayList<Provider> providers, ArrayList<SourceFile> sourceFiles) {
		ArrayList<Integer> badFiles = new ArrayList<Integer>();
		
		//Loop 
		for ( SourceFile sourceFile : sourceFiles) {
			for ( Provider provider : providers) {
				if(sourceFile.getFileName().toUpperCase().contains(provider.getProviderIdentifier().toUpperCase())){
					sourceFile.setProvider(provider);
				}
			}
			
			//We couldn't find a provider, we can process this file no longer.
			if(sourceFile.getProvider() == null){
				logger.warn("No Provider found for file '{}' (Array Index #{}). File will be ignored", sourceFile.getFileName(), sourceFiles.indexOf(sourceFile));
				badFiles.add(sourceFiles.indexOf(sourceFile));
				sourceFile.setStatus(SourceFile.STATUS_IGNORED);
			}
		}
		
		//Reversing Order to prevent removing good files
		Collections.reverse(badFiles);
		
		//Removing any bad files
		if (badFiles.size() > 0){
			for (Integer integer : badFiles) {
				logger.warn("Removing sourceFile with index of '{}'", integer);
				sourceFiles.remove(integer.intValue());
			}
		}
	}	
	
	
	public static void validateSourceFileSchemas(ArrayList<Schema> schemas,
		ArrayList<SourceFile> sourceFiles) {
		ArrayList<Integer> badFiles = new ArrayList<Integer>();
		//Loop 
		for ( SourceFile sourceFile : sourceFiles) {
			
			//We do not processed the ignored files
			if (!sourceFile.getStatus().equals(SourceFile.STATUS_IGNORED)){
				Provider provider = sourceFile.getProvider();
				for ( Schema schema : schemas) {
					if(provider.getProviderName().toUpperCase().equals(schema.getName().toUpperCase())){
						sourceFile.setSchema(schema);
					}
				}
				
				//We couldn't find a provider.
				if(sourceFile.getSchema() == null){
					logger.warn("No Schema found for file '{}' (Array Index #{}).", sourceFile.getFileName(), sourceFiles.indexOf(sourceFile));
					//logger.warn("No Schema found for file '{}' (Array Index #{}). File has been marked for removal", sourceFile.getFileName(), sourceFiles.indexOf(sourceFile));
					//badFiles.add(sourceFiles.indexOf(sourceFile));
				}
			}
			
			//Reversing Order to prevent removing good files
			Collections.reverse(badFiles);
			
			//Removing any bad files
			if (badFiles.size() > 0){
				for (Integer integer : badFiles) {
					logger.warn("Removing sourceFile with index of '{}'", integer);
					sourceFiles.remove(integer.intValue());
				}
			}
		}	
	}	

	
	/**
	 * This Function manages the processing of all of the source files.
	 * It loads that data into the SourceFile object and then performs any sort of cleansing
	 * validation, and/or processing on the data.
	 * 
	 * @param sourceFilesDirectory
	 * @param sourceFiles
	 */
	public static void processSourceFiles(ArrayList<SourceFile> sourceFiles) {
		//OK, now we start processing the files one at a time.
		for ( SourceFile sourceFile : sourceFiles) {
			if(sourceFile.getFileExtension().toUpperCase().equals("CSV")){
				logger.info("Processing File {} as a 'CSV'", sourceFile.getFileName()); 
				loadSourceFileObjectFromCSV(sourceFile);
			}else if(sourceFile.getFileExtension().toUpperCase().equals("XML")){
				 logger.info("Processing File {} as a 'XML'", sourceFile.getFileExtension());					
			}else if(sourceFile.getFileExtension().toUpperCase().equals("XLSX")){
				 logger.info("Processing File {} as a 'XLSX'", sourceFile.getFileExtension());						
			}
			
			
			//sourceFile Schema Processing
			if(sourceFile.getSchema() != null){
				sourceFile.explodeHeadersToSchema();
				
//				logger.info("Atempting to validate File {} against Schema {}", sourceFile.getFileName(), sourceFile.getSchema().getName());
//				for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
//					for (Data data: sourceFileRecord.getDatas()){
//						
//					}
//				}
			}else{
				logger.info("No schema was found for file {}. Ignoring sourceFile schema processing", sourceFile.getFileName());
			}
			
		}
	}

	/**
	 * This method ingests a CSV file into a sourceFile Object
	 * It checks for Null/Empty records
	 * 
	 * @param sourceFile
	 */
	private static void loadSourceFileObjectFromCSV(SourceFile sourceFile) {
		 try {
			Reader in = new FileReader(config.getProperty("sourcefiles_directory") + sourceFile.getFileName());
			final CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
			
			//Converting Apache Commons CSV header map from <String, Integer> to <Integer,String>
			Map<String, Integer> parserHeaderMap = parser.getHeaderMap();
			Iterator<?> parserHeaderMapIterator = parserHeaderMap.entrySet().iterator();
			while (parserHeaderMapIterator.hasNext()) {
				Map.Entry pairs = (Map.Entry)parserHeaderMapIterator.next();
				sourceFile.addHeader((Integer)pairs.getValue(), pairs.getKey().toString());
			}
			
			//logger.info("{}",parser.getHeaderMap());
			
			for (final CSVRecord csvRecord : parser) {
				sourceFile.incrementTotalRecords();
				
				SourceFileRecord thisRecord = new SourceFileRecord();
				
				//Ignoring null rows
				if (csvRecord.size() > 1 && sourceFile.getHeaders().size() > 1){
					Iterator<?> headerIterator = sourceFile.getHeaders().entrySet().iterator();
					while (headerIterator.hasNext()) {
						Map.Entry pairs = (Map.Entry)headerIterator.next();
						Data data = new Data();
						try {
							data.setData(csvRecord.get(pairs.getValue().toString()));
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
						sourceFile.addRecord(thisRecord);
					}else{
						sourceFile.incrementTotalEmptyRecords();
					}

				}else{
					//logger.debug("row {} in file '{}' had no data, ignoring.", recordCount, sourceFile.getFileName());
					sourceFile.incrementTotalNullRecords();
				}
		    }
			
			if (sourceFile.getTotalEmptyRecords()+sourceFile.getTotalNullRecords() > 0){
				logger.warn("Only {} out of {} rows processed from {}. Null Rows: {} Empty Records: {}", sourceFile.recordCount(), sourceFile.getTotalRecords(), sourceFile.getFileName(), sourceFile.getTotalNullRecords(), sourceFile.getTotalEmptyRecords());
			}else{
				logger.info("All {} Records successfully processed in {}", sourceFile.getTotalRecords(), sourceFile.getFileName());
			}
			sourceFile.setStatus(SourceFile.STATUS_LOADED);
			parser.close();
		} catch (FileNotFoundException e) {
			logger.error("There was an FileNotFoundException error with file {}", sourceFile.getFileName());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("There was an IOException error with file {}", sourceFile.getFileName());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.error("There was an IllegalArgumentException error with file {}", sourceFile.getFileName());
			e.printStackTrace();
		}
	}	
	
	/**
	 * Based upon the providers defined Output Type, we export the source files to the staged directory.
	 * 
	 * @param string
	 * @param sourceFiles
	 */
	public static void outputStagedSourceFiles(ArrayList<SourceFile> sourceFiles) {
		//OK, now we start processing the files one at a time.
		for ( SourceFile sourceFile : sourceFiles) {	
			outputStagedSourceFile(sourceFile);
		}
	}

	private static void outputStagedSourceFile(SourceFile sourceFile) {
		
		if(sourceFile.getProvider().getFileOutputType().toUpperCase().equals("CSV")){
			logger.info("Exporting File {} as a 'CSV'", sourceFile.getFileName()); 
			outputAsCSV(sourceFile);
		}else if(sourceFile.getProvider().getFileOutputType().toUpperCase().equals("XML")){
			//logger.info("Exporting File {} as a 'XML'", sourceFile.getFileExtension());	
			logger.error("We don't currently handle XML output at this point");
		}else if(sourceFile.getProvider().getFileOutputType().toUpperCase().equals("XLS")){
			logger.info("Exporting File {} as a 'XLS'", sourceFile.getFileName());
			outputAsExcel(sourceFile);
		}else if(sourceFile.getProvider().getFileOutputType().toUpperCase().equals("XLSX")){
			logger.info("Exporting File {} as a 'XLSX'", sourceFile.getFileName());
			outputAsExcel(sourceFile);				
		}else{
			logger.warn("I'm sorry, we cannot export a file as a '{}' defaulting to 'CSV'", sourceFile.getFileExtension());
			outputAsCSV(sourceFile);				
		}
	}



	/**
	 * This Method takes our SourceFile data and exports it to Excel format
	 * @param stagedDirectory
	 * @param newFileName
	 */
	private static void outputAsExcel(SourceFile sourceFile) {
		// create a new file
		FileOutputStream out;
		try {
			out = new FileOutputStream(config.getProperty("staged_directory") + FileHelper.buildFileName(sourceFile.getFileName(), sourceFile.getProvider().getFileOutputType()));

		// create a new workbook
		Workbook wb = (sourceFile.getProvider().getFileOutputType().toUpperCase().equals("XLSX") ? new XSSFWorkbook() : new HSSFWorkbook());
		// create a new sheet
		Sheet s = wb.createSheet();
		// declare a row object reference
		Row r = null;
		// declare a cell object reference
		Cell c = null;

		//creating header row
		r = s.createRow(0);

		for(int i=0; i < sourceFile.getHeaders().size();i++){
			c = r.createCell(i);
			c.setCellValue(sourceFile.getHeaders().get(i));
		}
		
		int counter = 0;
		
		//Now lets put some data in there....
		for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
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
			logger.error("There was an IOException error with file {}", sourceFile.getFileName());// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * @param string
	 * @param sourceFile
	 */
	private static void outputAsCSV(SourceFile sourceFile) {
		//Delimiter used in CSV file
		String newFileName = FileHelper.buildFileName(sourceFile.getFileName(), sourceFile.getProvider().getFileOutputType());
		String newLineSeparator = "\n";
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		
		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(newLineSeparator);
				
		try {
			
			//initialize FileWriter object
			fileWriter = new FileWriter(config.getProperty("staged_directory") + newFileName);
			
			//initialize CSVPrinter object 
		    csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
		    
		    
			List<String> csvHeaders = new ArrayList<String>();
			//Writing Headers
			Map<Integer,String> headerMap = sourceFile.getHeaders(); 	
			Iterator<?> headerMapIterator = headerMap.entrySet().iterator();
			while (headerMapIterator.hasNext()) {
				Map.Entry pairs = (Map.Entry)headerMapIterator.next();
				csvHeaders.add(pairs.getKey().toString());
			}
		    
		    
		    //Create CSV file header
		    csvFilePrinter.printRecord(csvHeaders);
			
		    //Writing Data
			for (SourceFileRecord record : sourceFile.getRecords()) {
				List<String> csvRecord = new ArrayList<String>();
				while (headerMapIterator.hasNext()) {
					Map.Entry headerMapPairs = (Map.Entry)headerMapIterator.next();
					//In case no data is there, we put in ""
					if(record.getDataByHeader((Short)headerMapPairs.getKey()).getData() != null){
						csvRecord.add(record.getDataByHeader((Short)headerMapPairs.getValue()).getData());						
					}else{
						csvRecord.add("");
					}
				}
		        csvFilePrinter.printRecord(csvRecord);
			}

			logger.info("{} Created Successfully. {} Records processed", sourceFile.getFileName(), sourceFile.recordCount());
			
		} catch (Exception e) {
			logger.error("Received Exception while processing {}", sourceFile.getFileName());
//			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				logger.error("Received error while flushing/closing fileWriter for {}", sourceFile.getFileName());
//		        e.printStackTrace();
			}
		}
	}
		
}
