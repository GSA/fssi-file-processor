package main.java.gov.gsa.fssi.files.providers.utils.strategies.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.strategies.ProviderLoaderStrategy;
import main.java.gov.gsa.fssi.helpers.ExcelHelper;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelProviderLoaderStrategy implements ProviderLoaderStrategy {
	
	public static final Logger logger = LoggerFactory
			.getLogger(ExcelProviderLoaderStrategy.class);

	@Override
	public void load(String directory, String fileName, List<Provider> providers) {
		logger.info("Running ExcelProviderLoaderStrategy to get providers from '{}'", fileName);
		Workbook wb;
		try {
			int providerNameColumn = 0;
			int providerIdentifierColumn = 0;
			int fileOutputTypeColumn = 0;
			int schemaNameColumn = 0;
			int providerEmailColumn = 0;
			wb = WorkbookFactory.create(new File(directory + fileName));
			Sheet sheet1 = wb.getSheetAt(0);

			int passCounter = 0;
			int failCounter = 0;

			logger.info("Found '{}' rows in file '{}'", sheet1.getLastRowNum(),
					fileName);
			for (Row row : sheet1) {
				Provider newProvider = new Provider();
				// If this is the header row, we need to figure out where the
				// columns we need are
				if (row.getRowNum() == 0) {
					if (!ExcelHelper.isRowEmpty(row)) {					
					for (Cell cell : row) {
						logger.debug("Loading Header Row {} - {}", cell.getColumnIndex(), cell.getStringCellValue().toUpperCase());
						if(!ExcelHelper.isCellEmpty(cell)){
							if("PROVIDER_NAME".equalsIgnoreCase(ExcelHelper.getStringValueFromCell(cell))){
								providerNameColumn = cell.getColumnIndex();
							}else if("PROVIDER_IDENTIFIER".equalsIgnoreCase(ExcelHelper.getStringValueFromCell(cell))){
								providerIdentifierColumn = cell.getColumnIndex();
							}else if("FILE_OUTPUT_TYPE".equalsIgnoreCase(ExcelHelper.getStringValueFromCell(cell))){
								fileOutputTypeColumn = cell.getColumnIndex();	
							}else if("SCHEMA".equalsIgnoreCase(ExcelHelper.getStringValueFromCell(cell)) || "SCHEMA_NAME".equalsIgnoreCase(ExcelHelper.getStringValueFromCell(cell)) ){
								schemaNameColumn = cell.getColumnIndex();
							}else if("PROVIDER_EMAIL".equalsIgnoreCase(ExcelHelper.getStringValueFromCell(cell))){
								providerEmailColumn = cell.getColumnIndex();
							}	
						}
					}
					logger.info("Completed mapping header indexes");
				} else logger.error("Header row (0) was empty in file '{}'.", fileName);
				} else {
					// TODO: Add logic to ignore empty rows
					if (!ExcelHelper.isRowEmpty(row)) {

						if (!ExcelHelper.isCellEmpty(row.getCell(providerNameColumn))) newProvider.setProviderName(ExcelHelper.getStringValueFromCell(row.getCell(providerNameColumn)));
						if (!ExcelHelper.isCellEmpty(row.getCell(providerIdentifierColumn))) newProvider.setProviderIdentifier(ExcelHelper.getStringValueFromCell(row.getCell(providerIdentifierColumn)));						
						if (!ExcelHelper.isCellEmpty(row.getCell(fileOutputTypeColumn))) newProvider.setFileOutputType(ExcelHelper.getStringValueFromCell(row.getCell(fileOutputTypeColumn)));
						if (!ExcelHelper.isCellEmpty(row.getCell(schemaNameColumn))) newProvider.setSchemaName(ExcelHelper.getStringValueFromCell(row.getCell(schemaNameColumn)));
						if (!ExcelHelper.isCellEmpty(row.getCell(providerEmailColumn))) newProvider.setProviderEmail(ExcelHelper.getStringValueFromCell(row.getCell(providerEmailColumn)));
				
						
						providers.add(newProvider);
						logger.info("Added new provider '{}' to list of Providers",newProvider.getProviderIdentifier());
						passCounter++;
					}else{
						failCounter++;
					}
				}
			}

			logger.info(
					"Successfully Processed {} Providers ({} Failed) from '{}'",
					passCounter, failCounter, fileName);

		} catch (FileNotFoundException e) {
			logger.error(
					"Received FileNotFoundException '{}' while trying to load {}",
					e.getMessage(), fileName);
		} catch (InvalidFormatException e) {
			logger.error(
					"Received InvalidFormatException '{}' while trying to load {}",
					e.getMessage(), fileName);
		} catch (java.lang.NullPointerException e) {
			logger.error(
					"received NullPointerException error '{}' while loading file '{}'",
					e.getMessage(), fileName);
		} catch (IOException e) {
			logger.error("Received IOException while trying to load {}",
					fileName);
		}

	}
}
