package main.java.gov.gsa.fssi.helpers.mockData;

import main.java.gov.gsa.fssi.files.sourceFiles.records.datas.Data;

public class MockData {

	
	public static Data make(){
		Data data = new Data();
		return data;
	}	
	
	public static Data make(String value){
		Data data = new Data();
		data.setData(value);
		return data;
	}
	

	public static Data make(String value, int headerIndex){
		Data data = new Data();
		data.setData(value);
		data.setHeaderIndex(headerIndex);
		return data;
	}	

}
