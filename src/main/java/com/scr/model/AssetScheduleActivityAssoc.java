package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the asset_schedule_activity_assoc database table.
 * 
 */
@Entity
@Table(name = "asset_schedule_activity_assoc" , uniqueConstraints={@UniqueConstraint(name = "old_pk_asset_schedule_activity_assoc_uniq", columnNames ={"seq_id", "data_div"})})
@NamedQuery(name="AssetScheduleActivityAssoc.findAll", query="SELECT a FROM AssetScheduleActivityAssoc a")
public class AssetScheduleActivityAssoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="activity_flag")
	private String activityFlag;

	@Column(name="activity_id")
	private String activityId;

	@Column(name="activity_position_id")
	private String activityPositionId;

	@Column(name="asa_seq_id")
	private String asaSeqId;

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

	@Column(name="display_order")
	private BigDecimal displayOrder;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="lower_limit")
	private String lowerLimit;

	@Column(name="make_code")
	private String makeCode;

	@Column(name="model_code")
	private String modelCode;

	@Column(name="report_column_header")
	private String reportColumnHeader;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="sub_asset_type")
	private String subAssetType;

	@Column(name="upper_limit")
	private String upperLimit;

	public AssetScheduleActivityAssoc() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActivityFlag() {
		return this.activityFlag;
	}

	public void setActivityFlag(String activityFlag) {
		this.activityFlag = activityFlag;
	}

	public String getActivityId() {
		return this.activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityPositionId() {
		return this.activityPositionId;
	}

	public void setActivityPositionId(String activityPositionId) {
		this.activityPositionId = activityPositionId;
	}

	public String getAsaSeqId() {
		return this.asaSeqId;
	}

	public void setAsaSeqId(String asaSeqId) {
		this.asaSeqId = asaSeqId;
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

	public BigDecimal getDisplayOrder() {
		return this.displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
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

	public String getLowerLimit() {
		return this.lowerLimit;
	}

	public void setLowerLimit(String lowerLimit) {
		this.lowerLimit = lowerLimit;
	}

	public String getMakeCode() {
		return this.makeCode;
	}

	public void setMakeCode(String makeCode) {
		this.makeCode = makeCode;
	}

	public String getModelCode() {
		return this.modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getReportColumnHeader() {
		return this.reportColumnHeader;
	}

	public void setReportColumnHeader(String reportColumnHeader) {
		this.reportColumnHeader = reportColumnHeader;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSubAssetType() {
		return this.subAssetType;
	}

	public void setSubAssetType(String subAssetType) {
		this.subAssetType = subAssetType;
	}

	public String getUpperLimit() {
		return this.upperLimit;
	}

	public void setUpperLimit(String upperLimit) {
		this.upperLimit = upperLimit;
	}

}
