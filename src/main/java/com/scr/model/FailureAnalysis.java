package com.scr.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "failure_analysis")
public class FailureAnalysis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "failure_id")
	private String failureId;
	@Column(name = "reported")
	private String reported;
	@Column(name = "reportDescription")
	private String reportDescription;
	@Column(name = "repurcussion")
	private String repurcussion;
	@Column(name = "date")
	private Timestamp date;
	@Column(name = "div")
	private String div;
	@Column(name = "section")
	private String section;
	@Column(name = "asset_type")
	private String assetType;
	@Column(name = "asset_id")
	private String assetId;
	@Column(name = "sub_asset_type")
	private String subAssetType;
	@Column(name = "sub_asset_id")
	private String subAssetId;
	@Column(name = "make")
	private String make;
	@Column(name = "model")
	private String model;
	@Column(name = "root_cause")
	private String rootCause;
	@Column(name = "action_plan")
	private String actionPlan;
	@Column(name = "action_status")
	private String actionStatus;
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

	@Column(name = "description")
	private String description;
	
	@Column(name = "avoidable")
	private String avoidable;
	
	@Column(name = "remark_details")
	private String remarkDetails;
	
	@Column(name = "remark_brief")
	private String remarkBrief;
	
	private String contentLink;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	public String getReported() {
		return reported;
	}

	public void setReported(String reported) {
		this.reported = reported;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
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

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

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

	public String getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(String actionStatus) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFailureId() {
		return failureId;
	}

	public void setFailureId(String failureId) {
		this.failureId = failureId;
	}

	public String getAvoidable() {
		return avoidable;
	}

	public void setAvoidable(String avoidable) {
		this.avoidable = avoidable;
	}

	public String getRemarkDetails() {
		return remarkDetails;
	}

	public void setRemarkDetails(String remarkDetails) {
		this.remarkDetails = remarkDetails;
	}

	public String getRemarkBrief() {
		return remarkBrief;
	}

	public void setRemarkBrief(String remarkBrief) {
		this.remarkBrief = remarkBrief;
	}

	public String getContentLink() {
		return contentLink;
	}

	public void setContentLink(String contentLink) {
		this.contentLink = contentLink;
	}

	@Override
	public String toString() {
		return "FailureAnalysis [id=" + id + ", failureId=" + failureId + ", reported=" + reported
				+ ", reportDescription=" + reportDescription + ", repurcussion=" + repurcussion + ", date=" + date
				+ ", div=" + div + ", section=" + section + ", assetType=" + assetType + ", assetId=" + assetId
				+ ", subAssetType=" + subAssetType + ", subAssetId=" + subAssetId + ", make=" + make + ", model="
				+ model + ", rootCause=" + rootCause + ", actionPlan=" + actionPlan + ", actionStatus=" + actionStatus
				+ ", approvedBy=" + approvedBy + ", actionTargetDate=" + actionTargetDate + ", actionCompletedDate="
				+ actionCompletedDate + ", actionDescription=" + actionDescription + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", statusId="
				+ statusId + ", description=" + description + ", avoidable=" + avoidable + ", remarkDetails="
				+ remarkDetails + ", remarkBrief=" + remarkBrief + ", contentLink=" + contentLink + "]";
	}

	

}
