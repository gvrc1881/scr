package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the party_supplemental_data database table.
 * 
 */
@Entity
@Table(name = "party_supplemental_data" , uniqueConstraints={@UniqueConstraint(name = "old_pk_party_supplemental_data_uniq", columnNames ={"data_div", "party_id"})})
@NamedQuery(name="PartySupplementalData.findAll", query="SELECT p FROM PartySupplementalData p")
public class PartySupplementalData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="annual_revenue")
	private BigDecimal annualRevenue;

	@Column(name="company_name")
	private String companyName;

	@Column(name="company_name_local")
	private String companyNameLocal;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="cst_number")
	private String cstNumber;

	@Column(name="currency_uom_id")
	private String currencyUomId;

	@Column(name="data_div")
	private String dataDiv;

	@Column(name="department_name")
	private String departmentName;

	@Column(name="general_prof_title")
	private String generalProfTitle;

	@Column(name="important_note",columnDefinition="TEXT")
	private String importantNote;

	@Column(name="industry_enum_id")
	private String industryEnumId;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="number_employees")
	private BigDecimal numberEmployees;

	@Column(name="ownership_enum_id")
	private String ownershipEnumId;

	@Column(name="pan_number")
	private String panNumber;

	@Column(name="parent_party_id")
	private String parentPartyId;

	@Column(name="party_id")
	private String partyId;

	@Column(name="primary_email_id")
	private String primaryEmailId;

	@Column(name="primary_postal_address_id")
	private String primaryPostalAddressId;

	@Column(name="primary_telecom_number_id")
	private String primaryTelecomNumberId;

	@Column(name="sic_code")
	private String sicCode;

	@Column(name="ticker_symbol")
	private String tickerSymbol;

	@Column(name="vat_tin_number")
	private String vatTinNumber;

	public PartySupplementalData() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAnnualRevenue() {
		return this.annualRevenue;
	}

	public void setAnnualRevenue(BigDecimal annualRevenue) {
		this.annualRevenue = annualRevenue;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyNameLocal() {
		return this.companyNameLocal;
	}

	public void setCompanyNameLocal(String companyNameLocal) {
		this.companyNameLocal = companyNameLocal;
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

	public String getCstNumber() {
		return this.cstNumber;
	}

	public void setCstNumber(String cstNumber) {
		this.cstNumber = cstNumber;
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

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getGeneralProfTitle() {
		return this.generalProfTitle;
	}

	public void setGeneralProfTitle(String generalProfTitle) {
		this.generalProfTitle = generalProfTitle;
	}

	public String getImportantNote() {
		return this.importantNote;
	}

	public void setImportantNote(String importantNote) {
		this.importantNote = importantNote;
	}

	public String getIndustryEnumId() {
		return this.industryEnumId;
	}

	public void setIndustryEnumId(String industryEnumId) {
		this.industryEnumId = industryEnumId;
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

	public BigDecimal getNumberEmployees() {
		return this.numberEmployees;
	}

	public void setNumberEmployees(BigDecimal numberEmployees) {
		this.numberEmployees = numberEmployees;
	}

	public String getOwnershipEnumId() {
		return this.ownershipEnumId;
	}

	public void setOwnershipEnumId(String ownershipEnumId) {
		this.ownershipEnumId = ownershipEnumId;
	}

	public String getPanNumber() {
		return this.panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getParentPartyId() {
		return this.parentPartyId;
	}

	public void setParentPartyId(String parentPartyId) {
		this.parentPartyId = parentPartyId;
	}

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getPrimaryEmailId() {
		return this.primaryEmailId;
	}

	public void setPrimaryEmailId(String primaryEmailId) {
		this.primaryEmailId = primaryEmailId;
	}

	public String getPrimaryPostalAddressId() {
		return this.primaryPostalAddressId;
	}

	public void setPrimaryPostalAddressId(String primaryPostalAddressId) {
		this.primaryPostalAddressId = primaryPostalAddressId;
	}

	public String getPrimaryTelecomNumberId() {
		return this.primaryTelecomNumberId;
	}

	public void setPrimaryTelecomNumberId(String primaryTelecomNumberId) {
		this.primaryTelecomNumberId = primaryTelecomNumberId;
	}

	public String getSicCode() {
		return this.sicCode;
	}

	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	public String getTickerSymbol() {
		return this.tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	public String getVatTinNumber() {
		return this.vatTinNumber;
	}

	public void setVatTinNumber(String vatTinNumber) {
		this.vatTinNumber = vatTinNumber;
	}

}
