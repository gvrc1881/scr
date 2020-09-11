package com.scr.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Failure;
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

	public void saveFailureByType(@Valid Failure failureRequest) {
		failureRequest.setCurrentStatus(Constants.ACTIVE);
		failuresRepository.save(failureRequest);
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
	public List<AssetMasterData> findByAssetId(String productId) {
		// TODO Auto-generated method stub
		return assetMasterdataRepository.findByAssetId(productId);
	}
}
