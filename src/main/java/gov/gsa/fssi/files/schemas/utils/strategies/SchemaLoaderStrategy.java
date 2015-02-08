package main.java.gov.gsa.fssi.files.schemas.utils.strategies;

import main.java.gov.gsa.fssi.files.schemas.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SchemaLoaderStrategy {
	Logger logger = LoggerFactory.getLogger(SchemaLoaderStrategy.class);
	
	/**
	 * @param fileName
	 * @return Schema
	 */
	void load(String directory, Schema schema);
}
