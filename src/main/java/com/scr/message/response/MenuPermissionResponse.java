package com.scr.message.response;

import java.io.Serializable;

public class MenuPermissionResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Integer masterId;
	private String masterRoleName;

	private Integer menuId;
	private String menuName;
	private String subMenuName;

	private Integer permissionId;
	private String permissionName;
	public Integer getMasterId() {
		return masterId;
	}
	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}
	public String getMasterRoleName() {
		return masterRoleName;
	}
	public void setMasterRoleName(String masterRoleName) {
		this.masterRoleName = masterRoleName;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getSubMenuName() {
		return subMenuName;
	}
	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}
	public Integer getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	@Override
	public String toString() {
		return "MenuPermissionResponse [masterId=" + masterId + ", masterRoleName=" + masterRoleName + ", menuId="
				+ menuId + ", menuName=" + menuName + ", subMenuName=" + subMenuName + ", permissionId=" + permissionId
				+ ", permissionName=" + permissionName + "]";
	}
	
	
}
