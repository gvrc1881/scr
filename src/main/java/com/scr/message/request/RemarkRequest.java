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
public @Data class RemarkRequest {
	private Integer jobId;
	private Integer runTypeId;
	private String remark;
	private Integer runBy;
	private Date runDate;
	private String processedDate;
	
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Integer getRunTypeId() {
		return runTypeId;
	}
	public void setRunTypeId(Integer runTypeId) {
		this.runTypeId = runTypeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
