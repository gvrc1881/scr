package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the dmtr database table.
 * 
 */
@Entity
@Table(name = "dmtr" , uniqueConstraints={@UniqueConstraint(name = "old_pk_dmtr_uniq", columnNames ={"dmtr_seq_id", "data_div"})})
@NamedQuery(name="Dmtr.findAll", query="SELECT d FROM Dmtr d")
public class Dmtr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="advice_note_id")
	private String adviceNoteId;

	@Column(name="cash_bill_id")
	private String cashBillId;

	@Column(name="contractor_name")
	private String contractorName;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="dmtr_id")
	private BigDecimal dmtrId;

	@Column(name="dmtr_seq_id")
	private String dmtrSeqId;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="it_challan_id_from")
	private String itChallanIdFrom;

	@Column(name="it_challan_id_to")
	private String itChallanIdTo;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="ledger_id")
	private BigDecimal ledgerId;

	@Column(name="order_id")
	private String orderId;

	@Column(name="product_id")
	private String productId;

	private BigDecimal quantity;

	@Column(name="return_voucher")
	private String returnVoucher;

	@Column(name="stock_voucher_id")
	private String stockVoucherId;

	@Column(name="transaction_date")
	private Timestamp transactionDate;

	@Column(name="work_effort_id")
	private String workEffortId;

	@Column(name="work_order_name")
	private String workOrderName;

	public Dmtr() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdviceNoteId() {
		return this.adviceNoteId;
	}

	public void setAdviceNoteId(String adviceNoteId) {
		this.adviceNoteId = adviceNoteId;
	}

	public String getCashBillId() {
		return this.cashBillId;
	}

	public void setCashBillId(String cashBillId) {
		this.cashBillId = cashBillId;
	}

	public String getContractorName() {
		return this.contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
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

	public BigDecimal getDmtrId() {
		return this.dmtrId;
	}

	public void setDmtrId(BigDecimal dmtrId) {
		this.dmtrId = dmtrId;
	}

	public String getDmtrSeqId() {
		return this.dmtrSeqId;
	}

	public void setDmtrSeqId(String dmtrSeqId) {
		this.dmtrSeqId = dmtrSeqId;
	}

	public String getFacilityId() {
		return this.facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getItChallanIdFrom() {
		return this.itChallanIdFrom;
	}

	public void setItChallanIdFrom(String itChallanIdFrom) {
		this.itChallanIdFrom = itChallanIdFrom;
	}

	public String getItChallanIdTo() {
		return this.itChallanIdTo;
	}

	public void setItChallanIdTo(String itChallanIdTo) {
		this.itChallanIdTo = itChallanIdTo;
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

	public BigDecimal getLedgerId() {
		return this.ledgerId;
	}

	public void setLedgerId(BigDecimal ledgerId) {
		this.ledgerId = ledgerId;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getReturnVoucher() {
		return this.returnVoucher;
	}

	public void setReturnVoucher(String returnVoucher) {
		this.returnVoucher = returnVoucher;
	}

	public String getStockVoucherId() {
		return this.stockVoucherId;
	}

	public void setStockVoucherId(String stockVoucherId) {
		this.stockVoucherId = stockVoucherId;
	}

	public Timestamp getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Timestamp transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getWorkEffortId() {
		return this.workEffortId;
	}

	public void setWorkEffortId(String workEffortId) {
		this.workEffortId = workEffortId;
	}

	public String getWorkOrderName() {
		return this.workOrderName;
	}

	public void setWorkOrderName(String workOrderName) {
		this.workOrderName = workOrderName;
	}

}
