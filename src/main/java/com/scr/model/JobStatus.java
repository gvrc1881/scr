package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;



@Entity
@NamedQuery(name="JobStatus.findAll", query="SELECT j FROM JobStatus j")
public class JobStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int jobStatusId;

	private Timestamp createdDate;

	private int isActive;

	private String jobStatusName;

	private Timestamp modifiedDate;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="CreatedBy")
	private User createdBy;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="ModifiedBy")
	private User modifiedBy;

	//bi-directional many-to-one association to Schedulejob
	@OneToMany(mappedBy="jobStatus", fetch = FetchType.EAGER)
	private List<SchedulerJob> schedulerJobs;

	public JobStatus() {
	}

	public int getJobStatusId() {
		return this.jobStatusId;
	}

	public void setJobStatusId(int jobStatusId) {
		this.jobStatusId = jobStatusId;
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

	public String getJobStatusName() {
		return this.jobStatusName;
	}

	public void setJobStatusName(String jobStatusName) {
		this.jobStatusName = jobStatusName;
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

	public void setSchedulerJobs(List<SchedulerJob> schedulerJobs) {
		this.schedulerJobs = schedulerJobs;
	}

	public List<SchedulerJob> getSchedulerJobs() {
		return this.schedulerJobs;
	}

	public void setSchedulejobs(List<SchedulerJob> schedulejobs) {
		this.schedulerJobs = schedulejobs;
	}

	public SchedulerJob addSchedulejob(SchedulerJob schedulejob) {
		getSchedulerJobs().add(schedulejob);
		schedulejob.setJobStatus(this);

		return schedulejob;
	}

	public SchedulerJob removeSchedulejob(SchedulerJob schedulejob) {
		getSchedulerJobs().remove(schedulejob);
		schedulejob.setJobStatus(null);

		return schedulejob;
	}

}