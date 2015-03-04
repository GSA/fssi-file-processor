package main.java.gov.gsa.fssi.helpers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ExcelHelper {

	/**
	 * Row has no data whatsoever. checks each row
	 * @param row
	 * @return
	 */
	public static boolean isRowEmpty(Row row) {
		for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
			/*
			 * If one cell has data, the row is not considered empty
			 */
			if(!isCellEmpty(row.getCell(i))) return false;
		}
		return true;
	}

	public static boolean isCellEmpty(Cell cell) {
		if (cell == null){
			return true;
		}else {
			if (cell.getCellType() == Cell.CELL_TYPE_BLANK) return true;	
			if (cell.getCellType() == Cell.CELL_TYPE_STRING && (cell.getStringCellValue() == null || "".equals(cell.getStringCellValue()) || "NULL".equalsIgnoreCase(cell.getStringCellValue()))) return true;
		}
		return false;
	}

	public static String getStringValueFromCell(Cell cell) {
		if (cell.getCellType() == Cell.CELL_TYPE_BLANK) return null;	
		if (cell.getCellType() == Cell.CELL_TYPE_STRING) return cell.getStringCellValue();
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) return String.valueOf(cell.getNumericCellValue());
		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) return (cell.getBooleanCellValue() == false ? "false" : "true");
		if (cell.getCellType() == Cell.CELL_TYPE_ERROR) return null;
		return null;
	}
}
