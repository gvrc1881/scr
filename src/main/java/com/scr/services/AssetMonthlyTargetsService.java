package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.response.AssetStatusUpdateResponse;
import com.scr.model.AssetMonthlyTarget;
import com.scr.model.AssetScheduleAssoc;
import com.scr.repository.AssetMonthlyTargetRepository;
import com.scr.repository.AssetSchAssoRepository;

@Service
public class AssetMonthlyTargetsService {
static Logger logger = LogManager.getLogger(AssetMonthlyTargetsService.class);

	
	@Autowired
	private AssetMonthlyTargetRepository assetTargetRepository;
	
	@Autowired
	private AssetSchAssoRepository assetSchAssocRepository;
	
	public List<AssetMonthlyTarget> findAll(){
		return assetTargetRepository.findAll();
	}
	
	public void save(AssetMonthlyTarget assetMonthlyTarget) {
		assetTargetRepository.save(assetMonthlyTarget);
	}
	public void updateAssetMonthlyTarget(List<AssetMonthlyTarget> assetMonthlyTargetList) {
		
		logger.info("targets from new==="+assetMonthlyTargetList.toString());
		for (AssetMonthlyTarget assetMonthlyTargetData : assetMonthlyTargetList) {
			//Optional<AssetMonthlyTarget> assetMonTarget = assetTargetRepository.findByFacilityId(assetMonthlyTargetData.getFacilityId());
			Optional<AssetMonthlyTarget> assetMonTarget = assetTargetRepository.findByFacilityIdAndYearAndAssetTypeAndScheduleType(assetMonthlyTargetData.getFacilityId(),assetMonthlyTargetData.getYear(),assetMonthlyTargetData.getAssetType(),assetMonthlyTargetData.getScheduleType());
			logger.info("update records===="+assetMonTarget.toString());
			if (assetMonTarget.isPresent()) {
				
				logger.info("in if condition====");
				AssetMonthlyTarget updateAssetMonthlyTargetData = assetMonTarget.get();
				updateAssetMonthlyTargetData.setAssetType(assetMonthlyTargetData.getAssetType());
				updateAssetMonthlyTargetData.setScheduleType(assetMonthlyTargetData.getScheduleType());
				updateAssetMonthlyTargetData.setTotalPopulation(assetMonthlyTargetData.getTotalPopulation());
				updateAssetMonthlyTargetData.setTargetApr(assetMonthlyTargetData.getTargetApr());
				updateAssetMonthlyTargetData.setTargetMay(assetMonthlyTargetData.getTargetMay());
				updateAssetMonthlyTargetData.setTargetJune(assetMonthlyTargetData.getTargetJune());
				updateAssetMonthlyTargetData.setTargetJuly(assetMonthlyTargetData.getTargetJuly());
				updateAssetMonthlyTargetData.setTargetAug(assetMonthlyTargetData.getTargetAug());
				updateAssetMonthlyTargetData.setTargetSep(assetMonthlyTargetData.getTargetSep());
				updateAssetMonthlyTargetData.setTargetOct(assetMonthlyTargetData.getTargetOct());
				updateAssetMonthlyTargetData.setTargetNov(assetMonthlyTargetData.getTargetNov());
				updateAssetMonthlyTargetData.setTargetDec(assetMonthlyTargetData.getTargetDec());
				updateAssetMonthlyTargetData.setTargetJan(assetMonthlyTargetData.getTargetJan());
				updateAssetMonthlyTargetData.setTargetFeb(assetMonthlyTargetData.getTargetFeb());
				updateAssetMonthlyTargetData.setTargetMar(assetMonthlyTargetData.getTargetMar());
				updateAssetMonthlyTargetData.setFacilityId(assetMonthlyTargetData.getFacilityId());
				updateAssetMonthlyTargetData.setYear(assetMonthlyTargetData.getYear());
				

						assetTargetRepository.save(updateAssetMonthlyTargetData);
					}else {						
						
					/*	AssetMonthlyTarget updateAssetMonthlyTargetData = new AssetMonthlyTarget();
						updateAssetMonthlyTargetData.setAssetType(assetMonthlyTargetData.getAssetType());
						updateAssetMonthlyTargetData.setScheduleType(assetMonthlyTargetData.getScheduleType());
						updateAssetMonthlyTargetData.setTotalPopulation(assetMonthlyTargetData.getTotalPopulation());
						updateAssetMonthlyTargetData.setTargetApr(assetMonthlyTargetData.getTargetApr());
						updateAssetMonthlyTargetData.setTargetMay(assetMonthlyTargetData.getTargetMay());
						updateAssetMonthlyTargetData.setTargetJune(assetMonthlyTargetData.getTargetJune());
						updateAssetMonthlyTargetData.setTargetJuly(assetMonthlyTargetData.getTargetJuly());
						updateAssetMonthlyTargetData.setTargetAug(assetMonthlyTargetData.getTargetAug());
						updateAssetMonthlyTargetData.setTargetSep(assetMonthlyTargetData.getTargetSep());
						updateAssetMonthlyTargetData.setTargetOct(assetMonthlyTargetData.getTargetOct());
						updateAssetMonthlyTargetData.setTargetNov(assetMonthlyTargetData.getTargetNov());
						updateAssetMonthlyTargetData.setTargetDec(assetMonthlyTargetData.getTargetDec());
						updateAssetMonthlyTargetData.setTargetJan(assetMonthlyTargetData.getTargetJan());
						updateAssetMonthlyTargetData.setTargetFeb(assetMonthlyTargetData.getTargetFeb());
						updateAssetMonthlyTargetData.setTargetMar(assetMonthlyTargetData.getTargetMar());
						updateAssetMonthlyTargetData.setFacilityId(assetMonthlyTargetData.getFacilityId());
						updateAssetMonthlyTargetData.setYear(assetMonthlyTargetData.getYear());*/

								assetTargetRepository.save(assetMonthlyTargetData);
					}
		}
		}
		
	
	public Optional<AssetMonthlyTarget> findAssetMonthlyTargetById(Long id) {
		// TODO Auto-generated method stub
		return assetTargetRepository.findById(id);

	}
	
