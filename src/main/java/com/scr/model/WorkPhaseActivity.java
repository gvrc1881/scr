package com.scr.model;

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
@Table(name = "workPhaseActivity")
@NamedQuery(name = "WorkPhaseActivity.findAll", query = "SELECT wpa FROM WorkPhaseActivity wpa")
public class WorkPhaseActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 

	private String name;

	private String depotType;

	private String assetType;

	private String isObjectIdRequired;

	private String isCheckList;

	private String description;

	private Double sequence;

	private String dependencyToStart;
	
	private String uom;
	
	@Temporal(TemporalType.DATE)
	private Date plannedStartDate;

	@Temporal(TemporalType.DATE)
	private Date commenceDate;

	@Temporal(TemporalType.DATE)
	private Date targetCompletionDate;

	@Temporal(TemporalType.DATE)
	private Date completionDate;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;

	@ManyToOne
	@JoinColumn(name = "work_phase_id", foreignKey = @ForeignKey(name = "fk_work_phase_activity_work_phases"))
	private WorkPhases workPhaseId;
	
	@ManyToOne
	@JoinColumn(name = "test_inspection_id", foreignKey = @ForeignKey(name = "fk_work_phase_activity_test_inspection"))
	private TestInspection testInspectionId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepotType() {
		return depotType;
	}

	public void setDepotType(String depotType) {
		this.depotType = depotType;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getIsObjectIdRequired() {
		return isObjectIdRequired;
	}

	public void setIsObjectIdRequired(String isObjectIdRequired) {
		this.isObjectIdRequired = isObjectIdRequired;
	}

	public String getIsCheckList() {
		return isCheckList;
	}

	public void setIsCheckList(String isCheckList) {
		this.isCheckList = isCheckList;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getSequence() {
		return sequence;
	}

	public void setSequence(Double sequence) {
		this.sequence = sequence;
	}

	public String getDependencyToStart() {
		return dependencyToStart;
	}

	public void setDependencyToStart(String dependencyToStart) {
		this.dependencyToStart = dependencyToStart;
	}

	public WorkPhases getWorkPhaseId() {
		return workPhaseId;
	}

	public void setWorkPhaseId(WorkPhases workPhaseId) {
		this.workPhaseId = workPhaseId;
	}

	public String getUom() {
		return uom;
	}

	public Date getPlannedStartDate() {
		return plannedStartDate;
	}

	public void setPlannedStartDate(Date plannedStartDate) {
		this.plannedStartDate = plannedStartDate;
	}

	public Date getCommenceDate() {
		return commenceDate;
	}

	public void setCommenceDate(Date commenceDate) {
		this.commenceDate = commenceDate;
	}

	public Date getTargetCompletionDate() {
		return targetCompletionDate;
	}

	public void setTargetCompletionDate(Date targetCompletionDate) {
		this.targetCompletionDate = targetCompletionDate;
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

	public void setUom(String uom) {
		this.uom = uom;
	}

	public TestInspection getTestInspectionId() {
		return testInspectionId;
	}

	public void setTestInspectionId(TestInspection testInspectionId) {
		this.testInspectionId = testInspectionId;
	}

	@Override
	public String toString() {
		return "WorkPhaseActivity [id=" + id + ", name=" + name + ", depotType=" + depotType + ", assetType="
				+ assetType + ", isObjectIdRequired=" + isObjectIdRequired + ", isCheckList=" + isCheckList
				+ ", description=" + description + ", sequence=" + sequence + ", dependencyToStart=" + dependencyToStart
				+ ", uom=" + uom + ", plannedStartDate=" + plannedStartDate + ", commenceDate=" + commenceDate
				+ ", targetCompletionDate=" + targetCompletionDate + ", completionDate=" + completionDate
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn + ", updatedOn="
				+ updatedOn + ", workPhaseId=" + workPhaseId + ", testInspectionId=" + testInspectionId + "]";
	}

	

}
