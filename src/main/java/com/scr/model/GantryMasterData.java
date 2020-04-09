package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the gantry_master_data database table.
 * 
 */
@Entity
// @Table(name="gantry_master_data")

@Table(name = "gantry_master_data" , uniqueConstraints={@UniqueConstraint(name = "old_pk_gantry_master_data_uniq", columnNames ={"data_div", "seq_id"})})

@NamedQuery(name="GantryMasterData.findAll", query="SELECT g FROM GantryMasterData g")
public class GantryMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String description;

	private String division;

	@Column(name="elementary_sections")
	private String elementarySections;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="from_location")
	private String fromLocation;

	@Column(name="from_location_type")
	private String fromLocationType;

	@Column(name="gantry_code")
	private String gantryCode;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="normally_open")
	private String normallyOpen;

	@Column(name="protection_longitudnal_dn")
	private String protectionLongitudnalDn;

	@Column(name="protection_longitudnal_up")
	private String protectionLongitudnalUp;

	@Column(name="protection_traverse_crossover")
	private String protectionTraverseCrossover;

	@Column(name="protection_traverse_turnout")
	private String protectionTraverseTurnout;

	private String remarks;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="to_location")
	private String toLocation;

	@Column(name="to_location_type")
	private String toLocationType;

	@Column(name="tpc_board")
	private String tpcBoard;

	public GantryMasterData() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getElementarySections() {
		return this.elementarySections;
	}

	public void setElementarySections(String elementarySections) {
		this.elementarySections = elementarySections;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFromLocation() {
		return this.fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getFromLocationType() {
		return this.fromLocationType;
	}

	public void setFromLocationType(String fromLocationType) {
		this.fromLocationType = fromLocationType;
	}

	public String getGantryCode() {
		return this.gantryCode;
	}

	public void setGantryCode(String gantryCode) {
		this.gantryCode = gantryCode;
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

	public String getNormallyOpen() {
		return this.normallyOpen;
	}

	public void setNormallyOpen(String normallyOpen) {
		this.normallyOpen = normallyOpen;
	}

	public String getProtectionLongitudnalDn() {
		return this.protectionLongitudnalDn;
	}

	public void setProtectionLongitudnalDn(String protectionLongitudnalDn) {
		this.protectionLongitudnalDn = protectionLongitudnalDn;
	}

	public String getProtectionLongitudnalUp() {
		return this.protectionLongitudnalUp;
	}

	public void setProtectionLongitudnalUp(String protectionLongitudnalUp) {
		this.protectionLongitudnalUp = protectionLongitudnalUp;
	}

	public String getProtectionTraverseCrossover() {
		return this.protectionTraverseCrossover;
	}

	public void setProtectionTraverseCrossover(String protectionTraverseCrossover) {
		this.protectionTraverseCrossover = protectionTraverseCrossover;
	}

	public String getProtectionTraverseTurnout() {
		return this.protectionTraverseTurnout;
	}

	public void setProtectionTraverseTurnout(String protectionTraverseTurnout) {
		this.protectionTraverseTurnout = protectionTraverseTurnout;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getToLocation() {
		return this.toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getToLocationType() {
		return this.toLocationType;
	}

	public void setToLocationType(String toLocationType) {
		this.toLocationType = toLocationType;
	}

	public String getTpcBoard() {
		return this.tpcBoard;
	}

	public void setTpcBoard(String tpcBoard) {
		this.tpcBoard = tpcBoard;
	}

}