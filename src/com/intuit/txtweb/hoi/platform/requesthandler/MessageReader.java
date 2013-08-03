package com.intuit.txtweb.hoi.platform.requesthandler;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.db.PersistenceHelper;
import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
import com.intuit.txtweb.hoi.platform.util.MessageUtil;
import com.intuit.txtweb.hoi.platform.util.Operation;

public class MessageReader implements HoiHandler{

	private Logger logger = Logger.getLogger(MessageReader.class.getName());
	
	@Override
	public String handleMessage(String groupName, String userName,
			String mobileHash, Operation op, String text)
			throws InvalidOperationException {
		String message = null;
		try {
			message = PersistenceHelper.readMessageFromGroup(groupName);
				//read latest message from group
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, ex.getMessage());
				return MessageUtil.formatMessage(MessageUtil.READ_MESSAGE_FAILURE, groupName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return MessageUtil.formatMessage(MessageUtil.READ_MESSAGE_FAILURE, groupName);
			}			
		logger.log(Level.INFO, "Message fetched, to be sent");
		return message;
	}

}
