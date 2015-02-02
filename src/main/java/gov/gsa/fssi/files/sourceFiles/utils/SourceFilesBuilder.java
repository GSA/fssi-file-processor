package main.java.gov.gsa.fssi.files.sourceFiles.utils;

import java.util.ArrayList;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.schemas.schemaFields.fieldConstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * The purpose of this class is to handle the automated processing of all source files.
 *
 * @author davidlarrimore
 *
 */
public class SourceFilesBuilder {
	static Config config = new Config();	    
	static Logger logger = LoggerFactory.getLogger(SourceFilesBuilder.class);
	
	/**
	 * The purpose of this function is just to prep file processing. We are not actually loading data yet
	 * @param sourceFileDirectory
	 * @return 
	 */
	public ArrayList<SourceFile> build(ArrayList<Schema> schemas,ArrayList<Provider> providers) {	
		ArrayList<SourceFile> sourceFiles = new ArrayList<SourceFile>();
		//Loop through files in sourceFileDirectory and populate SourceFile objects
		for (String fileName : FileHelper.getFilesFromDirectory(config.getProperty(Config.SOURCEFILES_DIRECTORY), ".csv")) {
	    	SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
	    	sourceFiles.add(sourceFileBuilder.build(fileName, schemas, providers));   
		}
		return sourceFiles;	
	}
	
}
