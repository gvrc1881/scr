package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the inventory_item database table.
 * 
 */
@Entity
// @Table(name="inventory_item")

@Table(name = "inventory_item" , uniqueConstraints={@UniqueConstraint(name = "old_pk_inventory_item_uniq", columnNames ={"inventory_item_id", "data_div"})})
//
@NamedQuery(name="InventoryItem.findAll", query="SELECT i FROM InventoryItem i")
public class InventoryItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="accounting_quantity_total")
	private BigDecimal accountingQuantityTotal;

	@Column(name="acctg_tag_enum_id1")
	private String acctgTagEnumId1;

	@Column(name="acctg_tag_enum_id10")
	private String acctgTagEnumId10;

	@Column(name="acctg_tag_enum_id2")
	private String acctgTagEnumId2;

	@Column(name="acctg_tag_enum_id3")
	private String acctgTagEnumId3;

	@Column(name="acctg_tag_enum_id4")
	private String acctgTagEnumId4;

	@Column(name="acctg_tag_enum_id5")
	private String acctgTagEnumId5;

	@Column(name="acctg_tag_enum_id6")
	private String acctgTagEnumId6;

	@Column(name="acctg_tag_enum_id7")
	private String acctgTagEnumId7;

	@Column(name="acctg_tag_enum_id8")
	private String acctgTagEnumId8;

	@Column(name="acctg_tag_enum_id9")
	private String acctgTagEnumId9;

	@Column(name="activation_number")
	private String activationNumber;

	@Column(name="activation_valid_thru")
	private Timestamp activationValidThru;

	@Column(name="additional_specifications" , columnDefinition="TEXT")
	private String additionalSpecifications;

	@Column(name="available_to_promise")
	private BigDecimal availableToPromise;

	@Column(name="available_to_promise_total")
	private BigDecimal availableToPromiseTotal;

	@Column(name="bill_date")
	private Timestamp billDate;

	@Column(name="bill_no")
	private String billNo;

	@Column(name="bin_number")
	private String binNumber;

	private String comments;

	@Column(name="container_id")
	private String containerId;

	@Column(name="contrator_name")
	private String contratorName;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="currency_uom_id")
	private String currencyUomId;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="datetime_manufactured")
	private Timestamp datetimeManufactured;

	@Column(name="datetime_received")
	private Timestamp datetimeReceived;

	private String depot;

	private String division;

	@Column(name="expire_date")
	private Timestamp expireDate;

	@Column(name="external_depot")
	private String externalDepot;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="functional_unit")
	private String functionalUnit;

	@Column(name="inventory_item_id")
	private String inventoryItemId;

	@Column(name="inventory_item_type_id")
	private String inventoryItemTypeId;

	@Column(name="inward_no")
	private String inwardNo;

	@Column(name="issue_note_no")
	private String issueNoteNo;

	@Column(name="issue_note_remarks")
	private String issueNoteRemarks;

	@Column(name="issue_purpose")
	private String issuePurpose;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="location_seq_id")
	private String locationSeqId;

	@Column(name="lot_id")
	private String lotId;

	@Column(name="owner_party_id")
	private String ownerPartyId;

	@Column(name="parent_inventory_item_id")
	private String parentInventoryItemId;

	@Column(name="party_id")
	private String partyId;

	@Column(name="product_id")
	private String productId;

	@Column(name="purchase_from")
	private String purchaseFrom;

	@Column(name="quantity_on_hand")
	private BigDecimal quantityOnHand;

	@Column(name="quantity_on_hand_total")
	private BigDecimal quantityOnHandTotal;

	@Column(name="serial_number")
	private String serialNumber;

	@Column(name="soft_identifier")
	private String softIdentifier;

	@Column(name="status_id")
	private String statusId;

	@Column(name="unit_cost")
	private BigDecimal unitCost;

	@Column(name="uom_id")
	private String uomId;

	@Column(name="voucher_no")
	private String voucherNo;

	private String ward;

	@Column(name="work_order")
	private String workOrder;

	@Column(name="work_order_name")
	private String workOrderName;

	public InventoryItem() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAccountingQuantityTotal() {
		return this.accountingQuantityTotal;
	}

	public void setAccountingQuantityTotal(BigDecimal accountingQuantityTotal) {
		this.accountingQuantityTotal = accountingQuantityTotal;
	}

	public String getAcctgTagEnumId1() {
		return this.acctgTagEnumId1;
	}

	public void setAcctgTagEnumId1(String acctgTagEnumId1) {
		this.acctgTagEnumId1 = acctgTagEnumId1;
	}

	public String getAcctgTagEnumId10() {
		return this.acctgTagEnumId10;
	}

	public void setAcctgTagEnumId10(String acctgTagEnumId10) {
		this.acctgTagEnumId10 = acctgTagEnumId10;
	}

	public String getAcctgTagEnumId2() {
		return this.acctgTagEnumId2;
	}

	public void setAcctgTagEnumId2(String acctgTagEnumId2) {
		this.acctgTagEnumId2 = acctgTagEnumId2;
	}

	public String getAcctgTagEnumId3() {
		return this.acctgTagEnumId3;
	}

	public void setAcctgTagEnumId3(String acctgTagEnumId3) {
		this.acctgTagEnumId3 = acctgTagEnumId3;
	}

	public String getAcctgTagEnumId4() {
		return this.acctgTagEnumId4;
	}

	public void setAcctgTagEnumId4(String acctgTagEnumId4) {
		this.acctgTagEnumId4 = acctgTagEnumId4;
	}

	public String getAcctgTagEnumId5() {
		return this.acctgTagEnumId5;
	}

	public void setAcctgTagEnumId5(String acctgTagEnumId5) {
		this.acctgTagEnumId5 = acctgTagEnumId5;
	}

	public String getAcctgTagEnumId6() {
		return this.acctgTagEnumId6;
	}

	public void setAcctgTagEnumId6(String acctgTagEnumId6) {
		this.acctgTagEnumId6 = acctgTagEnumId6;
	}

	public String getAcctgTagEnumId7() {
		return this.acctgTagEnumId7;
	}

	public void setAcctgTagEnumId7(String acctgTagEnumId7) {
		this.acctgTagEnumId7 = acctgTagEnumId7;
	}

	public String getAcctgTagEnumId8() {
		return this.acctgTagEnumId8;
	}

	public void setAcctgTagEnumId8(String acctgTagEnumId8) {
		this.acctgTagEnumId8 = acctgTagEnumId8;
	}

	public String getAcctgTagEnumId9() {
		return this.acctgTagEnumId9;
	}

	public void setAcctgTagEnumId9(String acctgTagEnumId9) {
		this.acctgTagEnumId9 = acctgTagEnumId9;
	}

	public String getActivationNumber() {
		return this.activationNumber;
	}

	public void setActivationNumber(String activationNumber) {
		this.activationNumber = activationNumber;
	}

	public Timestamp getActivationValidThru() {
		return this.activationValidThru;
	}

	public void setActivationValidThru(Timestamp activationValidThru) {
		this.activationValidThru = activationValidThru;
	}

	public String getAdditionalSpecifications() {
		return this.additionalSpecifications;
	}

	public void setAdditionalSpecifications(String additionalSpecifications) {
		this.additionalSpecifications = additionalSpecifications;
	}

	public BigDecimal getAvailableToPromise() {
		return this.availableToPromise;
	}

	public void setAvailableToPromise(BigDecimal availableToPromise) {
		this.availableToPromise = availableToPromise;
	}

	public BigDecimal getAvailableToPromiseTotal() {
		return this.availableToPromiseTotal;
	}

	public void setAvailableToPromiseTotal(BigDecimal availableToPromiseTotal) {
		this.availableToPromiseTotal = availableToPromiseTotal;
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

	public String getBinNumber() {
		return this.binNumber;
	}

	public void setBinNumber(String binNumber) {
		this.binNumber = binNumber;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContainerId() {
		return this.containerId;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
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

	public String getCurrencyUomId() {
		return this.currencyUomId;
	}

	public void setCurrencyUomId(String currencyUomId) {
		this.currencyUomId = currencyUomId;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public Timestamp getDatetimeManufactured() {
		return this.datetimeManufactured;
	}

	public void setDatetimeManufactured(Timestamp datetimeManufactured) {
		this.datetimeManufactured = datetimeManufactured;
	}

	public Timestamp getDatetimeReceived() {
		return this.datetimeReceived;
	}

	public void setDatetimeReceived(Timestamp datetimeReceived) {
		this.datetimeReceived = datetimeReceived;
	}

	public String getDepot() {
		return this.depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public Timestamp getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(Timestamp expireDate) {
		this.expireDate = expireDate;
	}

	public String getExternalDepot() {
		return this.externalDepot;
	}

	public void setExternalDepot(String externalDepot) {
		this.externalDepot = externalDepot;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFunctionalUnit() {
		return this.functionalUnit;
	}

	public void setFunctionalUnit(String functionalUnit) {
		this.functionalUnit = functionalUnit;
	}

	public String getInventoryItemId() {
		return this.inventoryItemId;
	}

	public void setInventoryItemId(String inventoryItemId) {
		this.inventoryItemId = inventoryItemId;
	}

	public String getInventoryItemTypeId() {
		return this.inventoryItemTypeId;
	}

	public void setInventoryItemTypeId(String inventoryItemTypeId) {
		this.inventoryItemTypeId = inventoryItemTypeId;
	}

	public String getInwardNo() {
		return this.inwardNo;
	}

	public void setInwardNo(String inwardNo) {
		this.inwardNo = inwardNo;
	}

	public String getIssueNoteNo() {
		return this.issueNoteNo;
	}

	public void setIssueNoteNo(String issueNoteNo) {
		this.issueNoteNo = issueNoteNo;
	}

	public String getIssueNoteRemarks() {
		return this.issueNoteRemarks;
	}

	public void setIssueNoteRemarks(String issueNoteRemarks) {
		this.issueNoteRemarks = issueNoteRemarks;
	}

	public String getIssuePurpose() {
		return this.issuePurpose;
	}

	public void setIssuePurpose(String issuePurpose) {
		this.issuePurpose = issuePurpose;
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

	public String getLocationSeqId() {
		return this.locationSeqId;
	}

	public void setLocationSeqId(String locationSeqId) {
		this.locationSeqId = locationSeqId;
	}

	public String getLotId() {
		return this.lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getOwnerPartyId() {
		return this.ownerPartyId;
	}

	public void setOwnerPartyId(String ownerPartyId) {
		this.ownerPartyId = ownerPartyId;
	}

	public String getParentInventoryItemId() {
		return this.parentInventoryItemId;
	}

	public void setParentInventoryItemId(String parentInventoryItemId) {
		this.parentInventoryItemId = parentInventoryItemId;
	}

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPurchaseFrom() {
		return this.purchaseFrom;
	}

	public void setPurchaseFrom(String purchaseFrom) {
		this.purchaseFrom = purchaseFrom;
	}

	public BigDecimal getQuantityOnHand() {
		return this.quantityOnHand;
	}

	public void setQuantityOnHand(BigDecimal quantityOnHand) {
		this.quantityOnHand = quantityOnHand;
	}

	public BigDecimal getQuantityOnHandTotal() {
		return this.quantityOnHandTotal;
	}

	public void setQuantityOnHandTotal(BigDecimal quantityOnHandTotal) {
		this.quantityOnHandTotal = quantityOnHandTotal;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSoftIdentifier() {
		return this.softIdentifier;
	}

	public void setSoftIdentifier(String softIdentifier) {
		this.softIdentifier = softIdentifier;
	}

	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public BigDecimal getUnitCost() {
		return this.unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public String getUomId() {
		return this.uomId;
	}

	public void setUomId(String uomId) {
		this.uomId = uomId;
	}

	public String getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getWard() {
		return this.ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
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