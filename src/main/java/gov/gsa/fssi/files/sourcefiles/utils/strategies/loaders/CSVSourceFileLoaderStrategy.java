package main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.loaders;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourcefiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.SourceFileLoaderStrategy;
import main.java.gov.gsa.fssi.helpers.CSVHelper;
import main.java.gov.gsa.fssi.helpers.FileHelper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class CSVSourceFileLoaderStrategy implements SourceFileLoaderStrategy {
	private static final Logger logger = LoggerFactory
			.getLogger(CSVSourceFileLoaderStrategy.class);

	/**
	 *
	 * @return Schema loaded from fileName in schemas_directory
	 */
	@Override
	public void load(String directory, String fileName, SourceFile sourceFile) {
		sourceFile.setLoadStage(File.STAGE_LOADING);
		try {

			final URL url = FileHelper.getFile(FileHelper.getFullPath(directory, fileName)).toURI().toURL();
			final Reader reader = new InputStreamReader(new BOMInputStream(url.openStream()), "UTF-8");
			final CSVParser parser = new CSVParser(reader,CSVFormat.EXCEL.withHeader());

			try {
				processHeaders(fileName, sourceFile, parser);
			} catch (NullPointerException e) {
				logger.error(
						"Received NullPointerException '{}' while processing headers for file '{}'",
						e.getMessage(), sourceFile.getFileName());
				sourceFile.setMaxErrorLevel(3);
				sourceFile.setLoadStatus(false);
				sourceFile
						.addLoadStatusMessage("Received error processing headers");
			}

			if (sourceFile.getStatus()) {
				for (final CSVRecord csvRecord : parser) {
					if (csvRecord.size() > 1 && sourceFile.getSourceHeaders().size() > 1) {
						if(!CSVHelper.isRowEmpty(csvRecord)){
							processRecord(sourceFile, csvRecord);						
						}else sourceFile.incrementTotalEmptyRecords();						
					}else sourceFile.incrementTotalNullRecords();
				}
			}

			if (sourceFile.getTotalEmptyRecords()
					+ sourceFile.getTotalNullRecords() > 0) {
				logger.warn(
						"Only {} out of {} rows processed from {}. Null Rows: {} Empty Records: {}",
						sourceFile.recordCount(), sourceFile.getTotalRecords(),
						sourceFile.getFileName(),
						sourceFile.getTotalNullRecords(),
						sourceFile.getTotalEmptyRecords());
			} else {
				logger.info("All {} Records successfully processed in {}",
						sourceFile.getTotalRecords(), sourceFile.getFileName());
			}
			sourceFile.setLoadStage(File.STAGE_LOADED);
			reader.close();
			parser.close();
		} catch (FileNotFoundException e) {
			logger.error(
					"There was an FileNotFoundException error '{}' with file {}",e.getMessage(),
					sourceFile.getFileName());
			sourceFile.addLoadStatusMessage(e.getMessage());
			sourceFile.setMaxErrorLevel(3);			
		} catch (IOException e) {
			logger.error("There was an IOException error '{}' with file {}",e.getMessage(),
					sourceFile.getFileName());
			sourceFile.addLoadStatusMessage(e.getMessage());
			sourceFile.setMaxErrorLevel(3);			
		} catch (IllegalArgumentException e) {
			logger.error(
					"There was an IllegalArgumentException '{}' error with file {}",e.getMessage(),
					sourceFile.getFileName());
			sourceFile.addLoadStatusMessage(e.getMessage());
			sourceFile.setMaxErrorLevel(3);
		} catch (NullPointerException e) {
			logger.error(
					"Received NullPointerException '{}' while creating log for file '{}'",
					e.getMessage(), sourceFile.getFileName());
			sourceFile.addLoadStatusMessage(e.getMessage());
			sourceFile.setMaxErrorLevel(3);			
		}
		if (sourceFile.getStatus()) {
			sourceFile.setLoadStage(File.STAGE_LOADED);
		}

	}

	/**
	 * @param fileName
	 * @param sourceFile
	 * @param parser
	 */
	private void processHeaders(String fileName, SourceFile sourceFile,
			final CSVParser parser) throws NullPointerException {
		/*
		 * Converting Apache Commons CSV header map from <String, Integer> to
		 * <Integer,String>
		 */

		Map<String, Integer> parserHeaderMap = parser.getHeaderMap();
		Iterator<?> parserHeaderMapIterator = parserHeaderMap.entrySet()
				.iterator();
		while (parserHeaderMapIterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) parserHeaderMapIterator.next();
			sourceFile.addSourceHeader((Integer) pairs.getValue(), pairs
					.getKey().toString());
		}

		logger.info("file '{}' had the following headers: {}", fileName,
				parser.getHeaderMap());
	}

	/**
	 * @param sourceFile
	 * @param csvRecord
	 */
	private void processRecord(SourceFile sourceFile, final CSVRecord csvRecord) {
		sourceFile.incrementTotalRecords();

		SourceFileRecord thisRecord = new SourceFileRecord();
		thisRecord.setRowIndex((int) csvRecord.getRecordNumber() + 1);
		// Ignoring null rows
		if (csvRecord.size() > 1 && sourceFile.getSourceHeaders().size() > 1) {

			Iterator<?> headerIterator = sourceFile.getSourceHeaders().entrySet().iterator();
			while (headerIterator.hasNext()) {
				Map.Entry dataPairs = (Map.Entry) headerIterator.next();
				try {
					Data data = new Data();
					data.setData(csvRecord.get(dataPairs.getValue().toString()).trim());
					data.setHeaderIndex((Integer) dataPairs.getKey());
					thisRecord.addData(data);
				} catch (IllegalArgumentException e) {
					logger.error("Received IllegalArgumentExceptions '{}' while loading file '{}'",e.getMessage(), sourceFile.getFileName());
					sourceFile.setMaxErrorLevel(3);
					sourceFile.setLoadStatus(false);
					sourceFile.addLoadStatusMessage("Received error processing record");
				}
			}
			sourceFile.addRecord(thisRecord);

		} else {
			sourceFile.incrementTotalNullRecords();
		}
	}
}
