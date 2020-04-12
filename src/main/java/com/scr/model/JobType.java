package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.sql.Timestamp;


/**
 * The persistent class for the jobtypes database table.
 * 
 */
@Entity
@Table(name="jobtypes")
@NamedQuery(name="JobType.findAll", query="SELECT j FROM JobType j")
public class JobType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_type_id", unique = true, nullable = false)
	private Integer jobTypeId;

	private Timestamp createdDate;

	private Integer isActive;

	private String jobTypeName;

	private Timestamp modifiedDate;

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by")
	@JsonBackReference(value="created_by")
	private User createdBy;

	// bi-directional many-to-one association to User
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "modified_by")
	@JsonBackReference(value="modified_by")
	private User modifiedBy;

	//bi-directional many-to-one association to Schedulejob
	/*
	 * @OneToMany(mappedBy="jobType") private List<SchedulerJob> schedulerJobs;
	 */

	public JobType() {
	}

	public JobType(int jobTypeId, Timestamp createdDate, int isActive, String jobTypeName, Timestamp modifiedDate,
			User createdBy, User modifiedBy) {
		super();
		this.jobTypeId = jobTypeId;
		this.createdDate = createdDate;
		this.isActive = isActive;
		this.jobTypeName = jobTypeName;
		this.modifiedDate = modifiedDate;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		//this.schedulerJobs = schedulerJobs;
	}

	
	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	

	public Integer getJobTypeId() {
		return jobTypeId;
	}

	public void setJobTypeId(Integer jobTypeId) {
		this.jobTypeId = jobTypeId;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getJobTypeName() {
		return this.jobTypeName;
	}

	public void setJobTypeName(String jobTypeName) {
		this.jobTypeName = jobTypeName;
	}

	public Timestamp getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
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

	/*
	 * public void setSchedulerJobs(List<SchedulerJob> schedulerJobs) {
	 * this.schedulerJobs = schedulerJobs; }
	 * 
	 * public List<SchedulerJob> getSchedulerJobs() { return this.schedulerJobs; }
	 * 
	 * public void setSchedulejobs(List<SchedulerJob> schedulejobs) {
	 * this.schedulerJobs = schedulejobs; }
	 */

	/*
	 * public SchedulerJob addSchedulerJob(SchedulerJob schedulerJob) {
	 * getSchedulerJobs().add(schedulerJob); schedulerJob.setJobType(this);
	 * 
	 * return schedulerJob; }
	 * 
	 * public SchedulerJob removeSchedulejob(SchedulerJob schedulejob) {
	 * getSchedulerJobs().remove(schedulejob); schedulejob.setJobType(null);
	 * 
	 * return schedulejob; }
	 */

	/*
	 * @Override public String toString() { return "JobType [jobTypeId=" + jobTypeId
	 * + ", createdDate=" + createdDate + ", isActive=" + isActive +
	 * ", jobTypeName=" + jobTypeName + ", modifiedDate=" + modifiedDate +
	 * ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy + "]"; }
	 */

}