package main.java.gov.gsa.fssi.files.schemas.utils.strategies;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.schemas.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SchemaLoaderStrategy {
	Logger logger = LoggerFactory.getLogger(SchemaLoaderStrategy.class);
	Config config = new Config();	
	
	/**
	 * @param fileName
	 * @return Schema
	 */
	void load(Schema schema);
}
