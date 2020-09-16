package com.scr.services;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scr.mapper.AshMapper;
import com.scr.message.request.AssetsScheduleHistoryRequest;
import com.scr.model.AssetsScheduleHistory;
import com.scr.repository.AssetsScheduleHistoryRepository;

@Service
public class AssetScheduleHistoryService {
	
	static Logger logger = LogManager.getLogger(AssetScheduleHistoryService.class);
	
	@Value("${stipulation.path}")
	private String stipulationPath;
	
	@Value("${inspection.path}")
	private String inspectionPath;
	
	@Value("${content.management.path}")
	private String contentManagementPath;
		
	@Autowired
	private AshMapper ashMapper;
	
	@Autowired
	private AssetsScheduleHistoryRepository ashRepository;
	
	
	public List<AssetsScheduleHistory> findAllAshs() {
		logger.info("Fetcing history data where active is 1.");
		return ashRepository.findAll();
	}	

	public @Valid boolean saveAshData(@Valid AssetsScheduleHistoryRequest ashRequest) throws Exception {
		logger.info("Calling mapper for preparing the ash model object");
		AssetsScheduleHistory ash = ashMapper.prepareDriveModel(ashRequest);
		if (ash != null) {
			logger.info("After prepared model object, saving to ash table");
			ash = ashRepository.save(ash);
			logger.info("Ash data saved successfully.");
			return true;
		} else {
			logger.info("Preparing ash model object failed");
			return false;
		}
	}

	/*public String updateAshData(@Valid AssetsScheduleHistoryRequest request) {
		Optional<AssetsScheduleHistory> ash = ashRepository.findById(request.getId());
		if(ash.isPresent()) {
			AssetsScheduleHistory ashUpdate = driveMapper.prepareDriveUpdataData(ash.get(), request);
			ashUpdate = ashRepository.save(ashUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Ash Id";
		}
	}
	public String deleteAsh(Long id) {
		Optional<AssetsScheduleHistory> ashOptional = ashRepository.findById(id);
		if (ashOptional.isPresent()) {
			AssetsScheduleHistory ashToUpdate = ashOptional.get();
			ashToUpdate.setId(id);
			ashRepository.save(ashToUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Drive Id";
		}
	}*/
	
	

}
