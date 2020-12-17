package com.scr.message.response;

import java.time.LocalDate;
import java.util.Date;

public class AssetStatusUpdateResponse {
	
	private String assetType;
	
	private String assetId;
	
	private String make;
	 private String model;
	 private String facilityId;
	 private Date dateOfManufacture;
	 private LocalDate nextAoh;
	 private LocalDate nextPoh;
	 private Date dateOfStatus;
	 private String changeOfStatus;
	 private String newStatus;
	 private Date statusDate;
	 private String remarks;
	 
	 

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

	public void setDateOfStatus(Date dateOfStatus) {
		this.dateOfStatus = dateOfStatus;
	}

	public String getChangeOfStatus() {
		return changeOfStatus;
	}

	public void setChangeOfStatus(String changeOfStatus) {
		this.changeOfStatus = changeOfStatus;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
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

	@Override
	public String toString() {
		return "AssetStatusUpdateResponse [assetType=" + assetType + ", assetId=" + assetId + ", make=" + make
				+ ", model=" + model + ", facilityId=" + facilityId + ", dateOfManufacture=" + dateOfManufacture
				+ ", nextAoh=" + nextAoh + ", nextPoh=" + nextPoh + ", dateOfStatus=" + dateOfStatus
				+ ", changeOfStatus=" + changeOfStatus + ", newStatus=" + newStatus + ", statusDate=" + statusDate
				+ ", remarks=" + remarks + "]";
	}

	
	
	

}
