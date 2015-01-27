package gov.gsa.fssi.files.sourceFiles.utils.strategies.exporters;

import gov.gsa.fssi.config.Config;
import gov.gsa.fssi.files.sourceFiles.SourceFile;
import gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileExporterStrategy;
import gov.gsa.fssi.helpers.FileHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class ExcelSourceFileExporterStrategy implements SourceFileExporterStrategy{

	/**
	 *
	 * @return Schema loaded from fileName in schemas_directory
	 */
	public void export(SourceFile sourceFile) {
		logger.info("Exporting File {} as a 'XLS'", sourceFile.getFileName());
		FileOutputStream out;
		try {
			out = new FileOutputStream(config.getProperty(Config.STAGED_DIRECTORY) + FileHelper.buildNewFileName(sourceFile.getFileName(), sourceFile.getProvider().getFileOutputType()));

		// create a new workbook
		Workbook wb = (sourceFile.getProvider().getFileOutputType().toUpperCase().equals("XLSX") ? new XSSFWorkbook() : new HSSFWorkbook());
		// create a new sheet
		Sheet s = wb.createSheet();
		// declare a row object reference
		Row r = null;
		// declare a cell object reference
		Cell c = null;

		//creating header row
		r = s.createRow(0);

		for(int i=0; i < sourceFile.getSourceHeaders().size();i++){
			c = r.createCell(i);
			c.setCellValue(sourceFile.getSourceHeaders().get(i));
		}
		
		int counter = 0;
		
		//Now lets put some data in there....
		for (SourceFileRecord sourceFileRecord : sourceFile.getRecords()) {
			counter ++;	
			r = s.createRow(counter);
			
			ArrayList<Data> records = sourceFileRecord.getDatas(); 	
			for (Data data : records) {
				//logger.debug("{}", data.getHeaderIndex());
				c = r.createCell((int)data.getHeaderIndex());
				c.setCellValue(data.getData());
			}
		}
		
		// write the workbook to the output stream
		// close our file (don't blow out our file handles
			wb.write(out);
			out.close();
		} catch (IOException e) {
			logger.error("There was an IOException error '{}' with file {}. ", sourceFile.getFileName(), e.getMessage());
			e.printStackTrace();
		}
	}

}
