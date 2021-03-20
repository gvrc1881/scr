package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Make;
import com.scr.model.MeasureOrActivityList;
import com.scr.model.TssFeederMaster;
import com.scr.repository.TssFeederMasterRepository;
import com.scr.util.Constants;

@Service
public class TssFeederMasterService {
	
	@Autowired
	private TssFeederMasterRepository  tssFeederMasterRepository;

	public List<TssFeederMaster> findAllOrderByFeederNameAsc() {
		// TODO Auto-generated method stub
		return tssFeederMasterRepository.findAllOrderByFeederNameAsc();
	}
	public TssFeederMaster save(TssFeederMaster feeder) {
		// TODO Auto-generated method stub
		return tssFeederMasterRepository.save(feeder);
	}

	public Optional<TssFeederMaster> findById(Long id) {
		// TODO Auto-generated method stub
		return tssFeederMasterRepository.findById(id);
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		tssFeederMasterRepository.deleteById(id);
	}

	
	public Boolean existsByFeederName(String feederName) {
		// TODO Auto-generated method stub
		return tssFeederMasterRepository.existsByFeederName(feederName);
	}
	public Optional<TssFeederMaster> findByFeederName(String feederName) {
		// TODO Auto-generated method stub
		return tssFeederMasterRepository.findByFeederName(feederName);
	}

	public Optional<TssFeederMaster> findByFeederId(String feederId) {
		return tssFeederMasterRepository.findByFeederId(feederId);
	}
	public List<TssFeederMaster> getAllOrderByFeederNameAsc(List<String> fac) {
		
		return tssFeederMasterRepository.getAllByDataDivIn(fac);
	}
	public List<TssFeederMaster> findByTssName(String tssName) {
		
		return tssFeederMasterRepository.findByTssName(tssName);
	}


}
