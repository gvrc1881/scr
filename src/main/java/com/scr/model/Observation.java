package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the observations database table.
 * 
 */
@Entity
@Table(name = "observations" , uniqueConstraints={@UniqueConstraint(name = "old_pk_observations_uniq", columnNames ={"data_div", "seq_id"})})
@NamedQuery(name="Observation.findAll", query="SELECT o FROM Observation o")
public class Observation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String action;

	@Column(name="action_by")
	private String actionBy;
	
	@Column(name="action_required")
	private String actionRequired;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_date_time")
	private Timestamp createdDateTime;

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
	
	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name="inspection_seq_id")
	private String inspectionSeqId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;
	
	@Column(name = "attachment")
	private String attachment;

	private String location;

	private String observation;

	@Column(name="observation_category")
	private String observationCategory;

	@Column(name="observation_item")
	private String observationItem;

	@Column(name="seq_id")
	private String seqId;

	public Observation() {
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

	public String getActionRequired() {
		return actionRequired;
	}

	public void setActionRequired(String actionRequired) {
		this.actionRequired = actionRequired;
	}

	public String getActionBy() {
		return this.actionBy;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDateTime() {
		return this.createdDateTime;
	}

	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getInspectionSeqId() {
		return this.inspectionSeqId;
	}

	public void setInspectionSeqId(String inspectionSeqId) {
		this.inspectionSeqId = inspectionSeqId;
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

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getObservation() {
		return this.observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public String getObservationCategory() {
		return this.observationCategory;
	}

	public void setObservationCategory(String observationCategory) {
		this.observationCategory = observationCategory;
	}

	public String getObservationItem() {
		return this.observationItem;
	}

	public void setObservationItem(String observationItem) {
		this.observationItem = observationItem;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Override
	public String toString() {
		return "Observation [id=" + id + ", action=" + action + ", actionBy=" + actionBy + ", actionRequired="
				+ actionRequired + ", createdBy=" + createdBy + ", createdDateTime=" + createdDateTime
				+ ", createdStamp=" + createdStamp + ", createdTxStamp=" + createdTxStamp + ", dataDiv=" + dataDiv
				+ ", description=" + description + ", deviceId=" + deviceId + ", deviceSeqId=" + deviceSeqId
				+ ", updatedBy=" + updatedBy + ", inspectionSeqId=" + inspectionSeqId + ", lastUpdatedStamp="
				+ lastUpdatedStamp + ", lastUpdatedTxStamp=" + lastUpdatedTxStamp + ", attachment=" + attachment
				+ ", location=" + location + ", observation=" + observation + ", observationCategory="
				+ observationCategory + ", observationItem=" + observationItem + ", seqId=" + seqId + "]";
	}

}
