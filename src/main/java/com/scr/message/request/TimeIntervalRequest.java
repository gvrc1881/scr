package com.scr.message.request;

import java.sql.Timestamp;

import lombok.Data;

public @Data class TimeIntervalRequest {
	private Integer timeIntervalId;	
	private Integer createdBy;	
	private Timestamp createdDate;
	private Timestamp endDate;	
	private Integer isActive;	
	private Integer modifiedBy;	
	private Timestamp modifiedDate;	
	private Timestamp startDate;	
	private String timeInterval;
	public Integer getTimeIntervalId() {
		return timeIntervalId;
	}
	public void setTimeIntervalId(Integer timeIntervalId) {
		this.timeIntervalId = timeIntervalId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public String getTimeInterval() {
		return timeInterval;
	}
	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}
	
	
}