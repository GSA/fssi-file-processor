package main.java.gov.gsa.fssi.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateHelper {
	/**
	 * Use this method to get a date when you know the dateformat
	 * 
	 * @param string
	 * @param dateFormat
	 * @return
	 */
	public static Date getDate(String string, String dateFormat) {
		Date date;
		try {
			date = jodaParseDate(string, dateFormat);
		} catch (ParseException e) {
			date = null;
			if (logger.isDebugEnabled()) logger.debug("There was an ParseException error '{}' parsing date from string: '{}' using format '{}'", e.getMessage(), string, dateFormat);
		} catch(IllegalArgumentException e){
			date = null;
			if (logger.isDebugEnabled()) logger.debug("There was an IllegalArgumentException error '{}' parsing date from string: '{}' using format '{}'", e.getMessage(), string, dateFormat);			
		}
		
		if (logger.isDebugEnabled() && date != null) logger.debug("Parsed the date '{}' from string '{}' using format '{}'",date ,string, dateFormat);	
		
		return date;
	}

	public static Date getMaxDate() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, 50);
		date = c.getTime();
		return date;
	}

	/**
	 * Based upon the wisdom of the powers at be, we determined that a date that
	 * 
	 * @return date minimum acceptable date
	 */
	public static Date getMinDate() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, -50);
		date = c.getTime();
		return date;
	}

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

	public static String getTodaysDateAndTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getFormattedDate(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date newDate = date;
		return dateFormat.format(newDate);
	}

	/**
	 * @param string
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 * @deprecated
	 */
	private static Date parseDate(String string, String dateFormat)
			throws ParseException {
		TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_UTC);
		DateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		format.setLenient(false);
		format.setTimeZone(timeZone);
		return format.parse(string);
	}

	
	private static Date jodaParseDate(String string, String dateFormat)throws ParseException, IllegalArgumentException {
		DateTimeFormatter format = DateTimeFormat.forPattern(dateFormat);
		DateTimeFormatter umt = format.withLocale(Locale.US);
		DateTime dateTime = umt.parseDateTime(string);
		return dateTime.toDate();
	}	
	
	
	
	public static Date tomorrowsDate() {
		Date newDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(newDate);
		c.add(Calendar.DATE, 1);
		newDate = c.getTime();
		return newDate;
	}

	public static Date yesterdaysDate() {
		Date newDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(newDate);
		c.add(Calendar.DATE, -1);
		newDate = c.getTime();
		return newDate;
	}

	
	/**
	 * This method simply takes a format and adds a delimiter between date elements.
	 * 
	 * @param format
	 * @param delimiter
	 * @return
	 */
	public static String makeFormatDelimited(String format, char delimiter){
		String newFormat = new String();
		char thisChar = 'n';		
		for (int i = 0; i < format.length(); i++) {
			if(i == 1){
				thisChar = format.charAt(i);
				newFormat = String.valueOf(thisChar);
			}else{
				if(format.charAt(i) == thisChar){
					newFormat = newFormat+String.valueOf(thisChar);
				}else{
					newFormat = newFormat + delimiter + String.valueOf(thisChar);					
				}
			}
		}
		return newFormat;
	}
	
	
	
	private String makeDateDelimited(String dateString, String format){	
		
		return dateString;
	}
	
	
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(DateHelper.class);

	public static final String FORMAT_MMYYYY = "MMyyyy";

	public static final String FORMAT_MMDDYYYY = "MMddyyyy";

	public static final String FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm'Z'";

	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	public static final String TIMEZONE_UTC = "UTC";
}
