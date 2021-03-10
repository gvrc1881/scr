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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author vt1056
 *
 */
@Entity
@Table(name="event")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event {
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
	@Column(name = "event_date_time")
	private Timestamp EventDateTime;
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
	public Timestamp getEventDateTime() {
		return EventDateTime;
	}
	public void setEventDateTime(Timestamp eventDateTime) {
		EventDateTime = eventDateTime;
	}
	
}
