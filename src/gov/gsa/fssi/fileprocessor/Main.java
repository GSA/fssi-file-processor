package gov.gsa.fssi.fileprocessor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.filepreocessor.sourceFiles.SourceFileManager;
import gov.gsa.fssi.filepreocessor.sourceFiles.SourceFile;
import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.providers.ProviderManager;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.fileprocessor.schemas.SchemaManager;




/**
 * This is the main class for the FSSI File Processor Project
 * 
 * @author David Larrimore
 * @version 0.1
 */
public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		
	    logger.info("Starting FSSI File Processor");

		//First things first, lets get all of our configuration settings	
	    Config config = new Config();	    
	    
		//Next, we need to get all of our provider info. We currently do this up front to make multi-file processing faster
	    ArrayList<Schema> schemas = SchemaManager.initializeSchemas(config.getProperty("schemas_directory"));

		if (logger.isDebugEnabled()){
			for ( Schema schema : schemas) {
				schema.printSchema();
			}
		}
		
		//Next, we need to get all of our provider info. We currently do this up front to make multi-file processing faster
		ArrayList<Provider> providers = ProviderManager.initializeProviders(config.getProperty("providers_directory"));

		
		//Next, we need to get all of our sourceFiles info. We currently do this up front to make multi-file processing faster
		ArrayList<SourceFile> sourceFiles = SourceFileManager.initializeSourceFiles(config.getProperty("sourcefiles_directory"));

		if (logger.isDebugEnabled()){
			for ( SourceFile sourceFile : sourceFiles) {
				sourceFile.printSourceFile();
			}
		}		
		
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
		

	    logger.info("Completed FSSI File Processor");
		
	}
	
	
}
