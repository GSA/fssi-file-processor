package gov.gsa.fssi.files.sourceFiles.utils.validators;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.validators.strategies.MaxLengthDataValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.strategies.MaximumDataValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.strategies.MinLengthDataValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.strategies.MinimumDataValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.strategies.PatternDataValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.strategies.RequiredDataValidationStrategy;

public class DataValidationContext {
	   private DataValidationStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setDataValidationStrategy(DataValidationStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public DataValidationStrategy getDataValidationStrategy(){
	       return this.strategy;
	   }
	   
	  //use the strategy
		/**
		 * @param field
		 * @param constraint
		 * @param data
		 */
		public void validateConstraint(SchemaField field, FieldConstraint constraint, Data data) {
			if(this.getDataValidationStrategy() == null){
				switch (constraint.getType()){
					case FieldConstraint.TYPE_MAXIMUM:
						this.setDataValidationStrategy(new MaximumDataValidationStrategy());
						break;
					case FieldConstraint.TYPE_MINIMUM:
						this.setDataValidationStrategy(new MinimumDataValidationStrategy());
						break;		
					case FieldConstraint.TYPE_MAXLENGTH:
						this.setDataValidationStrategy(new MaxLengthDataValidationStrategy());
						break;		
					case FieldConstraint.TYPE_MINLENGTH:
						this.setDataValidationStrategy(new MinLengthDataValidationStrategy());
						break;		
					case FieldConstraint.TYPE_PATTERN:
						this.setDataValidationStrategy(new PatternDataValidationStrategy());
						break;		
					case FieldConstraint.TYPE_REQUIRED:
						this.setDataValidationStrategy(new RequiredDataValidationStrategy());
						break;				
				}
			}
			strategy.validate(field, constraint, data); //Validate Constraint
	   }
}
