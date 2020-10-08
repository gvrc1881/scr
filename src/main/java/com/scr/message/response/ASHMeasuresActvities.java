package com.scr.message.response;

import java.io.Serializable;

/**
 * The persistent class for the assets_schedule_history database table.
 * 
 */
public class ASHMeasuresActvities implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String assetId;

	private String assetType;

	private String facilityId;

	private String scheduleCode;

	private String make;

	private String model;
	
	private String activityId;
	
	private String activityName;

	private String activityPositionId;

	private String lowerLimit;

	private String upperLimit;

	private String seqId;

	private String description;

	private String subAssetType;

	public ASHMeasuresActvities() {
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

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityPositionId() {
		return activityPositionId;
	}

	public void setActivityPositionId(String activityPositionId) {
		this.activityPositionId = activityPositionId;
	}

	public String getLowerLimit() {
		return lowerLimit;
	}

	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public String getUpperLimit() {
		return upperLimit;
	}

	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubAssetType() {
		return subAssetType;
	}

	public void setSubAssetType(String subAssetType) {
		this.subAssetType = subAssetType;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAssetType() {
		return this.assetType;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getScheduleCode() {
		return this.scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

}
