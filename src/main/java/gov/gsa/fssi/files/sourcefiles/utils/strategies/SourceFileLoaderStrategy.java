package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileLoaderStrategy {
	void load(String directory, String fileName, SourceFile sourceFile);
}
