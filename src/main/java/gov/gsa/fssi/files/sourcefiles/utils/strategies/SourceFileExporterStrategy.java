package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileExporterStrategy {
	void export(String directory, SourceFile sourceFile);
}
