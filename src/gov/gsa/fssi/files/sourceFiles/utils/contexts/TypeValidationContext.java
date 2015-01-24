package gov.gsa.fssi.files.sourceFiles.utils.contexts;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.TypeValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.MaxLengthConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.MaximumConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.MinLengthConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.MinimumConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.PatternConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.RequiredConstraintValidationStrategy;

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
		public void validateType(SchemaField field, Data data) {
			strategy.validate(field, data); //Validate Constraint
	   }
		
		public boolean isValid(SchemaField field, Data data) {
			return strategy.isValid(field, data); //Validate Constraint
	   }
}
