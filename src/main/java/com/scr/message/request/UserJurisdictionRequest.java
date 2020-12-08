package com.scr.message.request;

import java.util.Arrays;
import java.util.List;

import com.scr.model.User;
import com.scr.model.WorkGroup;
import com.scr.model.Works;

public class UserJurisdictionRequest {

	private Long id;
	private Long userId;
	private Integer workId;
	private Long[] workGroup;
	private Long[] section;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
	}

	public Long[] getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(Long[] workGroup) {
		this.workGroup = workGroup;
	}

	public Long[] getSection() {
		return section;
	}

	public void setSection(Long[] section) {
		this.section = section;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserJurisdictionRequest [id=" + id + ", userId=" + userId + ", workId=" + workId + ", workGroup="
				+ Arrays.toString(workGroup) + ", section=" + Arrays.toString(section) + "]";
	}

}
