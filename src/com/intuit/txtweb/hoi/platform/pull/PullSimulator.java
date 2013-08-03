package com.intuit.txtweb.hoi.platform.pull;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.HoiServlet;


public class PullSimulator {
	private static Logger logger = Logger.getLogger(PullSimulator.class.getName());
	

	public static boolean sendPullMessage(String message, String mobileHash) {
		boolean result = false;
		String head = "<html>" + "<head>"
				+ "<meta name=\"txtweb-appkey\" content=\"" + HoiServlet.appKey
				+ "\">" + "</head>" + "<body>";

		String tail = "</body></html>";

		String htmlMessage = head + message + tail;

		try {
			String urlParams = "txtweb-message="
					+ URLEncoder.encode(htmlMessage, "UTF-8")
					+ "&txtweb-mobile=" + mobileHash + "&txtweb-pubkey="
					+ HoiServlet.pubKey;

			logger.log(Level.INFO, urlParams);
			URL url = new URL("http://ec2-54-251-70-140.ap-southeast-1.compute.amazonaws.com:8080/hoi/HoiServlet?" + urlParams);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);

			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConn.setRequestMethod("POST");

			// open up the output stream of the connection
			DataOutputStream output = new DataOutputStream(
					urlConn.getOutputStream());

			// write out the data
			// output.writeBytes( urlParams );
			output.close();
			logger.log(
					Level.INFO,
					urlConn.getResponseCode() + " "
							+ urlConn.getResponseMessage());

			// get ready to read the response
			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			String line = null;
			StringBuffer pushResponse = new StringBuffer();
			while ((line = br.readLine()) != null) {
				pushResponse.append(line);
			}
			br.close();

			logger.log(Level.INFO,
					mobileHash + " - Response: " + pushResponse.toString());
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
