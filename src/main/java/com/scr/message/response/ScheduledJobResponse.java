package com.scr.message.response;

import java.util.List;

import com.scr.model.Repository;
import com.scr.model.TimeInterval;

import lombok.Data;

public @Data class ScheduledJobResponse extends ResponseStatus{
	
	private List<Repository> repositoryList;
	private List<com.scr.model.JobType> jobTypeList;
	private List<TimeInterval> timeIntervalList;
	
	private int JobId;
	private int ToatalRecords;
	private int ProcessedRecords;
	private int UnprocessedRecords;
	private String JobType;
	private String FilePath;
	private String JobStatus;
	private String ProcessedDate;
	private String StartTime;
	private String EndTime;
	private String CreatedBy;
	private String ModifiedBy;
	private String CreatedDate;
	private String ModifiedDate;
	public List<Repository> getRepositoryList() {
		return repositoryList;
	}
	public void setRepositoryList(List<Repository> repositoryList) {
		this.repositoryList = repositoryList;
	}
	public List<com.scr.model.JobType> getJobTypeList() {
		return jobTypeList;
	}
	public void setJobTypeList(List<com.scr.model.JobType> jobTypeList) {
		this.jobTypeList = jobTypeList;
	}
	public List<TimeInterval> getTimeIntervalList() {
		return timeIntervalList;
	}
	public void setTimeIntervalList(List<TimeInterval> timeIntervalList) {
		this.timeIntervalList = timeIntervalList;
	}
	public int getJobId() {
		return JobId;
	}
	public void setJobId(int jobId) {
		JobId = jobId;
	}
	public int getToatalRecords() {
		return ToatalRecords;
	}
	public void setToatalRecords(int toatalRecords) {
		ToatalRecords = toatalRecords;
	}
	public int getProcessedRecords() {
		return ProcessedRecords;
	}
	public void setProcessedRecords(int processedRecords) {
		ProcessedRecords = processedRecords;
	}
	public int getUnprocessedRecords() {
		return UnprocessedRecords;
	}
	public void setUnprocessedRecords(int unprocessedRecords) {
		UnprocessedRecords = unprocessedRecords;
	}
	public String getJobType() {
		return JobType;
	}
	public void setJobType(String jobType) {
		JobType = jobType;
	}
	public String getFilePath() {
		return FilePath;
	}
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
	public String getJobStatus() {
		return JobStatus;
	}
	public void setJobStatus(String jobStatus) {
		JobStatus = jobStatus;
	}
	public String getProcessedDate() {
		return ProcessedDate;
	}
	public void setProcessedDate(String processedDate) {
		ProcessedDate = processedDate;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public String getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public String getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}
	public String getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}	
	
	
}
