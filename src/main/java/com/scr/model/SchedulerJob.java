package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;


/**
 * The persistent class for the schedulejobs database table.
 * 
 */
@Entity
@Table(name="schedulerjobs")
@NamedQuery(name="SchedulerJob.findAll", query="SELECT s FROM SchedulerJob s")
public class SchedulerJob implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int jobId;

	private Timestamp createdDate;

	private int isActive;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to Jobdatacsv
	/*
	 * @JsonIgnore
	 * 
	 * @OneToMany(mappedBy="schedulerJob") private List<Jobdatacsv> jobdatacsvs;
	 */

	//bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="CreatedBy")
	private User createdBy;

	//bi-directional many-to-one association to Jobstatus
	//@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="JobStatusId")
	private JobStatus jobStatus;

	//bi-directional many-to-one association to Jobtype
	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name="JobTypeId")
	private JobType jobType;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="ModifiedBy")
	private User modifiedBy;	

	//bi-directional many-to-one association to Repositorytype
	//@JsonIgnore
	@ManyToOne
	@JoinColumn(name="repositoryId")
	private Repository repository;

	@Column(name="last_run_timestamp")
	private Timestamp lastRunTimestamp;
	//bi-directional many-to-one association to Timeinterval
	
	
	@ManyToOne
	@JoinColumn(name="TimeIntervalId")
	private TimeInterval timeInterval;	
	
	public SchedulerJob() {
	}

	public int getJobId() {
		return this.jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/*
	 * public List<Jobdatacsv> getJobdatacsvs() { return this.jobdatacsvs; }
	 * 
	 * public void setJobdatacsvs(List<Jobdatacsv> jobdatacsvs) { this.jobdatacsvs =
	 * jobdatacsvs; }
	

	public Jobdatacsv addJobdatacsv(Jobdatacsv jobdatacsv) {
		getJobdatacsvs().add(jobdatacsv);
		jobdatacsv.setSchedulerJob(this);

		return jobdatacsv;
	}

	public Jobdatacsv removeJobdatacsv(Jobdatacsv jobdatacsv) {
		getJobdatacsvs().remove(jobdatacsv);
		jobdatacsv.setSchedulerJob(null);

		return jobdatacsv;
	}

	 */
	

	public JobStatus getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	

	public JobType getJobType() {
		return jobType;
	}

	public void setJobType(JobType jobType) {
		this.jobType = jobType;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Repository getRepository() {
		return this.repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public TimeInterval getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(TimeInterval timeInterval) {
		this.timeInterval = timeInterval;
	}	
	
	
	public Timestamp getLastRunTimestamp() {
		return lastRunTimestamp;
	}

	public void setLastRunTimestamp(Timestamp lastRunTimestamp) {
		this.lastRunTimestamp = lastRunTimestamp;
	}

	@Override
	public String toString() {
		return "SchedulerJob [jobId=" + jobId + ", createdDate=" + createdDate + ", isActive=" + isActive
				+ ", modifiedDate=" + modifiedDate + ", createdBy=" + createdBy
				+ ", jobStatus=" + jobStatus + ", jobType=" + jobType + ", modifiedBy=" + modifiedBy + ", repository="
				+ repository + ", timeInterval=" + timeInterval + "]";
	}

	

}