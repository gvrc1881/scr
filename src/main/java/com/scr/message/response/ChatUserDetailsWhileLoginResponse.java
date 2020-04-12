/**
 * 
 */
package com.scr.message.response;

import java.util.List;

/**
 * @author vt1056
 *
 */
public class ChatUserDetailsWhileLoginResponse extends BaseResponse {
	private ChatUser LoginUser;
	private List<ChatUser> ExceptLoginUser;
	private List<ChatUser> UnreadCount;
	public ChatUser getLoginUser() {
		return LoginUser;
	}
	public void setLoginUser(ChatUser loginUser) {
		LoginUser = loginUser;
	}
	public List<ChatUser> getExceptLoginUser() {
		return ExceptLoginUser;
	}
	public void setExceptLoginUser(List<ChatUser> exceptLoginUser) {
		ExceptLoginUser = exceptLoginUser;
	}
	public List<ChatUser> getUnreadCount() {
		return UnreadCount;
	}
	public void setUnreadCount(List<ChatUser> unreadCount) {
		UnreadCount = unreadCount;
	}
	
}
