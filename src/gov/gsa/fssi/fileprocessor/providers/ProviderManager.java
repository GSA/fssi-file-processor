package gov.gsa.fssi.fileprocessor.providers;

import gov.gsa.fssi.fileprocessor.FileHelper;

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
	
	public static ArrayList<Provider> initializeProviders(String providerDirectory) {	
		ArrayList<Provider> providers = new ArrayList<Provider>();
		Workbook wb;
	    int providerIdentifierColumn = 0;
	    int providerNameColumn = 0;
	    int providerIdColumn = 0;	    
	    int fileIdentifierColumn = 0;	
	    int fileInputTypeColumn = 0;		    
	    int fileOutputTypeColumn = 0;	
	    int schemaColumn = 0;	    
	    int dataMappingTemplateColumn = 0;	    
	    int schemaValidationColumn = 0;	 
	    
		System.out.println("Setting up Providers");
		System.out.println("----------------------------");
		
		ArrayList<String> fileNames = FileHelper.getFilesFromDirectory(providerDirectory, ".xlsx");
		
		for (String fileName : fileNames) {		
			try {
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
				    			default:
				    				break;
			    			}
			    		}
			    	}else{
			    		
			    		try {
			    			
				    			if (!(row.getCell(providerIdColumn) == null) && !(row.getCell(providerIdColumn).getStringCellValue().isEmpty())){
				    				newProvider.setProviderId(row.getCell(providerIdColumn).getStringCellValue());
				    			}else{
				    				newProvider.setProviderId(null);
				    			}
				    				    			
				    			if (!(row.getCell(providerNameColumn) == null) && !(row.getCell(providerNameColumn).getStringCellValue().isEmpty())){
				    				newProvider.setProviderName(row.getCell(providerNameColumn).getStringCellValue());
				    			}else{
				    				newProvider.setProviderName(null);
				    			}
				    			
				    			if (!(row.getCell(providerIdentifierColumn) == null) && !(row.getCell(providerIdentifierColumn).getStringCellValue().isEmpty())){
				    				newProvider.setProviderIdentifier(row.getCell(providerIdentifierColumn).getStringCellValue());
				    			}else{
				    				newProvider.setProviderIdentifier(null);
				    			}
				    			
				    			if (!(row.getCell(fileIdentifierColumn) == null) && !(row.getCell(fileIdentifierColumn).getStringCellValue().isEmpty())){
				    				newProvider.setFileIdentifier(row.getCell(fileIdentifierColumn).getStringCellValue());
				    			}else{
				    				newProvider.setFileIdentifier(null);
				    			}	
			    			
				    			if (!(row.getCell(fileInputTypeColumn) == null) && !(row.getCell(fileInputTypeColumn).getStringCellValue().isEmpty())){
				    				newProvider.setFileInputType(row.getCell(fileInputTypeColumn).getStringCellValue());
				    			}else{
				    				newProvider.setFileInputType(null);
				    			}				    			
				    			
				    			if (!(row.getCell(fileOutputTypeColumn) == null) && !(row.getCell(fileOutputTypeColumn).getStringCellValue().isEmpty())){
				    				newProvider.setFileOutputType(row.getCell(fileOutputTypeColumn).getStringCellValue());
				    			}else{
				    				newProvider.setFileOutputType(null);
				    			}				    			
				    			
				    			if (!(row.getCell(schemaColumn) == null) && !(row.getCell(schemaColumn).getStringCellValue().isEmpty())){
				    				newProvider.setSchema(row.getCell(schemaColumn).getStringCellValue());
				    			}else{
				    				newProvider.setSchema(null);
				    			}				    						    			
				    			
				    			if (!(row.getCell(dataMappingTemplateColumn) == null) && !(row.getCell(dataMappingTemplateColumn).getStringCellValue().isEmpty())){
				    				newProvider.setDataMappingTemplate(row.getCell(dataMappingTemplateColumn).getStringCellValue());
				    			}else{
				    				newProvider.setDataMappingTemplate(null);
				    			}				    			
				    			
				    			if (!(row.getCell(schemaValidationColumn) == null) && !(row.getCell(schemaValidationColumn).getStringCellValue().isEmpty())){
				    				newProvider.setSchemaValidation(row.getCell(schemaValidationColumn).getStringCellValue());
				    			}else{
				    				newProvider.setSchemaValidation(null);
				    			}					    			
				    			
				    			
				    			//Certain fields are required.
				    			if (newProvider.getProviderId() == null || newProvider.getProviderName() == null || newProvider.getProviderIdentifier() == null){
				    				//System.out.println("          Found provider record on row " + row.getRowNum() + " without all of the required fields...ignoring");
				    			}else{
				    				//System.out.println("          Added Provider: " + newProvider.getProviderName() + " - " + newProvider.getProviderIdentifier());
				    				providers.add(newProvider);	
				    			}
	
			    			
			    		} catch (java.lang.NullPointerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    		
			    	}
			    }
		            
			} catch (FileNotFoundException e) {
				System.out.println("Received FileNotFoundException while trying to load Providers");	
				e.printStackTrace();
			} catch (InvalidFormatException e) {
				System.out.println("Received InvalidFormatException while trying to load Providers");	
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Received IOException while trying to load Providers");				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("     Successfully Processed " + providers.size() + " Providers from " + fileName);
		}
						
		System.out.println("Completed Provider setup. Added " + providers.size() + " Providers");	
		System.out.println(" ");	
		return providers;
	}	
}