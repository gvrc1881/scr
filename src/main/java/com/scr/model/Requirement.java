package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the requirement database table.
 * 
 */
@Entity
@Table(name = "requirement" , uniqueConstraints={@UniqueConstraint(name = "old_pk_requirement_uniq", columnNames ={"requirement_id", "data_div"})})
@NamedQuery(name="Requirement.findAll", query="SELECT r FROM Requirement r")
public class Requirement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="additional_specifications",columnDefinition="TEXT")
	private String additionalSpecifications;

	@Column(name="allocation_code")
	private String allocationCode;

	@Column(name="case_number")
	private String caseNumber;

	private String cat;

	private String consignee;

	@Column(name="consignee_code")
	private String consigneeCode;

	@Column(name="consumed_in_past_yar1")
	private String consumedInPastYar1;

	@Column(name="consumed_in_past_yar2")
	private String consumedInPastYar2;

	@Column(name="consumed_in_past_yar3")
	private String consumedInPastYar3;

	@Column(name="created_by_user_login")
	private String createdByUserLogin;

	@Column(name="created_date")
	private Timestamp createdDate;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="deliverable_id")
	private String deliverableId;

	private String depot;

	private String description;

	@Column(name="estimated_budget")
	private BigDecimal estimatedBudget;

	@Column(name="facility_id")
	private String facilityId;

	@Column(name="facility_id_to")
	private String facilityIdTo;

	@Column(name="facility_voucher_sequence")
	private String facilityVoucherSequence;

	@Column(name="financial_year")
	private String financialYear;

	@Column(name="fixed_asset_id")
	private String fixedAssetId;

	private String indentor;

	@Column(name="internal_name")
	private String internalName;

	@Column(name="last_modified_by_user_login")
	private String lastModifiedByUserLogin;

	@Column(name="last_modified_date")
	private Timestamp lastModifiedDate;

	@Column(name="last_prchse_particlrs")
	private String lastPrchseParticlrs;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="material_category")
	private String materialCategory;

	@Column(name="material_depot")
	private String materialDepot;

	@Column(name="matris_reqd_at")
	private String matrisReqdAt;

	@Column(name="nomenclature_no")
	private String nomenclatureNo;

	@Column(name="order_item_type_id")
	private String orderItemTypeId;

	@Column(name="period_from")
	private Timestamp periodFrom;

	@Column(name="period_to")
	private Timestamp periodTo;

	@Column(name="product_id")
	private String productId;

	@Column(name="qty_in_stok_with_indntr")
	private String qtyInStokWithIndntr;

	private BigDecimal quantity;

	private BigDecimal rate;

	private String reason;

	private String remarks;

	@Column(name="required_by_date")
	private Timestamp requiredByDate;

	@Column(name="requirement_id")
	private String requirementId;

	@Column(name="requirement_start_date")
	private Timestamp requirementStartDate;

	@Column(name="requirement_type_id")
	private String requirementTypeId;

	@Column(name="requisition_max_no")
	private BigDecimal requisitionMaxNo;

	@Column(name="requisition_no")
	private String requisitionNo;

	@Column(name="sanctioned_imprest_item")
	private String sanctionedImprestItem;

	@Column(name="status_id")
	private String statusId;

	@Column(name="supplier_depot")
	private String supplierDepot;

	@Column(name="tol_qty_agnst_othr_indts")
	private String tolQtyAgnstOthrIndts;

	@Column(name="total_qty_against_po")
	private String totalQtyAgainstPo;

	private String unit;

	@Column(name="use_case",columnDefinition="TEXT")
	private String useCase;

	@Column(name="voucher_no")
	private String voucherNo;

	public Requirement() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdditionalSpecifications() {
		return this.additionalSpecifications;
	}

	public void setAdditionalSpecifications(String additionalSpecifications) {
		this.additionalSpecifications = additionalSpecifications;
	}

	public String getAllocationCode() {
		return this.allocationCode;
	}

	public void setAllocationCode(String allocationCode) {
		this.allocationCode = allocationCode;
	}

	public String getCaseNumber() {
		return this.caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getCat() {
		return this.cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getConsignee() {
		return this.consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getConsigneeCode() {
		return this.consigneeCode;
	}

	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}

	public String getConsumedInPastYar1() {
		return this.consumedInPastYar1;
	}

	public void setConsumedInPastYar1(String consumedInPastYar1) {
		this.consumedInPastYar1 = consumedInPastYar1;
	}

	public String getConsumedInPastYar2() {
		return this.consumedInPastYar2;
	}

	public void setConsumedInPastYar2(String consumedInPastYar2) {
		this.consumedInPastYar2 = consumedInPastYar2;
	}

	public String getConsumedInPastYar3() {
		return this.consumedInPastYar3;
	}

	public void setConsumedInPastYar3(String consumedInPastYar3) {
		this.consumedInPastYar3 = consumedInPastYar3;
	}

	public String getCreatedByUserLogin() {
		return this.createdByUserLogin;
	}

	public void setCreatedByUserLogin(String createdByUserLogin) {
		this.createdByUserLogin = createdByUserLogin;
	}

	public Timestamp getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
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

	public String getDeliverableId() {
		return this.deliverableId;
	}

	public void setDeliverableId(String deliverableId) {
		this.deliverableId = deliverableId;
	}

	public String getDepot() {
		return this.depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getEstimatedBudget() {
		return this.estimatedBudget;
	}

	public void setEstimatedBudget(BigDecimal estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
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

	public String getFacilityVoucherSequence() {
		return this.facilityVoucherSequence;
	}

	public void setFacilityVoucherSequence(String facilityVoucherSequence) {
		this.facilityVoucherSequence = facilityVoucherSequence;
	}

	public String getFinancialYear() {
		return this.financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getFixedAssetId() {
		return this.fixedAssetId;
	}

	public void setFixedAssetId(String fixedAssetId) {
		this.fixedAssetId = fixedAssetId;
	}

	public String getIndentor() {
		return this.indentor;
	}

	public void setIndentor(String indentor) {
		this.indentor = indentor;
	}

	public String getInternalName() {
		return this.internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public String getLastModifiedByUserLogin() {
		return this.lastModifiedByUserLogin;
	}

	public void setLastModifiedByUserLogin(String lastModifiedByUserLogin) {
		this.lastModifiedByUserLogin = lastModifiedByUserLogin;
	}

	public Timestamp getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastPrchseParticlrs() {
		return this.lastPrchseParticlrs;
	}

	public void setLastPrchseParticlrs(String lastPrchseParticlrs) {
		this.lastPrchseParticlrs = lastPrchseParticlrs;
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

	public String getMaterialCategory() {
		return this.materialCategory;
	}

	public void setMaterialCategory(String materialCategory) {
		this.materialCategory = materialCategory;
	}

	public String getMaterialDepot() {
		return this.materialDepot;
	}

	public void setMaterialDepot(String materialDepot) {
		this.materialDepot = materialDepot;
	}

	public String getMatrisReqdAt() {
		return this.matrisReqdAt;
	}

	public void setMatrisReqdAt(String matrisReqdAt) {
		this.matrisReqdAt = matrisReqdAt;
	}

	public String getNomenclatureNo() {
		return this.nomenclatureNo;
	}

	public void setNomenclatureNo(String nomenclatureNo) {
		this.nomenclatureNo = nomenclatureNo;
	}

	public String getOrderItemTypeId() {
		return this.orderItemTypeId;
	}

	public void setOrderItemTypeId(String orderItemTypeId) {
		this.orderItemTypeId = orderItemTypeId;
	}

	public Timestamp getPeriodFrom() {
		return this.periodFrom;
	}

	public void setPeriodFrom(Timestamp periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Timestamp getPeriodTo() {
		return this.periodTo;
	}

	public void setPeriodTo(Timestamp periodTo) {
		this.periodTo = periodTo;
	}

	public String getProductId() {
		return this.productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getQtyInStokWithIndntr() {
		return this.qtyInStokWithIndntr;
	}

	public void setQtyInStokWithIndntr(String qtyInStokWithIndntr) {
		this.qtyInStokWithIndntr = qtyInStokWithIndntr;
	}

	public BigDecimal getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getRate() {
		return this.rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Timestamp getRequiredByDate() {
		return this.requiredByDate;
	}

	public void setRequiredByDate(Timestamp requiredByDate) {
		this.requiredByDate = requiredByDate;
	}

	public String getRequirementId() {
		return this.requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	public Timestamp getRequirementStartDate() {
		return this.requirementStartDate;
	}

	public void setRequirementStartDate(Timestamp requirementStartDate) {
		this.requirementStartDate = requirementStartDate;
	}

	public String getRequirementTypeId() {
		return this.requirementTypeId;
	}

	public void setRequirementTypeId(String requirementTypeId) {
		this.requirementTypeId = requirementTypeId;
	}

	public BigDecimal getRequisitionMaxNo() {
		return this.requisitionMaxNo;
	}

	public void setRequisitionMaxNo(BigDecimal requisitionMaxNo) {
		this.requisitionMaxNo = requisitionMaxNo;
	}

	public String getRequisitionNo() {
		return this.requisitionNo;
	}

	public void setRequisitionNo(String requisitionNo) {
		this.requisitionNo = requisitionNo;
	}

	public String getSanctionedImprestItem() {
		return this.sanctionedImprestItem;
	}

	public void setSanctionedImprestItem(String sanctionedImprestItem) {
		this.sanctionedImprestItem = sanctionedImprestItem;
	}

	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getSupplierDepot() {
		return this.supplierDepot;
	}

	public void setSupplierDepot(String supplierDepot) {
		this.supplierDepot = supplierDepot;
	}

	public String getTolQtyAgnstOthrIndts() {
		return this.tolQtyAgnstOthrIndts;
	}

	public void setTolQtyAgnstOthrIndts(String tolQtyAgnstOthrIndts) {
		this.tolQtyAgnstOthrIndts = tolQtyAgnstOthrIndts;
	}

	public String getTotalQtyAgainstPo() {
		return this.totalQtyAgainstPo;
	}

	public void setTotalQtyAgainstPo(String totalQtyAgainstPo) {
		this.totalQtyAgainstPo = totalQtyAgainstPo;
	}

	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUseCase() {
		return this.useCase;
	}

	public void setUseCase(String useCase) {
		this.useCase = useCase;
	}

	public String getVoucherNo() {
		return this.voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

}