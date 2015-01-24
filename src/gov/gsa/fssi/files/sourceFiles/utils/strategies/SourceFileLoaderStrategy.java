package gov.gsa.fssi.files.sourceFiles.utils.strategies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.sourceFiles.SourceFile;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileLoaderStrategy {
	static Logger logger = LoggerFactory.getLogger(SourceFileLoaderStrategy.class);
	static Config config = new Config();	
	public void load(String fileName, SourceFile sourceFile);
}
