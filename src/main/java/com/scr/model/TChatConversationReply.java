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
@Table(name="t_chat_conversation_reply")
@NamedQuery(name="TChatConversationReply.findAll", query="SELECT j FROM TChatConversationReply j")
public class TChatConversationReply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ID;
	@Column(name="message")
	private String Message;
	@Column(name="timestamp")
	private Timestamp TimeStamp;
	@Column(name="conversationid")
	private Integer conversationId;
	@Column(name="statusid")
	private Integer statusId;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Timestamp getTimeStamp() {
		return TimeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		TimeStamp = timeStamp;
	}

	public Integer getConversationId() {
		return conversationId;
	}
	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	
	
}
