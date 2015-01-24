package gov.gsa.fssi.files.providers.utils.loaders;

import java.util.ArrayList;

import gov.gsa.fssi.files.providers.Provider;

public class ProviderLoaderContext {
	   private ProviderLoaderStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setProviderLoaderStrategy(ProviderLoaderStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public ProviderLoaderStrategy getProviderLoaderStrategy(){
	       return this.strategy;
	   }
	   
	  //use the strategy
		/**
		 * @param fileName
		 * @param provider
		 */
		public void load(String fileName, ArrayList<Provider> providers) {
			strategy.load(fileName, providers); //Validate Constraint
	   }
}
