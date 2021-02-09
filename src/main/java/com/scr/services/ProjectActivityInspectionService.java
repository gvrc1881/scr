package com.scr.services;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.ProjectActivityInspectionMapper;
import com.scr.message.request.ProjectActivityInspectionRequest;
import com.scr.model.AssetScheduleActivityRecord;
import com.scr.model.ProjectActivityInspection;
import com.scr.repository.ProjectActivityInspectionRepository;

@Service
public class ProjectActivityInspectionService {
	
	static Logger logger = LogManager.getLogger(ProjectActivityInspectionService.class);
	
	@Autowired
	private ProjectActivityInspectionRepository projectActivityInspectionRepository;
	
	@Autowired
	private ProjectActivityInspectionMapper projectActivityInspectionMapper;

	public void saveEntry(@Valid ProjectActivityInspectionRequest projectActivityInspectionRequest) throws Exception {
		logger.info("Calling mapper for preparing the ash model object");
		ProjectActivityInspection prActInsRecord = projectActivityInspectionMapper.preparePAIEntryModel(projectActivityInspectionRequest);
		if (prActInsRecord != null) {
			logger.info("After prepared model object, saving to ash table");
			prActInsRecord = projectActivityInspectionRepository.save(prActInsRecord);
			logger.info("Ash data saved successfully.");
		}
		
	}

}
