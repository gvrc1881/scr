package com.scr.model;

import java.io.Serializable;
import javax.persistence.Column;
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

	private String checkPoint1Description;
	
	private String checkPoint2Description;


	private String displayGroup;

	private String displayOrder;
	
	
	private String active;
	
	@Column(name="seq_id")
	private String seqId;

	@ManyToOne
	@JoinColumn(name = "facility_id")
	private Facility facilityId;


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


	public String getCheckPoint1Description() {
		return checkPoint1Description;
	}


	public void setCheckPoint1Description(String checkPoint1Description) {
		this.checkPoint1Description = checkPoint1Description;
	}


	public String getCheckPoint2Description() {
		return checkPoint2Description;
	}


	public void setCheckPoint2Description(String checkPoint2Description) {
		this.checkPoint2Description = checkPoint2Description;
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


	public Facility getFacilityId() {
		return facilityId;
	}


	public void setFacilityId(Facility facilityId) {
		this.facilityId = facilityId;
	}


	public String getSeqId() {
		return seqId;
	}


	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
	
	

}
