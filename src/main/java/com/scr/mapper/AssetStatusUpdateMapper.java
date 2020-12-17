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
			Optional<AssetsScheduleHistory> ash = assetsScheduleHistoryRepository.findByAssetTypeAndAssetIdAndFacilityId(assetMasterData2.getAssetType(),assetMasterData2.getAssetId(),assetMasterData2.getFacilityId());
				
			if (ash.isPresent()) {
				logger.info("*** after ash  ***"+ash.get().getAssetId()+ash.get().getAssetType()+ash.get().getFacilityId());
				 Optional<AssetScheduleAssoc> asa = assetScheduleAssocRepository.findByAssetTypeAndScheduleCode(ash.get().getAssetType(),ash.get().getScheduleCode());
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
			Optional<AssetStatusUpdate> asu = assetStatusUpdateRepository.findByAssetTypeAndAssetIdAndFacilityId
					(assetMasterData2.getAssetType(),assetMasterData2.getAssetId(),assetMasterData2.getFacilityId());
			logger.info("*** before set values ***");
			if(asu.isPresent())
			{
				//asu.get().getDateOfStatus()
				//asur.setDateOfStatus();
				asur.setAssetType(asu.get().getAssetType());
				asur.setAssetId(asu.get().getAssetId());
				asur.setFacilityId(asu.get().getFacilityId());
				asur.setChangeOfStatus(asu.get().getCurrentStatus());
				asur.setDateOfStatus(asu.get().getDateOfStatus());
				asur.setRemarks(asu.get().getRemarks());
			
			}
			else {
			asur.setAssetType(assetMasterData2.getAssetType());
			asur.setAssetId(assetMasterData2.getAssetId());
			asur.setDateOfManufacture(assetMasterData2.getDateOfManufacture());
			asur.setFacilityId(assetMasterData2.getFacilityId());
			asur.setModel(assetMasterData2.getModel());
			asur.setMake(assetMasterData2.getMake());			
			}			
			logger.info("*** object values****"+asur.toString());
			assetstatus.add(asur );
			
		}
		
		return assetstatus;
	}

	public List<AssetStatusUpdateResponse> prepareAssetStatusUpdateDataBasedOnFacility(AssetMasterData assetMasterData2,
			String facilityId) {
		List<AssetStatusUpdateResponse> assetstatus = new ArrayList<>();
		Optional<AssetMasterData> amd = assetMastersRepository.getByFacilityId(facilityId);	
		logger.info("*** in mapper class ***"+amd);
		
			AssetStatusUpdateResponse asur = new AssetStatusUpdateResponse();
			logger.info("*** before ashhh ***");
			Optional<AssetsScheduleHistory> ash = assetsScheduleHistoryRepository.findByAssetTypeAndAssetIdAndFacilityId(amd.get().getAssetType(),amd.get().getAssetId(),amd.get().getFacilityId());
			logger.info("*** before asuuu ***");
			Optional<AssetStatusUpdate> asu = assetStatusUpdateRepository.findByAssetTypeAndAssetIdAndFacilityId(ash.get().getAssetType(),ash.get().getAssetId(),ash.get().getFacilityId());
			asur.setAssetType(ash.get().getAssetType());
			asur.setAssetId(ash.get().getAssetId());
			asur.setDateOfManufacture(amd.get().getDateOfManufacture());
			asur.setFacilityId(ash.get().getFacilityId());
			Optional<AssetScheduleAssoc> asa = assetScheduleAssocRepository.findByAssetTypeAndScheduleCode(ash.get().getAssetType(),ash.get().getScheduleCode());
						//sch = Count(ash.get().getScheduleDate(),asa.get().getDuration());
			Date date= new Date();
			if(asa.get().getUomOfDuration() == "Time in Months" )
			{
				// date.add(date.getMonth(),asa.get().getDuration());
			}
			
			//asur.setNextAoh(asa.get().getDuration());
			//asur.setNextPoh(asa.get().getDuration());
			asur.setChangeOfStatus(asu.get().getCurrentStatus());
			asur.setDateOfStatus(asu.get().getDateOfStatus());
			
			assetstatus.add(asur );
			
		
		
		return assetstatus;
	}

	

}
