package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scr.model.Failure;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.Works;
import com.scr.mapper.ContentManagementMapper;
import com.scr.message.response.ResponseStatus;
import com.scr.model.AssetMasterData;
import com.scr.model.ContentManagement;
import com.scr.model.Facility;
import com.scr.repository.FailuresRepository;
import com.scr.repository.AssetMastersRepository;
import com.scr.repository.ContentManagementRepository;
import com.scr.util.Constants;



@Service
public class FailureService {
	
	private static Logger logger = LogManager.getLogger(FailureService.class);
	
	
	@Autowired
	private FailuresRepository failuresRepository;
	
	@Autowired
	private AssetMastersRepository assetMasterdataRepository;
	
	@Autowired
	private ContentManagementMapper contentManagementMapper;
	
	@Autowired
	private ContentManagementRepository contentManagementRepository;
	
	@Value("${uuo.path}")
	private String uuoPath;
	
	public List<Failure> findFailureByType(String typeOfFailure) {
		return failuresRepository.findByTypeOfFailureAndCurrentStatus(typeOfFailure, Constants.ACTIVE);
	}

	public Failure saveFailureByType(@Valid Failure failureRequest) {
		failureRequest.setCurrentStatus(Constants.ACTIVE);
		return failuresRepository.save(failureRequest);
	}

	public String deleteFailureTypeById(Long id) {
		Optional<Failure> failOptional = failuresRepository.findById(id);
		if(failOptional.isPresent()) {
			Failure updateFailure = failOptional.get();
			updateFailure.setCurrentStatus(Constants.INACTIVE);
			failuresRepository.save(updateFailure);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Failure Type Id";
		}
	}

	public Optional<Failure> findFailureTypeById(Long id) {
		return failuresRepository.findById(id);
	}

	public void updateFailureByType(@Valid Failure failureRequest) {
		failureRequest.setCurrentStatus(Constants.ACTIVE);
		failuresRepository.save(failureRequest);
	}
	public List<Failure> findAll() {
		// TODO Auto-generated method stub
		return failuresRepository.findAll();
	}
	/*public List<AssetMasterData> findByAssetId(String productId) {
		// TODO Auto-generated method stub
		return assetMasterdataRepository.findByAssetId(productId);
	}*/
	public List<AssetMasterData> findByAssetIdBasedOnFacilityName(String subStation) {
		// TODO Auto-generated method stub
		return assetMasterdataRepository.findByAssetIdBasedOnFacilityName(subStation);
	}
	
	public Boolean existsByFeedOfAndFromDateTime(String feedOf, Timestamp fromDateTime) {
		 //TODO Auto-generated method stub
		return failuresRepository.existsByFeedOfAndFromDateTime(feedOf,fromDateTime);
	}
	
	public Boolean existsBySubStationAndEquipmentAndFromDateTime(String subStation, String equipment,Timestamp fromDateTime) {
		 //TODO Auto-generated method stub
		return failuresRepository.existsBySubStationAndEquipmentAndFromDateTime(subStation,equipment,fromDateTime);
	}
	public Boolean existsBySubStationAndOccurrence(String subStation, Timestamp fromDateTime) {
		 //TODO Auto-generated method stub
		return failuresRepository.existsBySubStationAndOccurrence(subStation,fromDateTime);
	}
	public Boolean existsByOccurrenceAndPlaceAndFromDateTime(String occurrence, String place,Timestamp fromDateTime) {
		 //TODO Auto-generated method stub
		return failuresRepository.existsByOccurrenceAndPlaceAndFromDateTime(occurrence,place,fromDateTime);
	}
	public Boolean existsBySubStationAndLocationAndFromDateTime(String subStation, String location,Timestamp fromDateTime) {
		 //TODO Auto-generated method stub
		return failuresRepository.existsBySubStationAndLocationAndFromDateTime(subStation,location,fromDateTime);
	}
	
