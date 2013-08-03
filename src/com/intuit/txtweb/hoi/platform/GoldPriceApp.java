package com.intuit.txtweb.hoi.platform;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.goldPrice.Cache;
import com.intuit.txtweb.hoi.platform.goldPrice.Util;


/**
 * app that processes request and creates relevant response
 * 
 * @author mkurian
 *
 */
public class GoldPriceApp {

	private static Logger logger = Logger.getLogger(GoldPriceApp.class.getName());
	

	static {
		int i =3;
		boolean updated;
		do {
			updated = Cache.upateCache();
			i--;
		}  while (!updated && i > 0);
	}

	public static String processRequest(String text, String protocol, String mobileHash, String id, String verifyId){
		String errorMessage= "Sorry, we are not able to fetch gold price now. Please try again later.";
		String response = errorMessage;
		if (mobileHash == null)
			mobileHash = "";
		logger.log(Level.INFO, "Gold price request at "+ Util.getLocalCalendar().getTime().toString());
		
		StringBuilder builder = new StringBuilder();
		String goldPrice = null;
		String goldPricePerGram = null;
		String goldPricePerGramYday = null;
		
		if(Cache.needToUpdateCache()){
			Cache.upateCache();
		}
			goldPrice = Cache.getTodaysPricePer8Gram();
			goldPricePerGram = Cache.getTodaysPricePerGram();
			goldPricePerGramYday = Cache.getYesterdaysPricePerGram();
		
		if(goldPricePerGram != null)	{
			builder.append( "22 ct Gold Price in Kerala today:");			
		}
		if(goldPricePerGram != null)	{
			builder.append("<p>For 1 gram: Rs. ").append(goldPricePerGram);
		}
		if(goldPricePerGramYday != null){
			Integer yday = Integer.parseInt(goldPricePerGramYday);
			Integer today = Integer.parseInt(goldPricePerGram);
			if(today> yday){
				builder.append(". Rs.").append(today-yday).append(" up from yesterday.");
			}else if(today == yday){
				builder.append(". No change from yesterday");
			}else if(today < yday){
				builder.append(". Rs.").append(yday-today).append(" down from yesterday.");
			}
		}
		if(goldPrice != null){
			builder.append("<p>For 8 gram: Rs. ").append(goldPrice);
		}
		if(goldPricePerGram != null)	{
			builder.append("<p>Source: KeralaGold.com");
			builder.append("<p>Last updated: " + Cache.getLastUpdatedTime());
		}
		response = builder.toString();
		return response;
	}
}
