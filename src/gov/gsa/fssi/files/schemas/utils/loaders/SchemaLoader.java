package gov.gsa.fssi.files.schemas.utils.loaders;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.schemas.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SchemaLoader {
	static Logger logger = LoggerFactory.getLogger(SchemaLoader.class);
	static Config config = new Config();	
	
	/**
	 * @param fileName
	 * @return Schema
	 */
	public void load(Schema schema);
}
