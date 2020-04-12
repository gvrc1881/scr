/**
 * 
 */
package com.scr.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author vt1056
 *
 */
@Entity
@Table(name = "t_users")
@NamedQuery(name = "TUsers.findAll", query = "SELECT tu FROM TUsers tu")
public class TUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	@Column(name="username")
	private String UserName;
	@Column(name="password")
	private String Password;
	@Column(name="fullname")
	private String FullName;
	@Column(name="phoneno")
	private String PhoneNo;
	@Column(name="email")
	private String Email;
	@Column(name="employeeid")
	private String EmployeeId;
	@Column(name="locationid")
	private Integer LocationId;
	@Column(name="roleid")
	private Integer RoleId;
	@Column(name="passwordreset")
	private Integer PasswordReset;
	@Column(name="createdby")
	private Integer CreatedBy;
	@Column(name="createddate")
	private Timestamp CreatedDate;
	@Column(name="modifiedby")
	private Integer ModifiedBy;
	@Column(name="modifieddate")
	private Timestamp ModifiedDate;
	@Column(name="statusid")
	private Integer StatusId;
	@Column(name="onlinestatusid")
	private Integer OnlineStatusId;
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getPhoneNo() {
		return PhoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getEmployeeId() {
		return EmployeeId;
	}
	public void setEmployeeId(String employeeId) {
		EmployeeId = employeeId;
	}
	public Integer getLocationId() {
		return LocationId;
	}
	public void setLocationId(Integer locationId) {
		LocationId = locationId;
	}
	public Integer getRoleId() {
		return RoleId;
	}
	public void setRoleId(Integer roleId) {
		RoleId = roleId;
	}
	public Integer getPasswordReset() {
		return PasswordReset;
	}
	public void setPasswordReset(Integer passwordReset) {
		PasswordReset = passwordReset;
	}
	public Integer getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(Integer createdBy) {
		CreatedBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		CreatedDate = createdDate;
	}
	public Integer getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(Integer modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		ModifiedDate = modifiedDate;
	}
	public Integer getStatusId() {
		return StatusId;
	}
	public void setStatusId(Integer statusId) {
		StatusId = statusId;
	}
	public Integer getOnlineStatusId() {
		return OnlineStatusId;
	}
	public void setOnlineStatusId(Integer onlineStatusId) {
		OnlineStatusId = onlineStatusId;
	}
	
}
