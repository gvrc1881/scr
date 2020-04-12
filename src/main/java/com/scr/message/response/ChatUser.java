/**
 * 
 */
package com.scr.message.response;

/**
 * @author vt1056
 *
 */
public class ChatUser {
	private Integer ID;
    private String UserName;
    private String UserImagePath;
    private Integer OnlineStatusId;
    private String UserLocationName;
    private Integer UserLocationId;
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUserImagePath() {
		return UserImagePath;
	}
	public void setUserImagePath(String userImagePath) {
		UserImagePath = userImagePath;
	}
	public Integer getOnlineStatusId() {
		return OnlineStatusId;
	}
	public void setOnlineStatusId(Integer onlineStatusId) {
		OnlineStatusId = onlineStatusId;
	}
	public String getUserLocationName() {
		return UserLocationName;
	}
	public void setUserLocationName(String userLocationName) {
		UserLocationName = userLocationName;
	}
	public Integer getUserLocationId() {
		return UserLocationId;
	}
	public void setUserLocationId(Integer userLocationId) {
		UserLocationId = userLocationId;
	}
    
    
}
