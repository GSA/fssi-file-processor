package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileLoaderStrategy {
	Logger logger = LoggerFactory.getLogger(SourceFileLoaderStrategy.class);
	Config config = new Config();	
	void load(String fileName, SourceFile sourceFile);
}
