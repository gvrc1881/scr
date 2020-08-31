package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.ObservationCategory;
import com.scr.repository.ObservationCategoryRepository;

@Service
public class ObservationCategoriesService {
	
	@Autowired
	private ObservationCategoryRepository observationCategoryRepository;
	
	public List<ObservationCategory> findAll() {
		// TODO Auto-generated method stub
		return observationCategoryRepository.findAll();

	}

	public void save(ObservationCategory observationCategory) {
		observationCategoryRepository.save(observationCategory);
	}

	public Optional<ObservationCategory> findObservationCategoriesById(Long id) {
		// TODO Auto-generated method stub
		return observationCategoryRepository.findById(id);

	}

	public void deleteObservationCategoryById(Long id) {
		// TODO Auto-generated method stub
		observationCategoryRepository.deleteById(id);
	}
	public Boolean existsByInspectionTypeAndObservationCategory(String inspectionType, String observationCategory) {
		return observationCategoryRepository.existsByInspectionTypeAndObservationCategory(inspectionType,observationCategory);
	}
	public Optional<ObservationCategory> findByInspectionTypeAndObservationCategory(String inspectionType,String observationCategory) {
		// TODO Auto-generated method stub
		return observationCategoryRepository.findByInspectionTypeAndObservationCategory(inspectionType,observationCategory);
	}
}
