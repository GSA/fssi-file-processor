package gov.gsa.fssi.files.schemas.utils.contexts;

import gov.gsa.fssi.files.schemas.Schema;
import gov.gsa.fssi.files.schemas.utils.strategies.SchemaLoaderStrategy;

public class SchemaLoaderContext {
	   private SchemaLoaderStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setSchemaLoaderStrategy(SchemaLoaderStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public SchemaLoaderStrategy getSchemaLoaderStrategy(){
	       return this.strategy;
	   }
	   
	  //use the strategy
		/**
		 * @param field
		 * @param constraint
		 * @param data
		 */
		public void load(Schema schema) {
			strategy.load(schema); //Validate Constraint
	   }
}
