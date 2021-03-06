package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the works database table.
 * 
 */
@Entity
@Table(name = "work_group")
@NamedQuery(name = "WorkGroup.findAll", query = "SELECT w FROM WorkGroup w")

public class WorkGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "work_group")
	private String workGroup;

	@Column(name = "section")
	private String section;

	@Column(name = "division")
	private String division;

	@Column(name = "zone")
	private String zone;

	@Column(name = "agency")
	private String agency;

	@Column(name = "doubling_trippling ")
	private String doublingTrippling;

	@Column(name = "siding_yard_station")
	private String sidingYardStation;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@Column(name = "rkm")
	private Double rkm;

	@Column(name = "tkm")
	private Double tkm;
	
	@Column(name = "line_type")
	private String lineType;
	
	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	

	@ManyToOne
	@JoinColumn(name = "work_id", foreignKey = @ForeignKey(name = "fk_work_gorup_works"))
	private Works workId;

	public WorkGroup() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(String workGroup) {
		this.workGroup = workGroup;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getDoublingTrippling() {
		return doublingTrippling;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public void setDoublingTrippling(String doublingTrippling) {
		this.doublingTrippling = doublingTrippling;
	}

	public String getSidingYardStation() {
		return sidingYardStation;
	}

	public void setSidingYardStation(String sidingYardStation) {
		this.sidingYardStation = sidingYardStation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Works getWorkId() {
		return workId;
	}

	public void setWorkId(Works workId) {
		this.workId = workId;
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
		return "WorkGroup [id=" + id + ", workGroup=" + workGroup + ", section=" + section + ", division=" + division
				+ ", zone=" + zone + ", agency=" + agency + ", doublingTrippling=" + doublingTrippling
				+ ", sidingYardStation=" + sidingYardStation + ", code=" + code + ", description=" + description
				+ ", rkm=" + rkm + ", tkm=" + tkm + ", lineType=" + lineType + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn + ", workId="
				+ workId + "]";
	}

}
