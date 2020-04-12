package com.scr.message.response;

public class ScheduleSettingResponse {
	private int jobId;
	private int toatalRecords;
	private int processed;
	private int unprocessed;
	private String csvFilePath;
	

	private int createdBy;
	private int modifiedBy;
	private String createdDate;
	private String modifiedDate;
	
	
	private String jobTypeName;
	private String respositorySourceName;
	private String repositoryTypeName;
	private String startDate;
	private String endDate;
	private String timeInterval;
	private String jobStatusName;
	
	private int jobTypeId;
	private int repositorySourceId;
	private int repositoryTypeId;
	private int timeIntervalId;
	
	

	public ScheduleSettingResponse() {
		
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getToatalRecords() {
		return toatalRecords;
	}

	public void setToatalRecords(int toatalRecords) {
		this.toatalRecords = toatalRecords;
	}

	public int getProcessed() {
		return processed;
	}

	public void setProcessed(int processed) {
		this.processed = processed;
	}

	public int getUnprocessed() {
		return unprocessed;
	}

	public void setUnprocessed(int unprocessed) {
		this.unprocessed = unprocessed;
	}

	public String getCsvFilePath() {
		return csvFilePath;
	}

	public void setCsvFilePath(String csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getJobTypeName() {
		return jobTypeName;
	}

	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}

	public String getRespositorySourceName() {
		return respositorySourceName;
	}

	public void setRespositorySourceName(String respositorySourceName) {
		this.respositorySourceName = respositorySourceName;
	}

	public String getRepositoryTypeName() {
		return repositoryTypeName;
	}

	public void setRepositoryTypeName(String repositoryTypeName) {
		this.repositoryTypeName = repositoryTypeName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getJobStatusName() {
		return jobStatusName;
	}

	public void setJobStatusName(String jobStatusName) {
		this.jobStatusName = jobStatusName;
	}

	public int getJobTypeId() {
		return jobTypeId;
	}

	public void setJobTypeId(int jobTypeId) {
		this.jobTypeId = jobTypeId;
	}

	public int getRepositorySourceId() {
		return repositorySourceId;
	}

	public void setRepositorySourceId(int repositorySourceId) {
		this.repositorySourceId = repositorySourceId;
	}

	public int getRepositoryTypeId() {
		return repositoryTypeId;
	}

	public void setRepositoryTypeId(int repositoryTypeId) {
		this.repositoryTypeId = repositoryTypeId;
	}

	public int getTimeIntervalId() {
		return timeIntervalId;
	}

	public void setTimeIntervalId(int timeIntervalId) {
		this.timeIntervalId = timeIntervalId;
	}

}
