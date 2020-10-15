package com.scr.services;

import java.util.List;
import java.util.Optional;
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
	
	public List<PbSwitchControl> findAll() {
		// TODO Auto-generated method stub
		return pbSwitchControlRepository.findAll();
	}
	public void save(PbSwitchControl pbSwitchControl) {
		// TODO Auto-generated method stub
		pbSwitchControlRepository.save(pbSwitchControl);
	}

	public Optional<PbSwitchControl> findPbSwitchItemById(Long id) {
		// TODO Auto-generated method stub
		return pbSwitchControlRepository.findById(id);
	}

	public void deletePbSwitchItemById(Long id) {
		// TODO Auto-generated method stub
		pbSwitchControlRepository.deleteById(id);
	}

}
