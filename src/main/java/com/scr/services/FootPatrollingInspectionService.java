package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Compliance;
import com.scr.model.FootPatrollingInspection;
import com.scr.model.Observation;
import com.scr.repository.ComplianceRepository;
import com.scr.repository.FootPatrollingInspectionRepository;
import com.scr.repository.ObservationsRepository;

@Service
public class FootPatrollingInspectionService {
	
	
	@Autowired
	private FootPatrollingInspectionRepository footPatrollingInspectionRepository;
	@Autowired
	private ObservationsRepository observationsRepository;
	@Autowired
	private ComplianceRepository complianceRepository;
	
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
	
	
	//Observation Service
	public List<Observation> findAllObservations() {
		// TODO Auto-generated method stub
		return observationsRepository.findAll();
	}
	
	public void save(Observation observation) {
		// TODO Auto-generated method stub
		observationsRepository.save(observation);
	}
	public Optional<Observation> findObservationItemById(Long id) {
		// TODO Auto-generated method stub
		return observationsRepository.findById(id);
	}

	public void deleteObservationItemById(Long id) {
		observationsRepository.deleteById(id);
	}
	
	//Compliance Service
	public List<Compliance> findAllCompliances() {
		return complianceRepository.findAll();
	}
	
	public void save(Compliance compliance) {
		complianceRepository.save(compliance);
	}
	public Optional<Compliance> findComplianceItemById(Long id) {
		return complianceRepository.findById(id);
	}

	public void deleteComplianceItemById(Long id) {
		complianceRepository.deleteById(id);
	}
}
