package com.scr.model;

import java.sql.Timestamp;

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


@Entity
@Table(name="page_role_permission")
@NamedQuery(name="PageRolePermission.findAll", query="SELECT p FROM PageRolePermission p")
public class PageRolePermission {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String page;
	
	@ManyToOne
	@JoinColumn(name="masterRoleId",foreignKey=@ForeignKey(name = "fk_page_role_permission_master_role_id"))
	private MasterRoles masterRoleId;
	
	@ManyToOne
	@JoinColumn(name="permissionId",foreignKey=@ForeignKey(name = "fk_page_role_permission_permission_id"))
	private Permissions permissionId;
	
	@ManyToOne
	@JoinColumn(name="menuId",foreignKey=@ForeignKey(name = "fk_page_role_permission_menu_id"))
	private Menu menuId;
	
	private String status;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_on")
	private Timestamp createdOn;
	
	@Column(name = "modified_on")
	private Timestamp modifiedOn;
	
	@Column(name = "modified_by")
	private String modifiedBy;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public MasterRoles getMasterRoleId() {
		return masterRoleId;
	}

	public void setMasterRoleId(MasterRoles masterRoleId) {
		this.masterRoleId = masterRoleId;
	}

	public Permissions getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Permissions permissionId) {
		this.permissionId = permissionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Timestamp getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Timestamp modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Menu getMenuId() {
		return menuId;
	}

	public void setMenuId(Menu menuId) {
		this.menuId = menuId;
	}

	
}
