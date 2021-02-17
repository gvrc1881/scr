package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.AssetMonthlyTarget;
import com.scr.model.AssetScheduleAssoc;
import com.scr.model.WPASectionTargets;
import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
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
		for (AssetMonthlyTarget assetMonthlyTargetData : assetMonthlyTargetList) {
			Optional<AssetMonthlyTarget> assetMonTarget = assetTargetRepository.findByFacilityId(assetMonthlyTargetData.getFacilityId());
			if (assetMonTarget.isPresent()) {
				
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

						assetTargetRepository.save(updateAssetMonthlyTargetData);
					}
		}
		}
		
	
	public Optional<AssetMonthlyTarget> findAssetMonthlyTargetById(Long id) {
		// TODO Auto-generated method stub
		return assetTargetRepository.findById(id);

	}
	
   public Optional<AssetScheduleAssoc> findByIsDpr(String isDpr) {
		return assetSchAssocRepository.findByIsDpr(isDpr);
	}
   
   public List<AssetMonthlyTarget> findByFacilityIdAndYear(String facilityId,String year) {
		// TODO Auto-generated method stub
		return assetTargetRepository.findByFacilityIdAndYear(facilityId,year);

	}
}
