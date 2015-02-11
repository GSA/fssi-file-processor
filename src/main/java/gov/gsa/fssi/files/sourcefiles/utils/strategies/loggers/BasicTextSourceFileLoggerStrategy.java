package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.loggers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.results.ValidationResult;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.SourceFileLoggerStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.typevalidations.NumberTypeValidationStrategy;
import main.java.gov.gsa.fssi.helpers.FileHelper;

/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class BasicTextSourceFileLoggerStrategy implements SourceFileLoggerStrategy {
	static final Logger logger = LoggerFactory.getLogger(BasicTextSourceFileLoggerStrategy.class);
	/**
	 * This method maps a sourceFile to its schema and then conforms the
	 * file/data to the schema format We delete any data that is no longer
	 * necessary
	 */
	@Override
	public void createLog(String directory, SourceFile sourceFile) {

		try {

			// TODO: Switch to outputWriter

			String fileName = directory
					+ FileHelper.buildNewFileName(sourceFile.getFileName(),
							"log");


			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(fileName),
							"UTF-8"));

			for (SourceFileRecord record : sourceFile.getRecords()) {
				bufferedWriter.write("Line: "
						+ record.getRowIndex()
						+ " Status: "
						+ sourceFile.getStatusName()
						+ " Level: "
						+ File.getErrorLevelName(record
								.getMaxErrorLevel()));
				bufferedWriter.newLine();
				for (Data data : record.getDatas()) {
					if (data.getMaxErrorLevel() > 2) {
						bufferedWriter.write("     Field: "
								+ sourceFile.getSourceHeaderName(data
										.getHeaderIndex())
								+ " failed constraints: ");
						for (ValidationResult result : data
								.getValidationResults()) {
							if (!result.getStatus())
								bufferedWriter.write(result.getRule() + " ");
						}
						bufferedWriter.write(" against value: '"
								+ data.getData() + "'");
						bufferedWriter.newLine();
					}
				}
			}

			bufferedWriter.flush();
			bufferedWriter.close();

			System.out.println("Done");

		} catch (IOException e) {
			logger.error("There was a '{}' error while attempting to export the text file");
		}
	}

}
