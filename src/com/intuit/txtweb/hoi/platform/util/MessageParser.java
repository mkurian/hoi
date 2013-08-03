package com.intuit.txtweb.hoi.platform.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author mkurian
 *
 */
public class MessageParser {
	
	private static Logger logger = Logger.getLogger(MessageParser.class.getName());
	
	private static List<String> preprocessMessage(String message) throws InvalidOperationException{
		message = message.trim();
		String parts[] = message.split(" ");
		List<String> messageParts = new ArrayList<String>();
		for(String part : parts){
			if(part != null && !part.isEmpty())
				messageParts.add(part);
		}		
		
		return messageParts;
	}
	
	public static String getGroupName(String message) throws InvalidOperationException{		
		List<String> parts = preprocessMessage(message);	
		if(parts.size() < 2){
			logger.log(Level.SEVERE, message +" Parts.Length < 2");
			throw new InvalidOperationException(parts.get(1)  , Error.MISSING_GROUP);
		}
		if(parts.get(0) == null ){
			logger.log(Level.SEVERE, message +" Parts[0] is null");
			throw new InvalidOperationException(parts.get(1)  , Error.MISSING_GROUP);
		}
		else{
			return parts.get(0);
		}			
	}
	
	public static Operation getOperation(String message) throws InvalidOperationException{
		List<String> parts = preprocessMessage(message);
		if(parts.size() < 2){
			logger.log(Level.SEVERE, message +" Parts.Length < 2");
			throw new InvalidOperationException(parts.get(1) , Error.MISSING_OPERATOR);
		}
		if(parts.get(1) == null ){
			logger.log(Level.SEVERE, message +" Parts.Length < 2");
			throw new InvalidOperationException(parts.get(1) , Error.MISSING_OPERATOR );
		}
		else{
			String op = parts.get(1);
			return Operation.valueOf(op.toUpperCase());
		}			
	}

	public static String getText(String message) throws InvalidOperationException{
		List<String> parts = preprocessMessage(message);		
		if(parts.size() < 3){
			logger.log(Level.SEVERE, message +" Parts.Length < 2");
			throw new InvalidOperationException(message , Error.MISSING_TEXT);
		}			
		parts.remove(0);//remove groupName
		parts.remove(0);//remove operator
		//rest is the text to be sent
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for(String part : parts){			
			builder.append(part);
			if(i++ != parts.size()-1)
				builder.append(" ");
		}			
		return builder.toString();		
	}
}
