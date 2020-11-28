package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.MilestoneTargets;
import com.scr.repository.MilestoneTargetsRepository;

@Service
public class MilestoneTargetsService {
	
	private static Logger log = LogManager.getLogger(MilestoneTargetsService.class);
	
	@Autowired
	private MilestoneTargetsRepository milestoneTargetsRepository;

	public List<MilestoneTargets> findAll() {
		// TODO Auto-generated method stub
		return milestoneTargetsRepository.findAll();
	}

	public MilestoneTargets save(MilestoneTargets targets) {
		// TODO Auto-generated method stub
		return milestoneTargetsRepository.save(targets);
	}

	public Optional<MilestoneTargets> findById(Integer id) {
		// TODO Auto-generated method stub
		return milestoneTargetsRepository.findById(id);
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		milestoneTargetsRepository.deleteById(id);
		
	}

	
	

}
