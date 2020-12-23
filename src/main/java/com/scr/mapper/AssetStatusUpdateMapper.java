package com.scr.mapper;

import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.response.AssetStatusUpdateResponse;
import com.scr.model.AssetMasterData;
import com.scr.model.AssetScheduleAssoc;
import com.scr.model.AssetStatusUpdate;
import com.scr.model.AssetsScheduleHistory;
import com.scr.model.Facility;
import com.scr.repository.AssetMastersRepository;
import com.scr.repository.AssetSchAssoRepository;
import com.scr.repository.AssetStatusUpdateRepository;
import com.scr.repository.AssetsScheduleHistoryRepository;
import com.scr.repository.FacilityRepository;
import com.scr.services.AssetMasterDataService;

@Component
public class AssetStatusUpdateMapper {
	
	static Logger logger = LogManager.getLogger(AssetStatusUpdateMapper.class);
	
	@Autowired
	private AssetMastersRepository assetMastersRepository;
	
	@Autowired
	private AssetsScheduleHistoryRepository assetsScheduleHistoryRepository;
	
	@Autowired
	private AssetSchAssoRepository assetScheduleAssocRepository;
	
	@Autowired
	private AssetStatusUpdateRepository assetStatusUpdateRepository;
	
	@Autowired
	private FacilityRepository facilityRepository;

	public List<AssetStatusUpdateResponse> prepareAssetStatusUpdateData(AssetMasterData assetMasterData, String div) {		
		
		
		List<AssetStatusUpdateResponse> assetstatus = new ArrayList<>();
		List<AssetMasterData> amd = assetMastersRepository.getByDataDiv(div);	
		logger.info("*** in mapper class ***"+amd.size());
		for (AssetMasterData assetMasterData2 : amd) {
			logger.info("*** in foor loop ***");
			AssetStatusUpdateResponse asur = new AssetStatusUpdateResponse();
			//Optional<Facility> fac = facilityRepository.findByFacilityId(assetMasterData2.getFacilityId());
			logger.info("*** before ash ***"+assetMasterData2.getAssetId()+assetMasterData2.getAssetType()+assetMasterData2.getFacilityId());
			//Optional<AssetsScheduleHistory> ash = assetsScheduleHistoryRepository.findByAssetTypeAndAssetIdAndFacilityId(assetMasterData2.getAssetType(),assetMasterData2.getAssetId(),assetMasterData2.getFacilityId());
			List<AssetsScheduleHistory>	ashList = assetsScheduleHistoryRepository.findByAssetTypeAndAssetIdAndFacilityId(assetMasterData2.getAssetType(),assetMasterData2.getAssetId(),assetMasterData2.getFacilityId());
			for (AssetsScheduleHistory assetsScheduleHistory : ashList) {	
				
				Optional<AssetsScheduleHistory> ashistory = assetsScheduleHistoryRepository.getByAssetTypeAndAssetIdAndFacilityIdAndScheduleCode(assetsScheduleHistory.getAssetType(),assetsScheduleHistory.getAssetId(),assetsScheduleHistory.getFacilityId(),assetsScheduleHistory.getScheduleCode());
			
			if (ashistory.isPresent()) {
				logger.info("*** after ash  ***"+ashistory.get().getAssetId()+ashistory.get().getAssetType()+ashistory.get().getFacilityId());
				 Optional<AssetScheduleAssoc> asa = assetScheduleAssocRepository.findByAssetTypeAndScheduleCode(ashistory.get().getAssetType(),ashistory.get().getScheduleCode());
				 LocalDate currentDate =LocalDate.now();
					logger.info("** current date***"+currentDate);
				 	if(asa.get().getUomOfDuration().equals("Time in Months" ))
						{
							LocalDate resultDate = currentDate.plusMonths(asa.get().getDuration().longValue());
							asur.setNextAoh(resultDate);
							logger.info("** AOH***"+resultDate);
							asur.setNextPoh(resultDate);
							logger.info("** POH***"+resultDate);
						}
				 	else if(asa.get().getUomOfDuration().equals("Time in Years" ))
					{
						LocalDate resultDate = currentDate.plusYears(asa.get().getDuration().longValue());
						asur.setNextAoh(resultDate);
						asur.setNextPoh(resultDate);
					}
				 	else if(asa.get().getUomOfDuration().equals("Time in Days" ))
					{
						LocalDate resultDate = currentDate.plusDays(asa.get().getDuration().longValue());
						asur.setNextAoh(resultDate);
						asur.setNextPoh(resultDate);
					}
			}
			}	
			Optional<AssetStatusUpdate> asu = assetStatusUpdateRepository.findByAssetTypeAndAssetIdAndFacilityId
					(assetMasterData2.getAssetType(),assetMasterData2.getAssetId(),assetMasterData2.getFacilityId());
			logger.info("*** asset staus records== ***"+asu.toString());
			Optional<Facility> fac = facilityRepository.findByFacilityId(assetMasterData2.getFacilityId());
			logger.info("*** before set values ***");
			
			if(asu.isPresent())
			{
				asur.setAsuId(asu.get().getId());
				asur.setCurrentStatus(asu.get().getCurrentStatus());
				asur.setDateOfStatus(asu.get().getDateOfStatus());
				asur.setStatus(asu.get().getStatus());
				asur.setRemarks(asu.get().getRemarks());
				asur.setEditPermission(true);
			
			}
			else {
				asur.setEditPermission(false);
			}
			asur.setAssetType(assetMasterData2.getAssetType());
			asur.setAssetId(assetMasterData2.getAssetId());
			asur.setDateOfManufacture(assetMasterData2.getDateOfCommision());
			asur.setFacilityId(fac.get().getFacilityName());
			asur.setModel(assetMasterData2.getModel());
			asur.setMake(assetMasterData2.getMake());			
			logger.info("*** object values****"+asur.toString());
			assetstatus.add(asur );
			
		}
		
		return assetstatus;
	}

