package com.scr.message.request;

import java.sql.Timestamp;


public class InspectionRequest {
	
	private Long id;
	private String action;
	private String actionBy;
	private String actionRequired;
	private String createdBy;
	private Timestamp createdDateTime;
	private Timestamp createdStamp;
	private Timestamp createdTxStamp;
	private String dataDiv;
	private String description;
	private String deviceId;
	private String deviceSeqId;
	private String inspectionSeqId;
	private String updatedBy;
	private Timestamp lastUpdatedStamp;
	private Timestamp lastUpdatedTxStamp;
	private String attachment;
	private String document;
	private String location;
	private String observation;
	private String observationCategory;
	private String observationItem;
	private String seqId;
	private String complianceBy;
	private String complianceFullfilled;
	private String complianceRemark;
	private Timestamp compliedDateTime;
	private String obeservationSeqId;

	
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getActionBy() {
		return actionBy;
	}
	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}
	
	public String getActionRequired() {
		return actionRequired;
	}
	public void setActionRequired(String actionRequired) {
		this.actionRequired = actionRequired;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDateTime() {
		return createdDateTime;
	}
	public void setCreatedDateTime(Timestamp createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public Timestamp getCreatedStamp() {
		return createdStamp;
	}
	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}
	public Timestamp getCreatedTxStamp() {
		return createdTxStamp;
	}
	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}
	public String getDataDiv() {
		return dataDiv;
	}
	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceSeqId() {
		return deviceSeqId;
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
		return inspectionSeqId;
	}
	public void setInspectionSeqId(String inspectionSeqId) {
		this.inspectionSeqId = inspectionSeqId;
	}
	public Timestamp getLastUpdatedStamp() {
		return lastUpdatedStamp;
	}
	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}
	public Timestamp getLastUpdatedTxStamp() {
		return lastUpdatedTxStamp;
	}
	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getObservationCategory() {
		return observationCategory;
	}
	public void setObservationCategory(String observationCategory) {
		this.observationCategory = observationCategory;
	}
	public String getObservationItem() {
		return observationItem;
	}
	public void setObservationItem(String observationItem) {
		this.observationItem = observationItem;
	}
	public String getSeqId() {
		return seqId;
	}
	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	public String getComplianceBy() {
		return complianceBy;
	}
	public void setComplianceBy(String complianceBy) {
		this.complianceBy = complianceBy;
	}
	public String getComplianceFullfilled() {
		return complianceFullfilled;
	}
	public void setComplianceFullfilled(String complianceFullfilled) {
		this.complianceFullfilled = complianceFullfilled;
	}
	public String getComplianceRemark() {
		return complianceRemark;
	}
	public void setComplianceRemark(String complianceRemark) {
		this.complianceRemark = complianceRemark;
	}
	public Timestamp getCompliedDateTime() {
		return compliedDateTime;
	}
	public void setCompliedDateTime(Timestamp compliedDateTime) {
		this.compliedDateTime = compliedDateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getObeservationSeqId() {
		return obeservationSeqId;
	}
	public void setObeservationSeqId(String obeservationSeqId) {
		this.obeservationSeqId = obeservationSeqId;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}

}
