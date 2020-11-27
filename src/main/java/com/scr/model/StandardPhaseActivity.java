package com.scr.model;

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

@Entity
@Table(name = "standardPhaseActivity")
@NamedQuery(name = "StandardPhaseActivity.findAll", query = "SELECT spa FROM StandardPhaseActivity spa")
public class StandardPhaseActivity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String depotType;

	private String assetType;

	private String isObjectIdRequired;

	private String isCheckList;

	private String uom;
	
	private String description;

	
	@Column(name = "dependency_to_start")
	private String dependencyToStart;
	
	

	@ManyToOne
	@JoinColumn(name = "standard_phase_id", foreignKey = @ForeignKey(name = "fk_standard_phase_activity_standard_phases"))
	private StandardPhases standardPhaseId;

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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public StandardPhases getStandardPhaseId() {
		return standardPhaseId;
	}

	public void setStandardPhaseId(StandardPhases standardPhaseId) {
		this.standardPhaseId = standardPhaseId;
	}
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDependencyToStart() {
		return dependencyToStart;
	}

	public void setDependencyToStart(String dependencyToStart) {
		this.dependencyToStart = dependencyToStart;
	}

	@Override
	public String toString() {
		return "StandardPhaseActivity [id=" + id + ", name=" + name + ", depotType=" + depotType + ", assetType="
				+ assetType + ", isObjectIdRequired=" + isObjectIdRequired + ", isCheckList=" + isCheckList + ", uom="
				+ uom + ", description=" + description + ", dependencyToStart=" + dependencyToStart
				+ ", standardPhaseId=" + standardPhaseId + "]";
	}

}
