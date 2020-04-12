/**
 * 
 */
package com.scr.model;

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
@Table(name="receipents")
@NamedQuery(name="Receipents.findAll", query="SELECT r FROM Receipents r")
public class Receipents {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="mobile")
	private String mobile;
	@Column(name="whatsappid")
	private String whatsappid;
	@Column(name="primaryEmail")
	private String primaryEmail;
	@Column(name="secondaryEmail")
	private String secondaryEmail;
	@Column(name="userName")
	private String userName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getWhatsappid() {
		return whatsappid;
	}
	public void setWhatsappid(String whatsappid) {
		this.whatsappid = whatsappid;
	}
	public String getPrimaryEmail() {
		return primaryEmail;
	}
	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}
	public String getSecondaryEmail() {
		return secondaryEmail;
	}
	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
