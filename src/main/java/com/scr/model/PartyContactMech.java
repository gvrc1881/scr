package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the party_contact_mech database table.
 * 
 */
@Entity
@Table(name = "party_contact_mech_purpose" , uniqueConstraints={@UniqueConstraint(name = "old_pk_party_contact_mech_purpose_uniq", columnNames ={"contact_mech_purpose_type_id", "contact_mech_id", "party_id", "from_date"})})
@NamedQuery(name="PartyContactMech.findAll", query="SELECT p FROM PartyContactMech p")
public class PartyContactMech implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="allow_solicitation")
	private String allowSolicitation;

	private String comments;

	@Column(name="contact_mech_id")
	private String contactMechId;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="data_div")
	private String dataDiv;

	private String extension;

	@Column(name="from_date")
	private Timestamp fromDate;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="months_with_contact_mech")
	private BigDecimal monthsWithContactMech;

	@Column(name="party_id")
	private String partyId;

	@Column(name="role_type_id")
	private String roleTypeId;

	@Column(name="thru_date")
	private Timestamp thruDate;

	private String verified;

	@Column(name="years_with_contact_mech")
	private BigDecimal yearsWithContactMech;

	public PartyContactMech() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAllowSolicitation() {
		return this.allowSolicitation;
	}

	public void setAllowSolicitation(String allowSolicitation) {
		this.allowSolicitation = allowSolicitation;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getContactMechId() {
		return this.contactMechId;
	}

	public void setContactMechId(String contactMechId) {
		this.contactMechId = contactMechId;
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

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Timestamp getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
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

	public BigDecimal getMonthsWithContactMech() {
		return this.monthsWithContactMech;
	}

	public void setMonthsWithContactMech(BigDecimal monthsWithContactMech) {
		this.monthsWithContactMech = monthsWithContactMech;
	}

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getRoleTypeId() {
		return this.roleTypeId;
	}

	public void setRoleTypeId(String roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public Timestamp getThruDate() {
		return this.thruDate;
	}

	public void setThruDate(Timestamp thruDate) {
		this.thruDate = thruDate;
	}

	public String getVerified() {
		return this.verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
	}

	public BigDecimal getYearsWithContactMech() {
		return this.yearsWithContactMech;
	}

	public void setYearsWithContactMech(BigDecimal yearsWithContactMech) {
		this.yearsWithContactMech = yearsWithContactMech;
	}

}
