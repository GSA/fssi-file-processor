package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileExporterStrategy {
	void export(String directory, SourceFile sourceFile);
}
