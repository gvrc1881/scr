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
@Table(name="event_alert_recipient")
@NamedQuery(name="EventAlertRecipient.findAll", query="SELECT ear FROM EventAlertRecipient ear")
public class EventAlertRecipient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="event_type_id")
	private EventType eventTypeId;
	
	@ManyToOne
	@JoinColumn(name="alert_group_id")
	private AlertGroup alertGroupId;
	
	@ManyToOne
	@JoinColumn(name="alert_group__member_id")
	private AlertGroupMember alertGroupMemberId;

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

	public EventType getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(EventType eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public AlertGroup getAlertGroupId() {
		return alertGroupId;
	}

	public void setAlertGroupId(AlertGroup alertGroupId) {
		this.alertGroupId = alertGroupId;
	}

	public AlertGroupMember getAlertGroupMemberId() {
		return alertGroupMemberId;
	}

	public void setAlertGroupMemberId(AlertGroupMember alertGroupMemberId) {
		this.alertGroupMemberId = alertGroupMemberId;
	}
	
	
}
