package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.GantryMasterData;
import com.scr.repository.GantryMasterDataRepository;

@Service
public class GantryMasterDataService {
	
	@Autowired
	private GantryMasterDataRepository gantryMasterDataRepository;

	public List<GantryMasterData> findAll() {
		// TODO Auto-generated method stub
		return gantryMasterDataRepository.findAll();
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
