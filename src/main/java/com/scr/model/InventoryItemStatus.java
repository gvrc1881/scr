package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the inventory_item_status database table.
 * 
 */
@Entity
// @Table(name="inventory_item_status")

@Table(name = "inventory_item_status" , uniqueConstraints={@UniqueConstraint(name = "old_pk_inventory_item_status_uniq", columnNames ={"data_div", "status_datetime", "status_id", "inventory_item_id"})})
//
@NamedQuery(name="InventoryItemStatus.findAll", query="SELECT i FROM InventoryItemStatus i")
public class InventoryItemStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="inventory_item_id")
	private String inventoryItemId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="owner_party_id")
	private String ownerPartyId;

	@Column(name="product_id")
	private String productId;

	@Column(name="status_datetime")
	private Timestamp statusDatetime;

	@Column(name="status_end_datetime")
	private Timestamp statusEndDatetime;

	@Column(name="status_id")
	private String statusId;

	public InventoryItemStatus() {
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

	public String getInventoryItemId() {
		return this.inventoryItemId;
	}

	public void setInventoryItemId(String inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
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

	public String getOwnerPartyId() {
		return this.ownerPartyId;
	}

	public void setOwnerPartyId(String ownerPartyId) {
		this.ownerPartyId = ownerPartyId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Timestamp getStatusDatetime() {
		return this.statusDatetime;
	}

	public void setStatusDatetime(Timestamp statusDatetime) {
		this.statusDatetime = statusDatetime;
	}

	public Timestamp getStatusEndDatetime() {
		return this.statusEndDatetime;
	}

	public void setStatusEndDatetime(Timestamp statusEndDatetime) {
		this.statusEndDatetime = statusEndDatetime;
	}

	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

}