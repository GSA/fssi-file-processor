package gov.gsa.fssi.files.sourceFiles.utils.exporters;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.LoaderStatus;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;


/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class SourceFileExporterCSV implements SourceFileExporter{

	/**
	 *
	 * @return Schema loaded from fileName in schemas_directory
	 */
	public void export(SourceFile sourceFile) {
		// SourceFile sourceFile = new SourceFile(this.getFileName());
		try {
			Reader in = new FileReader(config.getProperty(Config.SOURCEFILES_DIRECTORY) + sourceFile.getFileName());
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
						Map.Entry dataPairs = (Map.Entry)headerIterator.next();
						Data data = new Data();
						try {
							data.setData(csvRecord.get(dataPairs.getValue().toString()).trim());
							data.setHeaderIndex((Integer)dataPairs.getKey());
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
			sourceFile.setLoaderStatusLevel(LoaderStatus.LOADED);
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
