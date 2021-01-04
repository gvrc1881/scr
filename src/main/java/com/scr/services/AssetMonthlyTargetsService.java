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
			List<AssetMonthlyTarget> assetMonTarget = assetTargetRepository.findByFacilityIdAndYear(assetMonthlyTargetData.getFacilityId(),assetMonthlyTargetData.getYear());
			          AssetMonthlyTarget assetMonthlyTarget = assetMonTarget.get(0);

						assetMonthlyTarget.setAssetType(assetMonthlyTargetData.getAssetType());
						assetMonthlyTarget.setScheduleType(assetMonthlyTargetData.getScheduleType());
						assetMonthlyTarget.setTotalPopulation(assetMonthlyTargetData.getTotalPopulation());
						assetMonthlyTarget.setTargetApr(assetMonthlyTargetData.getTargetApr());
						assetMonthlyTarget.setTargetMay(assetMonthlyTargetData.getTargetMay());
						assetMonthlyTarget.setTargetJune(assetMonthlyTargetData.getTargetJune());
						assetMonthlyTarget.setTargetJuly(assetMonthlyTargetData.getTargetJuly());
						assetMonthlyTarget.setTargetAug(assetMonthlyTargetData.getTargetAug());
						assetMonthlyTarget.setTargetSep(assetMonthlyTargetData.getTargetSep());
						assetMonthlyTarget.setTargetOct(assetMonthlyTargetData.getTargetOct());
						assetMonthlyTarget.setTargetNov(assetMonthlyTargetData.getTargetNov());
						assetMonthlyTarget.setTargetDec(assetMonthlyTargetData.getTargetDec());
						assetMonthlyTarget.setTargetJan(assetMonthlyTargetData.getTargetJan());
						assetMonthlyTarget.setTargetFeb(assetMonthlyTargetData.getTargetFeb());
						assetMonthlyTarget.setTargetMar(assetMonthlyTargetData.getTargetMar());

						assetTargetRepository.save(assetMonthlyTarget);
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
