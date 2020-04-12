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
@Table(name="event_type")
@NamedQuery(name="EventType.findAll", query="SELECT et FROM EventType et")
public class EventType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="alert_type_id")
	private AlertType alertTypeId;
	
	@Column(name="service_for_data")
	private String serviceFordata;
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
	
	
	public AlertType getAlertTypeId() {
		return alertTypeId;
	}
	public void setAlertTypeId(AlertType alertTypeId) {
		this.alertTypeId = alertTypeId;
	}
	public String getServiceFordata() {
		return serviceFordata;
	}
	public void setServiceFordata(String serviceFordata) {
		this.serviceFordata = serviceFordata;
	}
}
