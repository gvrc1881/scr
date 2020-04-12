/**
 * 
 */
package com.scr.message.response;

import com.scr.model.User;

import lombok.Data;

/**
 * @author venkat
 *
 */
public @Data class ResponseStatus {
	private int code;
	private String message;
	private String transactionId;
	private long timestamp;
	private User user;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
