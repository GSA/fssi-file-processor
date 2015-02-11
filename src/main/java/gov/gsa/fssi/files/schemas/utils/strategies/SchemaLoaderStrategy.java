package main.java.gov.gsa.fssi.files.schemas.utils.strategies;

import main.java.gov.gsa.fssi.files.schemas.Schema;

public interface SchemaLoaderStrategy {
	void load(String directory, Schema schema);
}
