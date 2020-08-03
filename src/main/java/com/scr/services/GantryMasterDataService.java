package com.scr.services;

import java.util.List;

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

}
