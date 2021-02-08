package com.scr.message.request;

import java.sql.Timestamp;

import com.scr.model.Facility;

public class OheThermovisionMeasureRequest {

	private Timestamp dateTime;
	private Facility facilityId;
	private String connectionPoint1;
	private String connectionPoint2;
	private double measure1;
	private double measure2;
	private double ambientTemp;
	private String by;
	private String generalRemark;
	private String location;

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public Facility getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Facility facilityId) {
		this.facilityId = facilityId;
	}

	public double getAmbientTemp() {
		return ambientTemp;
	}

	public void setAmbientTemp(double ambientTemp) {
		this.ambientTemp = ambientTemp;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getGeneralRemark() {
		return generalRemark;
	}

	public void setGeneralRemark(String generalRemark) {
		this.generalRemark = generalRemark;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getConnectionPoint1() {
		return connectionPoint1;
	}

	public void setConnectionPoint1(String connectionPoint1) {
		this.connectionPoint1 = connectionPoint1;
	}

	public String getConnectionPoint2() {
		return connectionPoint2;
	}

	public void setConnectionPoint2(String connectionPoint2) {
		this.connectionPoint2 = connectionPoint2;
	}

	public double getMeasure1() {
		return measure1;
	}

	public void setMeasure1(double measure1) {
		this.measure1 = measure1;
	}

	public double getMeasure2() {
		return measure2;
	}

	public void setMeasure2(double measure2) {
		this.measure2 = measure2;
	}

}
