package main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.TypeValidationStrategy;

public class TypeValidationContext {
	   private TypeValidationStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setTypeValidationStrategy(TypeValidationStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public TypeValidationStrategy getTypeValidationStrategy(){
	       return this.strategy;
	   }
	   
	  //use the strategy
		/**
		 * @param field
		 * @param constraint
		 * @param data
		 */
		public void validate(SchemaField field, Data data) {
			strategy.validate(field, data); //Validate Constraint
	   }
		
		public boolean isValid(SchemaField field, Data data) {
			return strategy.isValid(field, data); //Validate Constraint
	   }
}
