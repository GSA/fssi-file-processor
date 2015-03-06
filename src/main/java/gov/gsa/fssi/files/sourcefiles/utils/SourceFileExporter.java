package main.java.gov.gsa.fssi.files.sourcefiles.utils;

import main.java.gov.gsa.fssi.files.File;
import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.contexts.SourceFileExporterContext;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.exporters.CSVSourceFileExporterStrategy;
import main.java.gov.gsa.fssi.files.sourcefiles.utils.strategies.exporters.ExcelSourceFileExporterStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SourceFileExporter {
	static Logger logger = LoggerFactory.getLogger(SourceFileExporter.class);
	
	public void export(SourceFile sourceFile, String directory) {
		SourceFileExporterContext context = new SourceFileExporterContext();
		if (sourceFile.getRecords() != null) {
			if(sourceFile.getProvider() != null){
				logger.info("Attempting to export file '{}' in '{}' format",sourceFile.getFileName(), sourceFile.getProvider().getFileOutputType());
				if (sourceFile.getProvider().getFileOutputType()
						.equalsIgnoreCase(File.FILETYPE_CSV)) {
					
					context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());
				} else if (sourceFile.getProvider().getFileOutputType()
						.equalsIgnoreCase(File.FILETYPE_XLS)) {
					context.setSourceFileExporterStrategy(new ExcelSourceFileExporterStrategy());
				} else if (sourceFile.getProvider().getFileOutputType()
						.equalsIgnoreCase(File.FILETYPE_XLSX)) {
					context.setSourceFileExporterStrategy(new ExcelSourceFileExporterStrategy());
				}else if (sourceFile.getProvider().getFileOutputType() != null || "".equals(sourceFile.getProvider().getFileOutputType())){
					logger.warn(
							"Provider identifier '{}' does not have a file output type, defauting to '{}'",
							sourceFile.getProvider().getProviderIdentifier(),
							File.FILETYPE_CSV);
					context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());					
				} else {
					logger.warn(
							"We cannot currently export to a '{}'. defaulting to '{}'",
							sourceFile.getProvider().getFileOutputType(),
							File.FILETYPE_CSV);
					context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());
				}
			}else{
				logger.warn("No provider found for file, defaulting output to '{}'",File.FILETYPE_CSV);	
				context.setSourceFileExporterStrategy(new CSVSourceFileExporterStrategy());
				sourceFile.addExportStatusMessages("No provider found or file Output Type found, defaulting to '" + File.FILETYPE_CSV + "'");
			}
			
			context.export(directory, sourceFile);	
			
		} else {
			logger.error("Cannot export sourceFile '{}'. No data found",
					sourceFile.getFileName());
			sourceFile.addExportStatusMessages("Cannot export file. No data found");
		}
	}
}
