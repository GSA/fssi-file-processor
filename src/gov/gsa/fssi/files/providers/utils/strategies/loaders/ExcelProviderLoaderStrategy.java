package gov.gsa.fssi.files.providers.utils.strategies.loaders;

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

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.providers.Provider;
import gov.gsa.fssi.files.providers.utils.strategies.ProviderLoaderStrategy;


public class ExcelProviderLoaderStrategy implements ProviderLoaderStrategy {

	@Override
	public void load(String fileName, ArrayList<Provider> providers) {
		Workbook wb;
		try {
		    int providerIdentifierColumn = 0;
		    int providerNameColumn = 0;
		    int providerIdColumn = 0;	    	    
		    int fileOutputTypeColumn = 0;	
		    int schemaNameColumn = 0;			    
		    int providerEmailColumn = 0;
			wb = WorkbookFactory.create(new File(config.getProperty(Config.PROVIDERS_DIRECTORY) + fileName));
			Sheet sheet1 = wb.getSheetAt(0);
			
			int passCounter = 0;
			int failCounter = 0;
			
			
		    for (Row row : sheet1) {
		    	Provider newProvider = new Provider();	
		    	//If this is the header row, we need to figure out where the columns we need are
		    	if (row.getRowNum() == 0){
		    		for (Cell cell : row) {
		    			//logger.debug("{} - {}", cell.getColumnIndex(), cell.getStringCellValue().toUpperCase());
		    			switch(cell.getStringCellValue().toUpperCase().toString()) {
			    			case "PROVIDER_ID":
			    				providerIdColumn = cell.getColumnIndex();
			    				break;
			    			case "PROVIDER_NAME":			    				
			    				providerNameColumn = cell.getColumnIndex();
			    				break;
			    			case "PROVIDER_IDENTIFIER":
			    				providerIdentifierColumn = cell.getColumnIndex();
			    				break;
			    			case "FILE_OUTPUT_TYPE":
			    				fileOutputTypeColumn = cell.getColumnIndex();	
			    				break;
			    			case "SCHEMA":
			    				schemaNameColumn = cell.getColumnIndex();	
			    				break;			    				
			    			case "PROVIDER_EMAIL":
			    				providerEmailColumn = cell.getColumnIndex();	
			    				break;
			    			default:
			    				break;
		    			}
		    		}
		    	}else{
		    		try {
		    			
		    			if (!(row.getCell(providerIdColumn) == null) && !(row.getCell(providerIdColumn).getStringCellValue().isEmpty()) && !(row.getCell(providerIdColumn).getStringCellValue().toUpperCase().equals("NULL"))){
		    				newProvider.setProviderId(row.getCell(providerIdColumn).getStringCellValue().toUpperCase());
		    			}
		    				    			
		    			if (!(row.getCell(providerNameColumn) == null) && !(row.getCell(providerNameColumn).getStringCellValue().isEmpty()) && !(row.getCell(providerNameColumn).getStringCellValue().toUpperCase().equals("NULL"))){
		    				newProvider.setProviderName(row.getCell(providerNameColumn).getStringCellValue().toUpperCase());
		    			}
		    			
		    			if (!(row.getCell(providerIdentifierColumn) == null) && !(row.getCell(providerIdentifierColumn).getStringCellValue().isEmpty()) && !(row.getCell(providerIdentifierColumn).getStringCellValue().toUpperCase().equals("NULL"))){
		    				newProvider.setProviderIdentifier(row.getCell(providerIdentifierColumn).getStringCellValue().toUpperCase());
		    			}				    			
		    			
		    			if (!(row.getCell(fileOutputTypeColumn) == null) && !(row.getCell(fileOutputTypeColumn).getStringCellValue().isEmpty()) && !(row.getCell(fileOutputTypeColumn).getStringCellValue().toUpperCase().equals("NULL"))){
		    				newProvider.setFileOutputType(row.getCell(fileOutputTypeColumn).getStringCellValue().toUpperCase());
		    			}			    					    			
		    			
		    			if (!(row.getCell(schemaNameColumn) == null) && !(row.getCell(schemaNameColumn).getStringCellValue().isEmpty()) && !(row.getCell(schemaNameColumn).getStringCellValue().toUpperCase().equals("NULL"))){
		    				newProvider.setSchemaName(row.getCell(schemaNameColumn).getStringCellValue().toUpperCase());
		    			}				    			
		    			
		    			if (!(row.getCell(providerEmailColumn) == null) && !(row.getCell(providerEmailColumn).getStringCellValue().isEmpty()) && !(row.getCell(providerEmailColumn).getStringCellValue().toUpperCase().equals("NULL"))){
		    				newProvider.setProviderEmail(row.getCell(providerEmailColumn).getStringCellValue().toUpperCase());
		    				//logger.debug("{}", row.getCell(providerEmailColumn).getStringCellValue());
		    			}					    			
	    				providers.add(newProvider);
	    				logger.info("Added new provider '{}' to list of Providers", newProvider.getProviderIdentifier());
		    		} catch (java.lang.NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
		    	}
		    }
		    
			logger.info("Successfully Processed {} Providers ({} Failed) from '{}'", passCounter, failCounter, fileName);	
			
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
		
		
	}

}
