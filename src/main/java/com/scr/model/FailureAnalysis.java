package com.scr.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="failure_analysis")
public class FailureAnalysis implements Serializable {	
	private static final long serialVersionUID = 1L;
	

	@Id 
	private long id;

	@Column(name = "failure_id")
	private String failure_id;
	@Column(name = "reported")
	private String reported;
	@Column(name = "repurcussion")
	private String repurcussion;
	@Column(name = "date")
	private Timestamp date;
	@Column(name = "div")
	private BigInteger div;
	@Column(name = "section")
	private BigInteger section;
	@Column(name = "asset_type")
	private BigInteger assetType;
	@Column(name = "asset_id")
	private BigInteger assetId;
	@Column(name = "sub_asset_type")
	private String subAssetType;
	@Column(name = "sub_asset_id")
	private String subAssetId;
	@Column(name = "make")
	private BigInteger make;
	@Column(name = "model")
	private BigInteger model;
	@Column(name = "root_cause")
	private String rootCause;
	@Column(name = "action_plan")
	private String actionPlan;
	@Column(name = "action_status")
	private BigInteger actionStatus;
	@Column(name = "approved_by")
	private String approvedBy;
	@Column(name = "action_target_date")
	private Timestamp actionTargetDate;
	@Column(name = "action_completed_date")
	private Timestamp actionCompletedDate;
	@Column(name = "action_description")
	private String actionDescription;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	@Column(name = "status_id")
	private Integer statusId;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public String getFailure_id() {
		return failure_id;
	}

	public void setFailure_id(String failure_id) {
		this.failure_id = failure_id;
	}

	public String getReported() {
		return reported;
	}

	public void setReported(String reported) {
		this.reported = reported;
	}

	public String getRepurcussion() {
		return repurcussion;
	}

	public void setRepurcussion(String repurcussion) {
		this.repurcussion = repurcussion;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public BigInteger getDiv() {
		return div;
	}

	public void setDiv(BigInteger div) {
		this.div = div;
	}

	public BigInteger getSection() {
		return section;
	}

	public void setSection(BigInteger section) {
		this.section = section;
	}

	public BigInteger getAssetType() {
		return assetType;
	}

	public void setAssetType(BigInteger assetType) {
		this.assetType = assetType;
	}

	public BigInteger getAssetId() {
		return assetId;
	}

	public void setAssetId(BigInteger assetId) {
		this.assetId = assetId;
	}

	public String getSubAssetType() {
		return subAssetType;
	}

	public void setSubAssetType(String subAssetType) {
		this.subAssetType = subAssetType;
	}

	public String getSubAssetId() {
		return subAssetId;
	}

	public void setSubAssetId(String subAssetId) {
		this.subAssetId = subAssetId;
	}

	public BigInteger getMake() {
		return make;
	}

	public void setMake(BigInteger make) {
		this.make = make;
	}

	public BigInteger getModel() {
		return model;
	}

	public void setModel(BigInteger model) {
		this.model = model;
	}

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getActionPlan() {
		return actionPlan;
	}

	public void setActionPlan(String actionPlan) {
		this.actionPlan = actionPlan;
	}

	public BigInteger getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(BigInteger actionStatus) {
		this.actionStatus = actionStatus;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Timestamp getActionTargetDate() {
		return actionTargetDate;
	}

	public void setActionTargetDate(Timestamp actionTargetDate) {
		this.actionTargetDate = actionTargetDate;
	}

	public Timestamp getActionCompletedDate() {
		return actionCompletedDate;
	}

	public void setActionCompletedDate(Timestamp actionCompletedDate) {
		this.actionCompletedDate = actionCompletedDate;
	}

	public String getActionDescription() {
		return actionDescription;
	}

	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
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

	

}
