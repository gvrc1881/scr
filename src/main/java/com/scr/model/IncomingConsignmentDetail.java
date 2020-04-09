package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the incoming_consignment_detail database table.
 * 
 */
@Entity
@Table(name = "incoming_consignment_detail" , uniqueConstraints={@UniqueConstraint(name = "old_pk_incoming_consignment_detail_uniq", columnNames ={"data_div", "incoming_consignment_detail_id"})})
@NamedQuery(name="IncomingConsignmentDetail.findAll", query="SELECT i FROM IncomingConsignmentDetail i")
public class IncomingConsignmentDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="discrepancy_quantity")
	private String discrepancyQuantity;

	@Column(name="incoming_consignment_detail_id")
	private String incomingConsignmentDetailId;

	@Column(name="incoming_consignment_id")
	private String incomingConsignmentId;

	@Column(name="inventory_item_type")
	private String inventoryItemType;

	@Column(name="inward_no")
	private String inwardNo;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="lot_id")
	private String lotId;

	@Column(name="order_id")
	private String orderId;

	@Column(name="order_item_seq_id")
	private String orderItemSeqId;

	@Column(name="product_id")
	private String productId;

	@Column(name="quantity_received")
	private BigDecimal quantityReceived;

	@Column(name="quantity_tested")
	private String quantityTested;

	@Column(name="test_status")
	private String testStatus;

	public IncomingConsignmentDetail() {
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

	public String getDiscrepancyQuantity() {
		return this.discrepancyQuantity;
	}

	public void setDiscrepancyQuantity(String discrepancyQuantity) {
		this.discrepancyQuantity = discrepancyQuantity;
	}

	public String getIncomingConsignmentDetailId() {
		return this.incomingConsignmentDetailId;
	}

	public void setIncomingConsignmentDetailId(String incomingConsignmentDetailId) {
		this.incomingConsignmentDetailId = incomingConsignmentDetailId;
	}

	public String getIncomingConsignmentId() {
		return this.incomingConsignmentId;
	}

	public void setIncomingConsignmentId(String incomingConsignmentId) {
		this.incomingConsignmentId = incomingConsignmentId;
	}

	public String getInventoryItemType() {
		return this.inventoryItemType;
	}

	public void setInventoryItemType(String inventoryItemType) {
		this.inventoryItemType = inventoryItemType;
	}

	public String getInwardNo() {
		return this.inwardNo;
	}

	public void setInwardNo(String inwardNo) {
		this.inwardNo = inwardNo;
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

	public String getLotId() {
		return this.lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
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

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getQuantityReceived() {
		return this.quantityReceived;
	}

	public void setQuantityReceived(BigDecimal quantityReceived) {
		this.quantityReceived = quantityReceived;
	}

	public String getQuantityTested() {
		return this.quantityTested;
	}

	public void setQuantityTested(String quantityTested) {
		this.quantityTested = quantityTested;
	}

	public String getTestStatus() {
		return this.testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

}