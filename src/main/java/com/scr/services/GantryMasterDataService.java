package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.mapper.FacilityCommonMapper;
import com.scr.model.GantryMasterData;
import com.scr.repository.GantryMasterDataRepository;

@Service
public class GantryMasterDataService {
	
	static Logger logger = LogManager.getLogger(GantryMasterDataService.class);
	
	@Autowired
	private GantryMasterDataRepository gantryMasterDataRepository;
	
	@Autowired
	private FacilityCommonMapper facilityCommonMapper;


	
	public List<GantryMasterData> findAll() {
		logger.info("Calling mapper for preparing to get gantryMasterData Allmodel object");
		List<GantryMasterData> gmd = new ArrayList<>();
		List<GantryMasterData> gantryMasterDatas = gantryMasterDataRepository.findAll();
		for (GantryMasterData gantryMasterData : gantryMasterDatas) {
			gantryMasterData = facilityCommonMapper.prepareGantryData(gantryMasterData);
			gmd.add(gantryMasterData);
		}
		 return gmd;
	}
	
	public void save(GantryMasterData gantryMasterData) {
		// TODO Auto-generated method stub
		gantryMasterDataRepository.save(gantryMasterData);
	}

	public Optional<GantryMasterData> findGantryItemById(Long id) {
		// TODO Auto-generated method stub
		return gantryMasterDataRepository.findById(id);
	}

	public void deleteGantryItemById(Long id) {
		// TODO Auto-generated method stub
		gantryMasterDataRepository.deleteById(id);
	}
	public Boolean existsByGantryCode(String gantryCode) {
		// TODO Auto-generated method stub
		return gantryMasterDataRepository.existsByGantryCode(gantryCode);
	}
	
	public Optional<GantryMasterData> findByGantryCode(String gantryCode) {
		return gantryMasterDataRepository.findByGantryCode(gantryCode);
	}
}
