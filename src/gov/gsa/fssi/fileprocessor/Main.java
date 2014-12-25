package gov.gsa.fssi.fileprocessor;

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

import gov.gsa.fssi.filepreocessor.sourceFiles.FileManager;
import gov.gsa.fssi.filepreocessor.sourceFiles.SourceFile;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.providers.ProviderManager;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.fileprocessor.schemas.SchemaManager;
import gov.gsa.fssi.fileprocessor.schemas.fields.Field;




/**
 * This is the main class for the FSSI File Processor Project
 * 
 * @author David Larrimore
 * @version 0.1
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("Starting FSSI File Processor");
		//First things first, lets get all of our configuration settings	
		Config config = new Config();	
		
		System.out.println(" ");	
		
		//Next, we need to get all of our provider info. We currently do this up front to make multi-file processing faster
		ArrayList<Provider> providers = ProviderManager.initializeProviders(config.getProperty("providers_directory"));
		
		//Next, we need to get all of our provider info. We currently do this up front to make multi-file processing faster
		ArrayList<Schema> schemas = SchemaManager.initializeSchemas(config.getProperty("schemas_directory"));

		for ( Schema schema : schemas) {
			schema.printlnSchema();
		}
		
		
		//Next, we need to get all of our provider info. We currently do this up front to make multi-file processing faster
		ArrayList<SourceFile> sourceFiles = FileManager.initializeSourceFiles(config.getProperty("sourcefiles_directory"));

		
		
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
		
		System.out.println("Completed FSSI File Processor");
		
		
	}
	
	
}
