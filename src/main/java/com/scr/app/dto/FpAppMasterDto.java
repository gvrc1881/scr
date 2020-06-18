package com.scr.app.dto;

public class FpAppMasterDto {

	public FpAppMasterDto() {
		// TODO Auto-generated constructor stub
	}

	private String previousTimestamp;
	private String currentTimestamp;
	private String appName;
	private String imeiNo;
	private boolean imeiAuth;
	private String message;
	private String phoneNum;
	
	// server to app
	
	private ResponseUserLoginDto createdResponseUserLoginDto;
	private ResponseUserLoginDto updatedResponseUserLoginDto;
	
	public ResponseUserLoginDto getCreatedResponseUserLoginDto() {
		return createdResponseUserLoginDto;
	}

	public void setCreatedResponseUserLoginDto(
			ResponseUserLoginDto createdResponseUserLoginDto) {
		this.createdResponseUserLoginDto = createdResponseUserLoginDto;
	}

	public ResponseUserLoginDto getUpdatedResponseUserLoginDto() {
		return updatedResponseUserLoginDto;
	}

	public void setUpdatedResponseUserLoginDto(
			ResponseUserLoginDto updatedResponseUserLoginDto) {
		this.updatedResponseUserLoginDto = updatedResponseUserLoginDto;
	}

	
	// GETTERS and SETTERS for SERVER to APP

	public String getPreviousTimestamp() {
		return previousTimestamp;
	}

	public void setPreviousTimestamp(String previousTimestamp) {
		this.previousTimestamp = previousTimestamp;
	}

	public String getCurrentTimestamp() {
		return currentTimestamp;
	}

	public void setCurrentTimestamp(String currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public boolean getImeiAuth() {
		return imeiAuth;
	}

	public void setImeiAuth(boolean imeiAuth) {
		this.imeiAuth = imeiAuth;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}


}
