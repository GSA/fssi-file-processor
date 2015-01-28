package gov.gsa.fssi.files.sourceFiles.utils.strategies.organizers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileOrganizerStrategy;


/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class ImplodeSourceFileOrganizerStrategy implements SourceFileOrganizerStrategy{

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
		
		//The headerTranslationMap object translates the old headerIndex, to the new header index.
		//Key = Old Index, Value = New Index
		HashMap<Integer, Integer> headerTranslationMap = new HashMap<Integer, Integer>();
		
		//First, lets add all of the fields from our Schema, they always go first
		for (SchemaField field : sourceFile.getSchema().getFields()) {
			String fieldName = field.getName();
			//based upon prior processes, their is probably a match, so lets check!
			if(field.getHeaderIndex() >= 0){
				newHeader.put(headerCounter, field.getName());
				Iterator<?> currentHeaderIterator = sourceFile.getSourceHeaders().entrySet().iterator();
				while (currentHeaderIterator.hasNext()) {
					Map.Entry<Integer, String> currentHeaderIteratorPairs = (Map.Entry)currentHeaderIterator.next();
					if(currentHeaderIteratorPairs.getKey() == field.getHeaderIndex()){
						if(logger.isDebugEnabled()){
							logger.debug("Found SourceFile Field '{}' that matched Schema Field!", currentHeaderIteratorPairs.getValue());
						}
						//we allways use the source files naming to keep it consistant
						headerTranslationMap.put(currentHeaderIteratorPairs.getKey(), headerCounter);
						fieldName = currentHeaderIteratorPairs.getValue();
					}
				}	
			}
			
			field.setHeaderIndex(headerCounter);
			if(logger.isDebugEnabled()){
				logger.debug("Adding '{} - {}' to newHeader", headerCounter, fieldName);
			}
			newHeader.put(headerCounter, fieldName);
			headerCounter ++;	
		}
		
		
		//Now we need to find the stragglers...so we can delete them!
		ArrayList<Integer> deleteFieldDataList = new ArrayList<Integer>();
		Iterator<?> currentHeaderIterator = sourceFile.getSourceHeaders().entrySet().iterator();
		while (currentHeaderIterator.hasNext()) {
			Map.Entry<Integer, String> currentHeaderIteratorPairs = (Map.Entry)currentHeaderIterator.next();		
			boolean foundColumn = false;		
			Iterator<?> headerTranslationMapIterator = headerTranslationMap.entrySet().iterator();
			//We need to loop through the translation map and see if we have already addressed this column
			while (headerTranslationMapIterator.hasNext()) {
				Map.Entry<Integer, String> headerTranslationMapIteratorPairs = (Map.Entry)headerTranslationMapIterator.next();
					if (currentHeaderIteratorPairs.getKey() == headerTranslationMapIteratorPairs.getKey()){
						foundColumn = true;
					}
				}
				if (!foundColumn){
					//Could not find column, lets add it to index!
					logger.info("Source field '{} - {}' was not in Schema, Adding to Delete List", currentHeaderIteratorPairs.getValue().toString().toUpperCase(), currentHeaderIteratorPairs.getKey(),  headerCounter);
					deleteFieldDataList.add(currentHeaderIteratorPairs.getKey());
				}
		}
		
		//Now we delete the old data. We do this before we re-stack the headers
		logger.info("Deleting data from the following headers: {}", deleteFieldDataList);
		for(SourceFileRecord sourceFileRecord: sourceFile.getRecords()){
			for(Integer deleteField : deleteFieldDataList){
				sourceFileRecord.deleteDataByHeaderIndex(deleteField);
			}
		}
		
		//Lets print the new headers
		logger.info("Old Header:{}", sourceFile.getSourceHeaders());
		sourceFile.setSourceHeaders(newHeader);
		logger.debug("New Header: {}", sourceFile.getSourceHeaders());

		
		//now we can process the data by restacking data and filling in the blanks
		for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
			//Restacking
			for (Data data : sourceFileRecord.getDatas()) {
					data.setHeaderIndex(headerTranslationMap.get(data.getHeaderIndex()));
			}
			
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

}
