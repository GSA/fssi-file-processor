package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileExporterStrategy {
	static Logger logger = LoggerFactory.getLogger(SourceFileExporterStrategy.class);
	static Config config = new Config();	
	public void export(SourceFile sourceFile);
}