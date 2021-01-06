package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.mapper.FacilityCommonMapper;
import com.scr.message.request.OheLocationAndAssetsRequest;
import com.scr.model.AssetMasterData;
import com.scr.model.OheLocation;
import com.scr.repository.AssetMastersRepository;
import com.scr.repository.OheLocationRepository;

@Service
public class OheLocationService {
	
	static Logger logger = LogManager.getLogger(OheLocationService.class);

	
	@Autowired
	private OheLocationRepository oheLocationRepository;
	
	@Autowired
	private AssetMastersRepository assetMastersRepository;
	@Autowired
	private FacilityCommonMapper facilityCommonMapper;

	public List<OheLocation> findAll() {
		logger.info("Calling mapper for preparing to get Ohe Location Data Allmodel object");
		List<OheLocation> oheLoc = new ArrayList<>();
		List<OheLocation> oheLocationData = oheLocationRepository.findAll();
		for (OheLocation oheLocation : oheLocationData) {
			oheLocation = facilityCommonMapper.prepareOheLocationData(oheLocation);
			oheLoc.add(oheLocation);
		}
		 return oheLoc;
	}
	
	
	public void save(OheLocation oheLocation) {
		oheLocationRepository.save(oheLocation);
	}
	public Optional<OheLocation> findOheLocationById(Long id) {
		// TODO Auto-generated method stub
		return oheLocationRepository.findById(id);

	}

	public void deleteOheLocationById(Long id) {
		// TODO Auto-generated method stub
		oheLocationRepository.deleteById(id);
	}
	public @Valid boolean saveOheLocationAndAssetsData(@Valid OheLocationAndAssetsRequest oheAndAssetsRequest) throws Exception {
		logger.info("Calling mapper for preparing the oheAndAssetsRequest model object");
		OheLocation oheLocation = null;
		AssetMasterData assetMasterData = null;
		
			oheLocation = new OheLocation();
			assetMasterData = new AssetMasterData();
			
			
			oheLocation.setProject(oheAndAssetsRequest.getProject());
			oheLocation.setSection(oheAndAssetsRequest.getSection());
			oheLocation.setDivision(oheAndAssetsRequest.getDivision());
			oheLocation.setFacilityId(oheAndAssetsRequest.getFacilityId());
			oheLocation.setLocation(oheAndAssetsRequest.getAssetId());
			oheLocation.setKilometer(oheAndAssetsRequest.getKilometer());
			oheLocation.setSequenceNo(oheAndAssetsRequest.getSequenceNo());
			oheLocation.setFoundation(oheAndAssetsRequest.getFoundation());
			oheLocation.setOheMast(oheAndAssetsRequest.getOheMast());
			oheLocation.setTrackLine(oheLocation.getTrackLine());
			
			assetMasterData.setAssetType(oheAndAssetsRequest.getAssetType());
			assetMasterData.setAssetId(oheAndAssetsRequest.getAssetId());
			assetMasterData.setSection(oheAndAssetsRequest.getSection());
			assetMasterData.setFacilityId(oheAndAssetsRequest.getFacilityId());
			assetMasterData.setKilometer(oheAndAssetsRequest.getKilometer());
			assetMasterData.setStation(oheAndAssetsRequest.getStation());
			assetMasterData.setLine(oheAndAssetsRequest.getLine());


			 oheLocation = oheLocationRepository.save(oheLocation);
			 assetMasterData = assetMastersRepository.save(assetMasterData);
			logger.info("Ohe Location And AssetMaster data saved successfully.");
			return true;
		} 
	}
	
	

