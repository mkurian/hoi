package com.intuit.txtweb.hoi.platform.requesthandler;

import com.intuit.txtweb.hoi.platform.util.Error;
import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
import com.intuit.txtweb.hoi.platform.util.MessageUtil;
import com.intuit.txtweb.hoi.platform.util.Operation;
/**
 * Factory to return the respective handlers for various operations
 * @author mkurian
 *
 */
public class HoiFactory {

	public HoiHandler getHandler(Operation operation) throws InvalidOperationException{
		HoiHandler handler = null;
		switch(operation){
		case CREATE: handler = new GroupCreator(); break;
		case SEND: handler = new Sender(); break;
		case REGISTER: handler = new Registrar(); break;
		case UNREGISTER: handler = new UnRegistrar(); break;
		default: 
			throw new InvalidOperationException(MessageUtil.DEFAULT_MESSAGE, Error.GENERAL_ERROR);
		}
		return handler;
	}
}
