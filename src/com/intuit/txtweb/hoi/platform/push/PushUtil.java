package com.intuit.txtweb.hoi.platform.push;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.intuit.txtweb.hoi.platform.HoiServlet;

public class PushUtil {
	
	private static Logger logger = Logger.getLogger(PushUtil.class.getName());
	

	public static int sendPushMessage(String message, String mobileHash) {

		String head = "<html>" + "<head>"
				+ "<meta name=\"txtweb-appkey\" content=\"" + HoiServlet.appKey + "\">"
				+ "</head>" + "<body>";

		String tail = "</body></html>";

		String htmlMessage = head + message + tail;

		try {
			String urlParams = "txtweb-message=" + URLEncoder.encode(htmlMessage, "UTF-8")
					+ "&txtweb-mobile="	+ mobileHash
					+ "&txtweb-pubkey=" + HoiServlet.pubKey;

//			System.out.println(urlParams);
			URL url = new URL("http://api.txtweb.com/v1/push?" + urlParams);
			HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);

			urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			urlConn.setRequestMethod("POST");			

			// open up the output stream of the connection 
			DataOutputStream output = new DataOutputStream( urlConn.getOutputStream() ); 

			// write out the data 
//			output.writeBytes( urlParams ); 
			output.close();
			logger.log(Level.INFO, urlConn.getResponseCode() +" " + urlConn.getResponseMessage() );
			
			// get ready to read the response 
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
			String line = null;
			StringBuffer pushResponse = new StringBuffer();
			while ((line = br.readLine()) != null) {				
				pushResponse.append(line);
			}
			br.close();
			
			logger.log(Level.INFO, mobileHash+ " - Response: " + pushResponse.toString() );
			
//			System.out.println(pushResponse);
			int startIndex = pushResponse.indexOf("<code>") + 6;
			int endIndex = pushResponse.indexOf("</code>");
			String code = pushResponse.substring(startIndex, endIndex);
			return Integer.parseInt(code);
//			// Using DOM parser to parse the XML response from the API
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			Document doc = db.parse(pushResponse.toString());
//			NodeList statusNodes = doc.getElementsByTagName("status");
//			String code = "-1";
//			for (int index = 0; index < statusNodes.getLength(); index++) {
//				Node childNode = statusNodes.item(index);
//				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
//					Element element = (Element) childNode;
//					code = getTagValue("code", element);
//					return Integer.parseInt(code);
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -999; // APPLICATION ERROR
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nodeList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node node = nodeList.item(0);
		return node.getNodeValue();
	}

}
