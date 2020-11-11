package com.scr.model;
import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * The persistent class for the works database table.
 * 
 */
@Entity
@Table(name = "work_group")
@NamedQuery(name = "WorkGroup.findAll", query = "SELECT w FROM WorkGroup w")

public class WorkGroup  implements Serializable{
	

	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "work_group")
	private String workGroup;

	@Column(name = "section")
	private String section;

	@Column(name = "division")
	private String division;

	@Column(name = "zone")
	private String zone;

	@Column(name = "agency")
	private String agency;

	@Column(name = "doubling_trippling ")
	private String doublingTrippling;

	@Column(name = "siding_yard_station")
	private String sidingYardStation;

	@Column(name = "code")
	private String code;

	@Column(name = "description")
	private String description;

	@Column(name = "rkm")
	private Double rkm;
	
	@Column(name = "tkm")
	private Double tkm;

	public WorkGroup() {
	}

	public Integer getId() {
		return id;
	}
  
	public void setId(Integer id) {
		this.id = id;
	}

	public String getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(String workGroup) {
		this.workGroup = workGroup;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getDoublingTrippling() {
		return doublingTrippling;
	}

	public void setDoublingTrippling(String doublingTrippling) {
		this.doublingTrippling = doublingTrippling;
	}

	public String getSidingYardStation() {
		return sidingYardStation;
	}

	public void setSidingYardStation(String sidingYardStation) {
		this.sidingYardStation = sidingYardStation;
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

	public Double getRkm() {
		return rkm;
	}

	public void setRkm(Double rkm) {
		this.rkm = rkm;
	}

	public Double getTkm() {
		return tkm;
	}

	public void setTkm(Double tkm) {
		this.tkm = tkm;
	}

	

	

}
