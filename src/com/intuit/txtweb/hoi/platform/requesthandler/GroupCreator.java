package com.intuit.txtweb.hoi.platform.requesthandler;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.db.PersistenceHelper;
import com.intuit.txtweb.hoi.platform.util.MessageUtil;
import com.intuit.txtweb.hoi.platform.util.Operation;
/**
 * 
 * @author mkurian
 *
 */
public class GroupCreator implements HoiHandler{
	private Logger logger = Logger.getLogger(GroupCreator.class.getName());
	
	@Override
	public String handleMessage(String groupName, String userName, String mobileHash, Operation op, String text) {
		try {
			PersistenceHelper.createGroupAddAdmin(groupName, userName, mobileHash);
				//group successfully created, added the user as a member and admin of the group
			} catch (SQLException ex) {
				if(ex.getMessage().contains("Duplicate entry")){
					return MessageUtil.formatMessage(MessageUtil.GROUP_CREATE_FAIL_EXISTS_MESSAGE, groupName);
				}
				return MessageUtil.formatMessage(MessageUtil.GROUP_CREATE_OTHER_MESSAGE, groupName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return MessageUtil.formatMessage(MessageUtil.GROUP_CREATE_OTHER_MESSAGE, groupName);
			}
			
		logger.log(Level.INFO, "Group created");
		return MessageUtil.formatMessage(MessageUtil.GROUP_CREATE_SUCCESS_MESSAGE, groupName);
	}

}
