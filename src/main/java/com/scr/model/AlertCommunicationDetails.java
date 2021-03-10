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
@Table(name="alert_communication_details")
@NamedQuery(name="AlertCommunicationDetails.findAll", query="SELECT acd FROM AlertCommunicationDetails acd")
public class AlertCommunicationDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@Column(name="failed_reason")
	private String failedReason;
	@ManyToOne
	@JoinColumn(name="alert_mesaage_id")
	private AlertMesaage alertMesaageId;
	@ManyToOne
	@JoinColumn(name="receipents_id")
	private Receipents receipentsId;
	@Column(name = "date_time_delivered")
	private Timestamp dateTimeDelivered;
	@Column(name = "date_time_received")
	private Timestamp dateTimeReceived;
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
	public String getFailedReason() {
		return failedReason;
	}
	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}
	public AlertMesaage getAlertMesaageId() {
		return alertMesaageId;
	}
	public void setAlertMesaageId(AlertMesaage alertMesaageId) {
		this.alertMesaageId = alertMesaageId;
	}
	public Receipents getReceipentsId() {
		return receipentsId;
	}
	public void setReceipentsId(Receipents receipentsId) {
		this.receipentsId = receipentsId;
	}
	public Timestamp getDateTimeDelivered() {
		return dateTimeDelivered;
	}
	public void setDateTimeDelivered(Timestamp dateTimeDelivered) {
		this.dateTimeDelivered = dateTimeDelivered;
	}
	public Timestamp getDateTimeReceived() {
		return dateTimeReceived;
	}
	public void setDateTimeReceived(Timestamp dateTimeReceived) {
		this.dateTimeReceived = dateTimeReceived;
	}
	
}
