package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "track")
@NamedQuery(name = "Track.findAll", query = "SELECT t FROM Track t")
public class Track implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="created_by")
	private String createdBy;

	@Column(name="updated_by")
	private String updatedBy;
	
	private Timestamp createdOn;
	private Timestamp updatedOn;
	@ManyToOne
	@JoinColumn(name = "facility_id")
	private Facility facilityId;
	private Double tkm;
	private Double rkm;
	private String remark;
	@Column(name = "electrified_rkm")
	private Double electrifiedRkm;
	@Column(name = "electrified_tkm")
	private Double electrifiedTkm;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String UpdatedBy) {
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

	public Facility getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Facility facilityId) {
		this.facilityId = facilityId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getTkm() {
		return tkm;
	}

	public void setTkm(Double tkm) {
		this.tkm = tkm;
	}

	public Double getRkm() {
		return rkm;
	}

	public void setRkm(Double rkm) {
		this.rkm = rkm;
	}

	public Double getElectrifiedRkm() {
		return electrifiedRkm;
	}

	public void setElectrifiedRkm(Double electrifiedRkm) {
		this.electrifiedRkm = electrifiedRkm;
	}

	public Double getElectrifiedTkm() {
		return electrifiedTkm;
	}

	public void setElectrifiedTkm(Double electrifiedTkm) {
		this.electrifiedTkm = electrifiedTkm;
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", facilityId=" + facilityId + ", tkm=" + tkm + ", rkm=" + rkm
				+ ", remark=" + remark + "]";
	}

}