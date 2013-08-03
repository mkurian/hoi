package com.intuit.txtweb.hoi.platform;

import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.bible.BibleFetcher;


/**
 * app that processes request and creates relevant response
 * 
 * @author mkurian
 *
 */
public class BibleApp {

	private static Logger logger = Logger.getLogger(BibleApp.class.getName());

	public static String processRequest(String text, String protocol, String mobileHash, String id, String verifyId){
		String errorMessage = "Blessed are the poor in spirit, for theirs is the kingdom of heaven.";
		String response = errorMessage;
					
		StringBuilder builder = new StringBuilder();
		String quote = BibleFetcher.getVerse("http://www.sandersweb.net/bible/verse.php");
		builder.append(quote);
		builder.append("<p>Source: SandersWeb.net");
		response = builder.toString();
		return response;
	}
}
