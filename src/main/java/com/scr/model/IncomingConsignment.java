package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the incoming_consignment database table.
 * 
 */
@Entity
// @Table(name="incoming_consignment")

@Table(name = "incoming_consignment" , uniqueConstraints={@UniqueConstraint(name = "old_pk_incoming_consignment_uniq", columnNames ={"incoming_consignment_id", "data_div"})})
//

@NamedQuery(name="IncomingConsignment.findAll", query="SELECT i FROM IncomingConsignment i")
public class IncomingConsignment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="certificates_received")
	private String certificatesReceived;

	private String comments;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="date_received")
	private Timestamp dateReceived;

	@Column(name="delivery_challan_no")
	private String deliveryChallanNo;

	@Column(name="entered_by")
	private String enteredBy;

	@Column(name="gpi_no")
	private String gpiNo;

	@Column(name="incoming_consignment_id")
	private String incomingConsignmentId;

	@Column(name="invoice_no")
	private String invoiceNo;

	@Column(name="inward_no")
	private String inwardNo;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="no_of_documents")
	private String noOfDocuments;

	@Column(name="no_of_items_received")
	private String noOfItemsReceived;

	@Column(name="order_id")
	private String orderId;

	@Column(name="party_id")
	private String partyId;

	@Column(name="received_by")
	private String receivedBy;

	private String status;

	public IncomingConsignment() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCertificatesReceived() {
		return this.certificatesReceived;
	}

	public void setCertificatesReceived(String certificatesReceived) {
		this.certificatesReceived = certificatesReceived;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public Timestamp getDateReceived() {
		return this.dateReceived;
	}

	public void setDateReceived(Timestamp dateReceived) {
		this.dateReceived = dateReceived;
	}

	public String getDeliveryChallanNo() {
		return this.deliveryChallanNo;
	}

	public void setDeliveryChallanNo(String deliveryChallanNo) {
		this.deliveryChallanNo = deliveryChallanNo;
	}

	public String getEnteredBy() {
		return this.enteredBy;
	}

	public void setEnteredBy(String enteredBy) {
		this.enteredBy = enteredBy;
	}

	public String getGpiNo() {
		return this.gpiNo;
	}

	public void setGpiNo(String gpiNo) {
		this.gpiNo = gpiNo;
	}

	public String getIncomingConsignmentId() {
		return this.incomingConsignmentId;
	}

	public void setIncomingConsignmentId(String incomingConsignmentId) {
		this.incomingConsignmentId = incomingConsignmentId;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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

	public String getNoOfDocuments() {
		return this.noOfDocuments;
	}

	public void setNoOfDocuments(String noOfDocuments) {
		this.noOfDocuments = noOfDocuments;
	}

	public String getNoOfItemsReceived() {
		return this.noOfItemsReceived;
	}

	public void setNoOfItemsReceived(String noOfItemsReceived) {
		this.noOfItemsReceived = noOfItemsReceived;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getReceivedBy() {
		return this.receivedBy;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}