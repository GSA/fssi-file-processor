package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies;

import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileExporterStrategy {
	Logger logger = LoggerFactory.getLogger(SourceFileExporterStrategy.class);
	void export(String directory, SourceFile sourceFile);
}
