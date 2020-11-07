package com.scr.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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

	@ManyToOne
	@JoinColumn(name = "work_phase_id", foreignKey = @ForeignKey(name = "fk_work_phase_activity_work_phases"))
	private WorkPhases workPhaseId;

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

	@Override
	public String toString() {
		return "WorkPhaseActivity [id=" + id + ", name=" + name + ", depotType=" + depotType + ", assetType="
				+ assetType + ", isObjectIdRequired=" + isObjectIdRequired + ", isCheckList=" + isCheckList
				+ ", description=" + description + ", sequence=" + sequence + ", dependencyToStart=" + dependencyToStart
				+ ", workPhaseId=" + workPhaseId + "]";
	}

}
