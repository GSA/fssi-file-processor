package gov.gsa.fssi;

import gov.gsa.fssi.contracts.Contract;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
 * This is the main class for the FSSI File Processor Project
 * 
 * @author David Larrimore
 * @version 0.1
 */
public class Main {
	public static String WORKING_DIRECTORY = "./working/";
	public static String SOURCEFILES_DIRECTORY = WORKING_DIRECTORY + "srcfiles/";
	public static String CONFIG_DIRECTORY = WORKING_DIRECTORY + "config/";
	public static String LOG_DIRECTORY = WORKING_DIRECTORY + "logs/";
	
	public static void main(String[] args) {

		Workbook wb;
	    int solutionNameColumn = 0;
	    int contractNameColumn = 0;
	    ArrayList<Contract> contracts = new ArrayList<Contract>();
	    Contract contract;
	    
		try {
			wb = WorkbookFactory.create(new File(CONFIG_DIRECTORY + "FSSI_CONTRACT_XREF.xlsx"));
			Sheet sheet1 = wb.getSheetAt(0);
		    
		    for (Row row : sheet1) {
		    	//If this is the header row, we need to figure out where the columns we need are
		    	if (row.getRowNum() == 0){
		    		for (Cell cell : row) {
		    			if (cell.getCellType() ==Cell.CELL_TYPE_STRING){
		    				//System.out.println(cell.getStringCellValue().toUpperCase()) ;
		    				if ((cell.getStringCellValue().toUpperCase().equals("CONTRACT_NUMBER"))){
		    					 System.out.println("I found the Contract Number!!!") ;
		    					 contractNameColumn = cell.getColumnIndex();
		    				}else if ((cell.getStringCellValue().toUpperCase().equals("SOLUTION_NAME"))) {
		    					solutionNameColumn = cell.getColumnIndex();								
							}
		    			}
					}
		    	}else{
		    		
		    		try {
		    			if (!(row.getCell(contractNameColumn) == null) && !(row.getCell(solutionNameColumn) == null)){
		    				if (!(row.getCell(contractNameColumn).getStringCellValue().isEmpty()) && !(row.getCell(solutionNameColumn).getStringCellValue().isEmpty())){
		    					
		    				contracts.add(contract);
		    				//contractMap.put(row.getCell(contractNameColumn).getStringCellValue(), row.getCell(solutionNameColumn).getStringCellValue());
		    				//System.out.println(row.getCell(contractNameColumn).getStringCellValue() + " - " + row.getCell(solutionNameColumn).getStringCellValue()) ;
		    				}
		    			}
		    		} catch (java.lang.NullPointerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		
		    	}
		    }
	            
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		//System.out.println(contractMap);;
		
		//TODO: Read source csv files and format them
		//TODO: create object model for 
		
		
		
		
		
		
		//TODO: Read source csv
//		try {
//			Reader reader = new FileReader(SOURCEFILES_DIRECTORY +"GS07FBA394_usg_102014_002.csv");
//			final CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader());
//			
//			for (final CSVRecord record : parser) {
//		        System.out.println(record.get("ORDER_NUMBER"));
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//System.out.println("Hello World");
		
	}
	
}
