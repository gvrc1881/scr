package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.AssetStatusUpdateMapper;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetStatusUpdate;
import com.scr.model.WorkPhases;
import com.scr.repository.AssetStatusUpdateRepository;


@Service

public class AssetStatusUpdateService {
	

	@Autowired
	private AssetStatusUpdateRepository assetStatusRepository;
	
	@Autowired
	private AssetStatusUpdateMapper assetStatusUpdateMapper;
	
	
	public List<AssetStatusUpdate> findAll() {
		
		List<AssetStatusUpdate> assetStatusList = new ArrayList<>();
		List<AssetStatusUpdate> assetStatusUpdateList = assetStatusRepository.findAll();
		for (AssetStatusUpdate assetStatusUpdate : assetStatusUpdateList) {
			
			assetStatusUpdate = assetStatusUpdateMapper.prepareAssetStatusUpdateBasedOnFacility(assetStatusUpdate);
			assetStatusList.add(assetStatusUpdate);
			
		}
		
		 return assetStatusList;
	}
	
		public AssetStatusUpdate save(AssetStatusUpdate assetStatusUpdate) {
			return assetStatusRepository.save(assetStatusUpdate);
	}
	public Optional<AssetStatusUpdate> findAssetStatusById(Long id) {
		// TODO Auto-generated method stub
		return assetStatusRepository.findById(id);

	}

	public void deleteAssetStatusById(Long id) {
		// TODO Auto-generated method stub
		assetStatusRepository.deleteById(id);
	}
	
	public Boolean existsByAssetTypeAndAssetIdAndFacilityId(String assetType, String assetId, String facilityId) {
		// TODO Auto-generated method stub
		return assetStatusRepository.existsByAssetTypeAndAssetIdAndFacilityId(assetType,assetId,facilityId);
	}
	
/*	
public void addAssetStatus(List<AssetStatusUpdate> assetStatusUpdate) {
	
	for (AssetStatusUpdate assetStatusUpdate2 : assetStatusUpdate) {
		
		
		
	}
		
		for (AssetStatusUpdate workPhase : workPhasesData) {
			
			Optional<WorkPhases> wPData = workPhaseRepository.findByWorkIdAndPhaseName(workPhase.getWorkId(),workPhase.getPhaseName());
			if (wPData.isPresent()) {
				WorkPhases updateWorkPhaseData = wPData.get();
				updateWorkPhaseData.setWorkId(workPhase.getWorkId());
				updateWorkPhaseData.setPhaseName(workPhase.getPhaseName());
				updateWorkPhaseData.setDescription(workPhase.getDescription());
				updateWorkPhaseData.setSequence(workPhase.getSequence());
				updateWorkPhaseData.setDependencyToStart(workPhase.getDependencyToStart());
				updateWorkPhaseData.setWeightage(workPhase.getWeightage());
				updateWorkPhaseData.setStatus(workPhase.getStatus());
				updateWorkPhaseData.setPlannedStartDate(workPhase.getPlannedStartDate());
				updateWorkPhaseData.setTargetCompletionDate(workPhase.getTargetCompletionDate());				
				updateWorkPhaseData.setCommenceDate(workPhase.getCommenceDate());
				updateWorkPhaseData.setCompletionDate(workPhase.getCompletionDate());
				
				workPhaseRepository.save(updateWorkPhaseData);
			} else {
				 workPhaseRepository.save(workPhasesData);
			}
			
		} 
		
		
	}
*/

}
