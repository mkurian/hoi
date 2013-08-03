package com.intuit.txtweb.hoi.platform.requesthandler;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.db.PersistenceHelper;
import com.intuit.txtweb.hoi.platform.pull.PullSimulator;
import com.intuit.txtweb.hoi.platform.push.PushUtil;
import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
import com.intuit.txtweb.hoi.platform.util.MessageUtil;
import com.intuit.txtweb.hoi.platform.util.Operation;
/**
 * 
 * @author mkurian
 *
 */
public class Sender implements HoiHandler{

	private static Logger logger = Logger.getLogger(Sender.class.getName());
	@Override
	public String handleMessage(String groupName, String userName, String mobileHash, Operation op, String text) throws InvalidOperationException{
		
		Collection<String> registeredUsers = null;
		//get the list of mobileHash to send messages to.
		try {
			if (PersistenceHelper.canUserSendMessages(groupName, userName, mobileHash)) {
				registeredUsers = PersistenceHelper.getUsersOfGroup(groupName, userName, mobileHash);
			}
			else{
				return MessageUtil.formatMessage(MessageUtil.MESSAGE_SEND_FAIL_PERMISSION_MESSAGE, groupName);
			}
		} catch (Exception ex) {			
			return MessageUtil.formatMessage(MessageUtil.MESSAGE_SEND_FAIL_MESSAGE, groupName);
		}

		//persist the message to DB
		try {
			PersistenceHelper.logMessageToGroup(groupName, text, mobileHash);
		} catch (Exception ex) {
			ex.printStackTrace();			
		}
		
		int successCounter = 0;
		int failureCounter = 0;
//		for (String user : registeredUsers) {
//			int result = PushUtil.sendPushMessage(text, user);
//			if (result == 0) {
//				successCounter ++;
//			}
//			else if (result == -1) {
//				// try again
//				int tryResult = PushUtil.sendPushMessage(text, user);
//				successCounter ++;
//				logger.log(Level.INFO, "Retry result: " + tryResult);
//			}
//			else {
//				failureCounter ++;
//				logger.log(Level.SEVERE, "Error in sending message. ErrorCode: " + result);				
//			}
//		}
//				
		for (String user : registeredUsers) {
			if(PullSimulator.sendPullMessage(groupName+" GET", user)) successCounter ++ ; else failureCounter ++;
		}
		
		String response = "Regd. users="+ registeredUsers.size() +"<br/> No. of messages sent="+ successCounter + "<br/> No. of messages failed=" + failureCounter +"<br/>";		
		logger.log(Level.INFO, "Regd. users="+ registeredUsers.size() +" Successfully sent messages="+ successCounter + " Failed to send messages=" + failureCounter);
		
		return response;
	}
	
}
