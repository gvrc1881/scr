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
@Table(name = "inspection_versions")
@NamedQuery(name = "InspectionVersions.findAll", query = "SELECT insv FROM InspectionVersions insv")
public class InspectionVersions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String active;

	private Integer seqNo;

	private String versionName;

	private String remarks;

	@ManyToOne
	@JoinColumn(name = "inspections_id", foreignKey = @ForeignKey(name = "fk_ins_versions_inspections"))
	private Inspections inspectionsId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Inspections getInspectionsId() {
		return inspectionsId;
	}

	public void setInspectionsId(Inspections inspectionsId) {
		this.inspectionsId = inspectionsId;
	}

	@Override
	public String toString() {
		return "InsVersions [id=" + id + ", active=" + active + ", seqNo=" + seqNo + ", versionName=" + versionName
				+ ", remarks=" + remarks + ", inspectionsId=" + inspectionsId + "]";
	}

}
