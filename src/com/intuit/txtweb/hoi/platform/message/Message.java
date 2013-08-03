package com.intuit.txtweb.hoi.platform.message;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.intuit.txtweb.hoi.platform.util.InvalidOperationException;
import com.intuit.txtweb.hoi.platform.util.MessageParser;
import com.intuit.txtweb.hoi.platform.util.Operation;
/**
 * data and operations associated with a Message
 * @author mkurian
 *
 */
public class Message {

	String group;
	Operation operation;
	String text;
	String errorMessage;
	
	private Logger logger = Logger.getLogger(Message.class.getName());
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Message(String message) throws InvalidOperationException, Exception{
		try {			
			this.operation = MessageParser.getOperation(message);
			logger.log( Level.INFO,"Operation: " + operation.name());
			this.group = MessageParser.getGroupName(message);
			logger.log( Level.INFO,"Group: " + group);
			if(operation.equals(Operation.SEND)){
				this.text = MessageParser.getText(message);
				logger.log( Level.INFO,"Message: " + text);
			}
		} catch (InvalidOperationException ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			throw ex;
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw e;
		}
	}
}
