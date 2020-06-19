package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.FootPatrollingInspection;
import com.scr.repository.FootPatrollingInspectionRepository;

@Service
public class FootPatrollingInspectionService {
	
	
	@Autowired
	private FootPatrollingInspectionRepository footPatrollingInspectionRepository;
	
	public List<FootPatrollingInspection> findAll() {
		// TODO Auto-generated method stub
		return footPatrollingInspectionRepository.findAll();
	}
	
	public void save(FootPatrollingInspection footPatrollingInspection) {
		// TODO Auto-generated method stub
		footPatrollingInspectionRepository.save(footPatrollingInspection);
	}
	public Optional<FootPatrollingInspection> findFPInspectionItemById(Long id) {
		// TODO Auto-generated method stub
		return footPatrollingInspectionRepository.findById(id);
	}

	public void deleteFPInspectionItemById(Long id) {
		footPatrollingInspectionRepository.deleteById(id);
	}
}
