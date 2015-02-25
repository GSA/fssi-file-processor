package main.java.gov.gsa.fssi.files.sourcefiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.gov.gsa.fssi.config.Config;
import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.schemas.Schema;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.SourceFileValidator;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.SourceFileExporterContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.SourceFileLoaderContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.SourceFileOrganizerContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.exporters.CSVSourceFileExporterStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.exporters.ExcelSourceFileExporterStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.loaders.CSVSourceFileLoaderStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.organizers.ExplodeSourceFileOrganizerStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.organizers.ImplodeSourceFileOrganizerStrategy;
import main.java.gov.gsa.fssi.helpers.DateHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is our file object. It is an abstract class that forms the structure of
 * all source files Whether it be xls, xlsx, csv, etc...
 * 
 * @author David Larrimore
 * 
 */
public class SourceFile extends File {
	private static final Logger logger = LoggerFactory
			.getLogger(SourceFile.class);
	private Schema schema = null;
	private Provider provider = null;
	private Date reportingPeriod = null;
	private Integer totalRecords = 0;
	private Integer totalProcessedRecords = 0;
	private Integer totalNullRecords = 0;
	private Integer totalEmptyRecords = 0;
	private Integer totalFatalRecords = 0;
	private Integer totalErrorRecords = 0;
	private Integer totalWarningRecords = 0;
	/**
	 * SourceHeaders are the input headers that we received from the source
	 * files Once the file is loaded, this is used for posterities sake for
	 * logging
	 */
	private Map<Integer, String> sourceHeaders = new HashMap<Integer, String>();
	private List<SourceFileRecord> records = new ArrayList<SourceFileRecord>();

	public SourceFile() {
		super();
	}

	public SourceFile(String fileName) {
		super(fileName);
		this.setReportingPeriodUsingFileNameParts();
		if (this.getReportingPeriod() == null) {
			logger.warn("No reporting period found");
			this.setMaxErrorLevel(1);
		}
	}

	/**
	 * @param record
	 */
	public void addRecord(SourceFileRecord record) {
		this.records.add(record);
	}

	/**
	 * @param map
	 *            the headers to set
	 */
	public void addSourceHeader(Integer key, String value) {
		this.sourceHeaders.put(key, value);
	}

