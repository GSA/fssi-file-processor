package gov.gsa.fssi.files.sourceFiles.utils.strategies.exporters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileExporterStrategy;
import gov.gsa.fssi.helpers.FileHelper;


/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class CSVSourceFileExporterStrategy implements SourceFileExporterStrategy{

	/**
	 *
	 * @return Schema loaded from fileName in schemas_directory
	 */
	public void export(SourceFile sourceFile) {
		//Delimiter used in CSV file
		String newFileName = FileHelper.buildNewFileName(sourceFile.getFileName(), sourceFile.getProvider().getFileOutputType());
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
			Map<Integer,String> headerMap = sourceFile.getSourceHeaders(); 	
			Iterator<?> headerMapIterator = headerMap.entrySet().iterator();
			while (headerMapIterator.hasNext()) {
				Map.Entry pairs = (Map.Entry)headerMapIterator.next();
				csvHeaders.add(pairs.getValue().toString());
			}
		    
		    
		    //Create CSV file header
		    csvFilePrinter.printRecord(csvHeaders);
			
		    //Writing Data
			for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
				List<String> csvRecord = new ArrayList<String>();
				for(int i = 0;i < sourceFile.getSourceHeaders().size();i++){
					if(sourceFileRecord.getDataByHeaderIndex(i)!= null && sourceFileRecord.getDataByHeaderIndex(i).getData() != null){
						//sourceFileRecord.print();
						csvRecord.add(sourceFileRecord.getDataByHeaderIndex(i).getData());						
					}else{
						csvRecord.add("");
					}	
				}
				
		        csvFilePrinter.printRecord(csvRecord);
			}

			logger.info("{} Created Successfully. {} Records processed", sourceFile.getFileName(), sourceFile.recordCount());
			
		} catch (Exception e) {
			logger.error("Received Exception '{}' while processing {}", e.getMessage(), sourceFile.getFileName());
			e.printStackTrace();
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
