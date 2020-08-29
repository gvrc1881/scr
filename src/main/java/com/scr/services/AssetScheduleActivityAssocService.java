package com.scr.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.AssetScheduleActivityAssoc;
import com.scr.model.Make;
import com.scr.repository.AssetScheduleActivityAssocRepository;

@Service
public class AssetScheduleActivityAssocService {
	
	@Autowired
	private AssetScheduleActivityAssocRepository assetSchActivityAssocRepository;

	public List<AssetScheduleActivityAssoc> findAll() {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findAll();
	}

	public AssetScheduleActivityAssoc save(AssetScheduleActivityAssoc assetScheduleActivityAssoc) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.save(assetScheduleActivityAssoc);
	}

	public Optional<AssetScheduleActivityAssoc> findAssetSchAssocById(Long id) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findById(id);
	}

	public void deleteAssetSchActAssocById(Long id) {
		// TODO Auto-generated method stub
		assetSchActivityAssocRepository.deleteById(id);
	}

	public Boolean existsByAsaSeqIdAndActivityPositionId(String asaSeqId, String activityPositionId,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndActivityPositionId(asaSeqId,activityPositionId,makeCode,modelCode);
	}
	
	public Boolean existsByAsaSeqIdAndactivityId(String asaSeqId, String activityId,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndactivityId(asaSeqId,activityId,makeCode,modelCode);
	}
	
	public Boolean existsByAsaSeqIdAndactivityDisplayOrder(String asaSeqId, String activityId,String displayOrder,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndactivityDisplayOrder(asaSeqId,activityId,displayOrder,makeCode,modelCode);
	}

	//public Optional<AssetScheduleActivityAssoc> findByAsaSeqAndPositionIdAndMakeCodeAndModelCode(String asaSeqId,String activityPositionId,String makeCode,String modelCode) {
		// TODO Auto-generated method stub
		//return assetSchActivityAssocRepository.findByAsaSeqAndPositionIdAndMakeCodeAndModelCode(asaSeqId,activityPositionId,makeCode,modelCode);
	//}
//	public Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityIdAndmakeCodeAndModelCode(String asaSeqId,String activityId,String makeCode,String modelCode) {
//		// TODO Auto-generated method stub
//		return assetSchActivityAssocRepository.findByAsaSeqIdAndActivityIdAndmakeCodeAndModelCode(asaSeqId,activityId,makeCode,modelCode);
//	}
//	public Optional<AssetScheduleActivityAssoc> findByasaSeqIdAndActivityIdAndDisplayOrderAndMakeCodeAndModelCode(String asaSeqId,String activityId,String displayOrder,String makeCode,String modelCode) {
		// TODO Auto-generated method stub
	//	return assetSchActivityAssocRepository.findByasaSeqIdAndActivityIdAndDisplayOrderAndMakeCodeAndModelCode(asaSeqId,activityId,displayOrder,makeCode,modelCode);
	//}
}
