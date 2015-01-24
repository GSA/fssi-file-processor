package gov.gsa.fssi.files.sourceFiles.utils.contexts;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.ConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.MaxLengthConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.MaximumConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.MinLengthConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.MinimumConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.PatternConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.constraintValidation.RequiredConstraintValidationStrategy;

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
				switch (constraint.getType()){
					case FieldConstraint.TYPE_MAXIMUM:
						this.setDataValidationStrategy(new MaximumConstraintValidationStrategy());
						break;
					case FieldConstraint.TYPE_MINIMUM:
						this.setDataValidationStrategy(new MinimumConstraintValidationStrategy());
						break;		
					case FieldConstraint.TYPE_MAXLENGTH:
						this.setDataValidationStrategy(new MaxLengthConstraintValidationStrategy());
						break;		
					case FieldConstraint.TYPE_MINLENGTH:
						this.setDataValidationStrategy(new MinLengthConstraintValidationStrategy());
						break;		
					case FieldConstraint.TYPE_PATTERN:
						this.setDataValidationStrategy(new PatternConstraintValidationStrategy());
						break;		
					case FieldConstraint.TYPE_REQUIRED:
						this.setDataValidationStrategy(new RequiredConstraintValidationStrategy());
						break;				
				}
			}
			strategy.validate(field, constraint, data); //Validate Constraint
	   }
		
		public boolean isValid(SchemaField field, FieldConstraint constraint, Data data){
			return strategy.isValid(field, constraint, data); //is Valid
		}
}
