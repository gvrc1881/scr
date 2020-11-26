package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.StandardPhaseActivity;
import com.scr.repository.StandardPhaseActivityRepository;

@Service
public class StandardPhasesActivityService {
	
	@Autowired
	private StandardPhaseActivityRepository standardPhaseActivityRepository;
	
	public List<StandardPhaseActivity> findAll(){
		return standardPhaseActivityRepository.findAll();
	}
		
	public void save(StandardPhaseActivity standardPhaseActivity) {
		standardPhaseActivityRepository.save(standardPhaseActivity);
	}
	public Optional<StandardPhaseActivity> findStandardPhaseActivityById(Integer id) {
		// TODO Auto-generated method stub
		return standardPhaseActivityRepository.findById(id);

	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		standardPhaseActivityRepository.deleteById(id);
	}

}
