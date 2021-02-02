package com.scr.message.request;

import java.sql.Timestamp;

import com.scr.model.Facility;

public class OheThermovisionMeasureRequest {

	private Timestamp dateTime;
	private Facility facilityId;
	private String connectionPoint;
	private double measure;
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

	public String getConnectionPoint() {
		return connectionPoint;
	}

	public void setConnectionPoint(String connectionPoint) {
		this.connectionPoint = connectionPoint;
	}

	public double getMeasure() {
		return measure;
	}

	public void setMeasure(double measure) {
		this.measure = measure;
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

}
