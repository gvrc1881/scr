package com.scr.model;

import java.sql.Timestamp;

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
@Table(name = "thermovision_measures")
@NamedQuery(name = "ThermovisionMeasures.findAll", query = "SELECT tm FROM ThermovisionMeasures tm")
public class ThermovisionMeasures {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//private String fixedMeasure;

	//private String cClampMeasure;

	//private Double ambientTemp;
	
	private Double measurePoint1;
	
	private Double measurePoint2;

	private String imageId;

	private String remark;

	private String criticality;
	
	private String Location;
	
	private String connectionPoint1;
	
	private String connectionPoint2;
	
	private Double ambientTemp;
	
	private String createdBy;

	private String updatedBy;

	private Timestamp createdOn;

	private Timestamp updatedOn;

	//private Double varianceWithOtherPoint;

	@ManyToOne
	@JoinColumn(name = "tcp_id")
	private ThermovisionCheckPoints tcpId;
	
	@ManyToOne
	@JoinColumn(name = "tcp_schedule_id")
	private TcpSchedule tcpScheduleId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*public String getFixedMeasure() {
		return fixedMeasure;
	}

	public void setFixedMeasure(String fixedMeasure) {
		this.fixedMeasure = fixedMeasure;
	}

	public String getcClampMeasure() {
		return cClampMeasure;
	}

	public void setcClampMeasure(String cClampMeasure) {
		this.cClampMeasure = cClampMeasure;
	}

	public Double getAmbientTemp() {
		return ambientTemp;
	}

	public void setAmbientTemp(Double ambientTemp) {
		this.ambientTemp = ambientTemp;
	}*/

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/*public Double getVarianceWithOtherPoint() {
		return varianceWithOtherPoint;
	}

	public void setVarianceWithOtherPoint(Double varianceWithOtherPoint) {
		this.varianceWithOtherPoint = varianceWithOtherPoint;
	}*/

	public Double getMeasurePoint1() {
		return measurePoint1;
	}

	public void setMeasurePoint1(Double measurePoint1) {
		this.measurePoint1 = measurePoint1;
	}

	public Double getMeasurePoint2() {
		return measurePoint2;
	}

	public void setMeasurePoint2(Double measurePoint2) {
		this.measurePoint2 = measurePoint2;
	}

	public String getCriticality() {
		return criticality;
	}

	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}

	public TcpSchedule getTcpScheduleId() {
		return tcpScheduleId;
	}

	public void setTcpScheduleId(TcpSchedule tcpScheduleId) {
		this.tcpScheduleId = tcpScheduleId;
	}

	public ThermovisionCheckPoints getTcpId() {
		return tcpId;
	}

	public void setTcpId(ThermovisionCheckPoints tcpId) {
		this.tcpId = tcpId;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public double getAmbientTemp() {
		return ambientTemp;
	}

	public void setAmbientTemp(double ambientTemp) {
		this.ambientTemp = ambientTemp;
	}

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

}
