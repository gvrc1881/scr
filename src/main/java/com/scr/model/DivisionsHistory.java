package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the divisions_history database table.
 * 
 */
@Entity
@Table(name="divisions_history")
@NamedQuery(name="DivisionsHistory.findAll", query="SELECT d FROM DivisionsHistory d")
public class DivisionsHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")	 
	private Integer Id;	
	
	@Column(name="jobs_history_id")
	private Integer jobsHistoryId;
	
	@Column(name="division_id")	 
	private String divisionId;

	@Column(name="table_name")
	private String tableName;

	@Column(name="updated_date")
	private Timestamp updatedDate;

	@Column(name="updated_records")
	private Integer updatedRecords;

	@Column(name="validate_match")
	private Integer validateMatch;
	
	@Column(name="status")
	private String status;
	
	@Column(name="opration_type")
	private String operationType;
	
	public DivisionsHistory() {
	}

	public DivisionsHistory(Integer id, Integer jobsHistoryId, String divisionId, String tableName,
			Timestamp updatedDate, Integer updatedRecords, Integer validateMatch, String status, String operationType) {
		super();
		Id = id;
		this.jobsHistoryId = jobsHistoryId;
		this.divisionId = divisionId;
		this.tableName = tableName;
		this.updatedDate = updatedDate;
		this.updatedRecords = updatedRecords;
		this.validateMatch = validateMatch;
		this.status = status;
		this.operationType = operationType;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getJobsHistoryId() {
		return jobsHistoryId;
	}

	public void setJobsHistoryId(Integer jobsHistoryId) {
		this.jobsHistoryId = jobsHistoryId;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Integer getUpdatedRecords() {
		return updatedRecords;
	}

	public void setUpdatedRecords(Integer updatedRecords) {
		this.updatedRecords = updatedRecords;
	}

	public Integer getValidateMatch() {
		return validateMatch;
	}

	public void setValidateMatch(Integer validateMatch) {
		this.validateMatch = validateMatch;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	
	
	
}