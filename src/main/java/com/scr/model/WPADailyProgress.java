package com.scr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "wpa_daily_progress")
@NamedQuery(name = "WPADailyProgress.findAll", query = "SELECT wpadp FROM WPADailyProgress wpadp")
public class WPADailyProgress implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "work_phase_activity_id", foreignKey = @ForeignKey(name = "fk_wpa_daily_progress_work_phase_activity"))
	private WorkPhaseActivity workPhaseActivityId;

	@ManyToOne
	@JoinColumn(name = "work_group_id", foreignKey = @ForeignKey(name = "fk_wpa_daily_progress_work_group"))
	private WorkGroup workGroupId;

	@Temporal(TemporalType.DATE)
	private Date date;

	private Double performedCount;

	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WorkPhaseActivity getWorkPhaseActivityId() {
		return workPhaseActivityId;
	}

	public void setWorkPhaseActivityId(WorkPhaseActivity workPhaseActivityId) {
		this.workPhaseActivityId = workPhaseActivityId;
	}

	public WorkGroup getWorkGroupId() {
		return workGroupId;
	}

	public void setWorkGroupId(WorkGroup workGroupId) {
		this.workGroupId = workGroupId;
	}

	public Double getPerformedCount() {
		return performedCount;
	}

	public void setPerformedCount(Double performedCount) {
		this.performedCount = performedCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
