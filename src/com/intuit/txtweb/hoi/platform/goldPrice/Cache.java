package com.intuit.txtweb.hoi.platform.goldPrice;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/***
 * 
 * @author mkurian
 *
 */
public class Cache {

	static String today = null;
	static String today8Gram = null;
	static String yesterday = null;
	static String todaysPricePerGram = null;
	public static String getToday() {
		return today;
	}

	public static String getToday8Gram() {
		return today8Gram;
	}

	public static String getYesterday() {
		return yesterday;
	}

	public static String getTodaysPricePerGram() {
		return todaysPricePerGram;
	}

	public static String getTodaysPricePer8Gram() {
		return todaysPricePer8Gram;
	}

	public static String getYesterdaysPricePerGram() {
		return yesterdaysPricePerGram;
	}

	public static Logger getLogger() {
		return logger;
	}

	static String todaysPricePer8Gram = null;
	static String yesterdaysPricePerGram = null;
	
	static Logger logger = Logger.getLogger(Cache.class.getName());
	static Calendar lastUpdatedTime;
	
	
	public static String getLastUpdatedTime(){
		return Util.getDateTimeFormat().format(lastUpdatedTime.getTime());
	}
	
	public static void refreshLastUpdatedTime(){
		lastUpdatedTime = Util.getLocalCalendar();
	}
	
	public static boolean needToUpdateCache(){
		if(lastUpdatedTime == null ) 
			return true;
		Calendar currentHour = Util.getLocalCalendar();
		currentHour.add(Calendar.HOUR, -1);
		if(lastUpdatedTime.compareTo(currentHour) < 0){
			logger.log(Level.INFO,
					"Next hour. Update cache");
			return true;
		}
		else{ 
			return false;
		}
	}
	
	public static boolean upateCache(){
		logger.log(Level.INFO, "Cache update request");
		boolean price8grams = GoldPriceFetcher.scrapeGoldPriceFor8GramToday("http://www.keralagold.com/daily-gold-prices.htm") ;
		boolean priceToday = GoldPriceFetcher.scrapeGoldPriceToday("http://www.keralagold.com/kerala-gold-rate-per-gram.htm");
		boolean priceYesterday = GoldPriceFetcher.scrapeGoldPriceYesterday("http://www.keralagold.com/kerala-gold-rate-per-gram.htm");
		if(price8grams && priceToday && priceYesterday)
			return true;
		else
			return false;
	}
	
	 static boolean isCurrentDateSameAsCached(boolean perGram) {
		if(today == null) return false;
		if(needToUpdateCache()) return false; 
		
		Calendar todayCalendar = Util.getLocalCalendar();
		String currentDate = Util.getDateFormat().format(todayCalendar.getTime());
		if (perGram && currentDate.equals(today) || !perGram && currentDate.equals(today8Gram)) {
			return true;
		} else {
			logger.log(Level.INFO,
					"Current date is not same as cached. Update cache: "
							+ todaysPricePerGram +" " + todaysPricePer8Gram);
			return false;
		}
	}
	
	 static boolean isPreviousDateSameAsCached() {
		if(yesterday == null) return false;
		if(needToUpdateCache()) return false;
		
		Calendar yesterdayCalendar = Util.getLocalCalendar();
		yesterdayCalendar.add(Calendar.DAY_OF_MONTH, -1);
		String previousDate = Util.getDateFormat().format(yesterdayCalendar.getTime());
		if (previousDate.equals(yesterday)) {
			return true;
		} else {
			logger.log(Level.INFO,
					"Previous date is not same as cached. Update cache: "+ yesterdaysPricePerGram );
			return false;
		}
	}
	
	 
}
