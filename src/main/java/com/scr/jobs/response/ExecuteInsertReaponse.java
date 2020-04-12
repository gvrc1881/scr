/**
 * 
 */
package com.scr.jobs.response;

import lombok.Data;

/**
 * @author vt1056
 *
 */
public @Data class ExecuteInsertReaponse {
	private Integer records;
	private String status;
	private String message;
	private Integer statusCode;
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
