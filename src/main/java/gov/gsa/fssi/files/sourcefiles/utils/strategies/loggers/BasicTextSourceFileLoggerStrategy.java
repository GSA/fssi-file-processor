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
public class BasicTextSourceFileLoggerStrategy implements
		SourceFileLoggerStrategy {
	private static final Logger logger = LoggerFactory
			.getLogger(BasicTextSourceFileLoggerStrategy.class);

	/**
	 * This method maps a sourceFile to its schema and then conforms the
	 * file/data to the schema format We delete any data that is no longer
	 * necessary
	 */
	@Override
	public void createLog(String directory, SourceFile sourceFile,
			String errorLevel) {

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

			bufferedWriter.write("Version: 1.1");
			bufferedWriter.newLine();

			bufferedWriter.write("*******************************");
			bufferedWriter.newLine();
			long passingRecords = sourceFile.recordCount()
					- sourceFile.getTotalFatalRecords()
					- sourceFile.getTotalErrorRecords();

			bufferedWriter.write("File Name: " + sourceFile.getFileName());
			bufferedWriter.newLine();
			bufferedWriter.write("Date Processed: "
					+ DateHelper.getTodaysDateAndTime("yyyy-MM-dd hh:mma"));
			bufferedWriter.newLine();
			bufferedWriter.write("Provider: "
					+ (sourceFile.getProvider() == null ? "N/A" : sourceFile
							.getProvider().getProviderName()));
			bufferedWriter.newLine();
			bufferedWriter.write("Schema: "
					+ (sourceFile.getSchema() == null ? "N/A" : sourceFile
							.getSchema().getName()));
			bufferedWriter.newLine();
			bufferedWriter
					.write("Email: "
							+ (sourceFile.getProvider() == null
									|| sourceFile.getProvider()
											.getProviderEmail() == null ? "N/A"
									: sourceFile.getProvider()
											.getProviderEmail()));
			bufferedWriter.newLine();
			bufferedWriter.write("Status: " + sourceFile.getStatusName());
			bufferedWriter.newLine();
			bufferedWriter.write("Highest Error Level: "
					+ File.getErrorLevelName(sourceFile.getMaxErrorLevel()));
			bufferedWriter.newLine();
			bufferedWriter.write("Number of Fatal Record: "
					+ sourceFile.getTotalFatalRecords());
			bufferedWriter.newLine();
			bufferedWriter.write("Number of Error Records: "
					+ sourceFile.getTotalErrorRecords());
			bufferedWriter.newLine();
			bufferedWriter.write("Number of Warning Records: "
					+ sourceFile.getTotalWarningRecords());
			bufferedWriter.newLine();
			bufferedWriter
					.write("Number of Passsing Records (Includes Warnings): "
							+ passingRecords);
			bufferedWriter.newLine();
			bufferedWriter.write("Total Records Processed: "
					+ sourceFile.recordCount());
			bufferedWriter.newLine();
			bufferedWriter.write("*******************************");
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			
			printLoadStatusMessages(sourceFile, bufferedWriter);
			printExportStatusMessages(sourceFile, bufferedWriter);
			
			
			if (sourceFile.getSchema() != null
					&& sourceFile.getSchema().getStatus()) {
				bufferedWriter.write("Validation Results (Showing ");
				if(FieldConstraint.getErrorLevel(errorLevel) == 0) bufferedWriter.write("All");
				else if(FieldConstraint.getErrorLevel(errorLevel) == 1) bufferedWriter.write("[W]arning, [E]rror, and [F]atal");
				else if(FieldConstraint.getErrorLevel(errorLevel) == 2) bufferedWriter.write("[E]rror and [F]atal");
				else if(FieldConstraint.getErrorLevel(errorLevel) == 3) bufferedWriter.write("[F]atal Only");
				bufferedWriter.write("):");
				bufferedWriter.newLine();				
				bufferedWriter.newLine();
				
				printRecordValidationErrors(sourceFile, errorLevel, bufferedWriter);
			}
			bufferedWriter.flush();
			bufferedWriter.close();

			System.out.println("Done");

		} catch (IOException e) {
			logger.error("There was a '{}' error while attempting to export the text file", e.getMessage());
		}
	}

	private void printLoadStatusMessages(SourceFile sourceFile,
			BufferedWriter bufferedWriter) throws IOException {
		if(sourceFile.getLoadStatusMessage() != null && sourceFile.getLoadStatusMessage().size() > 0){
			bufferedWriter.write("File received the following load status messages:");
			bufferedWriter.newLine();
			for(String loadStatusMessage: sourceFile.getLoadStatusMessage()){
				bufferedWriter.write("     - " + loadStatusMessage);
				bufferedWriter.newLine();					
			}					
		}
		bufferedWriter.newLine();	
	}

	private void printExportStatusMessages(SourceFile sourceFile,
			BufferedWriter bufferedWriter) throws IOException {
		if(sourceFile.getExportStatusMessages() != null && sourceFile.getExportStatusMessages().size() > 0){
			bufferedWriter.write("File received the following export status messages:");
			bufferedWriter.newLine();
			for(String exportStatusMessage: sourceFile.getExportStatusMessages()){
				bufferedWriter.write("     - " + exportStatusMessage);
				bufferedWriter.newLine();					
			}					
		}
		bufferedWriter.newLine();	
	}
	

	private void printValidatorStatusMessages(SourceFile sourceFile,
			BufferedWriter bufferedWriter) throws IOException {
		if(sourceFile.getExportStatusMessages() != null && sourceFile.getExportStatusMessages().size() > 0){
			bufferedWriter.write("File received the following export status messages:");
			bufferedWriter.newLine();
			for(String exportStatusMessage: sourceFile.getExportStatusMessages()){
				bufferedWriter.write("     - " + exportStatusMessage);
				bufferedWriter.newLine();					
			}					
		}
		bufferedWriter.newLine();	
	}
	
	
	private void printRecordValidationErrors(SourceFile sourceFile,
			String errorLevel, BufferedWriter bufferedWriter)
			throws IOException {
		for (SourceFileRecord record : sourceFile.getRecords()) {
			if (record.getMaxErrorLevel() >= FieldConstraint.getErrorLevel(errorLevel)) {
				printRecordValidationError(sourceFile, errorLevel, bufferedWriter, record);
			}
		}
	}

	private void printRecordValidationError(SourceFile sourceFile, String errorLevel,
			BufferedWriter bufferedWriter, SourceFileRecord record)
			throws IOException {
		if (record.getMaxErrorLevel() >= FieldConstraint.getErrorLevel(errorLevel)) {		
			bufferedWriter
					.write("Line: "
							+ record.getRowIndex()
							+ " Status: "
							+ sourceFile.getStatusName()
							+ " Error Level: "
							+ File.getErrorLevelName(record
									.getMaxErrorLevel()));
			bufferedWriter.newLine();
			printDataValidationErrors(sourceFile, errorLevel, bufferedWriter,
					record);
		}
	}

	private void printDataValidationErrors(SourceFile sourceFile,
			String errorLevel, BufferedWriter bufferedWriter,
			SourceFileRecord record) throws IOException {
		for (Data data : record.getDatas()) {
			if (data.getMaxErrorLevel() >= FieldConstraint.getErrorLevel(errorLevel)) {
				printDataValidationError(sourceFile, errorLevel, bufferedWriter,
						data);
			}
		}
	}

	private void printDataValidationError(SourceFile sourceFile,
			String errorLevel, BufferedWriter bufferedWriter, Data data)
			throws IOException {
		if (data.getMaxErrorLevel() >= FieldConstraint.getErrorLevel(errorLevel)) {
			bufferedWriter.write("     Field '" + sourceFile.getSourceHeaderName(data.getHeaderIndex()) + "' failed constraints: ");
			for (ValidationResult result : data.getValidationResults()) {
				if (!result.getStatus()) {
					bufferedWriter.write("["+ File.getErrorLevelInitial(result.getErrorLevel()) + "] "+ result.getRule());
				}
			}
			bufferedWriter.write(" with value: '" + data.getData() + "'");
			bufferedWriter.newLine();
		}
	}

}
