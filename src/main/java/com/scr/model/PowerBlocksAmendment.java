package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the power_blocks_amendment database table.
 * 
 */
@Entity
@Table(name = "power_blocks_amendment" , uniqueConstraints={@UniqueConstraint(name = "old_pk_power_blocks_amendment_uniq", columnNames ={"data_div", "pb_amendment_seq_id"})})
@NamedQuery(name="PowerBlocksAmendment.findAll", query="SELECT p FROM PowerBlocksAmendment p")
public class PowerBlocksAmendment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String delete;

	@Column(name="field_no_ptw_issue")
	private String fieldNoPtwIssue;

	@Column(name="field_no_ptw_return")
	private String fieldNoPtwReturn;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="pb_amendment_seq_id")
	private String pbAmendmentSeqId;

	@Column(name="pb_operation_seq_id")
	private String pbOperationSeqId;

	@Column(name="ptw_availed_from_date_time")
	private Timestamp ptwAvailedFromDateTime;

	@Column(name="ptw_availed_thru_date_time")
	private Timestamp ptwAvailedThruDateTime;

	@Column(name="tpc_no_ptw_issue")
	private String tpcNoPtwIssue;

	@Column(name="tpc_no_ptw_return")
	private String tpcNoPtwReturn;

	@Column(name="updated_by")
	private String updatedBy;

	public PowerBlocksAmendment() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getDelete() {
		return this.delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getFieldNoPtwIssue() {
		return this.fieldNoPtwIssue;
	}

	public void setFieldNoPtwIssue(String fieldNoPtwIssue) {
		this.fieldNoPtwIssue = fieldNoPtwIssue;
	}

	public String getFieldNoPtwReturn() {
		return this.fieldNoPtwReturn;
	}

	public void setFieldNoPtwReturn(String fieldNoPtwReturn) {
		this.fieldNoPtwReturn = fieldNoPtwReturn;
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

	public String getPbAmendmentSeqId() {
		return this.pbAmendmentSeqId;
	}

	public void setPbAmendmentSeqId(String pbAmendmentSeqId) {
		this.pbAmendmentSeqId = pbAmendmentSeqId;
	}

	public String getPbOperationSeqId() {
		return this.pbOperationSeqId;
	}

	public void setPbOperationSeqId(String pbOperationSeqId) {
		this.pbOperationSeqId = pbOperationSeqId;
	}

	public Timestamp getPtwAvailedFromDateTime() {
		return this.ptwAvailedFromDateTime;
	}

	public void setPtwAvailedFromDateTime(Timestamp ptwAvailedFromDateTime) {
		this.ptwAvailedFromDateTime = ptwAvailedFromDateTime;
	}

	public Timestamp getPtwAvailedThruDateTime() {
		return this.ptwAvailedThruDateTime;
	}

	public void setPtwAvailedThruDateTime(Timestamp ptwAvailedThruDateTime) {
		this.ptwAvailedThruDateTime = ptwAvailedThruDateTime;
	}

	public String getTpcNoPtwIssue() {
		return this.tpcNoPtwIssue;
	}

	public void setTpcNoPtwIssue(String tpcNoPtwIssue) {
		this.tpcNoPtwIssue = tpcNoPtwIssue;
	}

	public String getTpcNoPtwReturn() {
		return this.tpcNoPtwReturn;
	}

	public void setTpcNoPtwReturn(String tpcNoPtwReturn) {
		this.tpcNoPtwReturn = tpcNoPtwReturn;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
