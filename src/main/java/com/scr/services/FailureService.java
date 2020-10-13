package com.scr.services;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Failure;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.AssetMasterData;
import com.scr.repository.FailuresRepository;
import com.scr.repository.AssetMastersRepository;
import com.scr.util.Constants;



@Service
public class FailureService {
	
	@Autowired
	private FailuresRepository failuresRepository;
	
	@Autowired
	private AssetMastersRepository assetMasterdataRepository;
	
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
}
