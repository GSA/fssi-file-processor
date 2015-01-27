package gov.gsa.fssi.files.sourceFiles.utils.strategies.organizers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.File;
import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileLoaderStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileOrganizerStrategy;


/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class ExplodeSourceFileOrganizerStrategy implements SourceFileOrganizerStrategy{

	/**
	 * This method maps a sourceFile to its schema and then conforms the file/data to the schema format 
	 * We delete any data that is no longer necessary
	 */	
	@Override
	public void organize(SourceFile sourceFile) {
		logger.info("Exploding sourceFile '{}' to Schema '{}'", sourceFile.getFileName(), sourceFile.getSchema().getName());
		HashMap<Integer, String> newHeader = new HashMap<Integer, String>();	
		//This is our count to determine location of each header
		Integer headerCounter = 0;
		
		//First, lets add all of the fields from our Schema, they always go first
		for (SchemaField field : sourceFile.getSchema().getFields()) {
			newHeader.put(headerCounter, field.getName());
			field.setHeaderIndex(headerCounter);
			headerCounter ++;
		}
		
		//Second, prep this sourcefiles 
		updateFieldNamesToMatchSchema(sourceFile);
		//logger.debug("{}", this.getHeaders());
		//logger.debug("{}", newHeader);
		//Now we iterate through the existing header and add any additional fields as well as create our "Translation Map"
		Map<Integer, String> thisHeader = sourceFile.getSourceHeaders();
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
		logger.info("Old Header:{}", sourceFile.getSourceHeaders());
		sourceFile.setSourceHeaders(newHeader);
		logger.debug("New Header: {}", sourceFile.getSourceHeaders());
		logger.debug("Header Translation (Old/New): {}", headerTranslationMap);
		for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
			//sourceFileRecord.print();
			for (Data data : sourceFileRecord.getDatas()) {
					data.setHeaderIndex(headerTranslationMap.get(data.getHeaderIndex()));
			}
			//sourceFileRecord.print();
			
			//Now we fill in the blanks
			Iterator<?> sourceFileHeaderIterator2 = sourceFile.getSourceHeaders().entrySet().iterator();
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
	 * This method fixes the column header names (Key) to match the Schema.
	 */
	public void updateFieldNamesToMatchSchema(SourceFile sourceFile){
		if(sourceFile.getSchema() != null){
			logger.info("Atempting to map field names from Schema {} to File {}", sourceFile.getSchema().getName(), sourceFile.getFileName());
			Map<Integer, String> thisHeader = sourceFile.getSourceHeaders();
			//HashMap<Integer, String> newHeader = new HashMap<Integer, String>();
			Iterator<?> thisHeaderIterator = thisHeader.entrySet().iterator();
			while (thisHeaderIterator.hasNext()) {
				Map.Entry<Integer, String> thisHeaderPairs = (Map.Entry<Integer, String>)thisHeaderIterator.next();
				String sourceFileFieldName = thisHeaderPairs.getValue().toString().trim().toUpperCase();
				sourceFile.addSourceHeader((Integer)thisHeaderPairs.getKey(), (sourceFile.getSchema().getFieldAndAliasNames().contains(sourceFileFieldName))? sourceFile.getSchema().getFieldName(sourceFileFieldName): sourceFileFieldName);
			}
			logger.info("Headers have been updated");	
		}else{
			logger.info("No schema was found for file {}. Will not Map Schema Fields to Header", sourceFile.getFileName());
		}
	}
}
