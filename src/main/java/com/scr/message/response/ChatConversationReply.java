/**
 * 
 */
package com.scr.message.response;

import java.sql.Timestamp;

/**
 * @author vt1056
 *
 */
public class ChatConversationReply {
	  private Integer ID;
	  private String Message;
	  private Timestamp TimeStamp;
	  private Integer ConversationId;
      private Integer StatusId;

      private Integer From_Id;
      private Integer To_Id;
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
		return ConversationId;
	}
	public void setConversationId(Integer conversationId) {
		ConversationId = conversationId;
	}
	public Integer getStatusId() {
		return StatusId;
	}
	public void setStatusId(Integer statusId) {
		StatusId = statusId;
	}
	public Integer getFrom_Id() {
		return From_Id;
	}
	public void setFrom_Id(Integer from_Id) {
		From_Id = from_Id;
	}
	public Integer getTo_Id() {
		return To_Id;
	}
	public void setTo_Id(Integer to_Id) {
		To_Id = to_Id;
	}
      
}
