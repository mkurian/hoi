package com.intuit.txtweb.hoi.platform.requesthandler;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.db.PersistenceHelper;
import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
import com.intuit.txtweb.hoi.platform.util.Operation;
import com.intuit.txtweb.hoi.platform.util.MessageUtil;
/**
 * 
 * @author mkurian
 *
 */
public class UnRegistrar implements HoiHandler{

	@Override
	public String handleMessage(String groupName, String userName,
			String mobileHash, Operation op, String text)
			throws InvalidOperationException {
		Logger logger = Logger.getLogger(UnRegistrar.class.getName());
		
		//Unregister user from the group in the db and send appropriate response
		try {
			PersistenceHelper.unregisterUserFromGroup(groupName, userName, mobileHash);				
			} catch (Exception ex) {
				return MessageUtil.formatMessage(MessageUtil.MEMBER_UNREGISTER_SYSTEM_ERROR_MESSAGE, groupName);
			}
		logger.log(Level.INFO, "User unregistered");
		return MessageUtil.formatMessage(MessageUtil.MEMBER_UNREGISTER_SUCCESS_MESSAGE, groupName);
	}

}
