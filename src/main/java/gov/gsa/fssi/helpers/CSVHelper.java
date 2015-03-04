package main.java.gov.gsa.fssi.helpers;

import org.apache.commons.csv.CSVRecord;

public class CSVHelper {

	public static boolean isRowEmpty(final CSVRecord csvRecord) {
		for(int i=0;i < csvRecord.size(); i++){
			if(!isCellEmpty(csvRecord.get(i))) return false;			
		}
		return true;
	}
	
	public static boolean isCellEmpty(String cell) {
		if(cell != null){
			if(!"".equals(cell) && !"NULL".equalsIgnoreCase(cell) && !"0".equals(cell)) return false;				
		}	
		return true;
	}	
}