	public void export(String directory) {
		SourceFileExporterContext context = new SourceFileExporterContext();
		if (this.getRecords() != null) {
				if(this.getProvider() !=null){
				if (this.getProvider().getFileOutputType()
						.equalsIgnoreCase(File.FILETYPE_CSV)) {
					context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());
				} else if (this.getProvider().getFileOutputType()
						.equalsIgnoreCase(File.FILETYPE_XLS)) {
					context.setSourceFileExporterStrategy(new ExcelSourceFileExporterStrategy());
				} else if (this.getProvider().getFileOutputType()
						.equalsIgnoreCase(File.FILETYPE_XLSX)) {
					context.setSourceFileExporterStrategy(new ExcelSourceFileExporterStrategy());
				} else {
					logger.warn(
							"We cannot currently export to a '{}'. defaulting to '{}'",
							this.getProvider().getFileOutputType(),
							File.FILETYPE_CSV);
					context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());
				}
	
				context.export(directory, this);
			}else{
				logger.error("No provider found, defaulting to '{}'",
						File.FILETYPE_CSV);	
				context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());
				context.export(directory, this);
			}
		} else {
			logger.error("Cannot export sourceFile '{}'. No data found",
					this.getFileName());
		}
	}

	/**
	 * @return the provider
	 */
	public Provider getProvider() {
		return provider;
	}

	/**
	 * @return the records
	 */
	public List<SourceFileRecord> getRecords() {
		return records;
	}

	/**
	 * @return the reportingPeriod
	 */
	public Date getReportingPeriod() {
		return reportingPeriod == null ? null : new Date(
				reportingPeriod.getTime());
	}

	/**
	 * @return the schema
	 */
	public Schema getSchema() {
		return schema;
	}

	/**
	 * @return the headers
	 */
	public String getSourceHeaderName(int key) {
		return sourceHeaders.get(key);
	}

	/**
	 * @return the headers
	 */
	public Map<Integer, String> getSourceHeaders() {
		return sourceHeaders;
	}

	/**
	 * @return
	 */
	public Integer getTotalEmptyRecords() {
		return totalEmptyRecords;
	}

	/**
	 * @return
	 */
	public Integer getTotalErrorRecords() {
		return totalErrorRecords;
	}

	/**
	 * @return
	 */
	public Integer getTotalFatalRecords() {
		return totalFatalRecords;
	}

	/**
	 * @return
	 */
	public Integer getTotalNullRecords() {
		return totalNullRecords;
	}

	/**
	 * @return
	 */
	public Integer getTotalProcessedRecords() {
		return totalProcessedRecords;
	}

	/**
	 * @return
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}

	/**
	 * @return
	 */
	public Integer getTotalWarningRecords() {
		return totalWarningRecords;
	}

	/**
	 * 
	 */
	public void incrementTotalEmptyRecords() {
		this.totalEmptyRecords++;
	}

	/**
	 * 
	 */
	public void incrementTotalErrorRecords() {
		this.totalErrorRecords++;
	}

	/**
	 * 
	 */
	public void incrementTotalFatalRecords() {
		this.totalFatalRecords++;
	}

	/**
	 * @param totalNullRecords
	 */
	public void incrementTotalNullRecords() {
		this.totalNullRecords++;
	}

	/**
	 * @param totalProcessedRecords
	 */
	public void incrementTotalProcessedRecords() {
		this.totalProcessedRecords++;
	}

	/**
	 * @param totalRecords
	 */
	public void incrementTotalRecords() {
		this.totalRecords++;
	}

	/**
	 * 
	 */
	public void incrementTotalWarningRecords() {
		this.totalWarningRecords++;
	}

	/**
	 * @param sourceFile
	 */
	public void load(String directory) {
		SourceFileLoaderContext context = new SourceFileLoaderContext();
		if (this.getFileExtension().equalsIgnoreCase(FILETYPE_CSV)) {
			logger.info("Loading file {} as a '{}'", this.getFileName(),
					this.getFileExtension());
			context.setSourceFileLoaderStrategy(new CSVSourceFileLoaderStrategy());
		} else {
			logger.warn("Could not load file '{}' as a '{}'",
					this.getFileName(), this.getFileExtension());
			this.setStatus(false);
		}

		context.load(directory, this.getFileName(), this);
	}

	/**
	 * This method processes a file against its schema
	 */
	public void organize(String exportMode) {
		SourceFileOrganizerContext context = new SourceFileOrganizerContext();
		if (this.getSchema() != null) {
			if (exportMode.equals(Config.EXPORT_MODE_EXPLODE)) {
				context.setSourceFileOrganizerStrategy(new ExplodeSourceFileOrganizerStrategy());
			}
			if (exportMode.equals(Config.EXPORT_MODE_IMPLODE)) {
				context.setSourceFileOrganizerStrategy(new ImplodeSourceFileOrganizerStrategy());
			} else {
				logger.warn("No Export Mode provided, defaulting to Implode");
				context.setSourceFileOrganizerStrategy(new ImplodeSourceFileOrganizerStrategy());
			}

			context.organize(this);
		} else {
			logger.info(
					"No schema was found for file {}. Ignoring sourceFile schema organizing",
					this.getFileName());
		}
	}

	/**
	 * 
	 */
	public void print() {
		printAll();
	}

	/**
	 * 
	 */
	public void printAll() {
		String providerString = null;
		if (!(this.getProvider() == null)) {
			providerString = this.getProvider().getProviderName() + " - "
					+ this.getProvider().getProviderIdentifier();
		}

		String schemaString = null;
		if (!(this.getSchema() == null)) {
			schemaString = this.schema.getName();
		}

		logger.debug(
				"FileName '{}' FileExtension: '{}' Status: '{}' Headers (Size): '{}' Provider: '{}' Schema: '{}'",
				this.getFileName(), this.getFileExtension(),
				this.getLoadStage(), this.getSourceHeaders().size(),
				providerString, schemaString);
		printRecords();
	}

	/**
	 * 
	 */
	private void printRecords() {
		for (SourceFileRecord sourceFileRecord : this.getRecords()) {
			sourceFileRecord.print();
		}
	}

	/**
	 * @param record
	 */
	public long recordCount() {
		return this.records.size();
	}

	/**
	 * @param index
	 */
	public void removeRecord(int index) {
		this.records.remove(index);
	}

	/**
	 * @param map
	 *            the headers to set
	 */
	public void removeSourceHeader(Integer key) {
		this.sourceHeaders.remove(key);
	}

	/**
	 * @param provider
	 *            the provider to set
	 */
	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	/**
	 * @param records
	 *            the records to set
	 */
	public void setRecords(List<SourceFileRecord> records) {
		this.records = records;
	}

	/**
	 * @param reportingPeriod
	 *            the reportingPeriod to set
	 */
	public void setReportingPeriod(Date reportingPeriod) {
		this.reportingPeriod = new Date(reportingPeriod.getTime());
	}

	/**
	 * 
	 */
	public void setReportingPeriodUsingFileNameParts() {
		if (this.getFileNameParts() == null
				|| this.getFileNameParts().isEmpty()) {
			logger.error("File has no fileNameParts, which means we cannot discern a provider or schema. we can process the file no farther");
			this.setStatus(false);
		} else {

			for (String fileNamePart : this.getFileNameParts()) {
				// Checking to see if fileNamePart is all numbers and meets the
				// length restrictions. if so Attempt to process as date
				if (fileNamePart.matches("[0-9]+")
						&& (fileNamePart.length() == 6 || fileNamePart.length() == 8)) {
					Date date = new Date();
					// if 6 digits, we attempt to get a Reporting Period in
					// mmYYYY format, example 072015 is July 2015
					if (fileNamePart.length() == 6) {
						logger.info(
								"attempting to map fileName Part '{}' to a MMyyyy date",
								fileNamePart);
						date = DateHelper.getDate(fileNamePart,
								DateHelper.FORMAT_MMYYYY);
						// if 8 digits, we attempt to get a Reporting Period in
						// MMddyyyy format, example 07252015 is July 25, 2015
					} else if (fileNamePart.length() == 8) {
						logger.info(
								"attempting to map fileName Part '{}' to a MMddyyyy date",
								fileNamePart);
						date = DateHelper.getDate(fileNamePart,
								DateHelper.FORMAT_MMDDYYYY);
					}
					if (date != null) {
						logger.info("Processed date as '{}'", date.toString());
						Date todaysDate = DateHelper.getTodaysDate();
						Date minimumDate = DateHelper.getMinDate();

						if (date.compareTo(todaysDate) > 0) {
							logger.error(
									"ReportingPeriod '{}' found in file name is later than current date. Please check file name",
									date.toString());
							this.setStatus(false);
							addLoadStatusMessage("ReportingPeriod '"+ fileNamePart + "' (Translated to " + DateHelper.getFormattedDate(date, DateHelper.FORMAT_YYYY_MM_DD) + ")" + "' found in FileName is later than current date. Please check file name");
						} else if (date.compareTo(minimumDate) < 0) {
							logger.error(
									"ReportingPeriod '{}' found in file name is before the year 2000 and may be inacurate. Please check file name",
									date.toString());
							addLoadStatusMessage("ReportingPeriod '"+ fileNamePart + "' (Translated to " + DateHelper.getFormattedDate(date, DateHelper.FORMAT_YYYY_MM_DD) + ")" + "' found in FileName is before the year 2000 and may be inacurate. Please check file name");
							this.setStatus(false);
						} else {
							logger.info(
									"Successfully added Reporting Period '{}'",
									date.toString());
							this.setReportingPeriod(date);
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(Schema schema) {
		this.schema = schema;
	}

	/**
	 * @param map
	 *            the headers to set
	 */
	public void setSourceHeaders(Map<Integer, String> map) {
		this.sourceHeaders = map;
	}

	/**
	 * @param totalEmptyRecords
	 */
	public void setTotalEmptyRecords(Integer totalEmptyRecords) {
		this.totalEmptyRecords = totalEmptyRecords;
	}

	/**
	 * @param totalErrorRecords
	 */
	public void setTotalErrorRecords(Integer totalErrorRecords) {
		this.totalErrorRecords = totalErrorRecords;
	}

	/**
	 * @param totalFatalRecords
	 */
	public void setTotalFatalRecords(Integer totalFatalRecords) {
		this.totalFatalRecords = totalFatalRecords;
	}

	/**
	 * @param totalNullRecords
	 */
	public void setTotalNullRecords(Integer totalNullRecords) {
		this.totalNullRecords = totalNullRecords;
	}

	/**
	 * @param totalProcessedRecords
	 */
	public void setTotalProcessedRecords(Integer totalProcessedRecords) {
		this.totalProcessedRecords = totalProcessedRecords;
	}

	/**
	 * @param totalRecords
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @param totalWarningRecords
	 */
	public void setTotalWarningRecords(Integer totalWarningRecords) {
		this.totalWarningRecords = totalWarningRecords;
	}

	public void validate() {
		SourceFileValidator validator = new SourceFileValidator();
		validator.validate(this);
	}

	public void logError(int errorLevel) {
		this.setMaxErrorLevel(errorLevel);
		switch (errorLevel) {
		case 1:
			this.incrementTotalWarningRecords();
			break;
		case 2:
			this.incrementTotalErrorRecords();
			break;
		case 3:
			this.incrementTotalFatalRecords();
			break;
		}

	}

}
