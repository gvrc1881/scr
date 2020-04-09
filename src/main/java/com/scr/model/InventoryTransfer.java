package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the inventory_transfer database table.
 * 
 */
@Entity
// @Table(name="inventory_transfer")

@Table(name = "inventory_transfer" , uniqueConstraints={@UniqueConstraint(name = "old_pk_inventory_transfer_uniq", columnNames ={"inventory_transfer_id", "data_div"})})
//
@NamedQuery(name="InventoryTransfer.findAll", query="SELECT i FROM InventoryTransfer i")
public class InventoryTransfer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="advice_note_no")
	private String adviceNoteNo;

	private String allocation;

	private String catg;

	@Column(name="certificate_status")
	private String certificateStatus;

	@Column(name="challan_no")
	private String challanNo;

	private String comments;

	@Column(name="container_id")
	private String containerId;

	@Column(name="container_id_to")
	private String containerIdTo;

	@Column(name="contractor_name")
	private String contractorName;

	@Column(name="controlling_officer")
	private String controllingOfficer;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="credit_note_no")
	private String creditNoteNo;

	@Column(name="data_div")
	private String dataDiv;

	private String depot;

	@Column(name="depot_name")
	private String depotName;

	private String division;

	@Column(name="external_depot")
	private String externalDepot;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="facility_id_to")
	private String facilityIdTo;

	@Column(name="functional_unit")
	private String functionalUnit;

	@Column(name="inventory_item_id")
	private String inventoryItemId;

	@Column(name="inventory_transfer_id")
	private String inventoryTransferId;

	@Column(name="issue_purpose")
	private String issuePurpose;

	@Column(name="item_issuance_id")
	private String itemIssuanceId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="location_seq_id")
	private String locationSeqId;

	@Column(name="location_seq_id_to")
	private String locationSeqIdTo;

	@Column(name="rate_demanded")
	private String rateDemanded;

	private String rateallowed;

	@Column(name="receive_date")
	private Timestamp receiveDate;

	@Column(name="rr_no")
	private String rrNo;

	@Column(name="send_date")
	private Timestamp sendDate;

	@Column(name="status_id")
	private String statusId;

	private String unit;

	@Column(name="voucher_no")
	private String voucherNo;

	@Column(name="wagon_no")
	private String wagonNo;

	private String ward;

	@Column(name="work_order")
	private String workOrder;

	public InventoryTransfer() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdviceNoteNo() {
		return this.adviceNoteNo;
	}

	public void setAdviceNoteNo(String adviceNoteNo) {
		this.adviceNoteNo = adviceNoteNo;
	}

	public String getAllocation() {
		return this.allocation;
	}

	public void setAllocation(String allocation) {
		this.allocation = allocation;
	}

	public String getCatg() {
		return this.catg;
	}

	public void setCatg(String catg) {
		this.catg = catg;
	}

	public String getCertificateStatus() {
		return this.certificateStatus;
	}

	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public String getChallanNo() {
		return this.challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
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

	public String getContainerIdTo() {
		return this.containerIdTo;
	}

	public void setContainerIdTo(String containerIdTo) {
		this.containerIdTo = containerIdTo;
	}

	public String getContractorName() {
		return this.contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getControllingOfficer() {
		return this.controllingOfficer;
	}

	public void setControllingOfficer(String controllingOfficer) {
		this.controllingOfficer = controllingOfficer;
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

	public String getCreditNoteNo() {
		return this.creditNoteNo;
	}

	public void setCreditNoteNo(String creditNoteNo) {
		this.creditNoteNo = creditNoteNo;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getDepot() {
		return this.depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getDepotName() {
		return this.depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
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

	public String getFacilityIdTo() {
		return this.facilityIdTo;
	}

	public void setFacilityIdTo(String facilityIdTo) {
		this.facilityIdTo = facilityIdTo;
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

	public String getInventoryTransferId() {
		return this.inventoryTransferId;
	}

	public void setInventoryTransferId(String inventoryTransferId) {
		this.inventoryTransferId = inventoryTransferId;
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

	public String getLocationSeqId() {
		return this.locationSeqId;
	}

	public void setLocationSeqId(String locationSeqId) {
		this.locationSeqId = locationSeqId;
	}

	public String getLocationSeqIdTo() {
		return this.locationSeqIdTo;
	}

	public void setLocationSeqIdTo(String locationSeqIdTo) {
		this.locationSeqIdTo = locationSeqIdTo;
	}

	public String getRateDemanded() {
		return this.rateDemanded;
	}

	public void setRateDemanded(String rateDemanded) {
		this.rateDemanded = rateDemanded;
	}

	public String getRateallowed() {
		return this.rateallowed;
	}

	public void setRateallowed(String rateallowed) {
		this.rateallowed = rateallowed;
	}

	public Timestamp getReceiveDate() {
		return this.receiveDate;
	}

	public void setReceiveDate(Timestamp receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getRrNo() {
		return this.rrNo;
	}

	public void setRrNo(String rrNo) {
		this.rrNo = rrNo;
	}

	public Timestamp getSendDate() {
		return this.sendDate;
	}

	public void setSendDate(Timestamp sendDate) {
		this.sendDate = sendDate;
	}

	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getWagonNo() {
		return this.wagonNo;
	}

	public void setWagonNo(String wagonNo) {
		this.wagonNo = wagonNo;
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

}