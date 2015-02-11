package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileLoggerStrategy {
	void createLog(String directory, SourceFile sourceFile);
}
