package main.java.gov.gsa.fssi.files.schemas.utils.strategies;

import java.io.File;

import main.java.gov.gsa.fssi.files.schemas.Schema;

public interface SchemaLoaderStrategy {
	void load(File file, Schema schema);
}
