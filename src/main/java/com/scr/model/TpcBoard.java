package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tpc_board_reporting_facilitys database table.
 * 
 */
@Entity
@Table(name = "tpc_board" , uniqueConstraints={@UniqueConstraint(name = "old_pk_tpc_board_uniq", columnNames ={"seq_id"})})
@NamedQuery(name="TpcBoard.findAll", query="SELECT t FROM TpcBoard t")
public class TpcBoard implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String description;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="seq_id")
	private String seqId;

	@Column(name="tpc_board")
	private String tpcBoard;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getCreatedStamp() {
		return createdStamp;
	}

	public void setCreatedStamp(Timestamp createdStamp) {
		this.createdStamp = createdStamp;
	}

	public Timestamp getCreatedTxStamp() {
		return createdTxStamp;
	}

	public void setCreatedTxStamp(Timestamp createdTxStamp) {
		this.createdTxStamp = createdTxStamp;
	}

	public String getDataDiv() {
		return dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getLastUpdatedStamp() {
		return lastUpdatedStamp;
	}

	public void setLastUpdatedStamp(Timestamp lastUpdatedStamp) {
		this.lastUpdatedStamp = lastUpdatedStamp;
	}

	public Timestamp getLastUpdatedTxStamp() {
		return lastUpdatedTxStamp;
	}

	public void setLastUpdatedTxStamp(Timestamp lastUpdatedTxStamp) {
		this.lastUpdatedTxStamp = lastUpdatedTxStamp;
	}

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getTpcBoard() {
		return tpcBoard;
	}

	public void setTpcBoard(String tpcBoard) {
		this.tpcBoard = tpcBoard;
	}

	@Override
	public String toString() {
		return "TpcBoard [id=" + id + ", createdStamp=" + createdStamp + ", createdTxStamp=" + createdTxStamp
				+ ", dataDiv=" + dataDiv + ", description=" + description + ", lastUpdatedStamp=" + lastUpdatedStamp
				+ ", lastUpdatedTxStamp=" + lastUpdatedTxStamp + ", seqId=" + seqId + ", tpcBoard=" + tpcBoard + "]";
	}

	

	
}