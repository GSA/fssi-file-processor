package gov.gsa.fssi.files.sourceFiles.utils.exporters;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.sourceFiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SourceFileExporter {
	static Logger logger = LoggerFactory.getLogger(SourceFileExporter.class);
	static Config config = new Config();	
	
	/**
	 * @return sourceFile
	 * @return Schema
	 */
	public void export(SourceFile sourceFile);
}
