package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.PbSwitchControl;
import com.scr.repository.PBSwitchControlRepository;

@Service
public class PbSwitchControlService {
	
	@Autowired
	private PBSwitchControlRepository pbSwitchControlRepository;

	public List<PbSwitchControl> findByPbExtentTypeAndPbExtentCodeIn(String extentType,  List<String> extentCode) {
		// TODO Auto-generated method stub
		return pbSwitchControlRepository.findByPbExtentTypeAndPbExtentCodeIn(extentType,extentCode);
	}

}
