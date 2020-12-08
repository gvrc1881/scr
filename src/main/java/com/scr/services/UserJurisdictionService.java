package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.controller.UserJurisdictionController;
import com.scr.message.request.UserJurisdictionRequest;
import com.scr.model.User;
import com.scr.model.UserJurisdiction;
import com.scr.model.WorkGroup;
import com.scr.model.Works;
import com.scr.repository.UserJurisdictionRepository;

@Service
public class UserJurisdictionService {

	static Logger logger = LogManager.getLogger(UserJurisdictionService.class);

	@Autowired
	private WorkGroupService workGroupService;

	@Autowired
	private UserJurisdictionRepository userJurisdictionRepository;

	@Autowired
	private WorksServices worksServices;

	@Autowired
	private UserServices userServices;

	public void saveUserJurisdiction(UserJurisdictionRequest userJurisdictionRequest) {
		// TODO Auto-generated method stub
		Long[] groupIds = userJurisdictionRequest.getWorkGroup();
		Long[] sectionIds = userJurisdictionRequest.getSection();

		Optional<Works> works = worksServices.findById(userJurisdictionRequest.getWorkId());
		Optional<User> user = userServices.findById(userJurisdictionRequest.getUserId());
		if (user.isPresent()) {
			if (works.isPresent()) {
				for (Long groupId : groupIds) {
					UserJurisdiction userJurisdiction = new UserJurisdiction();
					userJurisdiction.setWorkId(works.get());
					userJurisdiction.setUserId(user.get());
					Optional<WorkGroup> workGroup = workGroupService.findGroupsSectionsById(groupId);
					if (workGroup.isPresent()) {
						userJurisdiction.setWorkGroupId(workGroup.get());
						if (sectionIds.length > 0) {
							for (Long sectionId : sectionIds) {
								Optional<WorkGroup> workGroupSection = workGroupService
										.findGroupsSectionsById(sectionId);
								if (workGroupSection.isPresent()) {
									if (workGroupSection.get().getSection().equals(workGroup.get().getSection())) {
										logger.info("*** section id***"+workGroupSection.get().getSection()+"*** group section ***"+workGroup.get().getSection());
										Optional<UserJurisdiction> userJuriList = userJurisdictionRepository.findByUserIdAndWorkIdAndWorkGroupIdAndSection(user.get(),works.get(),workGroup.get(),workGroupSection.get());
										if(!userJuriList.isPresent()) {
											userJurisdiction.setSection(workGroupSection.get());
											userJurisdictionRepository.save(userJurisdiction);
										}
									}else {
										List<UserJurisdiction> userJuriGroupList = userJurisdictionRepository.findByUserIdAndWorkIdAndWorkGroupId(user.get(),works.get(),workGroup.get());
										if (userJuriGroupList.size() == 0) {
											userJurisdictionRepository.save(userJurisdiction);
										}
									}
								}
							}
						} else {
							userJurisdictionRepository.save(userJurisdiction);
						}

						/*
						 * List<WorkGroup> sectionList = userJurisdictionRequest.getSection(); for
						 * (WorkGroup section : sectionList) { if
						 * (section.getSection().equals(workGroup.get().getSection())) {
						 * userJurisdiction.setSection(section);
						 * userJurisdictionRepository.save(userJurisdiction); } }
						 */

					}
				}
			}
		}

	}

	public List<UserJurisdiction> findAll() {
		return userJurisdictionRepository.findAll();
	}

	public void deleteById(Long id) {
		userJurisdictionRepository.deleteById(id);
	}

	public Optional<UserJurisdiction> findById(Long id) {
		return userJurisdictionRepository.findById(id);
	}

	public List<Works> findByUserId(User userData) {
		return userJurisdictionRepository.findDistinctWorkIdByUserId(userData);
	}

	public List<WorkGroup> findDistinctWorkGroupIdByWorkIdAndUserId(Works works, User user) {
		return userJurisdictionRepository.findDistinctWorkGroupIdByWorkIdAndUserId(works,user);
	}

}
