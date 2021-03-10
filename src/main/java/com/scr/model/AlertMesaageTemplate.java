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
@Table(name="alert_mesaage_template")
@NamedQuery(name="AlertMesaageTemplate.findAll", query="SELECT amt FROM AlertMesaageTemplate amt")
public class AlertMesaageTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@Column(name="text")
	private String text;
	@Column(name="data1")
	private String data1;
	@Column(name="seq_no")
	private Integer seqNo;
	@ManyToOne
	@JoinColumn(name="event_type_id")
	private EventType eventTypeId;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public Integer getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}
	public EventType getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(EventType eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	
}
