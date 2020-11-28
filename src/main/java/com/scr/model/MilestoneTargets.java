package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "milestone_targets")
@NamedQuery(name = "MilestoneTargets.findAll", query = "SELECT t FROM MilestoneTargets t")
public class MilestoneTargets implements Serializable {
	
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "work_id", foreignKey = @ForeignKey(name = "fk_milestone_targets_works"))
	private Works workId;


	@ManyToOne
	@JoinColumn(name = "work_group_id", foreignKey = @ForeignKey(name = "fk_milestone_targets_work_group"))
	private WorkGroup workGroupId;

	@Column(name = "target_type")
	private String TargetType;
	
	@Column(name = "rkm")
	private Double rkm;

	@Column(name = "tkm")
	private Double tkm;
	
	@Temporal(TemporalType.DATE)
	private Date TargetDate;
	
	@Column(name = "status")
	private String Status;
	
	@Column(name = "remarks")
	private String Remarks;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	public MilestoneTargets() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Works getWorkId() {
		return workId;
	}

	public void setWorkId(Works workId) {
		this.workId = workId;
	}

	public WorkGroup getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(WorkGroup workGroupId) {
		this.workGroupId = workGroupId;
	}

	public String getTargetType() {
		return TargetType;
	}

	public void setTargetType(String targetType) {
		TargetType = targetType;
	}

	public Double getRkm() {
		return rkm;
	}

	public void setRkm(Double rkm) {
		this.rkm = rkm;
	}

	public Double getTkm() {
		return tkm;
	}

	public void setTkm(Double tkm) {
		this.tkm = tkm;
	}

	public Date getTargetDate() {
		return TargetDate;
	}

	public void setTargetDate(Date targetDate) {
		TargetDate = targetDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
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

	@Override
	public String toString() {
		return "MilestoneTargets [id=" + id + ", workGroupId=" + workGroupId + ", TargetType=" + TargetType + ", rkm="
				+ rkm + ", tkm=" + tkm + ", TargetDate=" + TargetDate + ", Status=" + Status + ", Remarks=" + Remarks
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + "]";
	}

}
