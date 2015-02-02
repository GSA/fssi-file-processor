package main.java.gov.gsa.fssi.files.schemas.utils.strategies;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.schemas.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SchemaLoaderStrategy {
	static Logger logger = LoggerFactory.getLogger(SchemaLoaderStrategy.class);
	static Config config = new Config();	
	
	/**
	 * @param fileName
	 * @return Schema
	 */
	public void load(Schema schema);
}
