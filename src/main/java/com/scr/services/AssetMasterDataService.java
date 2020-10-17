package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scr.model.AssetMasterData;
import com.scr.model.AssetMasterDataFormParameter;
import com.scr.repository.AssetMasterFormParameterRepository;
import com.scr.repository.AssetMastersRepository;

@Service
public class AssetMasterDataService {
	
	@Autowired
	private AssetMastersRepository assetMastersRepository;
	@Autowired
	private AssetMasterFormParameterRepository assetMasterFormParameterRepository;
	
	public List<AssetMasterData> findPaginated(int from, int to) {
		Pageable paging = PageRequest.of(from, to);
		Page<AssetMasterData> pagedResult = assetMastersRepository.findAll(paging);
		//return assetMastersRepository.findAll();
		return pagedResult.getContent();
	}

	public void save(AssetMasterData assetMasterData) {
		
		assetMastersRepository.save(assetMasterData);
	}

	public Optional<AssetMasterData> findAssetMasterItemById(Long id) {
		
		return assetMastersRepository.findById(id);
	}

	public void deleteAssetMasterDataById(Long id) {
		
		assetMastersRepository.deleteById(id);
	}
	public List<AssetMasterData> findByAssetTypeAndFacilityId(String assetType,String facilityId) {
		List<AssetMasterData> assetId = assetMastersRepository.findByAssetTypeAndFacilityId(assetType,facilityId);
		return assetId;
	}
	
	public List<AssetMasterDataFormParameter> findByAssetTypeAndActive(String assetType,String active) {	
		return assetMasterFormParameterRepository.findByAssetTypeAndActive(assetType,active);
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
