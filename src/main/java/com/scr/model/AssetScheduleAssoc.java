package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the asset_schedule_assoc database table.
 * 
 */
@Entity
@Table(name = "asset_schedule_assoc" , uniqueConstraints={@UniqueConstraint(name = "old_pk_asset_schedule_assoc_uniq", columnNames ={"schedule_code", "asset_type", "data_div"})})
@NamedQuery(name="AssetScheduleAssoc.findAll", query="SELECT a FROM AssetScheduleAssoc a")
public class AssetScheduleAssoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="asa_seq_id")
	private String asaSeqId;

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

	private String description;

	private double duration;

	@Column(name="is_dpr")
	private String isDpr;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="schedule_code")
	private String scheduleCode;

	@Column(name="sequence_code")
	private String sequenceCode;

	@Column(name="target_plan_months")
	private String targetPlanMonths;

	@Column(name="uom_of_duration")
	private String uomOfDuration;

	public AssetScheduleAssoc() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAsaSeqId() {
		return this.asaSeqId;
	}

	public void setAsaSeqId(String asaSeqId) {
		this.asaSeqId = asaSeqId;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDuration() {
		return this.duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getIsDpr() {
		return this.isDpr;
	}

	public void setIsDpr(String isDpr) {
		this.isDpr = isDpr;
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

	public String getScheduleCode() {
		return this.scheduleCode;
	}

	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}

	public String getSequenceCode() {
		return this.sequenceCode;
	}

	public void setSequenceCode(String sequenceCode) {
		this.sequenceCode = sequenceCode;
	}

	public String getTargetPlanMonths() {
		return this.targetPlanMonths;
	}

	public void setTargetPlanMonths(String targetPlanMonths) {
		this.targetPlanMonths = targetPlanMonths;
	}

	public String getUomOfDuration() {
		return this.uomOfDuration;
	}

	public void setUomOfDuration(String uomOfDuration) {
		this.uomOfDuration = uomOfDuration;
	}

}
