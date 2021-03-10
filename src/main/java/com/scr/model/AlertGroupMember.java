/**
 * 
 */
package com.scr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author vt1056
 *
 */
@Entity
@Table(name="alert_group_member")
@NamedQuery(name="AlertGroupMember.findAll", query="SELECT agm FROM AlertGroupMember agm")
public class AlertGroupMember {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@ManyToOne
	@JoinColumn(name="alert_group_id")
	private AlertGroup alertGroupId;
	@ManyToOne
	@JoinColumn(name="receipents_id")
	private Receipents receipentsId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public AlertGroup getAlertGroupId() {
		return alertGroupId;
	}
	public void setAlertGroupId(AlertGroup alertGroupId) {
		this.alertGroupId = alertGroupId;
	}
	public Receipents getReceipentsId() {
		return receipentsId;
	}
	public void setReceipentsId(Receipents receipentsId) {
		this.receipentsId = receipentsId;
	}
	
	
}
