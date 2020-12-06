package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.PrecautionaryMeasure;
import com.scr.repository.PrecautionaryMeasureRepository;

@Service
public class PrecautionaryMeasureService {

	@Autowired
	private PrecautionaryMeasureRepository precautionaryMeasureRepository;

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

	public void deletePrecautionaryMeasureById(Long id) {
		// TODO Auto-generated method stub
		precautionaryMeasureRepository.deleteById(id);
	}
	
	
}