	public Optional<Failure> findByFeedOfAndFromDateTime(String feedOf, Timestamp fromDateTime) {
		// TODO Auto-generated method stub
		return failuresRepository.findByFeedOfAndFromDateTime(feedOf,fromDateTime);
	}
	
	public Optional<Failure> findBySubStationAndEquipmentAndFromDateTime(String subStation,String equipment, Timestamp fromDateTime) {
		// TODO Auto-generated method stub
		return failuresRepository.findBySubStationAndEquipmentAndFromDateTime(subStation,equipment,fromDateTime);
	}
	public Optional<Failure> findBySubStationAndOccurrence(String subStation,Timestamp fromDateTime) {
		// TODO Auto-generated method stub
		return failuresRepository.findBySubStationAndOccurrence(subStation,fromDateTime);
	}
	public Optional<Failure> findByOccurrenceAndPlaceAndFromDateTime(String occurrence,String place,Timestamp fromDateTime) {
		// TODO Auto-generated method stub
		return failuresRepository.findByOccurrenceAndPlaceAndFromDateTime(occurrence,place,fromDateTime);
	}
	public Optional<Failure> findBySubStationAndLocationAndFromDateTime(String subStation,String location,Timestamp fromDateTime) {
		// TODO Auto-generated method stub
		return failuresRepository.findBySubStationAndLocationAndFromDateTime(subStation,location,fromDateTime);
	}

	public List<Failure> findFailureByTypeAndSubStation(String failureType, List<String> fac) {
		
		return failuresRepository.findByTypeOfFailureAndSubStationInAndCurrentStatus(failureType,fac,Constants.ACTIVE );
	}

	public List<Failure> findFailureByTypeAndFeedOf(String failureType, List<String> fac) {
		
		return failuresRepository.findByTypeOfFailureAndFeedOfInAndCurrentStatus(failureType,fac,Constants.ACTIVE);
	}

	public ResponseStatus storeUploadedFiles(List<MultipartFile> file, String contentCategory, String description,
			String divisionCode, String createdBy, String zonal, String fU, String contentTopic, Long unUsualOccurenceFailId) 
	{
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			ResponseStatus folderResponse = contentManagementMapper.checkAndCreateFolderStructure(uuoPath, contentCategory );
			if(folderResponse.getCode() == Constants.SUCCESS_CODE) {				
				List<ContentManagement> liContentManagements = new ArrayList<ContentManagement>();	
				ContentManagement fileId = contentManagementRepository.findTopByOrderByCommonFileIdDesc();
				Long commonFileId = (long) 0.0; 
				if(fileId == null || fileId.getCommonFileId() == null) {
					commonFileId = (long) 1;
				}else {
					commonFileId = fileId.getCommonFileId()+1;
				}
				Optional<Failure> uuoFailure =failuresRepository.findById(unUsualOccurenceFailId);
				if (uuoFailure.isPresent()) {
					Failure failure = uuoFailure.get();
					if (failure.getContentLink() != null) {
						commonFileId = Long.parseLong(failure.getContentLink());
					} else {
						failure.setContentLink(String.valueOf(commonFileId));
					}
					
					failuresRepository.save(failure);
				}
				
				for(MultipartFile mf: file)
				{
					String folderPath = folderResponse.getMessage();
					liContentManagements.add(contentManagementMapper.saveAndStoreDetails(mf, divisionCode, createdBy, zonal,fU, contentTopic, description, contentCategory, folderPath, commonFileId));									
				}
				if(!liContentManagements.isEmpty()) {
					liContentManagements = contentManagementRepository.saveAll(liContentManagements);
					logger.info("Files Details saved in to Database Successfully.");
				}
			}					
			responseStatus.setCode(Constants.SUCCESS_CODE);
			responseStatus.setMessage(Constants.JOB_SUCCESS_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while saving files "+e.getMessage());
			responseStatus.setCode(Constants.FAILURE_CODE);
			responseStatus.setMessage("ERROR >>> "+e.getMessage());
		}
		return responseStatus;
		
		
	}

	

	
	
}
