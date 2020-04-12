/**
 * 
 */
package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author vt1056
 *
 */
@Entity
@Table(name="scheduler_jobs_tracking")
@NamedQuery(name="SchedulerJobsTracking.findAll", query="SELECT s FROM SchedulerJobsTracking s")
public class SchedulerJobsTracking implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer trackingId;
	
	@ManyToOne
	@JoinColumn(name="jobId")
	private SchedulerJob jobId;
	
	@ManyToOne
	@JoinColumn(name="repositoryId")
	private Repository repository;
	
	//@ManyToOne
	@Column(name="jobTypeId")
	private SchedulerJob jobType;
	
	@ManyToOne
	@JoinColumn(name="TimeIntervalId")
	private TimeInterval timeInterval;	
	
	@Column(name="end_time")
	private String endTime;
	
	@Column(name="start_time")
	private String startTime;
	
	@Column(name="run_type")
	private String runType;
	
	@Column(name="job_status")
	private String jobStatus;
	
	@Column(name="processed_date")
	private Timestamp processedDate;
	
	@Column(name="process_status")
	private String processStatus;	
	
	@Column(name="run_by")
	private String runBy;

	public Integer getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}

	public SchedulerJob getJobId() {
		return jobId;
	}

	public void setJobId(SchedulerJob jobId) {
		this.jobId = jobId;
	}


	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	

	public SchedulerJob getJobType() {
		return jobType;
	}

	public void setJobType(SchedulerJob jobType) {
		this.jobType = jobType;
	}

	public TimeInterval getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(TimeInterval timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getRunType() {
		return runType;
	}

	public void setRunType(String runType) {
		this.runType = runType;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getRunBy() {
		return runBy;
	}

	public void setRunBy(String runBy) {
		this.runBy = runBy;
	}

	public Timestamp getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Timestamp processedDate) {
		this.processedDate = processedDate;
	}

	@Override
	public String toString() {
		return "SchedulerJobsTracking [trackingId=" + trackingId + ", jobId=" + jobId + ", repository=" + repository
				+ ", jobType=" + jobType + ", timeInterval=" + timeInterval + ", endTime=" + endTime + ", startTime="
				+ startTime + ", runType=" + runType + ", jobStatus=" + jobStatus + ", processedDate=" + processedDate
				+ ", processStatus=" + processStatus + ", runBy=" + runBy + "]";
	}
	
	
}
