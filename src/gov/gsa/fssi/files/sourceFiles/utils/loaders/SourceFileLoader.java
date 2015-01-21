package gov.gsa.fssi.files.sourceFiles.utils.loaders;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.sourceFiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SourceFileLoader {
	static Logger logger = LoggerFactory.getLogger(SourceFileLoader.class);
	static Config config = new Config();	
	
	/**
	 * @return sourceFile
	 * @return Schema
	 */
	public void load(SourceFile sourceFile);
}
