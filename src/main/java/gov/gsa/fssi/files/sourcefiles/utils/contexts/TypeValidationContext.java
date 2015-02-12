package main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts;

import main.java.gov.gsa.fssi.files.schemas.schemafields.SchemaField;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.TypeValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.AnyTypeValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.DateTypeValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.IntegerTypeValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.NumberTypeValidationStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.StringTypeValidationStrategy;

public class TypeValidationContext {
	private TypeValidationStrategy strategy;

	// this can be set at runtime by the application preferences
	public TypeValidationStrategy getTypeValidationStrategy() {
		return this.strategy;
	}

	public boolean isValid(SchemaField field, Data data) {
		return strategy.isValid(field, data); // Validate Constraint
	}

	// this can be set at runtime by the application preferences
	public void setTypeValidationStrategy(TypeValidationStrategy strategy) {
		this.strategy = strategy;
	}

	// use the strategy
	/**
	 * @param field
	 * @param constraint
	 * @param data
	 */
	public void validate(SchemaField field, Data data) {
		if (this.getTypeValidationStrategy() == null) {
			if (field.getType().equals(SchemaField.TYPE_ANY)) {
				this.setTypeValidationStrategy(new AnyTypeValidationStrategy());
			} else if (field.getType().equals(SchemaField.TYPE_STRING)) {
				this.setTypeValidationStrategy(new StringTypeValidationStrategy());
			} else if (field.getType().equals(SchemaField.TYPE_DATE)) {
				this.setTypeValidationStrategy(new DateTypeValidationStrategy());
			} else if (field.getType().equals(SchemaField.TYPE_NUMBER)) {
				this.setTypeValidationStrategy(new NumberTypeValidationStrategy());
			} else if (field.getType().equals(SchemaField.TYPE_INTEGER)) {
				this.setTypeValidationStrategy(new IntegerTypeValidationStrategy());
			} else {
				this.setTypeValidationStrategy(new StringTypeValidationStrategy());
			}
		}
		strategy.validate(field, data); // Validate Type
	}
}