	public List<AssetStatusUpdateResponse> prepareAssetStatusUpdateDataBasedOnFacility(AssetMasterData assetMasterData2,
			String facilityId) {
		List<AssetStatusUpdateResponse> assetstatus = new ArrayList<>();
		List<AssetMasterData> amd = assetMastersRepository.getFacilityId(facilityId);	
		logger.info("*** in mapper class ***"+amd);
		for (AssetMasterData assetMasterData : amd) {
			
		
			AssetStatusUpdateResponse asur = new AssetStatusUpdateResponse();
			
			logger.info("*** before ashhh ***");
			List<AssetsScheduleHistory>	ashList = assetsScheduleHistoryRepository.findByAssetTypeAndAssetIdAndFacilityId(assetMasterData.getAssetType(),assetMasterData.getAssetId(),assetMasterData.getFacilityId());
			for (AssetsScheduleHistory assetsScheduleHistory : ashList) {
				
				Optional<AssetsScheduleHistory> ashistory = assetsScheduleHistoryRepository.getByAssetTypeAndAssetIdAndFacilityIdAndScheduleCode(assetsScheduleHistory.getAssetType(),assetsScheduleHistory.getAssetId(),assetsScheduleHistory.getFacilityId(),assetsScheduleHistory.getScheduleCode());
				
				if (ashistory.isPresent()) {
					logger.info("*** after ash  ***"+ashistory.get().getAssetId()+ashistory.get().getAssetType()+ashistory.get().getFacilityId());
					 Optional<AssetScheduleAssoc> asa = assetScheduleAssocRepository.findByAssetTypeAndScheduleCode(ashistory.get().getAssetType(),ashistory.get().getScheduleCode());
					 LocalDate currentDate =LocalDate.now();
						logger.info("** current date***"+currentDate);
					 	if(asa.get().getUomOfDuration().equals("Time in Months" ))
							{
								LocalDate resultDate = currentDate.plusMonths(asa.get().getDuration().longValue());
								asur.setNextAoh(resultDate);
								asur.setNextPoh(resultDate);
							}
					 	else if(asa.get().getUomOfDuration().equals("Time in Years" ))
						{
							LocalDate resultDate = currentDate.plusYears(asa.get().getDuration().longValue());
							asur.setNextAoh(resultDate);
							asur.setNextPoh(resultDate);
						}
					 	else if(asa.get().getUomOfDuration().equals("Time in Days" ))
						{
							LocalDate resultDate = currentDate.plusDays(asa.get().getDuration().longValue());
							asur.setNextAoh(resultDate);
							asur.setNextPoh(resultDate);
						}
				} 
				}
			Optional<AssetStatusUpdate> asu = assetStatusUpdateRepository.findByAssetTypeAndAssetIdAndFacilityId
					(assetMasterData.getAssetType(),assetMasterData.getAssetId(),assetMasterData.getFacilityId());
			//Optional<AssetStatusUpdate> asuMax = assetStatusUpdateRepository.findByDateOfStatus(asu.get().getDateOfStatus());
			
			Optional<Facility> fac = facilityRepository.findByFacilityId(assetMasterData.getFacilityId());
			
			logger.info("*** before set values ***");
			if(asu.isPresent())
			{
				asur.setAsuId(asu.get().getId());
				asur.setCurrentStatus(asu.get().getCurrentStatus());
				asur.setDateOfStatus(asu.get().getDateOfStatus());
				asur.setStatus(asu.get().getStatus());
				asur.setRemarks(asu.get().getRemarks());
				asur.setEditPermission(true);
			
			}
			else {
				asur.setEditPermission(false);
			}
			asur.setAssetType(assetMasterData.getAssetType());
			asur.setAssetId(assetMasterData.getAssetId());
			asur.setDateOfManufacture(assetMasterData.getDateOfCommision());
			asur.setFacilityId(fac.get().getFacilityName());
			asur.setModel(assetMasterData.getModel());
			asur.setMake(assetMasterData.getMake());		
			
			
					
			logger.info("*** object values****"+asur.toString());
			assetstatus.add(asur );
	}
		
		return assetstatus;
	}



	public AssetStatusUpdate prepareAssetStatusUpdateBasedOnFacility(AssetStatusUpdate assetStatusUpdate) {
		
		try {
			
			if (assetStatusUpdate.getFacilityId() != null ) {
				Optional<Facility> facility  = facilityRepository.findByFacilityId(assetStatusUpdate.getFacilityId());
				if (facility.isPresent()) {
					assetStatusUpdate.setFacilityId(facility.get().getFacilityName());
				}
			}
			}catch (Exception e) {
				logger.error("ERROR >>> while finding facility, "+e.getMessage());
			}
			
			return assetStatusUpdate;
	}

}
