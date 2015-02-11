package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.exporters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import main.java.gov.gsa.fssi.files.schemas.schemaFields.SchemaField;
import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileExporterStrategy;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class CSVSourceFileExporterStrategy implements
		SourceFileExporterStrategy {

	/**
	 *
	 * @return Schema loaded from fileName in schemas_directory
	 */
	public void export(String directory, SourceFile sourceFile) {
		try {
			// Delimiter used in CSV file
			String newFileName = FileHelper.buildNewFileName(sourceFile
					.getFileName(), sourceFile.getProvider()
					.getFileOutputType());
			String newLineSeparator = "\n";
			// FileWriter fileWriter = null;
			CSVPrinter csvFilePrinter = null;
			// Create the CSVFormat object with "\n" as a record delimiter
			CSVFormat csvFileFormat = CSVFormat.DEFAULT
					.withRecordSeparator(newLineSeparator);
			// initialize FileWriter object
			File file = new File(FileHelper.getFullPath(directory, newFileName));
			Writer writer = new OutputStreamWriter(new FileOutputStream(file),
					"UTF-8");
			PrintWriter printWriter = new PrintWriter(writer);
			// initialize CSVPrinter object
			csvFilePrinter = new CSVPrinter(printWriter, csvFileFormat);

			List<String> csvHeaders = new ArrayList<String>();
			// Writing Headers
			Map<Integer, String> headerMap = sourceFile.getSourceHeaders();
			Iterator<?> headerMapIterator = headerMap.entrySet().iterator();
			while (headerMapIterator.hasNext()) {
				String fieldName = null;
				Map.Entry<Integer, String> headerMapIteratorPairs = (Map.Entry) headerMapIterator
						.next();
				// getting correct header name from Schema
				if (sourceFile.getSchema() != null) {
					for (SchemaField field : sourceFile.getSchema().getFields()) {
						if (field.getHeaderIndex() == headerMapIteratorPairs
								.getKey()) {
							logger.info(
									"Using Schema name '{}' for field '{}'",
									field.getName(), headerMapIteratorPairs
											.getValue().toString());
							fieldName = field.getName();
						}
					}
				}
				csvHeaders.add((fieldName == null ? headerMapIteratorPairs
						.getValue().toString() : fieldName));
			}

			// Create CSV file header
			csvFilePrinter.printRecord(csvHeaders);

			// Writing Data
			for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
				List<String> csvRecord = new ArrayList<String>();
				for (int i = 0; i < sourceFile.getSourceHeaders().size(); i++) {
					if (sourceFileRecord.getDataByHeaderIndex(i) != null
							&& sourceFileRecord.getDataByHeaderIndex(i)
									.getData() != null) {
						// sourceFileRecord.print();
						csvRecord.add(sourceFileRecord.getDataByHeaderIndex(i)
								.getData());
					} else {
						csvRecord.add("");
					}
				}

				csvFilePrinter.printRecord(csvRecord);
			}
			csvFilePrinter.close();
			printWriter.close();
			logger.info("{} Created Successfully. {} Records processed",
					sourceFile.getFileName(), sourceFile.recordCount());

		} catch (IOException e) {
			logger.error("Received Exception '{}' while processing {}",
					e.getMessage(), sourceFile.getFileName());
			// e.printStackTrace();
		}
	}
}
