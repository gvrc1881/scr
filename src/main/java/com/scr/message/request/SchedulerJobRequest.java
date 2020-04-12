/**
 * 
 */
package com.scr.message.request;

import java.sql.Timestamp;
import com.scr.model.JobStatus;
import lombok.Data;

/**
 * @author vt1056
 *
 */
public @Data class SchedulerJobRequest {
	private Timestamp createdDate;
	private Integer jobId;
	private int isActive;
	private Timestamp modifiedDate;	
	private Integer createdBy;
	private JobStatus jobStatus;
	private Integer jobTypeId;
	private Integer modifiedBy;
	private Integer repositoryId;
	private Integer timeIntervalId;
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public JobStatus getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}
	public Integer getJobTypeId() {
		return jobTypeId;
	}
	public void setJobTypeId(Integer jobTypeId) {
		this.jobTypeId = jobTypeId;
	}
	public Integer getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(Integer repositoryId) {
		this.repositoryId = repositoryId;
	}
	public Integer getTimeIntervalId() {
		return timeIntervalId;
	}
	public void setTimeIntervalId(Integer timeIntervalId) {
		this.timeIntervalId = timeIntervalId;
	}
	
}
