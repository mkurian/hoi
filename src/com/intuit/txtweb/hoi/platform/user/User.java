package com.intuit.txtweb.hoi.platform.user;
/***
 * data and operations associated with a User
 * @author mkurian
 *
 */
public class User {
	private String mobileHash;

	public String getMobileHash() {
		return mobileHash;
	}

	public void setMobileHash(String mobileHash) {
		this.mobileHash = mobileHash;
	}
	
	public User(String mobileHash){
		this.mobileHash = mobileHash;
	}

}
