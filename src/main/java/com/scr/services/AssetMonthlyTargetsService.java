package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
	public Optional<AssetMonthlyTarget> findAssetMonthlyTargetById(Long id) {
		// TODO Auto-generated method stub
		return assetTargetRepository.findById(id);

	}
   public List<AssetScheduleAssoc> getScheduleAssocOnDpr(String isDpr) {
		return assetSchAssocRepository.findByIsDpr(isDpr);
	}
}
