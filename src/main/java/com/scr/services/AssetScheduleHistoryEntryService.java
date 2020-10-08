package com.scr.services;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.scr.mapper.AshEntryMapper;
import com.scr.mapper.AshMapper;
import com.scr.message.request.ASHEntryRequest;
import com.scr.message.response.ASHMeasuresActvities;
import com.scr.model.AssetScheduleActivityRecord;
import com.scr.repository.AssetScheduleActivityRecordsRepository;
import com.scr.repository.AssetsScheduleHistoryRepository;
import com.scr.repository.AssetsScheduleHistoryUtilRepository;

@Service
public class AssetScheduleHistoryEntryService {
	
	static Logger logger = LogManager.getLogger(AssetScheduleHistoryEntryService.class);
	
	@Value("${stipulation.path}")
	private String stipulationPath;
	
	@Value("${inspection.path}")
	private String inspectionPath;
	
	@Value("${content.management.path}")
	private String contentManagementPath;
	
	@Autowired
	private AshEntryMapper ashEntryMapper;
	
	@Autowired
	private AssetScheduleActivityRecordsRepository activityRecordsRepository;
	
	@Autowired
	AssetsScheduleHistoryUtilRepository ashutilRepository;
	
	
	
	public List<ASHMeasuresActvities> findMeasuresOrActivities(String type,String assetType, String scheduleCode, String make,
			String model) {
		logger.info("Calling respository for :::"+type);
		return ashutilRepository.findMeasuresOrActivities(type,assetType,scheduleCode,make,model);
	}



	public @Valid boolean saveEntry(@Valid ASHEntryRequest ashEntryRequest) throws Exception {
		logger.info("Calling mapper for preparing the ash model object");
		AssetScheduleActivityRecord assetScheduleActivityRecord = ashEntryMapper.prepareASHEntryModel(ashEntryRequest);
		if (assetScheduleActivityRecord != null) {
			logger.info("After prepared model object, saving to ash table");
			assetScheduleActivityRecord = activityRecordsRepository.save(assetScheduleActivityRecord);
			logger.info("Ash data saved successfully.");
			return true;
		} else {
			logger.info("Preparing ash model object failed");
			return false;
		}
	}	
		

//	public @Valid boolean saveAshData(@Valid AssetsScheduleHistoryRequest ashRequest) throws Exception {
//		logger.info("Calling mapper for preparing the ash model object");
//		AssetsScheduleHistory ash = ashMapper.prepareDriveModel(ashRequest);
//		if (ash != null) {
//			logger.info("After prepared model object, saving to ash table");
//			ash = ashRepository.save(ash);
//			logger.info("Ash data saved successfully.");
//			return true;
//		} else {
//			logger.info("Preparing ash model object failed");
//			return false;
//		}
//	}

//	public String updateAshData(@Valid AssetsScheduleHistoryRequest request) {
//		Optional<AssetsScheduleHistory> ash = ashRepository.findById(request.getId());
//		if(ash.isPresent()) {
//			AssetsScheduleHistory ashUpdate = ashMapper.prepareDriveUpdataData(ash.get(), request);
//			ashUpdate = ashRepository.save(ashUpdate);
//			return Constants.JOB_SUCCESS_MESSAGE;
//		}else {
//			return "Invalid Ash Id";
//		}
//	}
	
	
	

}
