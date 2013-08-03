package com.intuit.txtweb.hoi.platform.bible;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

/**
 * ScreenScraper for SandersWeb
 * 
 * @author mkurian
 * 
 */
public class BibleFetcher {

	private static Logger logger = Logger.getLogger(BibleFetcher.class
			.getName());

	public static String getVerse(String targetURLString) {
		try {
			URL targetURL = new URL(targetURLString);
			URLConnection targetConnection = targetURL.openConnection();
			targetConnection.setDoOutput(true);

			StringBuilder bldr = new StringBuilder();
			// Post to output
			BufferedReader in = new BufferedReader(new InputStreamReader(
					targetConnection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				bldr.append(inputLine);
			}
			in.close();

			Document doc = Jsoup.parse(bldr.toString());
			StringBuilder verse = new StringBuilder();
			for (Element e : doc.getElementsByClass("esv-text")) {
				for (Element c : e.getElementsByTag("p")) {
					for (Node n : c.childNodes()) {
						String name = n.nodeName();
						if (name.equals("span")) {
							for (Attribute attr : n.attributes()) {
//								if (attr.getValue().equals("small-caps") || attr.getValue().equals("woc")) {
								if(!attr.getValue().equals("footnote")){
									for(Node child: n.childNodes()){
										if(child instanceof TextNode){
										TextNode txtNode = (TextNode) child;
											if(!verse.toString().contains(txtNode.getWholeText())){
												verse.append(txtNode.getWholeText());
											}
										}
									}
								}
							}
						} else {
							if(!verse.toString().contains(n.toString())){
									verse.append(n.toString());
							}
						}
					}
				}
			}

			for (Element e : doc.getElementsByTag("h2")){
				TextNode quote = (TextNode) e.childNodes().get(0);
				verse.append("<br/>" + quote.getWholeText());	
			}						
			
			String finalVerse = verse.toString();
			finalVerse.replaceAll("\\?", " ");
			finalVerse.replaceAll("\"", " ");
			finalVerse.replaceAll("&#\\d*;", " ");
			logger.log(Level.INFO, finalVerse);
			return (finalVerse);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			return null;
		}
	}

}