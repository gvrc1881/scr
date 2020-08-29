package com.scr.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.AssetScheduleAssoc;

import com.scr.repository.AssetSchAssoRepository;

@Service
public class AssetScheduleAssocService {
	
	@Autowired
	private AssetSchAssoRepository assetSchAssocRepository;

	public List<AssetScheduleAssoc> findAll() {
		// TODO Auto-generated method stub
		return assetSchAssocRepository.findAll();
	}

	public AssetScheduleAssoc save(AssetScheduleAssoc assetScheduleAssoc) {
		// TODO Auto-generated method stub
		return assetSchAssocRepository.save(assetScheduleAssoc);
	}

	public Optional<AssetScheduleAssoc> findAssetSchAssocById(Long id) {
		// TODO Auto-generated method stub
		return assetSchAssocRepository.findById(id);
	}


	public void deleteAssetScheduleAssocById(Long id) {
		// TODO Auto-generated method stub
		assetSchAssocRepository.deleteById(id);
	}


	public Boolean existsByassetTypeAndScheduleCode(String assetType, String scheduleCode) {
		 //TODO Auto-generated method stub
		return assetSchAssocRepository.existsByAssetTypeAndScheduleCode(assetType,scheduleCode);
	}
	
	public Optional<AssetScheduleAssoc> findByAssetTypeAndScheduleCode(String assetType,String scheduleCode) {
		// TODO Auto-generated method stub
		return assetSchAssocRepository.findByAssetTypeAndScheduleCode(assetType,scheduleCode);
	}
}
