package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.controller.UserJurisdictionController;
import com.scr.message.request.UserJurisdictionRequest;
import com.scr.model.UserJurisdiction;
import com.scr.model.WorkGroup;
import com.scr.repository.UserJurisdictionRepository;

@Service
public class UserJurisdictionService {

	static Logger logger = LogManager.getLogger(UserJurisdictionService.class);

	@Autowired
	private WorkGroupService workGroupService;

	@Autowired
	private UserJurisdictionRepository userJurisdictionRepository;

	public void saveUserJurisdiction(UserJurisdictionRequest userJurisdictionRequest) {
		// TODO Auto-generated method stub
		Long[] groupIds = userJurisdictionRequest.getWorkGroup();
		for (Long groupId : groupIds) {
			UserJurisdiction userJurisdiction = new UserJurisdiction();
			userJurisdiction.setWorkId(userJurisdictionRequest.getWork());
			userJurisdiction.setUserId(userJurisdictionRequest.getUser());
			Optional<WorkGroup> workGroup = workGroupService.findGroupsSectionsById(groupId);
			if (workGroup.isPresent()) {
				userJurisdiction.setWorkGroupId(workGroup.get());
				List<WorkGroup> sectionList = userJurisdictionRequest.getSection();
				for (WorkGroup section : sectionList) {
					if (section.getSection().equals(workGroup.get().getSection())) {
						userJurisdiction.setSection(section);
						userJurisdictionRepository.save(userJurisdiction);
					}
				}

			}
		}

	}

}
