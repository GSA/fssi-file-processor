package main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MaxLengthConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MaximumConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MinLengthConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.MinimumConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.PatternConstraintValidationStrategy;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidations.RequiredConstraintValidationStrategy;

public class ConstraintValidationContext {
	   private ConstraintValidationStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setDataValidationStrategy(ConstraintValidationStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public ConstraintValidationStrategy getDataValidationStrategy(){
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
				if(constraint.getType().equals(FieldConstraint.TYPE_MAXIMUM)){
					this.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
				}else if(constraint.getType().equals(FieldConstraint.TYPE_MINIMUM)){ 
					this.setDataValidationStrategy(new MinimumConstraintValidationStrategy());
				}else if(constraint.getType().equals(FieldConstraint.TYPE_MAXLENGTH)){ 
					this.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());
				}else if(constraint.getType().equals(FieldConstraint.TYPE_MINLENGTH)){ 
					this.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
				}else if(constraint.getType().equals(FieldConstraint.TYPE_PATTERN)){ 
					this.setDataValidationStrategy(new PatternConstraintValidationStrategy());
				}else if(constraint.getType().equals(FieldConstraint.TYPE_REQUIRED)){ 
					this.setDataValidationStrategy(new RequiredConstraintValidationStrategy());
				}
			}
			strategy.validate(field, constraint, data); //Validate Constraint
	   }
		
		public boolean isValid(SchemaField field, FieldConstraint constraint, Data data){
			return strategy.isValid(field, constraint, data); //is Valid
		}
}