   public List<AssetScheduleAssoc> findByIsDpr(String isDpr) {
		return assetSchAssocRepository.findByIsDpr(isDpr);
	}
   
   public List<AssetMonthlyTarget> findByFacilityIdAndYear(String facilityId,String year) {	   
	   
	   logger.info("in service");
	   List <AssetMonthlyTarget> assetTargets = new ArrayList<>();
	   
	   List<AssetScheduleAssoc> asa = assetSchAssocRepository.getAssetTypesAndSchedules();
	   for (AssetScheduleAssoc assetScheduleAssoc : asa) {
		   logger.info("in fro loop");
		   Optional<AssetMonthlyTarget> amt = assetTargetRepository.findByFacilityIdAndYearAndAssetTypeAndScheduleType(facilityId,year,assetScheduleAssoc.getAssetType(),assetScheduleAssoc.getScheduleCode());
		   logger.info(" after amt ==="+amt.toString());
		   AssetMonthlyTarget AssetMonthlyTargetData = new AssetMonthlyTarget();
		   if(amt.isPresent()) {
			   
			   logger.info(" in if condition ===");
				
			   AssetMonthlyTargetData.setFacilityId(amt.get().getFacilityId());
			   AssetMonthlyTargetData.setYear(amt.get().getYear());
				AssetMonthlyTargetData.setAssetType(amt.get().getAssetType());
				AssetMonthlyTargetData.setScheduleType(amt.get().getScheduleType());
				AssetMonthlyTargetData.setTargetApr(amt.get().getTargetApr());
				AssetMonthlyTargetData.setTargetMay(amt.get().getTargetMay());
				AssetMonthlyTargetData.setTargetJune(amt.get().getTargetJune());
				AssetMonthlyTargetData.setTargetJuly(amt.get().getTargetJuly());
				AssetMonthlyTargetData.setTargetAug(amt.get().getTargetAug());
				AssetMonthlyTargetData.setTargetSep(amt.get().getTargetSep());
				AssetMonthlyTargetData.setTargetOct(amt.get().getTargetOct());
				AssetMonthlyTargetData.setTargetNov(amt.get().getTargetNov());
				AssetMonthlyTargetData.setTargetDec(amt.get().getTargetDec());
				AssetMonthlyTargetData.setTargetJan(amt.get().getTargetJan());
				AssetMonthlyTargetData.setTargetFeb(amt.get().getTargetFeb());
				AssetMonthlyTargetData.setTargetMar(amt.get().getTargetMar());
				AssetMonthlyTargetData.setSeqId(assetScheduleAssoc.getTargetPlanMonths());
				AssetMonthlyTargetData.setTotalPopulation(amt.get().getTotalPopulation());
				

		   }
		   else {
			   
			   logger.info(" in else ===");
			   AssetMonthlyTargetData.setFacilityId(facilityId);
			   AssetMonthlyTargetData.setYear(year);
			   AssetMonthlyTargetData.setAssetType(assetScheduleAssoc.getAssetType());
				AssetMonthlyTargetData.setScheduleType(assetScheduleAssoc.getScheduleCode());
				AssetMonthlyTargetData.setTargetApr(0);
				AssetMonthlyTargetData.setTargetMay(0);
				AssetMonthlyTargetData.setTargetJune(0);
				AssetMonthlyTargetData.setTargetJuly(0);
				AssetMonthlyTargetData.setTargetAug(0);
				AssetMonthlyTargetData.setTargetSep(0);
				AssetMonthlyTargetData.setTargetOct(0);
				AssetMonthlyTargetData.setTargetNov(0);
				AssetMonthlyTargetData.setTargetDec(0);
				AssetMonthlyTargetData.setTargetJan(0);
				AssetMonthlyTargetData.setTargetFeb(0);
				AssetMonthlyTargetData.setTargetMar(0);
				AssetMonthlyTargetData.setTotalPopulation(0);
				AssetMonthlyTargetData.setSeqId(assetScheduleAssoc.getTargetPlanMonths());
		   }
		   assetTargets.add(AssetMonthlyTargetData);
		   logger.info("assetTargets===="+assetTargets.size());
	}
	   
	   

	   return assetTargets;
	}
}
