package test.java.gov.gsa.fssi.mockData;

import java.util.ArrayList;
import java.util.HashMap;

import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourceFiles.records.SourceFileRecord;

public class MockSourceFile{
	
	public static SourceFile make(){
		SourceFile sourceFile = new SourceFile();
		return sourceFile;
	}
	
	public static SourceFile make(String fileName){
		SourceFile sourceFile = new SourceFile(fileName);
		return sourceFile;
	}	
	
	public static SourceFile make(String fileName, SourceFileRecord sourceFileRecord){
		SourceFile sourceFile = new SourceFile(fileName);
		sourceFile.addRecord(sourceFileRecord);
		return sourceFile;
	}	
	
	public static SourceFile make(String fileName, ArrayList<SourceFileRecord> sourceFileRecords){
		SourceFile sourceFile = new SourceFile(fileName);
		for(SourceFileRecord sourceFileRecord: sourceFileRecords){
			sourceFile.addRecord(sourceFileRecord);			
		}
		return sourceFile;
	}	
	
	public static SourceFile make(String fileName, HashMap<Integer,String> sourceHeaders){
		SourceFile sourceFile = new SourceFile(fileName);
		sourceFile.setSourceHeaders(sourceHeaders);
		return sourceFile;
	}	
	
	public static SourceFile make(String fileName, HashMap<Integer,String> sourceHeaders, ArrayList<SourceFileRecord> sourceFileRecords){
		SourceFile sourceFile = new SourceFile(fileName);
		sourceFile.setSourceHeaders(sourceHeaders);
		for(SourceFileRecord sourceFileRecord: sourceFileRecords){
			sourceFile.addRecord(sourceFileRecord);			
		}
		return sourceFile;
	}	
	
}
