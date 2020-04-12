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
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author venkat
 *
 */
@Entity
@Table(name="scheduler_operations_types_tracking")
@NamedQuery(name="SchedulerOperationTypesTracking.findAll", query="SELECT s FROM SchedulerOperationTypesTracking s")

public class SchedulerOperationTypesTracking implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5010658851910660208L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer operationId;
	
	@Column(name="tracking_id")
	private Integer trackingId;
	
	@Column(name="job_type")
	private String jobType;
	
	@Column(name="job_status")
	private String jobStatus;
	
	@Column(name="processed_date")
	private Timestamp processedDate;
	
	@Column(name="process_status")
	private String processStatus;
	
	@Column(name="end_time")
	private String endTime;
	
	@Column(name="start_time")
	private String startTime;
	
	@Column(name="division_schema")
	private String divisionSchema;
	
	@Column(name="temp_schema")
	private String tempSchema;

	@Column(name="zonal_schema")
	private String zonalSchema;
	public Integer getOperationId() {
		return operationId;
	}

	public void setOperationId(Integer operationId) {
		this.operationId = operationId;
	}

	public Integer getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(Integer trackingId) {
		this.trackingId = trackingId;
	}



	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
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

	public String getDivisionSchema() {
		return divisionSchema;
	}

	public void setDivisionSchema(String divisionSchema) {
		this.divisionSchema = divisionSchema;
	}

	public String getTempSchema() {
		return tempSchema;
	}

	public void setTempSchema(String tempSchema) {
		this.tempSchema = tempSchema;
	}

	public String getZonalSchema() {
		return zonalSchema;
	}

	public void setZonalSchema(String zonalSchema) {
		this.zonalSchema = zonalSchema;
	}
	
	
}
