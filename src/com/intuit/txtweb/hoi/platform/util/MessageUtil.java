package com.intuit.txtweb.hoi.platform.util;

import java.text.MessageFormat;

/**
 * 
 * @author mkurian
 *
 */
public class MessageUtil {

	public static final String DEFAULT_MESSAGE = "To create a new group ABC, send @hoi ABC CREATE<br/>To subscribe to group ABC, send @hoi ABC REGISTER<br/>";	
	public static final String GROUP_CREATE_SUCCESS_MESSAGE = "{0} created<br/>Group members should send: @hoi {0} REGISTER<br/>Send yourmessage to group: @hoi {0} SEND yourmessage" ;
	public static final String GROUP_CREATE_NAME_MESSAGE = "Sorry, {0} is already in use.<br/>Choose another name." ;
	public static final String GROUP_CREATE_OTHER_MESSAGE = "Sorry, {0} could not be created.<br/>Please try later." ;
	public static final String GROUP_CREATE_FAIL_MESSAGE = "Sorry, {0} could not be created.<br/>To create a new group ABC, send @hoi ABC CREATE<br/>" ;
	public static final String GROUP_CREATE_FAIL_EXISTS_MESSAGE = "Sorry, {0} is not available. Please choose another name</br>To create a new group ABC, send @hoi ABC CREATE<br/>" ;
	public static final String GROUP_REMOVE_FAIL_MESSAGE = "Sorry, {0} could not be removed.<br/>To remove group ABC, send @hoi ABC REMOVE<br/>" ;
	public static final String MEMBER_REGISTER_SUCCESS_MESSAGE = "Thank you for registering, you will receive messages from group {0}.<br/>" ;
	public static final String MEMBER_REGISTER_INCORRECT_GROUP_MESSAGE = "Sorry, could not register to group {0}.<br/> Please use correct group name." ;
	public static final String MEMBER_REGISTER_SYSTEM_ERROR_MESSAGE = "Sorry, could not register to group {0}.<br/> Please try again later." ;
	public static final String MEMBER_UNREGISTER_INCORRECT_GROUP_MESSAGE = "Sorry, could not un-register from group {0}.<br/> Please use correct group name." ;
	public static final String MEMBER_UNREGISTER_SYSTEM_ERROR_MESSAGE = "Sorry, could not un-register from group {0}.<br/> Please try again later." ;
	public static final String MEMBER_UNREGISTER_SUCCESS_MESSAGE = "You will not receive messages from group {0}. <br/>To subscribe to {0} again, send @hoi {0} REGISTER<br/>" ;
	public static final String MESSAGE_SEND_FAIL_MESSAGE = "Sorry, could not send your message to group {0}.<br/> Use the format: @hoi {0} SEND yourmessage<br/>" ;
	public static final String MESSAGE_SEND_SYSTEM_ERROR_MESSAGE = "Sorry, could not send your message to group {0}.<br/> Please try again later." ;
	public static final String MESSAGE_SEND_FAIL_PERMISSION_MESSAGE = "Sorry, you don't have permission to send message to group {0}.<br/> To create a new group ABC, send @hoi ABC CREATE" ;
	public static final String MAKEADMIN_FAIL_MESSAGE = "Sorry, could not make admin.<br/> Use the format: @hoi {0} MAKEADMIN username" ;
	public static final String READ_MESSAGE_FAILURE = "Sorry, could get the latest message in group {0}." ;
	
	
	public static String formatMessage(String message, Object... args) {
		MessageFormat mf = new MessageFormat(message);
		return mf.format(args);
	}
}
