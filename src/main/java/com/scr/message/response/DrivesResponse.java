package com.scr.message.response;

import java.sql.Timestamp;
import java.util.Date;

import com.scr.model.FunctionalLocationTypes;

public class DrivesResponse {

	private Long id;
	private String name;
	private String description;
	private Date fromDate;
	private Date toDate;
	private String assetType;
	private String assetDescription;
	private String criteria;
	private double target_qty;
	private String functionalUnit;
	private String isIdRequired;
	private String checklist;
	private String active;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	private Integer statusId;
	private String frequency;
	private FunctionalLocationTypes depotType;
	private double performedCount;
	private double alreadyDone;
	private String facilityId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetDescription() {
		return assetDescription;
	}

	public void setAssetDescription(String assetDescription) {
		this.assetDescription = assetDescription;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public double getTarget_qty() {
		return target_qty;
	}

	public void setTarget_qty(double target_qty) {
		this.target_qty = target_qty;
	}

	public String getFunctionalUnit() {
		return functionalUnit;
	}

	public void setFunctionalUnit(String functionalUnit) {
		this.functionalUnit = functionalUnit;
	}

	public String getIsIdRequired() {
		return isIdRequired;
	}

	public void setIsIdRequired(String isIdRequired) {
		this.isIdRequired = isIdRequired;
	}

	public String getChecklist() {
		return checklist;
	}

	public void setChecklist(String checklist) {
		this.checklist = checklist;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public FunctionalLocationTypes getDepotType() {
		return depotType;
	}

	public void setDepotType(FunctionalLocationTypes depotType) {
		this.depotType = depotType;
	}

	public double getPerformedCount() {
		return performedCount;
	}

	public void setPerformedCount(double performedCount) {
		this.performedCount = performedCount;
	}

	public double getAlreadyDone() {
		return alreadyDone;
	}

	public void setAlreadyDone(double alreadyDone) {
		this.alreadyDone = alreadyDone;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public String toString() {
		return "DrivesResponse [id=" + id + ", name=" + name + ", description=" + description + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", assetType=" + assetType + ", assetDescription=" + assetDescription
				+ ", criteria=" + criteria + ", target_qty=" + target_qty + ", functionalUnit=" + functionalUnit
				+ ", isIdRequired=" + isIdRequired + ", checklist=" + checklist + ", active=" + active + ", createdBy="
				+ createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", statusId=" + statusId + ", frequency=" + frequency + ", depotType=" + depotType
				+ ", performedCount=" + performedCount + ", alreadyDone=" + alreadyDone + ", facilityId=" + facilityId
				+ "]";
	}

}
