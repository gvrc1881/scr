package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the inventory_item_detail database table.
 * 
 */
@Entity
// @Table(name="inventory_item_detail")

@Table(name = "inventory_item_detail" , uniqueConstraints={@UniqueConstraint(name = "old_pk_inventory_item_detail_uniq", columnNames ={"inventory_item_id", "data_div", "inventory_item_detail_seq_id"})})

//
@NamedQuery(name="InventoryItemDetail.findAll", query="SELECT i FROM InventoryItemDetail i")
public class InventoryItemDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="accounting_quantity_diff")
	private BigDecimal accountingQuantityDiff;

	@Column(name="available_to_promise_diff")
	private BigDecimal availableToPromiseDiff;

	@Column(name="bill_date")
	private Timestamp billDate;

	@Column(name="bill_no")
	private String billNo;

	@Column(name="contrator_name")
	private String contratorName;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String description;

	private String division;

	@Column(name="effective_date")
	private Timestamp effectiveDate;

	@Column(name="external_depot")
	private String externalDepot;

	@Column(name="fixed_asset_id")
	private String fixedAssetId;

	@Column(name="functional_unit")
	private String functionalUnit;

	@Column(name="inventory_item_detail_seq_id")
	private String inventoryItemDetailSeqId;

	@Column(name="inventory_item_id")
	private String inventoryItemId;

	@Column(name="issue_purpose")
	private String issuePurpose;

	@Column(name="item_issuance_id")
	private String itemIssuanceId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="maint_hist_seq_id")
	private String maintHistSeqId;

	@Column(name="mir_id")
	private String mirId;

	@Column(name="order_id")
	private String orderId;

	@Column(name="order_item_seq_id")
	private String orderItemSeqId;

	@Column(name="physical_inventory_id")
	private String physicalInventoryId;

	@Column(name="purchase_from")
	private String purchaseFrom;

	@Column(name="quantity_on_hand_diff")
	private BigDecimal quantityOnHandDiff;

	@Column(name="reason_enum_id")
	private String reasonEnumId;

	@Column(name="receipt_id")
	private String receiptId;

	@Column(name="return_id")
	private String returnId;

	@Column(name="return_item_seq_id")
	private String returnItemSeqId;

	@Column(name="ship_group_seq_id")
	private String shipGroupSeqId;

	@Column(name="shipment_id")
	private String shipmentId;

	@Column(name="shipment_item_seq_id")
	private String shipmentItemSeqId;

	@Column(name="unit_cost")
	private BigDecimal unitCost;

	@Column(name="voucher_no")
	private String voucherNo;

	@Column(name="work_effort_id")
	private String workEffortId;

	@Column(name="work_order")
	private String workOrder;

	@Column(name="work_order_name")
	private String workOrderName;

	public InventoryItemDetail() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAccountingQuantityDiff() {
		return this.accountingQuantityDiff;
	}

	public void setAccountingQuantityDiff(BigDecimal accountingQuantityDiff) {
		this.accountingQuantityDiff = accountingQuantityDiff;
	}

	public BigDecimal getAvailableToPromiseDiff() {
		return this.availableToPromiseDiff;
	}

	public void setAvailableToPromiseDiff(BigDecimal availableToPromiseDiff) {
		this.availableToPromiseDiff = availableToPromiseDiff;
	}

	public Timestamp getBillDate() {
		return this.billDate;
	}

	public void setBillDate(Timestamp billDate) {
		this.billDate = billDate;
	}

	public String getBillNo() {
		return this.billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getContratorName() {
		return this.contratorName;
	}

	public void setContratorName(String contratorName) {
		this.contratorName = contratorName;
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

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Timestamp getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExternalDepot() {
		return this.externalDepot;
	}

	public void setExternalDepot(String externalDepot) {
		this.externalDepot = externalDepot;
	}

	public String getFixedAssetId() {
		return this.fixedAssetId;
	}

	public void setFixedAssetId(String fixedAssetId) {
		this.fixedAssetId = fixedAssetId;
	}

	public String getFunctionalUnit() {
		return this.functionalUnit;
	}

	public void setFunctionalUnit(String functionalUnit) {
		this.functionalUnit = functionalUnit;
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

	public String getIssuePurpose() {
		return this.issuePurpose;
	}

	public void setIssuePurpose(String issuePurpose) {
		this.issuePurpose = issuePurpose;
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

	public String getMirId() {
		return this.mirId;
	}

	public void setMirId(String mirId) {
		this.mirId = mirId;
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

	public String getPhysicalInventoryId() {
		return this.physicalInventoryId;
	}

	public void setPhysicalInventoryId(String physicalInventoryId) {
		this.physicalInventoryId = physicalInventoryId;
	}

	public String getPurchaseFrom() {
		return this.purchaseFrom;
	}

	public void setPurchaseFrom(String purchaseFrom) {
		this.purchaseFrom = purchaseFrom;
	}

	public BigDecimal getQuantityOnHandDiff() {
		return this.quantityOnHandDiff;
	}

	public void setQuantityOnHandDiff(BigDecimal quantityOnHandDiff) {
		this.quantityOnHandDiff = quantityOnHandDiff;
	}

	public String getReasonEnumId() {
		return this.reasonEnumId;
	}

	public void setReasonEnumId(String reasonEnumId) {
		this.reasonEnumId = reasonEnumId;
	}

	public String getReceiptId() {
		return this.receiptId;
	}

	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
	}

	public String getReturnId() {
		return this.returnId;
	}

	public void setReturnId(String returnId) {
		this.returnId = returnId;
	}

	public String getReturnItemSeqId() {
		return this.returnItemSeqId;
	}

	public void setReturnItemSeqId(String returnItemSeqId) {
		this.returnItemSeqId = returnItemSeqId;
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

	public BigDecimal getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public String getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getWorkEffortId() {
		return this.workEffortId;
	}

	public void setWorkEffortId(String workEffortId) {
		this.workEffortId = workEffortId;
	}

	public String getWorkOrder() {
		return this.workOrder;
	}

	public void setWorkOrder(String workOrder) {
		this.workOrder = workOrder;
	}

	public String getWorkOrderName() {
		return this.workOrderName;
	}

	public void setWorkOrderName(String workOrderName) {
		this.workOrderName = workOrderName;
	}

}