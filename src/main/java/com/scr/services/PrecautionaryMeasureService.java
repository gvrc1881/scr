package com.scr.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.PrecautionaryMeasure;
import com.scr.model.PrecautionaryMeasuresMaster;
import com.scr.repository.PrecautionaryMeasureMasterRepository;
import com.scr.repository.PrecautionaryMeasureRepository;

@Service
public class PrecautionaryMeasureService {

	@Autowired
	private PrecautionaryMeasureRepository precautionaryMeasureRepository;
	
	@Autowired
	private PrecautionaryMeasureMasterRepository precautionaryMeasureMasterRepository;

	public List<PrecautionaryMeasure> findAll() {
		// TODO Auto-generated method stub
		return precautionaryMeasureRepository.findAll();
	}
	
	public void save(PrecautionaryMeasure precautionaryMeasure) {
		precautionaryMeasureRepository.save(precautionaryMeasure);
	}
	public Optional<PrecautionaryMeasure> findPrecautionaryMeasureById(Long id) {
		// TODO Auto-generated method stub
		return precautionaryMeasureRepository.findById(id);

	}
	public Optional<PrecautionaryMeasuresMaster> findById(Integer id) {
		// TODO Auto-generated method stub
		return precautionaryMeasureMasterRepository.findById(id);
	}

	public void deletePrecautionaryMeasureById(Long id) {
		// TODO Auto-generated method stub
		precautionaryMeasureRepository.deleteById(id);
	}
	
	public Optional<PrecautionaryMeasuresMaster> findByActive(String active) {
		return precautionaryMeasureMasterRepository.findByActiveOrderByPrecautionaryMeasureAsc(active);
	}
	
	public Boolean existsByFacilityIdAndPrecautionaryMeasureAndDateOfWork(String facilityId,PrecautionaryMeasuresMaster precautionaryMeasure, Timestamp dateOfWork) {
		// TODO Auto-generated method stub
		return precautionaryMeasureRepository.existsByFacilityIdAndPrecautionaryMeasureAndDateOfWork(facilityId,precautionaryMeasure,dateOfWork);
	}
	public Optional<PrecautionaryMeasure> findByFacilityIdAndPrecautionaryMeasureAndDateOfWork(String facilityId,PrecautionaryMeasuresMaster precautionaryMeasure,Timestamp dateOfWork) {
		// TODO Auto-generated method stub
		return precautionaryMeasureRepository.findByFacilityIdAndPrecautionaryMeasureAndDateOfWork(facilityId,precautionaryMeasure,dateOfWork);
	}
}
