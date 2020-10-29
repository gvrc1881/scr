/**
 * 
 */
package com.scr.message.response;

import java.util.List;

import com.scr.model.Facility;

/**
 * @author vt1056
 *
 */
public class LoggedUserResponse {
	 private String email;
	 private String userName;
	 private String userId;
	 private Integer gender;
	 private Integer department;
	 private String phone;
	 private Integer roleTypeId;
	 private String roleName;
	 private String permission;
	 private String menus;
	 
	 private List<MenuPermissionResponse> menuPermissionResponses;
	 
	 private List<Facility> zoneList;
	 
	 private List<Facility> divisionList;
	 
	 private List<Facility> subDivisionList;
	 
	 private List<Facility> depotList;
	 
	public List<MenuPermissionResponse> getMenuPermissionResponses() {
		return menuPermissionResponses;
	}
	public void setMenuPermissionResponses(List<MenuPermissionResponse> menuPermissionResponses) {
		this.menuPermissionResponses = menuPermissionResponses;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getDepartment() {
		return department;
	}
	public void setDepartment(Integer department) {
		this.department = department;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getRoleTypeId() {
		return roleTypeId;
	}
	public void setRoleTypeId(Integer roleTypeId) {
		this.roleTypeId = roleTypeId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getMenus() {
		return menus;
	}
	public void setMenus(String menus) {
		this.menus = menus;
	}
	public List<Facility> getZoneList() {
		return zoneList;
	}
	public void setZoneList(List<Facility> zoneList) {
		this.zoneList = zoneList;
	}
	public List<Facility> getDivisionList() {
		return divisionList;
	}
	public void setDivisionList(List<Facility> divisionList) {
		this.divisionList = divisionList;
	}
	public List<Facility> getSubDivisionList() {
		return subDivisionList;
	}
	public void setSubDivisionList(List<Facility> subDivisionList) {
		this.subDivisionList = subDivisionList;
	}
	public List<Facility> getDepotList() {
		return depotList;
	}
	public void setDepotList(List<Facility> depotList) {
		this.depotList = depotList;
	}
	 
}
