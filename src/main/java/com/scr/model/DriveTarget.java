package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the report_registry database table.
 * 
 */

@Entity
@Table(name = "drive_target")
public class DriveTarget implements Serializable {
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "unit_type")
	private String unitType;

	@Column(name = "unit_name")
	private String unitName;

	@Column(name = "target")
	private double target;

	@Column(name = "poulation")
	private String poulation;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "updated_on")
	private Timestamp updatedOn;
	
	
	@ManyToOne
	@JoinColumn(name="drive_id",foreignKey=@ForeignKey(name = "fk_drive_target_id"))
	private Drives driveId;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUnitType() {
		return unitType;
	}


	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}


	public String getUnitName() {
		return unitName;
	}


	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}


	public double getTarget() {
		return target;
	}


	public void setTarget(double target) {
		this.target = target;
	}


	/*public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}*/


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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


	public Drives getDriveId() {
		return driveId;
	}


	public void setDriveId(Drives driveId) {
		this.driveId = driveId;
	}


	public String getPoulation() {
		return poulation;
	}


	public void setPoulation(String poulation) {
		this.poulation = poulation;
	}



	
}


