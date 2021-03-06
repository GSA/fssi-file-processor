package main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.SourceFileExporterStrategy;

public class SourceFileExporterContext {
	private SourceFileExporterStrategy strategy;

	// use the strategy
	/**
	 * @param field
	 * @param constraint
	 * @param data
	 */
	public void export(String directory, SourceFile sourceFile) {
		strategy.export(directory, sourceFile);
	}

	// this can be set at runtime by the application preferences
	public SourceFileExporterStrategy getSourceFileExporterStrategy() {
		return this.strategy;
	}

	// this can be set at runtime by the application preferences
	public void setSourceFileExporterStrategy(
			SourceFileExporterStrategy strategy) {
		this.strategy = strategy;
	}
}
