package com.scr.message.response;

import java.time.LocalDate;
import java.util.Date;
import java.sql.Timestamp;

public class AssetStatusUpdateResponse {

	private String assetType;

	private String assetId;

	private String make;
	private String model;
	private String facilityId;
	private Date dateOfManufacture;
	private LocalDate nextAoh;
	private LocalDate nextPoh;
	private Timestamp dateOfStatus;
	private String currentStatus;
	private String status;
	private Timestamp statusDate;
	private String remarks;
	private boolean editPermission;
	private Long AsuId;

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public Date getDateOfManufacture() {
		return dateOfManufacture;
	}

	public void setDateOfManufacture(Date dateOfManufacture) {
		this.dateOfManufacture = dateOfManufacture;
	}

	public Date getDateOfStatus() {
		return dateOfStatus;
	}

	public void setDateOfStatus(Timestamp dateOfStatus) {
		this.dateOfStatus = dateOfStatus;
	}

	

	
	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Timestamp statusDate) {
		this.statusDate = statusDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDate getNextAoh() {
		return nextAoh;
	}

	public void setNextAoh(LocalDate nextAoh) {
		this.nextAoh = nextAoh;
	}

	public LocalDate getNextPoh() {
		return nextPoh;
	}

	public void setNextPoh(LocalDate nextPoh) {
		this.nextPoh = nextPoh;
	}

	public boolean isEditPermission() {
		return editPermission;
	}

	public void setEditPermission(boolean editPermission) {
		this.editPermission = editPermission;
	}

	public Long getAsuId() {
		return AsuId;
	}

	public void setAsuId(Long asuId) {
		AsuId = asuId;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AssetStatusUpdateResponse [assetType=" + assetType + ", assetId=" + assetId + ", make=" + make
				+ ", model=" + model + ", facilityId=" + facilityId + ", dateOfManufacture=" + dateOfManufacture
				+ ", nextAoh=" + nextAoh + ", nextPoh=" + nextPoh + ", dateOfStatus=" + dateOfStatus
				+ ", currentStatus=" + currentStatus + ", status=" + status + ", statusDate=" + statusDate
				+ ", remarks=" + remarks + ", editPermission=" + editPermission + ", AsuId=" + AsuId + "]";
	}



	

	

	

}
