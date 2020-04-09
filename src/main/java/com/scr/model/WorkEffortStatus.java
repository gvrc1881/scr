package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the work_effort_status database table.
 * 
 */
@Entity
@Table(name = "work_effort_status" , uniqueConstraints={@UniqueConstraint(name = "old_pk_work_effort_status_uniq", columnNames ={"data_div", "status_datetime", "status_id", "work_effort_id"})})
@NamedQuery(name="WorkEffortStatus.findAll", query="SELECT w FROM WorkEffortStatus w")
public class WorkEffortStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String reason;

	@Column(name="set_by_user_login")
	private String setByUserLogin;

	@Column(name="status_datetime")
	private Timestamp statusDatetime;

	@Column(name="status_id")
	private String statusId;

	@Column(name="work_effort_id")
	private String workEffortId;

	public WorkEffortStatus() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedStamp() {
		return this.createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return this.createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public Timestamp getLastUpdatedStamp() {
		return this.lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return this.lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSetByUserLogin() {
		return this.setByUserLogin;
	}

	public void setSetByUserLogin(String setByUserLogin) {
		this.setByUserLogin = setByUserLogin;
	}

	public Timestamp getStatusDatetime() {
		return this.statusDatetime;
	}

	public void setStatusDatetime(Timestamp statusDatetime) {
		this.statusDatetime = statusDatetime;
	}

	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getWorkEffortId() {
		return this.workEffortId;
	}

	public void setWorkEffortId(String workEffortId) {
		this.workEffortId = workEffortId;
	}

}