package com.scr.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.SwitchMaintenenceHistory;
import com.scr.repository.SwitchMaintenenceHistoryRepository;

@Service
public class SwitchMaintenenceHistoryService {
	
	@Autowired
	private SwitchMaintenenceHistoryRepository  switchMaintenenceHistoryRepository;

	public void save(SwitchMaintenenceHistory switchMaintenenceHistory) {
		// TODO Auto-generated method stub
		switchMaintenenceHistoryRepository.save(switchMaintenenceHistory);
	}

}
