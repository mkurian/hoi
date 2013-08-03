package com.intuit.txtweb.hoi.platform.util;
/**
 * 
 * @author mkurian
 *
 */
public class ErrorMapper {
	public static String getErrorMessage(Error type, String group){
		String errorMessage = null;
		switch(type){
		case CREATE_GROUP_FAILED: errorMessage = String.format(MessageUtil.GROUP_CREATE_FAIL_MESSAGE, group ); break;
		case REMOVE_GROUP_FAILED: errorMessage = String.format(MessageUtil.GROUP_REMOVE_FAIL_MESSAGE, group ); break;
		case REGISTER_MEMBER_FAILED: errorMessage = String.format(MessageUtil.MEMBER_REGISTER_INCORRECT_GROUP_MESSAGE, group ); break;
		case UNREGISTER_MEMBER_FAILED: errorMessage = String.format(MessageUtil.MEMBER_UNREGISTER_INCORRECT_GROUP_MESSAGE, group ); break;
		case SEND_FAILED: errorMessage = String.format(MessageUtil.MESSAGE_SEND_FAIL_MESSAGE, group ); break;
		case MAKEADMIN_FAILED: errorMessage = String.format(MessageUtil.MAKEADMIN_FAIL_MESSAGE, group ); break;
		case MISSING_TEXT: 
		case MISSING_GROUP: 
		case MISSING_OPERATOR: 
		case GENERAL_ERROR:
		default:
				errorMessage = MessageUtil.DEFAULT_MESSAGE ;
		}
		return errorMessage;
	}
}
