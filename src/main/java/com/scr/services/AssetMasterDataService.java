package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.response.AssetsScheduleHistoryResponse;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetMasterDataFormParameter;
import com.scr.model.ReportRepository;
import com.scr.repository.AssetMasterFormParameterRepository;
import com.scr.repository.AssetMastersRepository;

@Service
public class AssetMasterDataService {
	
	@Autowired
	private AssetMastersRepository assetMastersRepository;
	@Autowired
	private AssetMasterFormParameterRepository assetMasterFormParameterRepository;
	
	public List<AssetMasterData> findAll() {
		// TODO Auto-generated method stub
		return assetMastersRepository.findAll();
	}

	public void save(AssetMasterData assetMasterData) {
		// TODO Auto-generated method stub
		assetMastersRepository.save(assetMasterData);
	}

	public Optional<AssetMasterData> findAssetMasterItemById(Long id) {
		// TODO Auto-generated method stub
		return assetMastersRepository.findById(id);
	}

	public void deleteAssetMasterDataById(Long id) {
		// TODO Auto-generated method stub
		assetMastersRepository.deleteById(id);
	}
	public List<AssetMasterData> findByAssetTypeAndFacilityId(String assetType,String facilityId) {
		List<AssetMasterData> assetId = assetMastersRepository.findByAssetTypeAndFacilityId(assetType,facilityId);
		return assetId;
	}
	public List<AssetMasterDataFormParameter> findAssetMasterFormParamter() {	
		return assetMasterFormParameterRepository.findAll();
	}
	public List<AssetMasterDataFormParameter> findByAssetType(String assetType) {	
		return assetMasterFormParameterRepository.findByAssetType(assetType);
	}

	public List<AssetMasterData> findByAssetTypeAndFacilityIdAndKM(String assetType, String facilityId, Double fromKm,
			Double toKm) {
		List<AssetMasterData> assetIds = assetMastersRepository.findByAssetTypeAndFacilityIdAndKM(assetType,facilityId,fromKm,toKm);
		return assetIds;
	}

	public List<AssetMasterData> findAssetIdsByFacilityId(String facilityId, Double fromKm, Double toKm) {
		List<AssetMasterData> assetIds = assetMastersRepository.findAssetIdsByFacilityId(facilityId,fromKm,toKm);
		return assetIds;
	}

	public List<AssetMasterData> findMakeModel(String assetId, String assetType, String facilityId) {
		List<AssetMasterData> assetIds = assetMastersRepository.findMakeModel(assetId,assetType,facilityId);
		return assetIds;
	}
}
