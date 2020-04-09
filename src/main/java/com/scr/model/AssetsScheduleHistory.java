package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the assets_schedule_history database table.
 * 
 */
@Entity
@Table(name = "assets_schedule_history" , uniqueConstraints={@UniqueConstraint(name = "old_pk_assets_schedule_history_uniq", columnNames ={"data_div", "seq_id"})})
@NamedQuery(name="AssetsScheduleHistory.findAll", query="SELECT a FROM AssetsScheduleHistory a")
public class AssetsScheduleHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="asset_id")
	private String assetId;

	@Column(name="asset_type")
	private String assetType;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="details_of_maint")
	private String detailsOfMaint;

	@Column(name="device_created_stamp")
	private Timestamp deviceCreatedStamp;

	@Column(name="device_id")
	private String deviceId;

	@Column(name="device_last_updated_stamp")
	private Timestamp deviceLastUpdatedStamp;

	@Column(name="device_seq_id")
	private String deviceSeqId;

	@Column(name="done_by")
	private String doneBy;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="initial_of_incharge")
	private String initialOfIncharge;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="pb_operation_seq_id")
	private String pbOperationSeqId;

	private String remarks;

	@Column(name="schedule_code")
	private String scheduleCode;

	@Column(name="schedule_date")
	private Timestamp scheduleDate;

	@Column(name="seq_id")
	private String seqId;

	private String status;

	public AssetsScheduleHistory() {
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

}
