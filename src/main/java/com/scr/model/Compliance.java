package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the compliances database table.
 * 
 */
@Entity
@Table(name = "compliances" , uniqueConstraints={@UniqueConstraint(name = "old_pk_compliances_uniq", columnNames ={"seq_id", "data_div"})})
@NamedQuery(name="Compliance.findAll", query="SELECT c FROM Compliance c")
public class Compliance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String action;

	@Column(name="compliance_by")
	private String complianceBy;

	@Column(name="compliance_fullfilled")
	private String complianceFullfilled;

	@Column(name="compliance_remark")
	private String complianceRemark;

	@Column(name="complied_date_time")
	private Timestamp compliedDateTime;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String description;

	@Column(name="device_id")
	private String deviceId;

	@Column(name="device_seq_id")
	private String deviceSeqId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="obeservation_seq_id")
	private String obeservationSeqId;

	@Column(name="seq_id")
	private String seqId;

	private String status;

	public Compliance() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComplianceBy() {
		return this.complianceBy;
	}

	public void setComplianceBy(String complianceBy) {
		this.complianceBy = complianceBy;
	}

	public String getComplianceFullfilled() {
		return this.complianceFullfilled;
	}

	public void setComplianceFullfilled(String complianceFullfilled) {
		this.complianceFullfilled = complianceFullfilled;
	}

	public String getComplianceRemark() {
		return this.complianceRemark;
	}

	public void setComplianceRemark(String complianceRemark) {
		this.complianceRemark = complianceRemark;
	}

	public Timestamp getCompliedDateTime() {
		return this.compliedDateTime;
	}

	public void setCompliedDateTime(Timestamp compliedDateTime) {
		this.compliedDateTime = compliedDateTime;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceSeqId() {
		return this.deviceSeqId;
	}

	public void setDeviceSeqId(String deviceSeqId) {
		this.deviceSeqId = deviceSeqId;
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

	public String getObeservationSeqId() {
		return this.obeservationSeqId;
	}

	public void setObeservationSeqId(String obeservationSeqId) {
		this.obeservationSeqId = obeservationSeqId;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
