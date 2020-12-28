package com.scr.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "thermovision_check_points")
@NamedQuery(name = "ThermovisionCheckPoints.findAll", query = "SELECT tpc FROM ThermovisionCheckPoints tpc")
public class ThermovisionCheckPoints implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String checkPointPart;

	private String checkPointDescription;

	private String typeOfCheckPoint;

	private String displayGroup;

	private String displayOrder;

	private String active;

	private Long commparisonPoints;

	@ManyToOne
	@JoinColumn(name = "facilityId")
	private Facility facility;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCheckPointPart() {
		return checkPointPart;
	}

	public void setCheckPointPart(String checkPointPart) {
		this.checkPointPart = checkPointPart;
	}

	public String getCheckPointDescription() {
		return checkPointDescription;
	}

	public void setCheckPointDescription(String checkPointDescription) {
		this.checkPointDescription = checkPointDescription;
	}

	public String getTypeOfCheckPoint() {
		return typeOfCheckPoint;
	}

	public void setTypeOfCheckPoint(String typeOfCheckPoint) {
		this.typeOfCheckPoint = typeOfCheckPoint;
	}

	public String getDisplayGroup() {
		return displayGroup;
	}

	public void setDisplayGroup(String displayGroup) {
		this.displayGroup = displayGroup;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Long getCommparisonPoints() {
		return commparisonPoints;
	}

	public void setCommparisonPoints(Long commparisonPoints) {
		this.commparisonPoints = commparisonPoints;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

}
