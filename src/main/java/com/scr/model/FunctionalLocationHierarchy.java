package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the functional_location_hierarchy database table.
 * 
 */
@Entity
// @Table(name="functional_location_hierarchy")

@Table(name = "functional_location_hierarchy" , uniqueConstraints={@UniqueConstraint(name = "old_pk_functional_location_hierarchy_uniq", columnNames ={"party_id", "data_div"})})
//
@NamedQuery(name="FunctionalLocationHierarchy.findAll", query="SELECT f FROM FunctionalLocationHierarchy f")
public class FunctionalLocationHierarchy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="group_id")
	private String groupId;

	@Column(name="head_designation")
	private String headDesignation;

	@Column(name="head_login_id")
	private String headLoginId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="org_level")
	private String orgLevel;

	@Column(name="party_id")
	private String partyId;

	@Column(name="report_manager")
	private String reportManager;

	@Column(name="rm_login_id")
	private String rmLoginId;

	@Column(name="rm_seq_id")
	private String rmSeqId;

	@Column(name="unit_code")
	private String unitCode;

	@Column(name="unit_name")
	private String unitName;

	@Column(name="unit_station")
	private String unitStation;

	@Column(name="unit_type")
	private String unitType;

	public FunctionalLocationHierarchy() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreatedStamp() {
		return this.createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return this.createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getHeadDesignation() {
		return this.headDesignation;
	}

	public void setHeadDesignation(String headDesignation) {
		this.headDesignation = headDesignation;
	}

	public String getHeadLoginId() {
		return this.headLoginId;
	}

	public void setHeadLoginId(String headLoginId) {
		this.headLoginId = headLoginId;
	}

	public Timestamp getLastUpdatedStamp() {
		return this.lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return this.lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getOrgLevel() {
		return this.orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getReportManager() {
		return this.reportManager;
	}

	public void setReportManager(String reportManager) {
		this.reportManager = reportManager;
	}

	public String getRmLoginId() {
		return this.rmLoginId;
	}

	public void setRmLoginId(String rmLoginId) {
		this.rmLoginId = rmLoginId;
	}

	public String getRmSeqId() {
		return this.rmSeqId;
	}

	public void setRmSeqId(String rmSeqId) {
		this.rmSeqId = rmSeqId;
	}

	public String getUnitCode() {
		return this.unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitStation() {
		return this.unitStation;
	}

	public void setUnitStation(String unitStation) {
		this.unitStation = unitStation;
	}

	public String getUnitType() {
		return this.unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

}