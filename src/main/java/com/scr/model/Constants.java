/**
 * 
 */
package com.scr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Embeddable
@Entity
@Table(name = "constant")
public class Constants implements Serializable{
	@Id
	@Column(name = "constant_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer constantId;
	@Column(name = "constant_created_by")
	private Integer constantCreatedBy;
	@Column(name = "constant_created_date")
	private Date constantCreatedDate;
	@Column(name = "constant_key_name")
	private String constantKeyName;
	@Column(name = "constant_key_value")
	private String constantKeyvalue;
	@Column(name = "constant_modified_by")
	private Integer constantModifiedBy;
	@Column(name = "constant_modified_date")
	private Date constantModifiedDate;
	@Column(name = "constant_status_id")
	private Integer constantStatusId;
	
	
	
	
	public Constants() {
		super();
	}
	public Constants(int constantId, int constantCreatedBy, Date constantCreatedDate, String constantKeyName,
			String constantKeyvalue, int constantModifiedBy, Date constantModifiedDate, int constantStatusId) {
		super();
		this.constantId = constantId;
		this.constantCreatedBy = constantCreatedBy;
		this.constantCreatedDate = constantCreatedDate;
		this.constantKeyName = constantKeyName;
		this.constantKeyvalue = constantKeyvalue;
		this.constantModifiedBy = constantModifiedBy;
		this.constantModifiedDate = constantModifiedDate;
		this.constantStatusId = constantStatusId;
	}
	public int getConstantId() {
		return constantId;
	}
	public void setConstantId(int constantId) {
		this.constantId = constantId;
	}
	public int getConstantCreatedBy() {
		return constantCreatedBy;
	}
	public void setConstantCreatedBy(int constantCreatedBy) {
		this.constantCreatedBy = constantCreatedBy;
	}
	public Date getConstantCreatedDate() {
		return constantCreatedDate;
	}
	public void setConstantCreatedDate(Date constantCreatedDate) {
		this.constantCreatedDate = constantCreatedDate;
	}
	public String getConstantKeyName() {
		return constantKeyName;
	}
	public void setConstantKeyName(String constantKeyName) {
		this.constantKeyName = constantKeyName;
	}
	public String getConstantKeyvalue() {
		return constantKeyvalue;
	}
	public void setConstantKeyvalue(String constantKeyvalue) {
		this.constantKeyvalue = constantKeyvalue;
	}
	public int getConstantModifiedBy() {
		return constantModifiedBy;
	}
	public void setConstantModifiedBy(int constantModifiedBy) {
		this.constantModifiedBy = constantModifiedBy;
	}
	public Date getConstantModifiedDate() {
		return constantModifiedDate;
	}
	public void setConstantModifiedDate(Date constantModifiedDate) {
		this.constantModifiedDate = constantModifiedDate;
	}
	public int getConstantStatusId() {
		return constantStatusId;
	}
	public void setConstantStatusId(int constantStatusId) {
		this.constantStatusId = constantStatusId;
	}
	

}
