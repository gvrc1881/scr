/**
 * 
 */
package com.scr.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author vt1056
 *
 */
@Entity
@Table(name = "m_location")
@NamedQuery(name = "MLocation.findAll", query = "SELECT ml FROM MLocation ml")
public class MLocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	@Column(name="locationname")
	private String LocationName;
	@Column(name="stateid")
	private Integer StateId;
	@Column(name="districtid")
	private Integer DistrictId;
	@Column(name="depotid")
	private Integer DepotId;
	@Column(name="locationshortname")
	private String LocationShortName;
	@Column(name="statusid")
	private Integer StatusId;
	@Column(name="createdby")
	private Integer CreatedBy;
	@Column(name="modifiedby")
	private Integer ModifiedBy;
	@Column(name="createddate")
	private Timestamp CreatedDate;
	@Column(name="modifieddate")
	private Timestamp ModifiedDate;
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getLocationName() {
		return LocationName;
	}
	public void setLocationName(String locationName) {
		LocationName = locationName;
	}
	public Integer getStateId() {
		return StateId;
	}
	public void setStateId(Integer stateId) {
		StateId = stateId;
	}
	public Integer getDistrictId() {
		return DistrictId;
	}
	public void setDistrictId(Integer districtId) {
		DistrictId = districtId;
	}
	public Integer getDepotId() {
		return DepotId;
	}
	public void setDepotId(Integer depotId) {
		DepotId = depotId;
	}
	public String getLocationShortName() {
		return LocationShortName;
	}
	public void setLocationShortName(String locationShortName) {
		LocationShortName = locationShortName;
	}
	public Integer getStatusId() {
		return StatusId;
	}
	public void setStatusId(Integer statusId) {
		StatusId = statusId;
	}
	public Integer getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(Integer createdBy) {
		CreatedBy = createdBy;
	}
	public Integer getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Timestamp getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		CreatedDate = createdDate;
	}
	public Timestamp getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		ModifiedDate = modifiedDate;
	}
	
}
