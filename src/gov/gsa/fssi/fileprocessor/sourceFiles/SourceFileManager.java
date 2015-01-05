package gov.gsa.fssi.fileprocessor.sourceFiles;

import gov.gsa.fssi.fileprocessor.FileHelper;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.fileprocessor.sourceFiles.records.SourceFileRecord;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The purpose of this class is to manage the source files and data..
 *
 * @author davidlarrimore
 *
 */
public class SourceFileManager {
	
	//Status Types.
	public static String INITIALIZED = "initialized";
	public static String LOADED = "loaded";	
	public static String PREPARED = "prepared";		
	public static String COMPLETED = "completed";
	public static String ERROR = "error";			
	
	static Logger logger = LoggerFactory.getLogger(SourceFileManager.class);
	
	/**
	 * The purpose of this function is just to prep file processing. We are not actually loading data yet
	 * @param sourceFileDirectory
	 */
	public static ArrayList<SourceFile> initializeSourceFiles(String sourceFileDirectory) {	
	    logger.debug("Starting initializeSourceFiles('{}')", sourceFileDirectory);		
	    
		ArrayList<SourceFile> sourceFiles = new ArrayList<SourceFile>();	
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(sourceFileDirectory, ".csv ");
	
		//Loop through files in sourceFileDirectory and populate SourceFile objects
		for (String fileName : fileNames) {
	    	SourceFile newSourceFile = new SourceFile();	
	    	newSourceFile.setFileName(fileName);
	    	int startOfExtension = fileName.lastIndexOf(".")+1;
	    	newSourceFile.setFileExtension(fileName.substring(startOfExtension, fileName.length()));
	    	newSourceFile.setStatus(SourceFileManager.INITIALIZED);
	    	//newSourceFile.setProvider(getProvider(fileName.substring(0, (int)fileName.indexOf("_"))));	
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
				logger.warn("No Provider found for file '{}' (Array Index #{}). File has been marked for removal", sourceFile.getFileName(), sourceFiles.indexOf(sourceFile));
				badFiles.add(sourceFiles.indexOf(sourceFile));
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

	
	/**
	 * This Function manages the processing of all of the source files.
	 * It loads that data into the SourceFile object and then performs any sort of cleansing
	 * validation, and/or processing on the data.
	 * 
	 * @param sourceFilesDirectory
	 * @param sourceFiles
	 */
	public static void processSourceFiles(String sourceFilesDirectory,
			ArrayList<SourceFile> sourceFiles) {
		//OK, now we start processing the files one at a time.
		for ( SourceFile sourceFile : sourceFiles) {
			if(sourceFile.getFileExtension().toUpperCase().equals("CSV")){
				logger.info("Processing File {} as a 'CSV'", sourceFile.getFileName()); 
				processCSV(sourceFilesDirectory, sourceFile);
			}else if(sourceFile.getFileExtension().toUpperCase().equals("XML")){
				 logger.info("Processing File {} as a 'XML'", sourceFile.getFileExtension());					
			}else if(sourceFile.getFileExtension().toUpperCase().equals("XLSX")){
				 logger.info("Processing File {} as a 'XLSX'", sourceFile.getFileExtension());						
			}
		}
	}



	private static void processCSV(String sourceFilesDirectory, SourceFile sourceFile) {
		 int recordCount = 0;
		
		 try {
			ArrayList<SourceFileRecord> sourceFileRecords = new ArrayList<SourceFileRecord>();
			Reader in = new FileReader(sourceFilesDirectory + sourceFile.getFileName());
			final CSVParser parser = new CSVParser(in, CSVFormat.EXCEL.withHeader());
			sourceFile.setHeaders(parser.getHeaderMap());
			//logger.info("{}",parser.getHeaderMap());
			
			for (final CSVRecord csvRecord : parser) {
				recordCount ++;
		        //final String string = record.get("Ord_num");
				SourceFileRecord thisRecord = new SourceFileRecord();
				HashMap<Integer, String> thisData = new HashMap<Integer, String>(); 
				ArrayList<String> string = new ArrayList<String>();
				
				Map<String,Integer> header = sourceFile.getHeaders(); 	
				Iterator it = header.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					
					try {
						thisData.put((Integer)pairs.getValue(), csvRecord.get(pairs.getKey().toString()));
						string.add(pairs.getKey() + ": " + csvRecord.get(pairs.getKey().toString()));
					} catch (IllegalArgumentException e) {
						logger.error("Failed to process record '{} - {}' in file '{}'", pairs.getKey().toString(), pairs.getValue().toString(), sourceFile.getFileName());
						e.printStackTrace();
					}
					
				}
				thisRecord.setData(thisData);
		        //logger.debug("{}", string);
				sourceFile.addRecord(thisRecord);
		    }
			logger.info("{} out of {} Records successfully processed in {}", sourceFile.recordCount(), recordCount, sourceFile.getFileName());
			
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
	public static void outputStagedSourceFiles(String stagedDirectory, ArrayList<SourceFile> sourceFiles) {
		
		//OK, now we start processing the files one at a time.
		for ( SourceFile sourceFile : sourceFiles) {
			
			String newFileName = FileHelper.buildFileName(sourceFile.getFileName(), sourceFile.getProvider().getFileOutputType());
			
			if(sourceFile.getProvider().getFileOutputType().toUpperCase().equals("CSV")){
				logger.info("Exporting File {} as a 'CSV'", sourceFile.getFileName()); 
				//outputAsCSV(stagedDirectory, newFileName, sourceFile);
			}else if(sourceFile.getProvider().getFileOutputType().toUpperCase().equals("XML")){
				//logger.info("Exporting File {} as a 'XML'", sourceFile.getFileExtension());	
				logger.error("We don't currently handle XML output at this point");
			}else if(sourceFile.getProvider().getFileOutputType().toUpperCase().equals("XLSX")){
				logger.info("Exporting File {} as a 'XLSX'", sourceFile.getFileName());
				outputAsXLSX(stagedDirectory, newFileName, sourceFile);

				
			}
		}
		
	}



	/**
	 * @param stagedDirectory
	 * @param newFileName
	 */
	private static void outputAsXLSX(String stagedDirectory, String newFileName, SourceFile sourceFile) {
		// create a new file
		FileOutputStream out;
		try {
			out = new FileOutputStream(stagedDirectory + newFileName);

		// create a new workbook
		Workbook wb = new HSSFWorkbook();
		// create a new sheet
		Sheet s = wb.createSheet();
		// declare a row object reference
		Row r = null;
		// declare a cell object reference
		Cell c = null;

		//creating header row
		r = s.createRow(0);

		Map<String,Integer> headers = sourceFile.getHeaders(); 	
		Iterator headerMap = headers.entrySet().iterator();
		while (headerMap.hasNext()) {
			Map.Entry header = (Map.Entry)headerMap.next();
			c = r.createCell((int) header.getValue());
			c.setCellValue((String)header.getKey());
		}
		
		int counter = 0;
		//Now lets put some data in there....
		for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
			counter ++;	
			r = s.createRow(counter);
			
			Map<Integer,String> records = sourceFileRecord.getData(); 	
			Iterator dataMap = records.entrySet().iterator();
			while (dataMap.hasNext()) {
				Map.Entry data = (Map.Entry)dataMap.next();
				//logger.debug("{}", data.getKey());
				c = r.createCell((int)data.getKey());
				c.setCellValue((String)data.getValue());
			}
		}
				
//		int rownum;
//		for (rownum = (short) 0; rownum < 30; rownum++)
//		{
//		    // create a row
//		    r = s.createRow(rownum);
//
//		    for (short cellnum = (short) 0; cellnum < 10; cellnum += 2)
//		    {
//		        // create a numeric cell
//		        c = r.createCell(cellnum);
//		        // do some goofy math to demonstrate decimals
//		        c.setCellValue(rownum * 10000 + cellnum
//		                + (((double) rownum / 1000)
//		                + ((double) cellnum / 10000)));
//
//		        String cellValue;
//
//		        // create a string cell (see why += 2 in the
//		        c = r.createCell((short) (cellnum + 1));
//		        
//		        // on every other row
//		        if ((rownum % 2) == 0)
//		        {
//		            // set this cell to the first cell style we defined
//		            //c.setCellStyle(cs);
//		            // set the cell's string value to "Test"
//		            c.setCellValue( "Test" );
//		        }
//		        else
//		        {
//		            //c.setCellStyle(cs2);
//		            // set the cell's string value to "\u0422\u0435\u0441\u0442"
//		            c.setCellValue( "\u0422\u0435\u0441\u0442" );
//		        }
//
//
//		        // make this column a bit wider
//		        s.setColumnWidth((short) (cellnum + 1), (short) ((50 * 8) / ((double) 1 / 20)));
//		    }
//		}


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
	private static void outputAsCSV(String stagedDirectory, String newFileName, SourceFile sourceFile) {
		//Delimiter used in CSV file
		String newLineSeparator = "\n";
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		
		//Create the CSVFormat object with "\n" as a record delimiter
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(newLineSeparator);
				
		try {
			
			//initialize FileWriter object
			fileWriter = new FileWriter(stagedDirectory + newFileName);
			
			//initialize CSVPrinter object 
		    csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
		    
		    
			List csvHeaders = new ArrayList();
			
			Map<String, Integer> headerMap = sourceFile.getHeaders(); 	
			Iterator iter = headerMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry)iter.next();
				csvHeaders.add(pairs.getKey().toString());
			}
		    
		    
		    //Create CSV file header
		    csvFilePrinter.printRecord(csvHeaders);
			
			//Write a new student object list to the CSV file
			for (SourceFileRecord data : sourceFile.getRecords()) {
				List csvRecord = new ArrayList();

				Map<Integer,String> dataMap = data.getData(); 	
				Iterator it = dataMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry pairs = (Map.Entry)it.next();
					csvRecord.add(pairs.getValue().toString());
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
