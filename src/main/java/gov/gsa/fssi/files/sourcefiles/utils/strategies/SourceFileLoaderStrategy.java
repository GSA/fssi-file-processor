package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileLoaderStrategy {
	Logger logger = LoggerFactory.getLogger(SourceFileLoaderStrategy.class);

	void load(String directory, String fileName, SourceFile sourceFile);
}
