package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the elementary_sections database table.
 * 
 */
@Entity
// @Table(name="elementary_sections")

@Table(name = "elementary_sections" , uniqueConstraints={@UniqueConstraint(name = "old_pk_elementary_sections_uniq", columnNames ={"data_div", "seq_id"})})
//
@NamedQuery(name="ElementarySection.findAll", query="SELECT e FROM ElementarySection e")
public class ElementarySection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="alternate_supply")
	private String alternateSupply;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String description;

	@Column(name="devision_id")
	private String devisionId;

	@Column(name="elementary_section_code")
	private String elementarySectionCode;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="from_km")
	private String fromKm;

	@Column(name="from_seq")
	private String fromSeq;

	@Column(name="is_auto_dead")
	private String isAutoDead;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String longitudinal;

	@Column(name="longitudinal_dn")
	private String longitudinalDn;

	@Column(name="multi_es_remark")
	private String multiEsRemark;

	@Column(name="protection_crossover")
	private String protectionCrossover;

	@Column(name="protection_turnout")
	private String protectionTurnout;

	@Column(name="remarks_no")
	private String remarksNo;

	@Column(name="remarks_shunting")
	private String remarksShunting;

	@Column(name="section_code")
	private String sectionCode;

	@Column(name="sector_code")
	private String sectorCode;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="siding_main")
	private String sidingMain;

	@Column(name="station_code")
	private String stationCode;

	@Column(name="sub_sector_code")
	private String subSectorCode;

	@Column(name="to_km")
	private String toKm;

	@Column(name="to_seq")
	private String toSeq;

	@Column(name="tpc_board")
	private String tpcBoard;

	@Column(name="track_code")
	private String trackCode;

	public ElementarySection() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlternateSupply() {
		return this.alternateSupply;
	}

	public void setAlternateSupply(String alternateSupply) {
		this.alternateSupply = alternateSupply;
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

	public String getDevisionId() {
		return this.devisionId;
	}

	public void setDevisionId(String devisionId) {
		this.devisionId = devisionId;
	}

	public String getElementarySectionCode() {
		return this.elementarySectionCode;
	}

	public void setElementarySectionCode(String elementarySectionCode) {
		this.elementarySectionCode = elementarySectionCode;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFromKm() {
		return this.fromKm;
	}

	public void setFromKm(String fromKm) {
		this.fromKm = fromKm;
	}

	public String getFromSeq() {
		return this.fromSeq;
	}

	public void setFromSeq(String fromSeq) {
		this.fromSeq = fromSeq;
	}

	public String getIsAutoDead() {
		return this.isAutoDead;
	}

	public void setIsAutoDead(String isAutoDead) {
		this.isAutoDead = isAutoDead;
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

	public String getLongitudinal() {
		return this.longitudinal;
	}

	public void setLongitudinal(String longitudinal) {
		this.longitudinal = longitudinal;
	}

	public String getLongitudinalDn() {
		return this.longitudinalDn;
	}

	public void setLongitudinalDn(String longitudinalDn) {
		this.longitudinalDn = longitudinalDn;
	}

	public String getMultiEsRemark() {
		return this.multiEsRemark;
	}

	public void setMultiEsRemark(String multiEsRemark) {
		this.multiEsRemark = multiEsRemark;
	}

	public String getProtectionCrossover() {
		return this.protectionCrossover;
	}

	public void setProtectionCrossover(String protectionCrossover) {
		this.protectionCrossover = protectionCrossover;
	}

	public String getProtectionTurnout() {
		return this.protectionTurnout;
	}

	public void setProtectionTurnout(String protectionTurnout) {
		this.protectionTurnout = protectionTurnout;
	}

	public String getRemarksNo() {
		return this.remarksNo;
	}

	public void setRemarksNo(String remarksNo) {
		this.remarksNo = remarksNo;
	}

	public String getRemarksShunting() {
		return this.remarksShunting;
	}

	public void setRemarksShunting(String remarksShunting) {
		this.remarksShunting = remarksShunting;
	}

	public String getSectionCode() {
		return this.sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getSectorCode() {
		return this.sectorCode;
	}

	public void setSectorCode(String sectorCode) {
		this.sectorCode = sectorCode;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSidingMain() {
		return this.sidingMain;
	}

	public void setSidingMain(String sidingMain) {
		this.sidingMain = sidingMain;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getSubSectorCode() {
		return this.subSectorCode;
	}

	public void setSubSectorCode(String subSectorCode) {
		this.subSectorCode = subSectorCode;
	}

	public String getToKm() {
		return this.toKm;
	}

	public void setToKm(String toKm) {
		this.toKm = toKm;
	}

	public String getToSeq() {
		return this.toSeq;
	}

	public void setToSeq(String toSeq) {
		this.toSeq = toSeq;
	}

	public String getTpcBoard() {
		return this.tpcBoard;
	}

	public void setTpcBoard(String tpcBoard) {
		this.tpcBoard = tpcBoard;
	}

	public String getTrackCode() {
		return this.trackCode;
	}

	public void setTrackCode(String trackCode) {
		this.trackCode = trackCode;
	}

}