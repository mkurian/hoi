package com.intuit.txtweb.hoi.platform.goldPrice;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

/**
 * ScreenScraper for KeralaGold.com
 * 
 * @author mkurian
 * 
 */
public class GoldPriceFetcher {

	private static Logger logger = Logger.getLogger(GoldPriceFetcher.class.getName());
	
	final static String userAgent = "Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1464.0 Safari/537.36";
	static Map<String,String> cookies;
	static Map<String,String> headers;
	static{
		cookies = new HashMap<String, String>();
		cookies.put("__utma", "194788663.1949673299.1375511361.1375511361.1375511361.1");
		cookies.put("__utmb", "194788663.1.10.1375511361");
		cookies.put("__utmc", "194788663");
		cookies.put("__utmz", "194788663.1375511361.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)");
		
		headers = new HashMap<String, String>();
		headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1464.0 Safari/537.36");
	}
	
	public static boolean scrapeGoldPriceFor8GramToday(String url) {
		String todayDate = null;
		String price = null;
		
		try{
			Connection connection = Jsoup.connect(url).timeout(5000);
			connection.userAgent(userAgent);
//			connection.cookies(cookies);
			Document doc = connection.get();
			Element dateElement = doc.getElementsByClass("kg2b").get(0);
			Element priceElement = doc.getElementsByClass("kg2b").get(1);
			
			todayDate = getDateFromDocElement(dateElement);
			price = getPriceFromDocElement(priceElement);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
		}
		if(todayDate != null && price != null){
			Cache.refreshLastUpdatedTime();
			Cache.today8Gram = getFormattedDate(todayDate);
			Cache.todaysPricePer8Gram = price;
			return true;
		}else{
			return false;
		}
	}

	public static boolean scrapeGoldPriceToday(String url) {		
		String todayDate = null;
		String price = null;
		try {

			Connection connection = Jsoup.connect(url).timeout(5000);
			connection.userAgent(userAgent);
//			connection.cookies(cookies);
			Document doc = connection.get();
			Element dateElement = doc.getElementsByClass("kg2b").get(0);
			Element priceElement = doc.getElementsByClass("kg2b").get(1);

			todayDate = getDateFromDocElement(dateElement);
			price = getPriceFromDocElement(priceElement);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			
		}
		if(todayDate != null && price != null){
			Cache.refreshLastUpdatedTime();
			Cache.today = getFormattedDate(todayDate);
			Cache.todaysPricePerGram = price;
			return true;
		}else{
			return false;
		}
	}

	public static boolean scrapeGoldPriceYesterday(String url) {
		String yesterdayDate = null;
		String price =  null;
		
		try {
			Connection connection = Jsoup.connect(url).timeout(5000);
			connection.userAgent(userAgent);
//			connection.cookies(cookies);
			Document doc = connection.get();

			Element dateElement = doc.getElementsByClass("kg2").get(0);
			Element priceElement = doc.getElementsByClass("kg2").get(3);

			yesterdayDate = getDateFromDocElement(dateElement);
			price = getPriceFromDocElement(priceElement);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			
		}
		if(yesterdayDate != null && price != null){
			Cache.refreshLastUpdatedTime();
			Cache.yesterday = getFormattedDate(yesterdayDate);
			Cache.yesterdaysPricePerGram = price;
			return true;
		}else{
			return false;
		}
	}
	
	private static String getFormattedDate(String scrapedDate) {
		String date = scrapedDate;
		try {
			SimpleDateFormat dateFormatter = Util.getDateFormat();
			dateFormatter.parse(scrapedDate);
			Calendar cal = dateFormatter.getCalendar();
			cal.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
			date = dateFormatter.format(cal.getTime());
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
		}
		
		return date;
	}

	private static String getDateFromDocElement(Element dateElement){
		TextNode dateNode = (TextNode) dateElement.childNodes().get(0);
		return dateNode.getWholeText();
	}
	
	private static String getPriceFromDocElement(Element priceElement){
		TextNode priceNode = (TextNode) priceElement.childNodes().get(0);
		return (priceNode.getWholeText());
	}

	private static HashMap<String, HashMap<String, String>> host2cookies = new HashMap<String, HashMap<String, String>>();

	public static String[] DownloadPage(URL url) throws Exception
	{
	    Connection con = Jsoup.connect(url.toString()).timeout(600000);
	    loadCookiesByHost(url, con);


	    Document doc = con.get();
	    url = con.request().url();

	    storeCookiesByHost(url, con);

	    return new String[]{url.toString(), doc.html()};
	}

	private static void loadCookiesByHost(URL url, Connection con) {
	    try {
	        String host = url.getHost();
	        if (host2cookies.containsKey(host)) {
	            HashMap<String, String> cookies = host2cookies.get(host);
	            for (Entry<String, String> cookie : cookies.entrySet()) {
	                con.cookie(cookie.getKey(), cookie.getValue());
	            }
	        }
	    } catch (Throwable t) {
	        // MTMT move to log
	        System.err.println(t.toString()+":: Error loading cookies to: " + url);
	    }
	}

	private static void storeCookiesByHost(URL url, Connection con) {
	        try {
	            String host = url.getHost();
	            HashMap<String, String> cookies = host2cookies.get(host);
	            if (cookies == null) {
	                cookies = new HashMap<String, String>();
	                host2cookies.put(host, cookies);
	            }
	            cookies.putAll(con.response().cookies());
	        } catch (Throwable t) {
	            // MTMT move to log
	            System.err.println(t.toString()+":: Error saving cookies from: " + url);
	        }    
	}   
}

