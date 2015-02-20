package main.java.gov.gsa.fssi.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHelper {
	private static final Logger logger = LoggerFactory
			.getLogger(FileHelper.class);
	public static final byte SEPARATOR_UNDERSCORE = '_';
	public static final byte SEPARATOR_DASH = '-';
	public static final byte SEPARATOR_COMMA = ',';
	public static final byte SEPARATOR_PIPE = '|';
	public static final byte SEPARATOR_TILDE = '~';
	public static final byte SEPARATOR_FORWARDSLASH = '/';
	public static final byte SEPARATOR_BACKSLASH = '\\';

	public static String buildNewFileName(String oldFileName,
			String newExtension) {
		return oldFileName.substring(0, oldFileName.lastIndexOf('.') + 1)
				+ newExtension.toLowerCase();
	}

	public static List<String> getFilesFromDirectory(String directoryName) {
		return getFilesFromDirectory(directoryName, null);
	}

	public static List<String> getFilesFromDirectory(String directoryName,
			String whitelist) {
		logger.info("getFilesFromDirectory Looking in '{}' for '{}' files.",
				directoryName, whitelist);

		File directory = getFile(directoryName);
		if (isDirectory(directory)) {
			System.out.println(directory.getAbsolutePath());
			List<String> fileList = new ArrayList<String>();
			File[] listOfFiles = directory.listFiles();
			int fileCount = 0;
			int totalFileCount = 0;
			if (listOfFiles != null) {
				for (File file : listOfFiles) {
					if (isFile(file)) {
						totalFileCount++;
						if(file.getName().contains(".")){
							String fileExtension = file.getName().substring(
									file.getName().lastIndexOf("."),
									file.getName().length());
							if (file.isDirectory()) {
								logger.info(
										"Ignoring '{}' because it is a directory",
										whitelist);
							} else if (whitelist != null && !whitelist.isEmpty()
									&& !whitelist.contains(fileExtension)) {
								logger.info(
										"Ignoring '{}' because it is not in whitelist",
										file.getName());
							} else {
								logger.info("Added '{}' to ArrayList",
										file.getName());
								fileList.add(file.getName());
								fileCount++;
							}
						}else logger.info("file '{}' does not have an extension, ignoring", file.getName());
					}
				}
			} else
				logger.error("No files were found in '{}'", directory);
			logger.info("Added {} out of {} files", fileCount, totalFileCount);
			return fileList;
		} else
			logger.error("'{}' is not a valid directory", directory);
		return null;
	}

	public static String getFullPath(String directory, String fileName) {
		return directory + fileName;
	}

	public static File getFile(String path) {
		return new File(path);
	}

	public static boolean isFile(File file) {
		if (file.isFile())
			return true;
		else
			return false;
	}

	public static boolean isDirectory(File file) {
		if (file.isDirectory())
			return true;
		else
			return false;
	}

	public static boolean isDirectory(String path) {
		File file = getFile(path);
		if (file.isDirectory())
			return true;
		else
			return false;
	}

	public static boolean isFile(String path) {
		return isFile(getFile(path));
	}

	public static boolean createDirectory(String path) {
		File file = getFile(path);
		if (isDirectory(file)) {
			return true;
		} else {
			return file.mkdir();
		}
	}
}
