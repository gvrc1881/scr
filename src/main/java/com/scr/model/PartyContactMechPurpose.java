package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the party_contact_mech_purpose database table.
 * 
 */
@Entity
@Table(name = "party_contact_mech_purpose" , uniqueConstraints={@UniqueConstraint(name = "old_pk_party_contact_mech_purpose_uniq", columnNames ={"contact_mech_purpose_type_id", "contact_mech_id", "party_id", "from_date"})})
@NamedQuery(name="PartyContactMechPurpose.findAll", query="SELECT p FROM PartyContactMechPurpose p")
public class PartyContactMechPurpose implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="contact_mech_id")
	private String contactMechId;

	@Column(name="contact_mech_purpose_type_id")
	private String contactMechPurposeTypeId;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	@Column(name="from_date")
	private Timestamp fromDate;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="party_id")
	private String partyId;

	@Column(name="thru_date")
	private Timestamp thruDate;

	public PartyContactMechPurpose() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContactMechId() {
		return this.contactMechId;
	}

	public void setContactMechId(String contactMechId) {
		this.contactMechId = contactMechId;
	}

	public String getContactMechPurposeTypeId() {
		return this.contactMechPurposeTypeId;
	}

	public void setContactMechPurposeTypeId(String contactMechPurposeTypeId) {
		this.contactMechPurposeTypeId = contactMechPurposeTypeId;
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

	public String getPartyId() {
		return this.partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public Timestamp getThruDate() {
		return this.thruDate;
	}

	public void setThruDate(Timestamp thruDate) {
		this.thruDate = thruDate;
	}

}
