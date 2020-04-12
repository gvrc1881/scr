package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;
import java.sql.Timestamp;


/**
 * The persistent class for the timeintervals database table.
 * 
 */
@Entity
@Table(name="timeintervals")
@NamedQuery(name="TimeInterval.findAll", query="SELECT t FROM TimeInterval t")
public class TimeInterval implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int timeIntervalId;

	private Timestamp createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	private int isActive;

	private Timestamp modifiedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	private String timeInterval;

	//bi-directional many-to-one association to Schedulejob
	//@OneToMany(mappedBy="timeInterval")
	//private List<SchedulerJob> schedulerJobs;

	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by")
	@JsonBackReference(value="created_by")
	private User createdBy;

	// bi-directional many-to-one association to User
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name = "modified_by")
	@JsonBackReference(value="modified_by")
	private User modifiedBy;

	public TimeInterval() {
	}

	public int getTimeIntervalId() {
		return this.timeIntervalId;
	}

	public void setTimeIntervalId(int timeIntervalId) {
		this.timeIntervalId = timeIntervalId;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTimeInterval() {
		return this.timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	/*
	 * public List<SchedulerJob> getSchedulejobs() { return this.schedulerJobs; }
	 * 
	 * public void setSchedulejobs(List<SchedulerJob> schedulerJobs) {
	 * this.schedulerJobs = schedulerJobs; }
	 * 
	 * public SchedulerJob addSchedulejob(SchedulerJob schedulejob) {
	 * getSchedulejobs().add(schedulejob); schedulejob.setTimeInterval(this);
	 * 
	 * return schedulejob; }
	 * 
	 * public SchedulerJob removeSchedulejob(SchedulerJob schedulejob) {
	 * getSchedulejobs().remove(schedulejob); schedulejob.setTimeInterval(null);
	 * 
	 * return schedulejob; }
	 */

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
	 * @Override public String toString() { return "TimeInterval [timeIntervalId=" +
	 * timeIntervalId + ", createdDate=" + createdDate + ", endDate=" + endDate +
	 * ", isActive=" + isActive + ", modifiedDate=" + modifiedDate + ", startDate="
	 * + startDate + ", timeInterval=" + timeInterval + ", createdBy=" + createdBy +
	 * ", modifiedBy=" + modifiedBy + "]"; }
	 */

}