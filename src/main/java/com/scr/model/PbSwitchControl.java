package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the pb_switch_control database table.
 * 
 */
@Entity
@Table(name = "pb_switch_control" , uniqueConstraints={@UniqueConstraint(name = "old_pk_pb_switch_control_uniq", columnNames ={"data_div", "seq_id"})})
@NamedQuery(name="PbSwitchControl.findAll", query="SELECT p FROM PbSwitchControl p")
public class PbSwitchControl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="is_normally_opened")
	private String isNormallyOpened;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	private String line;

	@Column(name="pb_extent_code")
	private String pbExtentCode;

	@Column(name="pb_extent_type")
	private String pbExtentType;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="switch_id")
	private String switchId;

	@Column(name="switch_type")
	private String switchType;

	@Column(name="up_dn")
	private String upDn;

	public PbSwitchControl() {
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

	public String getLine() {
		return this.line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getPbExtentCode() {
		return this.pbExtentCode;
	}

	public void setPbExtentCode(String pbExtentCode) {
		this.pbExtentCode = pbExtentCode;
	}

	public String getPbExtentType() {
		return this.pbExtentType;
	}

	public void setPbExtentType(String pbExtentType) {
		this.pbExtentType = pbExtentType;
	}

	public String getSeqId() {
		return this.seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getSwitchId() {
		return this.switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}

	public String getSwitchType() {
		return this.switchType;
	}

	public void setSwitchType(String switchType) {
		this.switchType = switchType;
	}

	public String getUpDn() {
		return this.upDn;
	}

	public void setUpDn(String upDn) {
		this.upDn = upDn;
	}

}
