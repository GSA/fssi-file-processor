package gov.gsa.fssi.fileprocessor;

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

		//First things first, lets get all of our configuration settings	
		Config config = new Config();	
		
		//Next, we need to get all of our provider info. We currently do this up front to make multi-file processing faster
		ProviderManager providerManager = new ProviderManager(config.getProperty("providers_directory"));
		ArrayList<Provider> providers = providerManager.getProviders();
	
		for (Provider provider : providers) {
			System.out.println(provider.getProviderIdentifier());
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
		
		//System.out.println("Hello World");
		
	}
	
	
	
}
