package com.intuit.txtweb.hoi.platform.requesthandler;

import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
import com.intuit.txtweb.hoi.platform.util.Operation;
/**
 * 
 * @author mkurian
 *
 */
public interface HoiHandler {
	public String handleMessage(String groupName, String userName, String mobileHash, Operation op, String text) throws InvalidOperationException;
}
