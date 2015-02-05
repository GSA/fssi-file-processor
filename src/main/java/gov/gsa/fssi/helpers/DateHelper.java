package main.java.gov.gsa.fssi.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateHelper {
	static Logger logger = LoggerFactory.getLogger(SourceFile.class);
	//static Config config = new Config();	    	
	
	public static final String FORMAT_MMYYYY = "MMyyyy";
	public static final String FORMAT_MMDDYYYY = "MMddyyyy";
	public static final String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm'Z'";
	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String TIMEZONE_UTC = "UTC";

	/**
	 * @return
	 */
	public static Date getTodaysDate() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		return c.getTime();
	}	
	
	
	public static Date getMaxDate(){
		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.YEAR, 10);
		date = c.getTime();
		return date;
	}
	
	/**
	 * Based upon the wisdom of the powers at be, we determined that a date that 
	 * 
	 * @return date minimum acceptable date
	 */
	public static Date getMinDate(){
		Date date = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.YEAR, -50);
		date = c.getTime();
		return date;
	}
	
	/**
	 * Use this method to get a date when you know the dateformat
	 * @param string
	 * @param dateFormat
	 * @return
	 */
	public static Date getDate(String string, String dateFormat){
		Date date  = new Date();
		logger.info("Attempting to extract date from string: '{}' using format '{}", string, dateFormat);
		try {
			date = parseDate(string, dateFormat);
		} catch (ParseException e) {
			date = null;
			logger.error("There was an ParseException error '{}' attempting to get date from string: '{}'",e.getMessage(), string);
			//e.printStackTrace();
		}
		
		return date;
	}

	public static Date YesterdaysDate(){
		Date newDate = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(newDate); 
		c.add(Calendar.DATE, -1);
		newDate = c.getTime();
		return newDate;
	}
	
	
	public static Date TomorrowsDate(){
		Date newDate = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(newDate); 
		c.add(Calendar.DATE, 1);
		newDate = c.getTime();
		return newDate;
	}	
	
	
	private static Date parseDate(String string, String dateFormat) throws ParseException{
		TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_UTC);
		DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		format.setTimeZone(timeZone);
		return format.parse(string);
	}
}
