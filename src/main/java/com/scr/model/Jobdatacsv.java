package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the jobdatacsv database table.
 * 
 */
@Entity
@Table(name="jobdatacsv")
@NamedQuery(name="Jobdatacsv.findAll", query="SELECT j FROM Jobdatacsv j")
public class Jobdatacsv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="JOBDATACSV_ID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="JOBDATACSV_ID_GENERATOR")
	private int id;

	private String endTime;

	private String filePath;

	private String jobName;

	private String jobStatus;

	private Timestamp processedDate;

	private int processedRecords;

	private String startTime;

	private int totalRecords;

	private int unprocessedRecords;

	//bi-directional many-to-one association to Schedulejob
	@ManyToOne
	@JoinColumn(name="JobId")
	private SchedulerJob schedulerJob;

	public Jobdatacsv() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getJobName() {
		return this.jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobStatus() {
		return this.jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Timestamp getProcessedDate() {
		return this.processedDate;
	}

	public void setProcessedDate(Timestamp processedDate) {
		this.processedDate = processedDate;
	}

	public int getProcessedRecords() {
		return this.processedRecords;
	}

	public void setProcessedRecords(int processedRecords) {
		this.processedRecords = processedRecords;
	}

	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getTotalRecords() {
		return this.totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getUnprocessedRecords() {
		return this.unprocessedRecords;
	}

	public void setUnprocessedRecords(int unprocessedRecords) {
		this.unprocessedRecords = unprocessedRecords;
	}

	public SchedulerJob getSchedulerJob() {
		return this.schedulerJob;
	}

	public void setSchedulerJob(SchedulerJob schedulejob) {
		this.schedulerJob = schedulejob;
	}

}