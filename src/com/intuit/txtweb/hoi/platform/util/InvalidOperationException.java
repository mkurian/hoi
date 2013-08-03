package com.intuit.txtweb.hoi.platform.util;
/**
 * 
 * @author mkurian
 *
 */
@SuppressWarnings("serial")
public class InvalidOperationException extends Exception {
	Error type;
	public Error getType() {
		return type;
	}

	String message;
	
	public InvalidOperationException(String message, Error type) {
		super(message);
		this.type = type;
		this.message = message;
	}
	
	public String getMessage(){
		return super.getMessage();
	}
	
	
}
