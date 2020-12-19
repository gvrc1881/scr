package com.scr.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.scr.mapper.AssetMasterDataMapper;
import com.scr.mapper.AssetStatusUpdateMapper;
import com.scr.message.request.AssetMasterDataRequest;
import com.scr.message.response.AssetStatusUpdateResponse;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetMasterDataFormParameter;
import com.scr.model.AssetScheduleActivityAssoc;
import com.scr.model.AssetStatusUpdate;
import com.scr.repository.AssetMasterFormParameterRepository;
import com.scr.repository.AssetMastersRepository;
import com.scr.util.Constants;

@Service
public class AssetMasterDataService {
	
	static Logger logger = LogManager.getLogger(AssetMasterDataService.class);

	
	@Autowired
	private AssetMastersRepository assetMastersRepository;
	@Autowired
	private AssetMasterFormParameterRepository assetMasterFormParameterRepository;
	@Autowired
	private AssetMasterDataMapper assetMasterDataMapper;
	@Autowired
	private AssetStatusUpdateMapper assetStatusUpdateMapper;
	
	
	public List<AssetMasterData> findPaginated(int from, int to) {
		logger.info("Fetching data from page "+from+" to page "+to);
		Pageable paging = PageRequest.of(from, to);
		Page<AssetMasterData> pagedResult = assetMastersRepository.findAll(paging);
		//return assetMastersRepository.findAll();
		List<AssetMasterData> amdList =  new ArrayList<AssetMasterData>();// pagedResult.getContent();
		logger.info("Records size: "+amdList.size());
		for (AssetMasterData assetMasterData : pagedResult.getContent()) {
			assetMasterData = assetMasterDataMapper.prepareAssetMasterData(assetMasterData);
			amdList.add(assetMasterData);
		}
		return amdList;
	}

	public List<AssetMasterData> findAll() {
		logger.info("Calling mapper class for find All assetMaster Data");
		List<AssetMasterData> assetMasterList = new ArrayList<>();
		List<AssetMasterData> assetMasterDataList = assetMastersRepository.findAll();
		for (AssetMasterData assetMasterData : assetMasterDataList) {
			assetMasterData = assetMasterDataMapper.prepareAssetMasterData(assetMasterData);
			assetMasterList.add(assetMasterData);
		}
		 return assetMasterList;
	}
	
	public @Valid boolean saveAssetMasterData(@Valid AssetMasterDataRequest assetMasterDataRequest) throws Exception {
		logger.info("Calling mapper for preparing the Asset Master model object");
		AssetMasterData assetMasterData = assetMasterDataMapper.prepareAssetMasterDataModel(assetMasterDataRequest);
		if (assetMasterData != null) {
			logger.info("After prepared model object, saving to Asset Master table");
			assetMasterData = assetMastersRepository.save(assetMasterData);
			logger.info("Asset Master data saved successfully.");
			return true;
		} else {
			logger.info("Preparing Asset Master Data model object failed");
			return false;
		}
	}
	public String updateAssetMasterData(@Valid AssetMasterDataRequest assetMasterDataRequest) {
		Optional<AssetMasterData> assetMaster = assetMastersRepository.findById(assetMasterDataRequest.getId());
		if(assetMaster.isPresent()) {
			AssetMasterData assetMasterUpdate = assetMasterDataMapper.prepareAssetMasterUpdateData(assetMaster.get(), assetMasterDataRequest);
			assetMasterUpdate = assetMastersRepository.save(assetMasterUpdate);
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid Asset Master Id";
		}
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

	public List<AssetStatusUpdateResponse> getByDataDiv(String div) {
		// TODO Auto-generated method stub
		List<AssetMasterData> amd = assetMastersRepository.getByDataDiv(div);
		List<AssetStatusUpdateResponse> assetStatusUpdate = null;
		 logger.info("*** amd list ***"+amd.size());
			for (AssetMasterData assetMasterData : amd) 
			{
					
					 assetStatusUpdate = assetStatusUpdateMapper.prepareAssetStatusUpdateData(assetMasterData,div);
					
				}
			return assetStatusUpdate;
	}

	public List<AssetStatusUpdateResponse> getByFacilityId(String facilityId) {
		// TODO Auto-generated method stub
		
		List<AssetMasterData> amd = assetMastersRepository.getFacilityId(facilityId);
		 logger.info("*** amd list ***"+amd.size());
		 List<AssetStatusUpdateResponse> assetStatusUpdate = new ArrayList<>();
			for (AssetMasterData assetMasterData2 : amd) 
			{	
			logger.info("*** in for loop  ***"+assetMasterData2.getFacilityId());
				assetStatusUpdate = assetStatusUpdateMapper.prepareAssetStatusUpdateDataBasedOnFacility(assetMasterData2,facilityId);
			} 		
				
					
			return assetStatusUpdate;
		}
}
