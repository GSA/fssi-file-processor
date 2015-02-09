package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.loggers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;
import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.results.ValidationResult;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileLoggerStrategy;
import main.java.gov.gsa.fssi.helpers.FileHelper;


/**
 * This class loads a schema from an XML file
 * 
 * @author davidlarrimore
 *
 */
public class BasicTextSourceFileLoggerStrategy implements SourceFileLoggerStrategy{

	/**
	 * This method maps a sourceFile to its schema and then conforms the file/data to the schema format 
	 * We delete any data that is no longer necessary
	 */	
	@Override
	public void createLog(String directory, SourceFile sourceFile) {
		File file = null;
		
		try {

//          TODO: Switch to outputWriter
//			File file = new File(FileHelper.getFullPath(directory, newFileName));
//			Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//			PrintWriter printWriter = new PrintWriter(writer);
			
			String fileName = directory + FileHelper.buildNewFileName(sourceFile.getFileName(), "log");
			file = new File(fileName);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);	
			for (SourceFileRecord record : sourceFile.getRecords()) {
				if(!record.getStatus()){
					bufferedWriter.write("Line: " + record.getRowIndex() + " Status: FAIL Level: " + record.getErrorLevelName(record.getMaxErrorLevel()));
					bufferedWriter.newLine();
					for (Data data : record.getDatas()) {
						if(!data.getStatus()){
							bufferedWriter.write("     Field: "+sourceFile.getSourceHeaderName(data.getHeaderIndex())+" failed constraints: ");
							for (ValidationResult result : data.getValidationResults()) {
								if(!result.getStatus()) bufferedWriter.write(result.getRule()+" ");
							}
							bufferedWriter.write(" against value: '"+ data.getData() + "'");	
							bufferedWriter.newLine();
						}
					}					
				}else {
					bufferedWriter.write("Line: " + record.getRowIndex() + " Status: PASS");
					bufferedWriter.newLine();
				}
			}
			
			bufferedWriter.flush();
			bufferedWriter.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	
	
	
	
	
}
