package com.scr.app.dto;


public class ObservationsDto {

    public ObservationsDto(){
        //TODO : auto generated constructor stub
    }

    String seqId;
    String deviceId;
    String deviceSeqId;
    String inspectionSeqId;
    String observationCategory;
    String observationItem;
    String observation;
    String description;
    String action;
    String actionBy;
    String createdBy;
    String createdDateTime;
    String location;

    public String getSeqId() {
        return seqId;
    }
    public void setSeqId(String seqId) {
        this.seqId = seqId;
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

    public String getInspectionSeqId() {
        return inspectionSeqId;
    }
    public void setInspectionSeqId(String inspectionSeqId) {
        this.inspectionSeqId = inspectionSeqId;
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

    public String getObservation() {
        return observation;
    }
    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }
    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}