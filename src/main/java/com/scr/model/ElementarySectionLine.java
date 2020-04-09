package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the elementary_section_lines database table.
 * 
 */
@Entity
// @Table(name="elementary_section_lines")

@Table(name = "elementary_section_lines" , uniqueConstraints={@UniqueConstraint(name = "old_pk_elementary_section_lines_uniq", columnNames ={"seq_id", "data_div"})})
//
@NamedQuery(name="ElementarySectionLine.findAll", query="SELECT e FROM ElementarySectionLine e")
public class ElementarySectionLine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="elementary_section_code")
	private String elementarySectionCode;

	@Column(name="from_km")
	private String fromKm;

	@Column(name="from_location")
	private String fromLocation;

	@Column(name="from_seq")
	private String fromSeq;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String line;

	private String mainline;

	private String remark1;

	private String remark2;

	private String remark3;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="to_km")
	private String toKm;

	@Column(name="to_location")
	private String toLocation;

	@Column(name="to_seq")
	private String toSeq;

	public ElementarySectionLine() {
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

	public String getElementarySectionCode() {
		return this.elementarySectionCode;
	}

	public void setElementarySectionCode(String elementarySectionCode) {
		this.elementarySectionCode = elementarySectionCode;
	}

	public String getFromKm() {
		return this.fromKm;
	}

	public void setFromKm(String fromKm) {
		this.fromKm = fromKm;
	}

	public String getFromLocation() {
		return this.fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getFromSeq() {
		return this.fromSeq;
	}

	public void setFromSeq(String fromSeq) {
		this.fromSeq = fromSeq;
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

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getMainline() {
		return this.mainline;
	}

	public void setMainline(String mainline) {
		this.mainline = mainline;
	}

	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getToKm() {
		return this.toKm;
	}

	public void setToKm(String toKm) {
		this.toKm = toKm;
	}

	public String getToLocation() {
		return this.toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getToSeq() {
		return this.toSeq;
	}

	public void setToSeq(String toSeq) {
		this.toSeq = toSeq;
	}

}