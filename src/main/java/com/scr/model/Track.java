package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
	private Integer createdBy;
	private Integer updatedBy;
	private Timestamp createdOn;
	private Timestamp updatedOn;
	@ManyToOne
	@JoinColumn(name = "facility_id")
	private Facility facilityId;
	private Integer tkm;
	private Integer rkm;
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
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

	public Integer getTkm() {
		return tkm;
	}

	public void setTkm(Integer tkm) {
		this.tkm = tkm;
	}

	public Integer getRkm() {
		return rkm;
	}

	public void setRkm(Integer rkm) {
		this.rkm = rkm;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Track [id=" + id + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", createdOn=" + createdOn
				+ ", updatedOn=" + updatedOn + ", facilityId=" + facilityId + ", tkm=" + tkm + ", rkm=" + rkm
				+ ", remark=" + remark + "]";
	}

}