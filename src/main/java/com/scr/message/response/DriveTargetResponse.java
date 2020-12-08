package com.scr.message.response;

import java.sql.Timestamp;

import com.scr.model.Drives;

public class DriveTargetResponse {

	
	private Long id;

	
	private String unitType;

	
	private String unitName;

	
	private double target;

	
	private String poulation;

	
	private String createdBy;


	private String updatedBy;

	private Timestamp createdOn;

	
	private Timestamp updatedOn;
	

	private Integer statusId;
	
	private Drives driveId;
	
	private Integer divisionAggregation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public double getTarget() {
		return target;
	}

	public void setTarget(double target) {
		this.target = target;
	}

	public String getPoulation() {
		return poulation;
	}

	public void setPoulation(String poulation) {
		this.poulation = poulation;
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

	public Drives getDriveId() {
		return driveId;
	}

	public void setDriveId(Drives driveId) {
		this.driveId = driveId;
	}

	public Integer getDivisionAggregation() {
		return divisionAggregation;
	}

	public void setDivisionAggregation(Integer divisionAggregation) {
		this.divisionAggregation = divisionAggregation;
	}
}
