package com.intuit.txtweb.hoi.platform.requesthandler;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.db.PersistenceHelper;
import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
import com.intuit.txtweb.hoi.platform.util.MessageUtil;
import com.intuit.txtweb.hoi.platform.util.Operation;
/**
 * 
 * @author mkurian
 *
 */
public class Registrar implements HoiHandler{

	private Logger logger = Logger.getLogger(Registrar.class.getName());
	
	@Override
	public String handleMessage(String groupName, String userName, String mobileHash, Operation op, String text) throws InvalidOperationException{
		//Add user to the corresponding group in the db, persist and send appropriate response
		try {
			PersistenceHelper.registerUserToGroup(groupName, userName,
					mobileHash);
		} catch (Exception ex) {
			return MessageUtil.formatMessage(
					MessageUtil.MEMBER_REGISTER_SYSTEM_ERROR_MESSAGE,
					groupName);
		}
		logger.log(Level.INFO, "User registered");
		return MessageUtil.formatMessage(MessageUtil.MEMBER_REGISTER_SUCCESS_MESSAGE, groupName);
	}

}
