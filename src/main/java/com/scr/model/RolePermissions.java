package com.scr.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table (name = "role_permission")
public @Data class RolePermissions implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/*
	 * @Column(name="menu_id") private int menuId;
	 * 
	 * @Column(name="sub_menu_id") private int submenuId;
	 */
	@Column(name="role_type_id")
	private int roleTypeId;
	@Column(name="permission_id")
	private int permissionId;
	/*
	 * @Column(name="is_menu") private int ismenu;
	
	@Column(name="field_permission")
	private String filedPermission; */
	@Column(name="created_by")
	private int createdBy;
	@Column(name="created_date")
	private  Timestamp createdDate;
	@Column(name="modified_by")
	private  int modifiedBy;
	@Column(name="modified_date")
	private  Timestamp modifiedDate;
	@Column(name="status_id")
	private int statusId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleTypeId() {
		return roleTypeId;
	}
	public void setRoleTypeId(int roleTypeId) {
		this.roleTypeId = roleTypeId;
	}
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	

}
