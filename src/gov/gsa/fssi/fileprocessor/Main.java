package gov.gsa.fssi.fileprocessor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.providers.ProviderManager;
import gov.gsa.fssi.fileprocessor.schemas.Schema;
import gov.gsa.fssi.fileprocessor.schemas.SchemaManager;
import gov.gsa.fssi.fileprocessor.sourceFiles.SourceFile;
import gov.gsa.fssi.fileprocessor.sourceFiles.SourceFileManager;
import gov.gsa.fssi.fileprocessor.sourceFiles.records.SourceFileRecord;




/**
 * This is the main class for the FSSI File Processor Project
 * 
 * @author David Larrimore
 * @version 0.1
 */
/**
 * @author davidlarrimore
 *
 */
public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) {
		
	    logger.info("Starting FSSI File Processor");
	    logger.info("Starting Phase 1: Initialization");
		//First things first, lets get all of our configuration settings	
	    Config config = new Config();	    
	    
		//Next, we need to get all of our provider info. We currently do this up front to make multi-file processing faster
	    ArrayList<Schema> schemas = SchemaManager.initializeSchemas(config.getProperty("schemas_directory"));

		if (logger.isDebugEnabled()){
			logger.debug("Printing Schemas");
			for ( Schema schema : schemas) {
				schema.print();
			}
		}
		
		//Next, we need to get all of our provider info. We currently do this up front to make multi-file processing faster
		ArrayList<Provider> providers = ProviderManager.initializeProviders(config.getProperty("providers_directory"));

		if (logger.isDebugEnabled()){
			logger.debug("Printing Providers");
			for ( Provider provider : providers) {
				provider.print();
			}
		}	
		
		
		//Next, we need to get all of our sourceFiles info. We currently do this up front to make multi-file processing faster
		ArrayList<SourceFile> sourceFiles = SourceFileManager.initializeSourceFiles(config.getProperty("sourcefiles_directory"));
	
		
	    logger.info("Completed Phase 1");
	    logger.info("Starting Phase 2: Mapping");
		
	    
	    
	    //OK, now the fun stuff begins. The first step we do is map all of the files to schemas and providers. 
		SourceFileManager.validateSourceFileProviders(providers, sourceFiles);	
		
		
		//OK, now we map all of the files to the appropriate schemas 
		SourceFileManager.validateSourceFileSchemas(schemas, sourceFiles);	

	    logger.info("Completed Phase 2");
	    logger.info("Starting Phase 3: File Processing");
	    
	    
	  //OK, now we process the files
	    SourceFileManager.processSourceFiles(config.getProperty("sourcefiles_directory"), sourceFiles);	    
	    
	    
//		if (logger.isDebugEnabled()){
//			logger.debug("Printing Source Files");
//			for ( SourceFile sourceFile : sourceFiles) {
//				sourceFile.print();
//			}
//			logger.debug("Found {} sourceFiles", sourceFiles.size());
//		}	
		
		
	    logger.info("Completed Phase 3");
	    logger.info("Starting Phase 4: File Output");
		
	    SourceFileManager.outputStagedSourceFiles(config.getProperty("staged_directory"), sourceFiles);
	    
	    logger.info("Completed FSSI File Processor");
		
	}

	
}
