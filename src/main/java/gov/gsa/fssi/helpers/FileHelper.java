package main.java.gov.gsa.fssi.helpers;

import java.io.File;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
	static Logger logger = LoggerFactory.getLogger(FileHelper.class);
	public static final byte SEPARATOR_UNDERSCORE = '_';
	public static final byte SEPARATOR_DASH = '-';
	public static final byte SEPARATOR_COMMA = ',';
	public static final byte SEPARATOR_PIPE = '|';
	public static final byte SEPARATOR_TILDE = '~';
	public static final byte SEPARATOR_FORWARDSLASH = '/';
	public static final byte SEPARATOR_BACKSLASH = '\\';

	public static ArrayList<String> getFilesFromDirectory(String directoryName) {
		return getFilesFromDirectory(directoryName, null);
	}

	public static ArrayList<String> getFilesFromDirectory(String directoryName,
			String whitelist) {
		ArrayList<String> fileList = new ArrayList<String>();
		logger.info("getFilesFromDirectory Looking in '{}' for '{}' files.",
				directoryName, whitelist);

		File folder = new File(directoryName);
		System.out.println(folder.getAbsolutePath());
		File[] listOfFiles = folder.listFiles();
		int fileCount = 0;
		int totalFileCount = 0;

		if (listOfFiles != null) {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					totalFileCount++;
					String fileExtension = file.getName().substring(
							file.getName().lastIndexOf("."),
							file.getName().length());
					if (file.isDirectory()) {
						logger.info("Ignoring '{}' because it is a directory",
								whitelist);
					} else if (whitelist != null && !whitelist.isEmpty()
							&& !whitelist.contains(fileExtension)) {
						logger.info(
								"Ignoring '{}' because it is not in whitelist",
								file.getName());
					} else {
						logger.info("Added '{}' to ArrayList", file.getName());
						fileList.add(file.getName());
						fileCount++;
					}
				}
			}
		}

		logger.info("Added {} out of {} files", fileCount, totalFileCount);

		return fileList;
	}

	public static String getFullPath(String directory, String fileName) {
		return directory + fileName;
	}

	public static String buildNewFileName(String oldFileName,
			String newExtension) {
		return oldFileName.substring(0, oldFileName.lastIndexOf('.') + 1)
				+ newExtension.toLowerCase();
	}

}
