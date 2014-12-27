package gov.gsa.fssi.fileprocessor.providers;

import gov.gsa.fssi.fileprocessor.FileHelper;
import gov.gsa.fssi.fileprocessor.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * The purpose of this class is to quickly load provider data. It goes out to the provider.xls file
 * and loads all of its data into provider objects
 * 
 *TODO: Make this process iterate over all files in the "Providers" directory.
 * 
 * @author davidlarrimore
 *
 */
public class ProviderManager {
	static Logger logger = LoggerFactory.getLogger(ProviderManager.class);
	
	public static ArrayList<Provider> initializeProviders(String providerDirectory) {	
		ArrayList<Provider> providers = new ArrayList<Provider>();
		Workbook wb;
	    
	    logger.debug("Starting initializeProviders('{}')", providerDirectory);
		
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(providerDirectory, ".xlsx");
		
		for (String fileName : fileNames) {		
			try {
			    int providerIdentifierColumn = 0;
			    int providerNameColumn = 0;
			    int providerIdColumn = 0;	    
			    int fileIdentifierColumn = 0;	
			    int fileInputTypeColumn = 0;		    
			    int fileOutputTypeColumn = 0;	
			    int schemaColumn = 0;	    
			    int dataMappingTemplateColumn = 0;	    
			    int schemaValidationColumn = 0;	
			    int providerEmailColumn = 0;
				wb = WorkbookFactory.create(new File(providerDirectory + fileName));
				Sheet sheet1 = wb.getSheetAt(0);
			    
			    for (Row row : sheet1) {
			    	Provider newProvider = new Provider();	
			    	//If this is the header row, we need to figure out where the columns we need are
			    	if (row.getRowNum() == 0){
			    		for (Cell cell : row) {
			    			switch(cell.getStringCellValue().toUpperCase()) {
				    			case "PROVIDER_ID":
				    				providerIdColumn = cell.getColumnIndex();
				    			case "PROVIDER_NAME":			    				
				    				providerNameColumn = cell.getColumnIndex();
				    			case "PROVIDER_IDENTIFIER":
				    				providerIdentifierColumn = cell.getColumnIndex();
				    			case "FILE_IDENTIFIER":
				    				fileIdentifierColumn = cell.getColumnIndex();
				    			case "FILE_INPUT_TYPE":
				    				fileInputTypeColumn = cell.getColumnIndex();
				    			case "FILE_OUTPUT_TYPE":
				    				fileOutputTypeColumn = cell.getColumnIndex();
				    			case "SCHEMA":
				    				schemaColumn = cell.getColumnIndex();
				    			case "DATA_MAPPING_TEMPLATE":
				    				dataMappingTemplateColumn = cell.getColumnIndex();
				    			case "SCHEMA_VALIDATION":
				    				schemaValidationColumn = cell.getColumnIndex();		
				    			case "PROVIDER_EMAIL":
				    				providerEmailColumn = cell.getColumnIndex();						    				
				    			default:
				    				break;
			    			}
			    		}
			    	}else{
			    		
			    		try {
			    			
				    			if (!(row.getCell(providerIdColumn) == null) && !(row.getCell(providerIdColumn).getStringCellValue().isEmpty())){
				    				newProvider.setProviderId(row.getCell(providerIdColumn).getStringCellValue());
				    			}
				    				    			
				    			if (!(row.getCell(providerNameColumn) == null) && !(row.getCell(providerNameColumn).getStringCellValue().isEmpty())){
				    				newProvider.setProviderName(row.getCell(providerNameColumn).getStringCellValue());
				    			}
				    			
				    			if (!(row.getCell(providerIdentifierColumn) == null) && !(row.getCell(providerIdentifierColumn).getStringCellValue().isEmpty())){
				    				newProvider.setProviderIdentifier(row.getCell(providerIdentifierColumn).getStringCellValue());
				    			}
				    			
				    			if (!(row.getCell(fileIdentifierColumn) == null) && !(row.getCell(fileIdentifierColumn).getStringCellValue().isEmpty())){
				    				newProvider.setFileIdentifier(row.getCell(fileIdentifierColumn).getStringCellValue());
				    			}	
			    			
				    			if (!(row.getCell(fileInputTypeColumn) == null) && !(row.getCell(fileInputTypeColumn).getStringCellValue().isEmpty())){
				    				newProvider.setFileInputType(row.getCell(fileInputTypeColumn).getStringCellValue());
				    			}				    			
				    			
				    			if (!(row.getCell(fileOutputTypeColumn) == null) && !(row.getCell(fileOutputTypeColumn).getStringCellValue().isEmpty())){
				    				newProvider.setFileOutputType(row.getCell(fileOutputTypeColumn).getStringCellValue());
				    			}			    			
				    			
				    			if (!(row.getCell(schemaColumn) == null) && !(row.getCell(schemaColumn).getStringCellValue().isEmpty())){
				    				newProvider.setSchema(row.getCell(schemaColumn).getStringCellValue());
				    			}				    						    			
				    			
				    			if (!(row.getCell(dataMappingTemplateColumn) == null) && !(row.getCell(dataMappingTemplateColumn).getStringCellValue().isEmpty())){
				    				newProvider.setDataMappingTemplate(row.getCell(dataMappingTemplateColumn).getStringCellValue());
				    			}			    			
				    			
				    			if (!(row.getCell(providerEmailColumn) == null) && !(row.getCell(providerEmailColumn).getStringCellValue().isEmpty())){
				    				newProvider.setProviderEmail(row.getCell(providerEmailColumn).getStringCellValue());
				    			}
				    			if (!(row.getCell(schemaValidationColumn) == null) && !(row.getCell(schemaValidationColumn).getStringCellValue().isEmpty())){
				    				newProvider.setSchemaValidation(row.getCell(schemaValidationColumn).getStringCellValue());
				    			}					    			
				    			
				    			
				    			//Certain fields are required.
				    			if (newProvider.getProviderId() == null || newProvider.getProviderName() == null || newProvider.getProviderIdentifier() == null){
				    				//logger.info("          Found provider record on row " + row.getRowNum() + " without all of the required fields...ignoring");
				    			}else{
				    				//logger.info("          Added Provider: " + newProvider.getProviderName() + " - " + newProvider.getProviderIdentifier());
				    				providers.add(newProvider);	
				    			}
	
			    			
			    		} catch (java.lang.NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    		
			    	}
			    }
		            
			} catch (FileNotFoundException e) {
				logger.error("Received FileNotFoundException while trying to load {}", fileName);		
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				logger.error("Received InvalidFormatException while trying to load {}", fileName);	
				e.printStackTrace();
			} catch (IOException e) {
				logger.error("Received IOException while trying to load {}", fileName);				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Successfully Processed {} Providers from '{}'", providers.size(), fileName);
		}
						
		//logger.info("Completed Provider setup. Added " + providers.size() + " Providers");	
		return providers;
	}	
}