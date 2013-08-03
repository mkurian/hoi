package com.intuit.txtweb.hoi.platform.goldPrice;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/***
 * 
 * @author mkurian
 *
 */
public class Util {

	public static Calendar getLocalCalendar(){
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
		return cal;
	}
	
	public static SimpleDateFormat getDateFormat(){
		SimpleDateFormat keralaGoldDateFormat = new SimpleDateFormat("dd-MMM-yy");
		return keralaGoldDateFormat;
	}
	
	public static SimpleDateFormat getDateTimeFormat(){
			SimpleDateFormat keralaGoldDateFormat = new SimpleDateFormat("dd-MMM-yy hh:mm aa");
			return keralaGoldDateFormat;
		}
}
