package gov.gsa.fssi.fileprocessor;

import java.io.File;
import java.util.ArrayList;

import gov.gsa.fssi.fileprocessor.providers.Provider;
import gov.gsa.fssi.fileprocessor.providers.ProviderManager;




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
		System.out.println("Setting up Providers...");
		ProviderManager providerManager = new ProviderManager(config.getProperty("providers_directory"));
		ArrayList<Provider> providers = providerManager.getProviders();
	
		for (Provider provider : providers) {
			//System.out.println(provider.getProviderIdentifier() + " - " + provider.getProviderId() + " - " + provider.getProviderName());
		}
		System.out.println("...Completed Provider setup.");	
		
		System.out.println(" ");	
		
		System.out.println("Finding files to process...");			
		File folder = new File(config.getProperty("sourcefiles_directory"));
		File[] listOfFiles = folder.listFiles();
		int fileCount = 0;
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
			    fileCount++;
		        System.out.println("     File " + listOfFiles[i].getName());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println("     Directory " + listOfFiles[i].getName());
		      }
		    }
	    System.out.println("...found " + fileCount + " files in sourcefiles directory.");			    
		
		System.out.println(" ");	
	    
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
