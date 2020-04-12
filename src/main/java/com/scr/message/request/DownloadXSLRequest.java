/**
 * 
 */
package com.scr.message.request;

import java.sql.Date;

import lombok.Data;

/**
 * @author vt1056
 *
 */
public @Data class DownloadXSLRequest {
	private Integer trackingId;
	private Integer runTypeId;	
	private Integer runBy;
	private Date runDate;
	public Integer getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}
	public Integer getRunTypeId() {
		return runTypeId;
	}
	public void setRunTypeId(Integer runTypeId) {
		this.runTypeId = runTypeId;
	}
	public Integer getRunBy() {
		return runBy;
	}
	public void setRunBy(Integer runBy) {
		this.runBy = runBy;
	}
	public Date getRunDate() {
		return runDate;
	}
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}	
	
}
