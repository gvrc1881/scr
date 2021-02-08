package com.scr.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Make;
import com.scr.model.Model;
import com.scr.model.PrecautionaryMeasure;
import com.scr.model.PrecautionaryMeasuresMaster;
import com.scr.model.TestInspection;
import com.scr.repository.TestInspectionRepository;

@Service
public class TestInspectionService {
	
	@Autowired
	private TestInspectionRepository testInspectionRepository;
	
	public List<TestInspection> findAll() {
		// TODO Auto-generated method stub
		return testInspectionRepository.findAll();
	}
	public void save(TestInspection testInspection) {
		testInspectionRepository.save(testInspection);
}
	public Optional<TestInspection> findTestInspectionById(Long id) {
		// TODO Auto-generated method stub
		return testInspectionRepository.findById(id);
	}

	public void deleteTestInspectionById(Long id) {
		// TODO Auto-generated method stub
		testInspectionRepository.deleteById(id);
	}
	
	public Boolean existsByNameAndMakeCodeAndModelCode(String name,Make makeCode, Model modelCode) {
		// TODO Auto-generated method stub
		return testInspectionRepository.existsByNameAndMakeCodeAndModelCode(name,makeCode,modelCode);
	}
	public Optional<TestInspection> findByNameAndMakeCodeAndModelCode(String facilityId,Make makeCode,Model modelCode) {
		// TODO Auto-generated method stub
		return testInspectionRepository.findByNameAndMakeCodeAndModelCode(facilityId,makeCode,modelCode);
	}
	public Boolean existsByMakeCodeAndModelCodeAndDescription(Make makeCode, Model modelCode,String description) {
		// TODO Auto-generated method stub
		return testInspectionRepository.existsByMakeCodeAndModelCodeAndDescription(makeCode,modelCode,description);
	}
	public Optional<TestInspection> findByMakeCodeAndModelCodeAndDescription(Make makeCode,Model modelCode,String description) {
		// TODO Auto-generated method stub
		return testInspectionRepository.findByMakeCodeAndModelCodeAndDescription(makeCode,modelCode,description);
	}
}
	
	
	
