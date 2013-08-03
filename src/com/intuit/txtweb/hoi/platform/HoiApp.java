package com.intuit.txtweb.hoi.platform;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.message.Message;
import com.intuit.txtweb.hoi.platform.requesthandler.HoiFactory;
import com.intuit.txtweb.hoi.platform.requesthandler.HoiHandler;
import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
import com.intuit.txtweb.hoi.platform.util.MessageUtil;
/**
 * app that processes request and creates relevant response
 * 
 * @author mkurian
 *
 */
public class HoiApp {

	private static Logger logger = Logger.getLogger(HoiApp.class.getName());

	public String processRequest(String text, String protocol, String mobileHash, String id, String verifyId){
		String response = MessageUtil.DEFAULT_MESSAGE;
		if (mobileHash == null)
			mobileHash = "";
		if (text != null && !text.isEmpty()) {
			//log the incoming text
			logger.log(Level.INFO, text);
						
			HoiFactory factory = new HoiFactory();
			Message message = null;
			try {
				message = new Message(text);
				HoiHandler handler = factory.getHandler(message.getOperation());
				response = handler.handleMessage(message.getGroup(), null, mobileHash, 
						message.getOperation(), message.getText());
			} catch (InvalidOperationException e) {
				response = e.getMessage();				
				logger.log(Level.SEVERE, e.getMessage());
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.SEVERE, e.getMessage());
			}
		}
		return response;
	}
}
