package main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.SourceFileLoggerStrategy;

public class SourceFileLoggerContext {
	private SourceFileLoggerStrategy strategy;

	// this can be set at runtime by the application preferences
	public SourceFileLoggerStrategy getSourceFileLoggerStrategy() {
		return this.strategy;
	}

	// use the strategy
	/**
	 * @param field
	 * @param constraint
	 * @param data
	 */
	public void log(String directory, SourceFile sourceFile, String errorLevel) {
		strategy.createLog(directory, sourceFile, errorLevel);
	}

	// this can be set at runtime by the application preferences
	public void setSourceFileLoggerStrategy(SourceFileLoggerStrategy strategy) {
		this.strategy = strategy;
	}
}
