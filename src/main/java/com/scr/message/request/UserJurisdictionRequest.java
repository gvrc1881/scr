package com.scr.message.request;

import java.util.Arrays;
import java.util.List;

import com.scr.model.User;
import com.scr.model.WorkGroup;
import com.scr.model.Works;

public class UserJurisdictionRequest {

	private User user;
	private Works work;
	private Long[] workGroup;
	private List<WorkGroup> section;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Works getWork() {
		return work;
	}

	public void setWork(Works work) {
		this.work = work;
	}

	public Long[] getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(Long[] workGroup) {
		this.workGroup = workGroup;
	}

	public List<WorkGroup> getSection() {
		return section;
	}

	public void setSection(List<WorkGroup> section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return "UserJurisdictionRequest [user=" + user + ", work=" + work + ", workGroup=" + Arrays.toString(workGroup)
				+ ", section=" + section + "]";
	}

}
