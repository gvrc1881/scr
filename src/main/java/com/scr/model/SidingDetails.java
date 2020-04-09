package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "siding_details", uniqueConstraints = {
		@UniqueConstraint(name = "siding_details_siding_code_key", columnNames = { "siding_code" }) })

public class SidingDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "station")
	private String station;

	@Column(name = "siding_code")
	private String sidingCode;

	@Column(name = "section")
	private String section;

	@Column(name = "section_eletrified_status")
	private String sectionEletrifiedStatus;

	@Column(name = "siding_eletrified_status")
	private String sidingEletrifiedStatus;

	@Column(name = "private_Railway")
	private String privateRailway;

	@Column(name = "status")
	private String status;

	@Column(name = "TKM")
	private double TKM;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "siding_proposed")
	private String sidingProposed;

	@Column(name = "proposed_date")
	private Date proposedDate;

	@Column(name = "approval_date")
	private Date approvalDate;

	@Column(name = "work_order_date")
	private Date workOrderDate;

	@Column(name = "work_progress_percentage")
	private double workProgressPercentage;

	@Column(name = "work_progress_remark")
	private String workProgressRemark;

	@Column(name = "executing_agency")
	private String executingAgency;

	@Column(name = "estd_latest_antic_cost")
	private double estdLatestAnticCost;

	@Column(name = "completion_date")
	private Date completionDate;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getSidingCode() {
		return sidingCode;
	}

	public void setSidingCode(String sidingCode) {
		this.sidingCode = sidingCode;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSectionEletrifiedStatus() {
		return sectionEletrifiedStatus;
	}

	public void setSectionEletrifiedStatus(String sectionEletrifiedStatus) {
		this.sectionEletrifiedStatus = sectionEletrifiedStatus;
	}

	public String getSidingEletrifiedStatus() {
		return sidingEletrifiedStatus;
	}

	public void setSidingEletrifiedStatus(String sidingEletrifiedStatus) {
		this.sidingEletrifiedStatus = sidingEletrifiedStatus;
	}

	public String getPrivateRailway() {
		return privateRailway;
	}

	public void setPrivateRailway(String privateRailway) {
		this.privateRailway = privateRailway;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTKM() {
		return TKM;
	}

	public void setTKM(double tKM) {
		TKM = tKM;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSidingProposed() {
		return sidingProposed;
	}

	public void setSidingProposed(String sidingProposed) {
		this.sidingProposed = sidingProposed;
	}

	public Date getProposedDate() {
		return proposedDate;
	}

	public void setProposedDate(Date proposedDate) {
		this.proposedDate = proposedDate;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Date getWorkOrderDate() {
		return workOrderDate;
	}

	public void setWorkOrderDate(Date workOrderDate) {
		this.workOrderDate = workOrderDate;
	}

	public double getWorkProgressPercentage() {
		return workProgressPercentage;
	}

	public void setWorkProgressPercentage(double workProgressPercentage) {
		this.workProgressPercentage = workProgressPercentage;
	}

	public String getWorkProgressRemark() {
		return workProgressRemark;
	}

	public void setWorkProgressRemark(String workProgressRemark) {
		this.workProgressRemark = workProgressRemark;
	}

	public String getExecutingAgency() {
		return executingAgency;
	}

	public void setExecutingAgency(String executingAgency) {
		this.executingAgency = executingAgency;
	}

	public double getEstdLatestAnticCost() {
		return estdLatestAnticCost;
	}

	public void setEstdLatestAnticCost(double estdLatestAnticCost) {
		this.estdLatestAnticCost = estdLatestAnticCost;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	
}
