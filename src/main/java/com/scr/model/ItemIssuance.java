package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the item_issuance database table.
 * 
 */
@Entity
// @Table(name="item_issuance")

@Table(name = "item_issuance" , uniqueConstraints={@UniqueConstraint(name = "old_pk_item_issuance_uniq", columnNames ={"data_div", "item_issuance_id"})})
//
@NamedQuery(name="ItemIssuance.findAll", query="SELECT i FROM ItemIssuance i")
public class ItemIssuance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="cancel_quantity")
	private BigDecimal cancelQuantity;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="fixed_asset_id")
	private String fixedAssetId;

	@Column(name="inventory_item_id")
	private String inventoryItemId;

	@Column(name="issued_by_user_login_id")
	private String issuedByUserLoginId;

	@Column(name="issued_date_time")
	private Timestamp issuedDateTime;

	@Column(name="item_issuance_id")
	private String itemIssuanceId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="maint_hist_seq_id")
	private String maintHistSeqId;

	@Column(name="order_id")
	private String orderId;

	@Column(name="order_item_seq_id")
	private String orderItemSeqId;

	private BigDecimal quantity;

	@Column(name="ship_group_seq_id")
	private String shipGroupSeqId;

	@Column(name="shipment_id")
	private String shipmentId;

	@Column(name="shipment_item_seq_id")
	private String shipmentItemSeqId;

	public ItemIssuance() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getCancelQuantity() {
		return this.cancelQuantity;
	}

	public void setCancelQuantity(BigDecimal cancelQuantity) {
		this.cancelQuantity = cancelQuantity;
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

	public String getFixedAssetId() {
		return this.fixedAssetId;
	}

	public void setFixedAssetId(String fixedAssetId) {
		this.fixedAssetId = fixedAssetId;
	}

	public String getInventoryItemId() {
		return this.inventoryItemId;
	}

	public void setInventoryItemId(String inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public String getIssuedByUserLoginId() {
		return this.issuedByUserLoginId;
	}

	public void setIssuedByUserLoginId(String issuedByUserLoginId) {
		this.issuedByUserLoginId = issuedByUserLoginId;
	}

	public Timestamp getIssuedDateTime() {
		return this.issuedDateTime;
	}

	public void setIssuedDateTime(Timestamp issuedDateTime) {
		this.issuedDateTime = issuedDateTime;
	}

	public String getItemIssuanceId() {
		return this.itemIssuanceId;
	}

	public void setItemIssuanceId(String itemIssuanceId) {
		this.itemIssuanceId = itemIssuanceId;
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

	public String getMaintHistSeqId() {
		return this.maintHistSeqId;
	}

	public void setMaintHistSeqId(String maintHistSeqId) {
		this.maintHistSeqId = maintHistSeqId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderItemSeqId() {
		return this.orderItemSeqId;
	}

	public void setOrderItemSeqId(String orderItemSeqId) {
		this.orderItemSeqId = orderItemSeqId;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getShipGroupSeqId() {
		return this.shipGroupSeqId;
	}

	public void setShipGroupSeqId(String shipGroupSeqId) {
		this.shipGroupSeqId = shipGroupSeqId;
	}

	public String getShipmentId() {
		return this.shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getShipmentItemSeqId() {
		return this.shipmentItemSeqId;
	}

	public void setShipmentItemSeqId(String shipmentItemSeqId) {
		this.shipmentItemSeqId = shipmentItemSeqId;
	}

}