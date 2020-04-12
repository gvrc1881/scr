/**
 * 
 */
package com.scr.message.response;

import java.util.List;

/**
 * @author vt1056
 *
 */
public class ChatConversationBetweenUsersResponse {
	private List<ChatConversationReply> chatConversationReplies;
	private Integer fromId;
	private Integer toId;
	private String image;
	private String userName;
	
	public List<ChatConversationReply> getChatConversationReplies() {
		return chatConversationReplies;
	}
	public void setChatConversationReplies(List<ChatConversationReply> chatConversationReplies) {
		this.chatConversationReplies = chatConversationReplies;
	}
	public Integer getFromId() {
		return fromId;
	}
	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}
	public Integer getToId() {
		return toId;
	}
	public void setToId(Integer toId) {
		this.toId = toId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
