package com.scr.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.mapper.PrecautionaryMeasureMapper;
import com.scr.model.PrecautionaryMeasure;
import com.scr.model.PrecautionaryMeasuresMaster;
import com.scr.repository.PrecautionaryMeasureMasterRepository;
import com.scr.repository.PrecautionaryMeasureRepository;

@Service
public class PrecautionaryMeasureService {
	
	static Logger logger = LogManager.getLogger(PrecautionaryMeasureService.class);


	@Autowired
	private PrecautionaryMeasureRepository precautionaryMeasureRepository;
	
	@Autowired
	private PrecautionaryMeasureMasterRepository precautionaryMeasureMasterRepository;
	
	@Autowired
	private PrecautionaryMeasureMapper precautionaryMeasureMapper;

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
	
	public List<PrecautionaryMeasure> findAll() {
		logger.info("Calling mapper for preparing to get PrecautionaryMeasure Allmodel object");
		List<PrecautionaryMeasure> pm = new ArrayList<>();
		List<PrecautionaryMeasure> precautionaryMeasures = precautionaryMeasureRepository.findAll();
		for (PrecautionaryMeasure precautionaryMeasure : precautionaryMeasures) {
			precautionaryMeasure = precautionaryMeasureMapper.preparePreacutionaryMeasureData(precautionaryMeasure);
			pm.add(precautionaryMeasure);
		}
		 return pm;
	}
}
