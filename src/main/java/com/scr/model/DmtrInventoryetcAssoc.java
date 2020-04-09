package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the dmtr_inventoryetc_assoc database table.
 * 
 */
@Entity
@Table(name = "dmtr_inventoryetc_assoc" , uniqueConstraints={@UniqueConstraint(name = "old_pk_dmtr_inventoryetc_assoc_uniq", columnNames ={"data_div", "dmtr_inventoryetc_assoc_seq_id"})})
@NamedQuery(name="DmtrInventoryetcAssoc.findAll", query="SELECT d FROM DmtrInventoryetcAssoc d")
public class DmtrInventoryetcAssoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="dmtr_inventoryetc_assoc_seq_id")
	private String dmtrInventoryetcAssocSeqId;

	@Column(name="dmtr_seq_id")
	private String dmtrSeqId;

	@Column(name="inventory_item_detail_seq_id")
	private String inventoryItemDetailSeqId;

	@Column(name="inventory_item_id")
	private String inventoryItemId;

	@Column(name="inventory_transfer_id")
	private String inventoryTransferId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	public DmtrInventoryetcAssoc() {
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

	public String getDmtrInventoryetcAssocSeqId() {
		return this.dmtrInventoryetcAssocSeqId;
	}

	public void setDmtrInventoryetcAssocSeqId(String dmtrInventoryetcAssocSeqId) {
		this.dmtrInventoryetcAssocSeqId = dmtrInventoryetcAssocSeqId;
	}

	public String getDmtrSeqId() {
		return this.dmtrSeqId;
	}

	public void setDmtrSeqId(String dmtrSeqId) {
		this.dmtrSeqId = dmtrSeqId;
	}

	public String getInventoryItemDetailSeqId() {
		return this.inventoryItemDetailSeqId;
	}

	public void setInventoryItemDetailSeqId(String inventoryItemDetailSeqId) {
		this.inventoryItemDetailSeqId = inventoryItemDetailSeqId;
	}

	public String getInventoryItemId() {
		return this.inventoryItemId;
	}

	public void setInventoryItemId(String inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public String getInventoryTransferId() {
		return this.inventoryTransferId;
	}

	public void setInventoryTransferId(String inventoryTransferId) {
		this.inventoryTransferId = inventoryTransferId;
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

}
