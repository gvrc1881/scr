package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the party_group database table.
 * 
 */
@Entity
@Table(name = "party_group" , uniqueConstraints={@UniqueConstraint(name = "old_pk_party_group_uniq", columnNames ={"data_div", "party_id"})})
@NamedQuery(name="PartyGroup.findAll", query="SELECT p FROM PartyGroup p")
public class PartyGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="annual_revenue")
	private BigDecimal annualRevenue;

	private String comments;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="cst_no")
	private String cstNo;

	@Column(name="data_div")
	private String dataDiv;

	private String division;

	@Column(name="division_code")
	private String divisionCode;

	@Column(name="division_hq")
	private String divisionHq;

	@Column(name="federal_tax_id")
	private String federalTaxId;

	@Column(name="group_name")
	private String groupName;

	@Column(name="group_name_local")
	private String groupNameLocal;

	@Column(name="is_incorporated")
	private String isIncorporated;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="logo_image_url")
	private String logoImageUrl;

	@Column(name="num_employees")
	private BigDecimal numEmployees;

	@Column(name="office_site_name")
	private String officeSiteName;

	private String pan;

	@Column(name="party_id")
	private String partyId;

	private String requires1099;

	@Column(name="service_tax_no")
	private String serviceTaxNo;

	@Column(name="ticker_symbol")
	private String tickerSymbol;

	@Column(name="vat_tin")
	private String vatTin;

	private String zone;

	@Column(name="zone_code")
	private String zoneCode;

	@Column(name="zone_hq")
	private String zoneHq;

	public PartyGroup() {
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

	public String getCstNo() {
		return this.cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public String getDataDiv() {
		return this.dataDiv;
	}

	public void setDataDiv(String dataDiv) {
		this.dataDiv = dataDiv;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDivisionCode() {
		return this.divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getDivisionHq() {
		return this.divisionHq;
	}

	public void setDivisionHq(String divisionHq) {
		this.divisionHq = divisionHq;
	}

	public String getFederalTaxId() {
		return this.federalTaxId;
	}

	public void setFederalTaxId(String federalTaxId) {
		this.federalTaxId = federalTaxId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNameLocal() {
		return this.groupNameLocal;
	}

	public void setGroupNameLocal(String groupNameLocal) {
		this.groupNameLocal = groupNameLocal;
	}

	public String getIsIncorporated() {
		return this.isIncorporated;
	}

	public void setIsIncorporated(String isIncorporated) {
		this.isIncorporated = isIncorporated;
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

	public String getLogoImageUrl() {
		return this.logoImageUrl;
	}

	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}

	public BigDecimal getNumEmployees() {
		return this.numEmployees;
	}

	public void setNumEmployees(BigDecimal numEmployees) {
		this.numEmployees = numEmployees;
	}

	public String getOfficeSiteName() {
		return this.officeSiteName;
	}

	public void setOfficeSiteName(String officeSiteName) {
		this.officeSiteName = officeSiteName;
	}

	public String getPan() {
		return this.pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getRequires1099() {
		return this.requires1099;
	}

	public void setRequires1099(String requires1099) {
		this.requires1099 = requires1099;
	}

	public String getServiceTaxNo() {
		return this.serviceTaxNo;
	}

	public void setServiceTaxNo(String serviceTaxNo) {
		this.serviceTaxNo = serviceTaxNo;
	}

	public String getTickerSymbol() {
		return this.tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	public String getVatTin() {
		return this.vatTin;
	}

	public void setVatTin(String vatTin) {
		this.vatTin = vatTin;
	}

	public String getZone() {
		return this.zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getZoneCode() {
		return this.zoneCode;
	}

	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	public String getZoneHq() {
		return this.zoneHq;
	}

	public void setZoneHq(String zoneHq) {
		this.zoneHq = zoneHq;
	}

}
