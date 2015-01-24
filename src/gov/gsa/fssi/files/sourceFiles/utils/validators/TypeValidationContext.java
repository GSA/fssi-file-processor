package gov.gsa.fssi.files.sourceFiles.utils.validators;

import gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.validators.constraint.MaxLengthConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.constraint.MaximumConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.constraint.MinLengthConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.constraint.MinimumConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.constraint.PatternConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.constraint.RequiredConstraintValidationStrategy;
import gov.gsa.fssi.files.sourceFiles.utils.validators.type.TypeValidationStrategy;

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
}
