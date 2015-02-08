package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.loaders;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileLoaderStrategy;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class CSVSourceFileLoaderStrategy implements SourceFileLoaderStrategy{

	/**
	 *
	 * @return Schema loaded from fileName in schemas_directory
	 */
	public void load(String directory, String fileName, SourceFile sourceFile) {
		// SourceFile sourceFile = new SourceFile(this.getFileName());
		try {
			
			//File file = new File(FileHelper.getFullPath(directory, fileName));
			InputStream inputStream = new FileInputStream(FileHelper.getFullPath(directory, fileName));
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
	
			
			
			//InputStream in = ...
		    //Charset cs = new
			//InputStreamReader reader = new InputStreamReader(in, cs);
			//ReaderInputStream in2 = new ReaderInputStream(reader, cs);
			
			
			
			
			//Converting Apache Commons CSV header map from <String, Integer> to <Integer,String>
			Map<String, Integer> parserHeaderMap = parser.getHeaderMap();
			Iterator<?> parserHeaderMapIterator = parserHeaderMap.entrySet().iterator();
			while (parserHeaderMapIterator.hasNext()) {
				Map.Entry pairs = (Map.Entry)parserHeaderMapIterator.next();
				sourceFile.addSourceHeader((Integer)pairs.getValue(), pairs.getKey().toString());
			}
			
			logger.info("file '{}' had the following headers: {}",fileName, parser.getHeaderMap());
			
			for (final CSVRecord csvRecord : parser) {
				sourceFile.incrementTotalRecords();
				
				SourceFileRecord thisRecord = new SourceFileRecord();
				thisRecord.setRowIndex((int)csvRecord.getRecordNumber()+1);
				//Ignoring null rows
				if (csvRecord.size() > 1 && sourceFile.getSourceHeaders().size() > 1){
					Iterator<?> headerIterator = sourceFile.getSourceHeaders().entrySet().iterator();
					while (headerIterator.hasNext()) {
						Map.Entry dataPairs = (Map.Entry)headerIterator.next();
						Data data = new Data();
						try {
							data.setData(csvRecord.get(dataPairs.getValue().toString()).trim());
							data.setHeaderIndex((Integer)dataPairs.getKey());
							thisRecord.addData(data);
						} catch (IllegalArgumentException e) {
							//logger.error("Failed to process record '{} - {}' in file '{}'", pairs.getKey().toString(), pairs.getValue().toString(), sourceFile.getFileName());
							logger.error("{}", e.getMessage());
						}
						
					}
					
					//Checking to see if any data was in the row. if nothing is found, we consider this an Empty Record
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
						thisRecord.setStatus(File.STAGE_LOADED);
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
			sourceFile.setLoadStage(File.STAGE_LOADED);
			reader.close();
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
	

}
