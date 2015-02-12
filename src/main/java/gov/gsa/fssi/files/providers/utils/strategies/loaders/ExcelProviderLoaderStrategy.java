package main.java.gov.gsa.fssi.files.providers.utils.strategies.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import main.java.gov.gsa.fssi.files.providers.Provider;
import main.java.gov.gsa.fssi.files.providers.utils.strategies.ProviderLoaderStrategy;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelProviderLoaderStrategy implements ProviderLoaderStrategy {
	public static boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
				return false;
		}
		return true;
	}
	public static final Logger logger = LoggerFactory
			.getLogger(ExcelProviderLoaderStrategy.class);

	@Override
	public void load(String directory, String fileName, List<Provider> providers) {
		logger.info(
				"Running ExcelProviderLoaderStrategy to get providers from '{}'",
				fileName);
		Workbook wb;
		try {
			int providerIdentifierColumn = 0;
			int providerNameColumn = 0;
			int providerIdColumn = 0;
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
					for (Cell cell : row) {
						logger.debug("Loading Header Row {} - {}", cell
								.getColumnIndex(), cell.getStringCellValue()
								.toUpperCase());

						if (cell.getStringCellValue().toString()
								.equalsIgnoreCase("PROVIDER_ID")) {
							providerIdColumn = cell.getColumnIndex();
						} else if (cell.getStringCellValue().toString()
								.equalsIgnoreCase("PROVIDER_NAME")) {
							providerNameColumn = cell.getColumnIndex();
						} else if (cell.getStringCellValue().toString()
								.equalsIgnoreCase("PROVIDER_IDENTIFIER")) {
							providerIdentifierColumn = cell.getColumnIndex();
						} else if (cell.getStringCellValue().toString()
								.equalsIgnoreCase("FILE_OUTPUT_TYPE")) {
							fileOutputTypeColumn = cell.getColumnIndex();
						} else if (cell.getStringCellValue().toString()
								.equalsIgnoreCase("SCHEMA")) {
							schemaNameColumn = cell.getColumnIndex();
						} else if (cell.getStringCellValue().toString()
								.equalsIgnoreCase("PROVIDER_EMAIL")) {
							providerEmailColumn = cell.getColumnIndex();
						}
					}
					logger.info("Completed mapping header indexes");
				} else {

					// TODO: Add logic to ignore empty rows
					if (!isRowEmpty(row)) {
						if (!(row.getCell(providerIdColumn) == null)
								&& !(row.getCell(providerIdColumn)
										.getStringCellValue().isEmpty())
								&& !(row.getCell(providerIdColumn)
										.getStringCellValue()
										.equalsIgnoreCase("NULL"))) {
							newProvider.setProviderId(row
									.getCell(providerIdColumn)
									.getStringCellValue().toUpperCase());
						}

						if (!(row.getCell(providerNameColumn) == null)
								&& !(row.getCell(providerNameColumn)
										.getStringCellValue().isEmpty())
								&& !(row.getCell(providerNameColumn)
										.getStringCellValue()
										.equalsIgnoreCase("NULL"))) {
							newProvider.setProviderName(row
									.getCell(providerNameColumn)
									.getStringCellValue().toUpperCase());
						}

						if (!(row.getCell(providerIdentifierColumn) == null)
								&& !(row.getCell(providerIdentifierColumn)
										.getStringCellValue().isEmpty())
								&& !(row.getCell(providerIdentifierColumn)
										.getStringCellValue()
										.equalsIgnoreCase("NULL"))) {
							newProvider.setProviderIdentifier(row
									.getCell(providerIdentifierColumn)
									.getStringCellValue().toUpperCase());
						}

						if (!(row.getCell(fileOutputTypeColumn) == null)
								&& !(row.getCell(fileOutputTypeColumn)
										.getStringCellValue().isEmpty())
								&& !(row.getCell(fileOutputTypeColumn)
										.getStringCellValue()
										.equalsIgnoreCase("NULL"))) {
							newProvider.setFileOutputType(row
									.getCell(fileOutputTypeColumn)
									.getStringCellValue().toUpperCase());
						}

						if (!(row.getCell(schemaNameColumn) == null)
								&& !(row.getCell(schemaNameColumn)
										.getStringCellValue().isEmpty())
								&& !(row.getCell(schemaNameColumn)
										.getStringCellValue()
										.equalsIgnoreCase("NULL"))) {
							newProvider.setSchemaName(row
									.getCell(schemaNameColumn)
									.getStringCellValue().toUpperCase());
						}

						if (!(row.getCell(providerEmailColumn) == null)
								&& !(row.getCell(providerEmailColumn)
										.getStringCellValue().isEmpty())
								&& !(row.getCell(providerEmailColumn)
										.getStringCellValue()
										.equalsIgnoreCase("NULL"))) {
							newProvider.setProviderEmail(row
									.getCell(providerEmailColumn)
									.getStringCellValue().toUpperCase());
						}

						providers.add(newProvider);
						logger.info(
								"Added new provider '{}' to list of Providers",
								newProvider.getProviderIdentifier());

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
