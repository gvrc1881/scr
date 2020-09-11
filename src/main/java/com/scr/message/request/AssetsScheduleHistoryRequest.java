package com.scr.message.request;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the assets_schedule_history database table.
 * 
 */
public class AssetsScheduleHistoryRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String assetId;

	private String assetType;

	private String createdBy;

	private Timestamp createdOn;

	private Timestamp createdStamp;

	private Timestamp createdTxStamp;

	private String dataDiv;

	private String detailsOfMaint;

	private Timestamp deviceCreatedStamp;


	private Timestamp deviceLastUpdatedStamp;

	private String deviceSeqId;

	private String doneBy;

	private String facilityId;

	private String initialOfIncharge;

	private Timestamp lastUpdatedStamp;

	private Timestamp lastUpdatedTxStamp;

	private String pbOperationSeqId;

	private String remarks;

	private String scheduleCode;

	private Timestamp scheduleDate;

	private String seqId;

	private String status;
	
	private String deviceId;

	public AssetsScheduleHistoryRequest() {
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

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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

	public String getDetailsOfMaint() {
		return this.detailsOfMaint;
	}

	public void setDetailsOfMaint(String detailsOfMaint) {
		this.detailsOfMaint = detailsOfMaint;
	}

	public Timestamp getDeviceCreatedStamp() {
		return this.deviceCreatedStamp;
	}

	public void setDeviceCreatedStamp(Timestamp deviceCreatedStamp) {
		this.deviceCreatedStamp = deviceCreatedStamp;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Timestamp getDeviceLastUpdatedStamp() {
		return this.deviceLastUpdatedStamp;
	}

	public void setDeviceLastUpdatedStamp(Timestamp deviceLastUpdatedStamp) {
		this.deviceLastUpdatedStamp = deviceLastUpdatedStamp;
	}

	public String getDeviceSeqId() {
		return this.deviceSeqId;
	}

	public void setDeviceSeqId(String deviceSeqId) {
		this.deviceSeqId = deviceSeqId;
	}

	public String getDoneBy() {
		return this.doneBy;
	}

	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getInitialOfIncharge() {
		return this.initialOfIncharge;
	}

	public void setInitialOfIncharge(String initialOfIncharge) {
		this.initialOfIncharge = initialOfIncharge;
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

	public String getPbOperationSeqId() {
		return this.pbOperationSeqId;
	}

	public void setPbOperationSeqId(String pbOperationSeqId) {
		this.pbOperationSeqId = pbOperationSeqId;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getScheduleCode() {
		return this.scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public Timestamp getScheduleDate() {
		return this.scheduleDate;
	}

	public void setScheduleDate(Timestamp scheduleDate) {
		this.scheduleDate = scheduleDate;
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

	@Override
	public String toString() {
		return "AssetsScheduleHistoryRequest [id=" + id + ", assetId=" + assetId + ", assetType=" + assetType
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", createdStamp=" + createdStamp
				+ ", createdTxStamp=" + createdTxStamp + ", dataDiv=" + dataDiv + ", detailsOfMaint=" + detailsOfMaint
				+ ", deviceCreatedStamp=" + deviceCreatedStamp + ", deviceLastUpdatedStamp=" + deviceLastUpdatedStamp
				+ ", deviceSeqId=" + deviceSeqId + ", doneBy=" + doneBy + ", facilityId=" + facilityId
				+ ", initialOfIncharge=" + initialOfIncharge + ", lastUpdatedStamp=" + lastUpdatedStamp
				+ ", lastUpdatedTxStamp=" + lastUpdatedTxStamp + ", pbOperationSeqId=" + pbOperationSeqId + ", remarks="
				+ remarks + ", scheduleCode=" + scheduleCode + ", scheduleDate=" + scheduleDate + ", seqId=" + seqId
				+ ", status=" + status + ", deviceId=" + deviceId + "]";
	}

}
