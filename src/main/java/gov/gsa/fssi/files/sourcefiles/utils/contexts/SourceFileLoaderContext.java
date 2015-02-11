package main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.SourceFileLoaderStrategy;

public class SourceFileLoaderContext {
	private SourceFileLoaderStrategy strategy;

	// this can be set at runtime by the application preferences
	public void setSourceFileLoaderStrategy(SourceFileLoaderStrategy strategy) {
		this.strategy = strategy;
	}

	// this can be set at runtime by the application preferences
	public SourceFileLoaderStrategy getSourceFileLoaderStrategy() {
		return this.strategy;
	}

	// use the strategy
	/**
	 * @param field
	 * @param constraint
	 * @param data
	 */
	public void load(String directory, String fileName, SourceFile sourceFile) {
		strategy.load(directory, fileName, sourceFile); // Validate Constraint
	}
}
