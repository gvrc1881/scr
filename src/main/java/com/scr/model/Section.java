package com.scr.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "section" )
public class Section {
	
	@Id
	private Long id;
	
	@Column(name="code")
	private String code;

	@Column(name="description")
	private String description;
	
	@Column(name="location_from")
	private BigInteger locationFrom;
	
	@Column(name="location_to")
	private String locationTo;
	
	@Column(name="depot")
	private BigInteger depot;
	
	@Column(name="division")
	private BigInteger division;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigInteger getLocationFrom() {
		return locationFrom;
	}

	public void setLocationFrom(BigInteger locationFrom) {
		this.locationFrom = locationFrom;
	}

	public String getLocationTo() {
		return locationTo;
	}

	public void setLocationTo(String locationTo) {
		this.locationTo = locationTo;
	}

	public BigInteger getDepot() {
		return depot;
	}

	public void setDepot(BigInteger depot) {
		this.depot = depot;
	}

	public BigInteger getDivision() {
		return division;
	}

	public void setDivision(BigInteger division) {
		this.division = division;
	}

}
