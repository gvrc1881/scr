/**
 * 
 */
package com.scr.model;

import java.io.Serializable;
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
@Table(name="mobile_sms")
@NamedQuery(name="MobileSMS.findAll", query="SELECT d FROM MobileSMS d")
public class MobileSMS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")	 
	private Long Id;
	@Column(name = "processId")
	private Long processId;
	@Column(name="message",columnDefinition = "TEXT")
	private String message;	
	@Column(name="message_type")
	private String messageType;
	@Column(name = "recipient")
	private String recipient;
	@Column(name = "send_time")
	private Timestamp sendTime;
	@Column(name = "smsStatus")
	private String smsStatus;
	@Column(name = "receivedTime")
	private Timestamp receivedTime;
	@Column(name = "failedReason")
	private String failedReason;
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public Long getProcessId() {
		return processId;
	}
	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public Timestamp getSendTime() {
		return sendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		this.sendTime = sendTime;
	}
	public String getSmsStatus() {
		return smsStatus;
	}
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}
	public Timestamp getReceivedTime() {
		return receivedTime;
	}
	public void setReceivedTime(Timestamp receivedTime) {
		this.receivedTime = receivedTime;
	}
	public String getFailedReason() {
		return failedReason;
	}
	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
}
