package com.scr.app.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FunctionalLocationHierarchyDto {

	public FunctionalLocationHierarchyDto() {
		// TODO Auto-generated constructor stub
	}
	
	String id;
	String headDesignation;
	String unitCode;
	String unitName;
	String unitStation;
	String unitType;
	String reportManager;
	String rmSeqId;
	String groupId;
	String orgLevel;
	String partyId;
	String headLoginId;
	String rmLoginId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHeadDesignation() {
		return headDesignation;
	}
	public void setHeadDesignation(String headDesignation) {
		this.headDesignation = headDesignation;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getUnitStation() {
		return unitStation;
	}
	public void setUnitStation(String unitStation) {
		this.unitStation = unitStation;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getReportManager() {
		return reportManager;
	}
	public void setReportManager(String reportManager) {
		this.reportManager = reportManager;
	}
	public String getRmSeqId() {
		return rmSeqId;
	}
	public void setRmSeqId(String rmSeqId) {
		this.rmSeqId = rmSeqId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getHeadLoginId() {
		return headLoginId;
	}
	public void setHeadLoginId(String headLoginId) {
		this.headLoginId = headLoginId;
	}
	public String getRmLoginId() {
		return rmLoginId;
	}
	public void setRmLoginId(String rmLoginId) {
		this.rmLoginId = rmLoginId;
	}
	
	
}