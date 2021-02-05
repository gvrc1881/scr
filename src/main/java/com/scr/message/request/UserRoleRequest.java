package com.scr.message.request;

public class UserRoleRequest {

	private Long userId;
	
	private Long masterRoleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMasterRoleId() {
		return masterRoleId;
	}

	public void setMasterRoleId(Long masterRoleId) {
		this.masterRoleId = masterRoleId;
	}


}
