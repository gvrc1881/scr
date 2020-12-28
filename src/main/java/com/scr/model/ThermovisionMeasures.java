package com.scr.model;

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

	private String fixedMeasure;

	private String cClampMeasure;

	private Double ambientTemp;

	private String imageId;

	private String remark;

	private Long criticality;

	private Double varianceWithOtherPoint;

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

	public String getFixedMeasure() {
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
	}

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

	public Long getCriticality() {
		return criticality;
	}

	public void setCriticality(Long criticality) {
		this.criticality = criticality;
	}

	public Double getVarianceWithOtherPoint() {
		return varianceWithOtherPoint;
	}

	public void setVarianceWithOtherPoint(Double varianceWithOtherPoint) {
		this.varianceWithOtherPoint = varianceWithOtherPoint;
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

}
