package gov.gsa.fssi.files.sourceFiles.loaders;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.sourceFiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface NewSourceFileLoader {
	static Logger logger = LoggerFactory.getLogger(NewSourceFileLoader.class);
	static Config config = new Config();	
	
	/**
	 * @return sourceFile
	 * @return Schema
	 */
	public void load(SourceFile sourceFile);
}
