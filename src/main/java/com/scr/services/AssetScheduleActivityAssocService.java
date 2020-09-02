package com.scr.services;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.AssetScheduleActivityAssoc;
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

	public Boolean existsByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(String asaSeqId, String activityPositionId,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(asaSeqId,activityPositionId,makeCode,modelCode);
	}
	
	public Boolean existsByAsaSeqIdAndactivityIdAndMakeCodeAndModelCode(String asaSeqId, String activityId,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndactivityIdAndMakeCodeAndModelCode(asaSeqId,activityId,makeCode,modelCode);
	}
	
	public Boolean existsByAsaSeqIdAndactivityIdAndDisplayOrderAndMakeCodeAndModelCode(String asaSeqId, String activityId,BigDecimal displayOrder,String makeCode,String modelCode) {
		 //TODO Auto-generated method stub
		return assetSchActivityAssocRepository.existsByAsaSeqIdAndactivityIdAndDisplayOrderAndMakeCodeAndModelCode(asaSeqId,activityId,displayOrder,makeCode,modelCode);
	}

	public Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(String asaSeqId,String activityPositionId,String makeCode,String modelCode) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findByAsaSeqIdAndActivityPositionIdAndMakeCodeAndModelCode(asaSeqId,activityPositionId,makeCode,modelCode);
	}
	public Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityIdAndMakeCodeAndModelCode(String asaSeqId,String activityId,String makeCode,String modelCode) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findByAsaSeqIdAndActivityIdAndMakeCodeAndModelCode(asaSeqId,activityId,makeCode,modelCode);
	}
	public Optional<AssetScheduleActivityAssoc> findByAsaSeqIdAndActivityIdAndDisplayOrderAndMakeCodeAndModelCode(String asaSeqId,String activityId,BigDecimal displayOrder,String makeCode,String modelCode) {
		// TODO Auto-generated method stub
		return assetSchActivityAssocRepository.findByAsaSeqIdAndActivityIdAndDisplayOrderAndMakeCodeAndModelCode(asaSeqId,activityId,displayOrder,makeCode,modelCode);
	}
}
