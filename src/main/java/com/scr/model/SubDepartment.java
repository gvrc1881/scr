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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The persistent class for the report_registry database table.
 * 
 */
@Entity
@Table(name = "sub_department", uniqueConstraints = @UniqueConstraint(name ="sub_department_code_description_key", columnNames = {"code", "description" }))
@NamedQuery(name = "SubDepartment.findAll", query = "SELECT sd FROM SubDepartment sd")
public class SubDepartment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code")
	private String code;
	@Column(name = "description")
	private String descripti_on;
	@Column(name = "created_by")
	private String createdBy;
	@Column(name = "created_on")
	private Timestamp createdOn;
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name = "updated_on")
	private Timestamp updatedOn;
	@Column(name = "department")
	private String department;

	@ManyToOne
	@JoinColumn(name="departmentcode_id",foreignKey = @ForeignKey(name = "fk_sub_department_departmentcode_id"))
	private Department departmentcodeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescripti_on() {
		return descripti_on;
	}

	public void setDescripti_on(String descripti_on) {
		this.descripti_on = descripti_on;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Department getDepartmentcodeId() {
		return departmentcodeId;
	}

	public void setDepartmentcodeId(Department departmentcodeId) {
		this.departmentcodeId = departmentcodeId;
	}

	
}
