package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.TssFeederMaster;
import com.scr.repository.TssFeederMasterRepository;

@Service
public class TssFeederMasterService {
	
	@Autowired
	private TssFeederMasterRepository  tssFeederMasterRepository;

	public List<TssFeederMaster> findAllOrderByFeederNameAsc() {
		// TODO Auto-generated method stub
		return tssFeederMasterRepository.findAllOrderByFeederNameAsc();
	}

}
