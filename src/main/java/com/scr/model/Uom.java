package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the uom database table.
 * 
 */
@Entity
@Table(name = "uom" , uniqueConstraints={@UniqueConstraint(name = "old_pk_uom_uniq", columnNames ={"uom_id"})})
@NamedQuery(name="Uom.findAll", query="SELECT u FROM Uom u")
public class Uom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String abbreviation;

	@Column(name="created_stamp")
	private Timestamp createdStamp;

	@Column(name="created_tx_stamp")
	private Timestamp createdTxStamp;

	private String description;

	@Column(name="last_updated_stamp")
	private Timestamp lastUpdatedStamp;

	@Column(name="last_updated_tx_stamp")
	private Timestamp lastUpdatedTxStamp;

	@Column(name="rly_uom_id")
	private String rlyUomId;

	@Column(name="uom_id")
	private String uomId;

	@Column(name="uom_type_id")
	private String uomTypeId;

	public Uom() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getRlyUomId() {
		return this.rlyUomId;
	}

	public void setRlyUomId(String rlyUomId) {
		this.rlyUomId = rlyUomId;
	}

	public String getUomId() {
		return this.uomId;
	}

	public void setUomId(String uomId) {
		this.uomId = uomId;
	}

	public String getUomTypeId() {
		return this.uomTypeId;
	}

	public void setUomTypeId(String uomTypeId) {
		this.uomTypeId = uomTypeId;
	}

}