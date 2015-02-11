package main.java.gov.gsa.fssi.helpers.mockdata;

import java.util.HashMap;
import java.util.List;

import main.java.gov.gsa.fssi.files.sourcefiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourcefiles.records.SourceFileRecord;

public class MockSourceFile {

	public static SourceFile make() {
		SourceFile sourceFile = new SourceFile();
		return sourceFile;
	}

	public static SourceFile make(String fileName) {
		SourceFile sourceFile = new SourceFile(fileName);
		return sourceFile;
	}

	public static SourceFile make(String fileName,
			SourceFileRecord sourceFileRecord) {
		SourceFile sourceFile = new SourceFile(fileName);
		sourceFile.addRecord(sourceFileRecord);
		return sourceFile;
	}

	public static SourceFile make(String fileName,
			List<SourceFileRecord> sourceFileRecords) {
		SourceFile sourceFile = new SourceFile(fileName);
		for (SourceFileRecord sourceFileRecord : sourceFileRecords) {
			sourceFile.addRecord(sourceFileRecord);
		}
		return sourceFile;
	}

	public static SourceFile make(String fileName,
			HashMap<Integer, String> sourceHeaders) {
		SourceFile sourceFile = new SourceFile(fileName);
		sourceFile.setSourceHeaders(sourceHeaders);
		return sourceFile;
	}

	public static SourceFile make(String fileName,
			HashMap<Integer, String> sourceHeaders,
			List<SourceFileRecord> sourceFileRecords) {
		SourceFile sourceFile = new SourceFile(fileName);
		sourceFile.setSourceHeaders(sourceHeaders);
		for (SourceFileRecord sourceFileRecord : sourceFileRecords) {
			sourceFile.addRecord(sourceFileRecord);
		}
		return sourceFile;
	}

}
