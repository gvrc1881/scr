package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;



@Entity
@Table(name="jobs_history")
@NamedQuery(name="JobsHistory.findAll", query="SELECT j FROM JobsHistory j")
public class JobsHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="operation_id")
	private Integer operationId;
	
	@Column(name="opration_type")
	private String operationType;
	
	@Column(name="end_time")
	private String endTime;	
	
	
	@Column(name="job_status")
	private String jobStatus;

	@Column(name="processed_date")
	private Timestamp processedDate;

	@Column(name="start_time")
	private String startTime;

	@Column(name="total_tables_count")
	private Integer totalTablesCount;
	
	@Column(name="success_tables_count")
	private Integer successTablesCount;
	
	@Column(name="failed_tables_count")
	private Integer failedTablesCount;	
	
	@Column(name="status")
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public Timestamp getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Timestamp processedDate) {
		this.processedDate = processedDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getTotalTablesCount() {
		return totalTablesCount;
	}

	public void setTotalTablesCount(Integer totalTablesCount) {
		this.totalTablesCount = totalTablesCount;
	}

	public Integer getSuccessTablesCount() {
		return successTablesCount;
	}

	public void setSuccessTablesCount(Integer successTablesCount) {
		this.successTablesCount = successTablesCount;
	}

	public Integer getFailedTablesCount() {
		return failedTablesCount;
	}

	public void setFailedTablesCount(Integer failedTablesCount) {
		this.failedTablesCount = failedTablesCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}