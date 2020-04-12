/**
 * 
 */
package com.scr.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vt1056
 *
 */
@Embeddable
@Entity
@Table(name = "remark")
public class Remark implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "remark_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer remarkId;
	
	private Integer jobId;
	private Integer runTypeId;
	private String remark;
	private Integer runBy;
	private Date runDate;
	public Integer getRemarkId() {
		return remarkId;
	}
	public void setRemarkId(Integer remarkId) {
		this.remarkId = remarkId;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public Integer getRunTypeId() {
		return runTypeId;
	}
	public void setRunTypeId(Integer runTypeId) {
		this.runTypeId = runTypeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getRunBy() {
		return runBy;
	}
	public void setRunBy(Integer runBy) {
		this.runBy = runBy;
	}
	public Date getRunDate() {
		return runDate;
	}
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}	
	
	
}
