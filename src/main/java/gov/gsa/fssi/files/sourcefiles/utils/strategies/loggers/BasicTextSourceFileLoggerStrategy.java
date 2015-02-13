package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.loggers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.schemas.schemafields.fieldconstraints.FieldConstraint;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.results.ValidationResult;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.SourceFileLoggerStrategy;
import main.java.gov.gsa.fssi.helpers.DateHelper;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class BasicTextSourceFileLoggerStrategy
		implements
			SourceFileLoggerStrategy {
	private static final Logger logger = LoggerFactory
			.getLogger(BasicTextSourceFileLoggerStrategy.class);
	/**
	 * This method maps a sourceFile to its schema and then conforms the
	 * file/data to the schema format We delete any data that is no longer
	 * necessary
	 */
	@Override
	public void createLog(String directory, SourceFile sourceFile, String errorLevel) {

		try {

			// TODO: Switch to outputWriter

			String fileName = directory
					+ FileHelper.buildNewFileName(sourceFile.getFileName(),
							"log");

			BufferedWriter bufferedWriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(fileName),
							"UTF-8"));
			
			bufferedWriter.write("FSSI File Processor Basic Text Log");		
			bufferedWriter.newLine();
			
			bufferedWriter.write("Version: 1.0");		
			bufferedWriter.newLine();
			
			bufferedWriter.write("*******************************");
			bufferedWriter.newLine();		
			long passingRecords = sourceFile.recordCount() - sourceFile.getTotalFatalRecords() - sourceFile.getTotalErrorRecords();
			
			bufferedWriter.write("File Name: "+ sourceFile.getFileName());
			bufferedWriter.newLine();
			bufferedWriter.write("Date Processed: " + DateHelper.getTodaysDateAndTime("yyyy-MM-dd hh:mma"));
			bufferedWriter.newLine();
			bufferedWriter.write("Provider: " + (sourceFile.getProvider() == null ? "N/A": sourceFile.getProvider().getProviderName()));
			bufferedWriter.newLine();
			bufferedWriter.write("Schema: " + (sourceFile.getSchema() == null ? "N/A": sourceFile.getSchema().getName()));
			bufferedWriter.newLine();
			bufferedWriter.write("Email: " + (sourceFile.getProvider() == null || sourceFile.getProvider().getProviderEmail() == null ? "N/A": sourceFile.getProvider().getProviderEmail()));	
			bufferedWriter.newLine();
			bufferedWriter.write("Status: " + sourceFile.getStatusName());			
			bufferedWriter.newLine();
			bufferedWriter.write("Highest Error Level: " + File.getErrorLevelName(sourceFile.getMaxErrorLevel()));
			bufferedWriter.newLine();
			bufferedWriter.write("Number of Fatal Errors: " + sourceFile.getTotalFatalRecords());
			bufferedWriter.newLine();
			bufferedWriter.write("Number of Errors: " + sourceFile.getTotalErrorRecords());	
			bufferedWriter.newLine();
			bufferedWriter.write("Number of Warnings: " + sourceFile.getTotalWarningRecords());
			bufferedWriter.newLine();
			bufferedWriter.write("Number of Passsing Records (Includings Warnings): " + passingRecords);			
			bufferedWriter.newLine();
			bufferedWriter.write("Total Records Processed: " + sourceFile.recordCount());			
			bufferedWriter.newLine();	
			bufferedWriter.write("*******************************");
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			bufferedWriter.write("Results:");	
			bufferedWriter.newLine();
			
			for (SourceFileRecord record : sourceFile.getRecords()) {
				bufferedWriter.write("Line: " + record.getRowIndex()
						+ " Status: " + sourceFile.getStatusName() + " Level: "
						+ File.getErrorLevelName(record.getMaxErrorLevel()));
				bufferedWriter.newLine();
				for (Data data : record.getDatas()) {
					if (data.getMaxErrorLevel() > FieldConstraint.getLevel(errorLevel)) {
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
