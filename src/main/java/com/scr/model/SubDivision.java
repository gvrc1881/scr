
package com.scr.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the report_registry database table.
 * 
 */

@Entity
@Table(name = "sub_division" , uniqueConstraints={@UniqueConstraint(name = "sub_division_code_key",columnNames ={"code"})})
@NamedQuery(name="SubDivision.findAll", query="SELECT sd FROM SubDivision sd")
public class SubDivision implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="code")
	private String code;

	@Column(name="description")
	private String description;

	@Column(name="headquarters")
	private String headquarters;


	@Column(name="address_1")
	private String address_1;

	@Column(name="address_2")
	private String address_2;


	@Column(name="city")
	private String city;
	
	@Column(name="district")
	private String district;


	@Column(name="state")
	private String state;

	@Column(name="pin")
	private String pin;

	@Column(name="created_by")
	private String createdBy;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="updated_on")
	private Timestamp updatedOn;	
	
	

	@ManyToOne
	@JoinColumn(name="division_id",foreignKey=@ForeignKey(name = "fk_sub_division_division_id"))
	private Division divisionId;

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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
