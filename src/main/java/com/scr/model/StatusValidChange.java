package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the status_valid_change database table.
 * 
 */
@Entity
@Table(name = "status_valid_change" , uniqueConstraints={@UniqueConstraint(name = "old_pk_status_valid_change_uniq", columnNames ={"status_id_to", "status_id"})})
@NamedQuery(name="StatusValidChange.findAll", query="SELECT s FROM StatusValidChange s")
public class StatusValidChange implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="condition_expression")
	private String conditionExpression;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="status_id")
	private String statusId;

	@Column(name="status_id_to")
	private String statusIdTo;

	@Column(name="transition_name")
	private String transitionName;

	public StatusValidChange() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConditionExpression() {
		return this.conditionExpression;
	}

	public void setConditionExpression(String conditionExpression) {
		this.conditionExpression = conditionExpression;
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

	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getStatusIdTo() {
		return this.statusIdTo;
	}

	public void setStatusIdTo(String statusIdTo) {
		this.statusIdTo = statusIdTo;
	}

	public String getTransitionName() {
		return this.transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

}