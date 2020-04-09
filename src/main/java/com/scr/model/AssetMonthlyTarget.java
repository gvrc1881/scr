package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the asset_monthly_targets database table.
 * 
 */
@Entity
@Table(name = "asset_monthly_targets" , uniqueConstraints={@UniqueConstraint(name = "old_pk_asset_monthly_targets_uniq", columnNames ={"asset_type", "year", "schedule_type", "facility_id", "data_div"})})
@NamedQuery(name="AssetMonthlyTarget.findAll", query="SELECT a FROM AssetMonthlyTarget a")
public class AssetMonthlyTarget implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

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

	@Column(name="elementary_section")
	private String elementarySection;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="schedule_type")
	private String scheduleType;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="target_apr")
	private double targetApr;

	@Column(name="target_aug")
	private double targetAug;

	@Column(name="target_dec")
	private double targetDec;

	@Column(name="target_feb")
	private double targetFeb;

	@Column(name="target_jan")
	private double targetJan;

	@Column(name="target_july")
	private double targetJuly;

	@Column(name="target_june")
	private double targetJune;

	@Column(name="target_mar")
	private double targetMar;

	@Column(name="target_may")
	private double targetMay;

	@Column(name="target_nov")
	private double targetNov;

	@Column(name="target_oct")
	private double targetOct;

	@Column(name="target_sep")
	private double targetSep;

	private String year;

	public AssetMonthlyTarget() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getElementarySection() {
		return this.elementarySection;
	}

	public void setElementarySection(String elementarySection) {
		this.elementarySection = elementarySection;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
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

	public String getScheduleType() {
		return this.scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public double getTargetApr() {
		return this.targetApr;
	}

	public void setTargetApr(double targetApr) {
		this.targetApr = targetApr;
	}

	public double getTargetAug() {
		return this.targetAug;
	}

	public void setTargetAug(double targetAug) {
		this.targetAug = targetAug;
	}

	public double getTargetDec() {
		return this.targetDec;
	}

	public void setTargetDec(double targetDec) {
		this.targetDec = targetDec;
	}

	public double getTargetFeb() {
		return this.targetFeb;
	}

	public void setTargetFeb(double targetFeb) {
		this.targetFeb = targetFeb;
	}

	public double getTargetJan() {
		return this.targetJan;
	}

	public void setTargetJan(double targetJan) {
		this.targetJan = targetJan;
	}

	public double getTargetJuly() {
		return this.targetJuly;
	}

	public void setTargetJuly(double targetJuly) {
		this.targetJuly = targetJuly;
	}

	public double getTargetJune() {
		return this.targetJune;
	}

	public void setTargetJune(double targetJune) {
		this.targetJune = targetJune;
	}

	public double getTargetMar() {
		return this.targetMar;
	}

	public void setTargetMar(double targetMar) {
		this.targetMar = targetMar;
	}

	public double getTargetMay() {
		return this.targetMay;
	}

	public void setTargetMay(double targetMay) {
		this.targetMay = targetMay;
	}

	public double getTargetNov() {
		return this.targetNov;
	}

	public void setTargetNov(double targetNov) {
		this.targetNov = targetNov;
	}

	public double getTargetOct() {
		return this.targetOct;
	}

	public void setTargetOct(double targetOct) {
		this.targetOct = targetOct;
	}

	public double getTargetSep() {
		return this.targetSep;
	}

	public void setTargetSep(double targetSep) {
		this.targetSep = targetSep;
	}

	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
