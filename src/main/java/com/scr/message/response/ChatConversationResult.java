/**
 * 
 */
package com.scr.message.response;

import java.util.List;

/**
 * @author vt1056
 *
 */
public class ChatConversationResult {
	private List<ChatConversationReply> Result;
	private Integer To_Id;
	private Integer From_Id;
	private String Image;
	private String UserName;
	public List<ChatConversationReply> getResult() {
		return Result;
	}
	public void setResult(List<ChatConversationReply> result) {
		Result = result;
	}
	public Integer getTo_Id() {
		return To_Id;
	}
	public void setTo_Id(Integer to_Id) {
		To_Id = to_Id;
	}
	public Integer getFrom_Id() {
		return From_Id;
	}
	public void setFrom_Id(Integer from_Id) {
		From_Id = from_Id;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	
}
