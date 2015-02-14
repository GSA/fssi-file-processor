package main.java.gov.gsa.fssi.files.sourcefiles.utils;

import java.util.ArrayList;
import java.util.List;

import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The purpose of this class is to handle the automated processing of all source
 * files.
 *
 * @author davidlarrimore
 *
 */
public class SourceFilesBuilder {
	private static final Logger logger = LoggerFactory
			.getLogger(SourceFilesBuilder.class);

	/**
	 * The purpose of this function is just to prep file processing. We are not
	 * actually loading data yet
	 * 
	 * @param sourceFileDirectory
	 * @return
	 */
	public List<SourceFile> build(String directory, List<Schema> schemas,
			String exportMode, List<Provider> providers) {
		List<SourceFile> sourceFiles = new ArrayList<SourceFile>();
		// Loop through files in sourceFileDirectory and populate SourceFile
		// objects
		for (String fileName : FileHelper.getFilesFromDirectory(directory,
				".csv")) {
			SourceFileBuilder sourceFileBuilder = new SourceFileBuilder();
			sourceFiles.add(sourceFileBuilder.build(directory, fileName,
					exportMode, schemas, providers));
		}
		return sourceFiles;
	}

}
