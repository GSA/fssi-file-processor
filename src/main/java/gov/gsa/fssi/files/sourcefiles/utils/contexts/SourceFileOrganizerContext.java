package main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.SourceFileOrganizerStrategy;

public class SourceFileOrganizerContext {
	private SourceFileOrganizerStrategy strategy;

	// this can be set at runtime by the application preferences
	public SourceFileOrganizerStrategy getSourceFileOrganizerStrategy() {
		return this.strategy;
	}

	// use the strategy
	/**
	 * @param field
	 * @param constraint
	 * @param data
	 */
	public void organize(SourceFile sourceFile) {
		strategy.organize(sourceFile);
	}

	// this can be set at runtime by the application preferences
	public void setSourceFileOrganizerStrategy(
			SourceFileOrganizerStrategy strategy) {
		this.strategy = strategy;
	}
}
