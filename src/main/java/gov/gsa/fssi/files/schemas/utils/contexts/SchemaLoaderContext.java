package main.java.gov.gsa.fssi.files.schemas.utils.contexts;

import java.io.File;

import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.utils.strategies.SchemaLoaderStrategy;

public class SchemaLoaderContext {
	private SchemaLoaderStrategy strategy;

	// this can be set at runtime by the application preferences
	public SchemaLoaderStrategy getSchemaLoaderStrategy() {
		return this.strategy;
	}

	// use the strategy
	/**
	 * @param field
	 * @param constraint
	 * @param data
	 */
	public void load(File file, Schema schema) {
		strategy.load(file, schema); // Validate Constraint
	}

	// this can be set at runtime by the application preferences
	public void setSchemaLoaderStrategy(SchemaLoaderStrategy strategy) {
		this.strategy = strategy;
	}
}
