package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tss_feeder_master database table.
 * 
 */
@Entity
@Table(name = "tss_feeder_master" , uniqueConstraints={@UniqueConstraint(name = "old_pk_tss_feeder_master_uniq", columnNames ={"data_div", "feeder_id"})})
@NamedQuery(name="TssFeederMaster.findAll", query="SELECT t FROM TssFeederMaster t")
public class TssFeederMaster implements Serializable {
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

	@Column(name="feeder_id")
	private String feederId;

	@Column(name="feeder_name")
	private String feederName;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="state_electricity_board")
	private String stateElectricityBoard;

	@Column(name="tss_name")
	private String tssName;

	public TssFeederMaster() {
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

	public String getFeederId() {
		return this.feederId;
	}

	public void setFeederId(String feederId) {
		this.feederId = feederId;
	}

	public String getFeederName() {
		return this.feederName;
	}

	public void setFeederName(String feederName) {
		this.feederName = feederName;
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

	public String getStateElectricityBoard() {
		return this.stateElectricityBoard;
	}

	public void setStateElectricityBoard(String stateElectricityBoard) {
		this.stateElectricityBoard = stateElectricityBoard;
	}

	public String getTssName() {
		return this.tssName;
	}

	public void setTssName(String tssName) {
		this.tssName = tssName;
	}

}