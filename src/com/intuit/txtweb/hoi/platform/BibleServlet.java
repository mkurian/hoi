package com.intuit.txtweb.hoi.platform;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Entry point for Bible application
 * @author mkurian
 *
 */
public class BibleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 218813212964425810L;
	public static final String appKey = "1f9b0c90-378b-4eda-92ab-a96856fe64e9";
	public static final String pubKey = "8d8d7b68-a80f-40e6-a4e3-83929ab965a5";
	
	private static Logger logger = Logger.getLogger(BibleServlet.class.getName());
	/*
	 * There are 5 parameters that the platform sends to an application viz-
	 * 
	 * txtweb-mobile: The mobile number of the end user in hash format
	 * 
	 * txtweb-message: Message sent by the end user
	 * 
	 * txtweb-id: Unique identifier for the message
	 * 
	 * txtweb-verifyid: The id used to verify the source. Check API
	 * documentation for more details
	 * 
	 * txtweb-protocol: The protocol through which the message was received
	 * 
	 * SMS: 1000 USSD: 1001 WEB: 200x EMULATOR: 2100 INSTANT MESSENGER: 220x
	 */

	@Override
	public void doGet(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws ServletException {
		processRequest(httpRequest, httpResponse);
	}

	@Override
	public void doPost(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) throws ServletException {
		processRequest(httpRequest, httpResponse);
	}

	private static void processRequest(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		String text = httpRequest.getParameter("txtweb-message");
		String protocol = httpRequest.getParameter("txtweb-protocol");
		String mobileHash = httpRequest.getParameter("txtweb-mobile");
		String id = httpRequest.getParameter("txtweb-id");
		String verifyid = httpRequest.getParameter("txtweb-verifyid");			
		String response = BibleApp.processRequest(text, protocol, mobileHash, id, verifyid);
		sendResponse(httpResponse, response);
	}


	private static void sendResponse(HttpServletResponse httpResponse,
			String response) {
		String head = "<html>" + "<head>"
				+ "<meta name='txtweb-appkey' content='" + appKey + "'>"
				+ "</head>" + "<body>";

		String tail = "</body></html>";

		try {
			httpResponse.setContentType("text/html");
			PrintWriter out = httpResponse.getWriter();
			out.println(head + response + tail);
		} catch (IOException e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
}