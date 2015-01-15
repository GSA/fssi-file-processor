package gov.gsa.fssi.helpers;

import gov.gsa.fssi.fileprocessor.sourceFiles.SourceFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateHelper {
	static Logger logger = LoggerFactory.getLogger(SourceFile.class);
	//static Config config = new Config();	    	
	
	public static String FORMAT_MMYYYY = "MMyyyy";
	public static String FORMAT_MMDDYYYY = "MMddyyyy";
	
	
	
	/**
	 * @return
	 */
	public static Date getTodaysDate() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0); //anything 0 - 23
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date todaysDate = c.getTime(); //the midnight, that's the first second of the day.
		return todaysDate;
	}	
	
	
	public static Date getDate(String string, String dateFormat){
		Date date = new Date();
		DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		logger.info("Attempting to extract date from string: '{}' using format '{}", string, dateFormat);
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			date = null;
			logger.error("There was an '{}' error attempting to get date from string: '{}'",e.getMessage(), string);
			//e.printStackTrace();
		}
		
		return date;
	}
}
