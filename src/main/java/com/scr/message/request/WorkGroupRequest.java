package com.scr.message.request;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.scr.model.Works;




public class WorkGroupRequest {
	
	private Integer id;

	private String workGroup;

	private String section;

	private String division;

	private String zone;

	private String agency;

	private String doublingTrippling;

	private String sidingYardStation;

	private String code;

	private String description;

	private Double rkm;

	private Double tkm;

	private String lineType;

	private String createdBy;

	private String updatedBy;

	private Timestamp createdOn;

	private Timestamp updatedOn;
	
	private Works workId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Works getWorkId() {
		return workId;
	}

	public void setWorkId(Works workId) {
		this.workId = workId;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getDoublingTrippling() {
		return doublingTrippling;
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

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
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

	


}
