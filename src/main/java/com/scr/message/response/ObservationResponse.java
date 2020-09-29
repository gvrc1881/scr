package com.scr.message.response;

import java.sql.Timestamp;

import javax.persistence.Column;

import org.springframework.stereotype.Component;

@Component
public class ObservationResponse {
	
	private Long id;
	private String createdBy;
	private Timestamp createdDate;
	private Timestamp createdOn;
	private String currentStatus;
	private String dataDiv;
	private String endLocation;
	private String facilityId;
	private Timestamp fromDateTime;
	private String inspectionSeqId;
	private String nameOfStaff;
	private String nextDayPlan;
	private String observation;
	private String placeOfWork;
	private String schedule;
	private String section;
	private String staffType;
	private String startLocation;
	private String statusOfWork;
	private String typeOfWork;
	private String action;
	private String actionBy;
	private String actionRequired;
	private Timestamp createdDateTime;
	private Timestamp createdStamp;
	private Timestamp createdTxStamp;
	private String description;
	private String deviceId;
	private String deviceSeqId;
	private String updatedBy;
	private String location;
	private String observationCategory;
	private String observationItem;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getDataDiv() {
		return dataDiv;
	}
	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}
	public String getEndLocation() {
		return endLocation;
	}
	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public Timestamp getFromDateTime() {
		return fromDateTime;
	}
	public void setFromDateTime(Timestamp fromDateTime) {
		this.fromDateTime = fromDateTime;
	}
	public String getInspectionSeqId() {
		return inspectionSeqId;
	}
	public void setInspectionSeqId(String inspectionSeqId) {
		this.inspectionSeqId = inspectionSeqId;
	}
	public String getNameOfStaff() {
		return nameOfStaff;
	}
	public void setNameOfStaff(String nameOfStaff) {
		this.nameOfStaff = nameOfStaff;
	}
	public String getNextDayPlan() {
		return nextDayPlan;
	}
	public void setNextDayPlan(String nextDayPlan) {
		this.nextDayPlan = nextDayPlan;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public String getPlaceOfWork() {
		return placeOfWork;
	}
	public void setPlaceOfWork(String placeOfWork) {
		this.placeOfWork = placeOfWork;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getStaffType() {
		return staffType;
	}
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}
	public String getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}
	public String getStatusOfWork() {
		return statusOfWork;
	}
	public void setStatusOfWork(String statusOfWork) {
		this.statusOfWork = statusOfWork;
	}
	public String getTypeOfWork() {
		return typeOfWork;
	}
	public void setTypeOfWork(String typeOfWork) {
		this.typeOfWork = typeOfWork;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
	@Override
	public String toString() {
		return "ObservationResponse [id=" + id + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", createdOn=" + createdOn + ", currentStatus=" + currentStatus + ", dataDiv=" + dataDiv
				+ ", endLocation=" + endLocation + ", facilityId=" + facilityId + ", fromDateTime=" + fromDateTime
				+ ", inspectionSeqId=" + inspectionSeqId + ", nameOfStaff=" + nameOfStaff + ", nextDayPlan="
				+ nextDayPlan + ", observation=" + observation + ", placeOfWork=" + placeOfWork + ", schedule="
				+ schedule + ", section=" + section + ", staffType=" + staffType + ", startLocation=" + startLocation
				+ ", statusOfWork=" + statusOfWork + ", typeOfWork=" + typeOfWork + ", action=" + action + ", actionBy="
				+ actionBy + ", actionRequired=" + actionRequired + ", createdDateTime=" + createdDateTime
				+ ", createdStamp=" + createdStamp + ", createdTxStamp=" + createdTxStamp + ", description="
				+ description + ", deviceId=" + deviceId + ", deviceSeqId=" + deviceSeqId + ", updatedBy=" + updatedBy
				+ ", location=" + location + ", observationCategory=" + observationCategory + ", observationItem="
				+ observationItem + "]";
	}

}
