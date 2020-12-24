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
	
	public Boolean existsByAssetId(String assetId) {
		// TODO Auto-generated method stub
		return assetStatusRepository.existsByAssetId(assetId);
	}

	

}
