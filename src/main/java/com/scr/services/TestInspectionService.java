package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
	
	
	
