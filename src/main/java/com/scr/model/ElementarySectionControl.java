package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the elementary_section_controls database table.
 * 
 */
@Entity
// @Table(name="elementary_section_controls")

@Table(name = "elementary_section_controls" , uniqueConstraints={@UniqueConstraint(name = "old_pk_elementary_section_controls_uniq", columnNames ={"data_div", "seq_id"})})
//
@NamedQuery(name="ElementarySectionControl.findAll", query="SELECT e FROM ElementarySectionControl e")
public class ElementarySectionControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="elementary_section")
	private String elementarySection;

	@Column(name="is_normally_opened")
	private String isNormallyOpened;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="sub_sector_from")
	private String subSectorFrom;

	@Column(name="sub_sector_side")
	private String subSectorSide;

	@Column(name="sub_sector_to")
	private String subSectorTo;

	@Column(name="switch_code")
	private String switchCode;

	@Column(name="switch_type")
	private String switchType;

	public ElementarySectionControl() {
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

	public String getElementarySection() {
		return this.elementarySection;
	}

	public void setElementarySection(String elementarySection) {
		this.elementarySection = elementarySection;
	}

	public String getIsNormallyOpened() {
		return this.isNormallyOpened;
	}

	public void setIsNormallyOpened(String isNormallyOpened) {
		this.isNormallyOpened = isNormallyOpened;
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

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSubSectorFrom() {
		return this.subSectorFrom;
	}

	public void setSubSectorFrom(String subSectorFrom) {
		this.subSectorFrom = subSectorFrom;
	}

	public String getSubSectorSide() {
		return this.subSectorSide;
	}

	public void setSubSectorSide(String subSectorSide) {
		this.subSectorSide = subSectorSide;
	}

	public String getSubSectorTo() {
		return this.subSectorTo;
	}

	public void setSubSectorTo(String subSectorTo) {
		this.subSectorTo = subSectorTo;
	}

	public String getSwitchCode() {
		return this.switchCode;
	}

	public void setSwitchCode(String switchCode) {
		this.switchCode = switchCode;
	}

	public String getSwitchType() {
		return this.switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

}