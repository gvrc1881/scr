package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="zone" , uniqueConstraints={@UniqueConstraint(name="zone_code_key", columnNames={"code"})})

public class Zone implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id 
	private long id;

	@Column(name = "code")
	private String code;
	@Column(name = "description")
	private String description;
	@Column(name = "headquarters")
	private String headquarters;
	@Column(name = "address_1")
	private String address_1;
	@Column(name = "address_2")
	private String address_2;
	@Column(name = "city")
	private String city;
	@Column(name = "district")
	private String district;
	@Column(name = "state")
	private String state;
	@Column(name = "pin")
	private String pin;
	@Column(name = "createdby")
	private String createdBy;
	@Column(name = "createdon")
	private Timestamp createdOn;
	@Column(name = "updatedby")
	private String updatedBy;
	@Column(name = "updatedon")
	private Timestamp updatedOn;

	@ManyToOne
	@JoinColumn(name="division_id",foreignKey = @ForeignKey(name = "fk_zone_division_id"))
	private Division divisionId;

	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getHeadquarters() {
		return headquarters;
	}
	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}
	public String getAddress_1() {
		return address_1;
	}
	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}
	public String getAddress_2() {
		return address_2;
	}
	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}
	
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Division getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(Division divisionId) {
		this.divisionId = divisionId;
	}
	
	

   
    
}
