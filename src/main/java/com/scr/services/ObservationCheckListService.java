package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.ObservationsCheckList;
import com.scr.repository.ObservationCheckListRepository;

@Service
public class ObservationCheckListService {
	
	@Autowired
	private ObservationCheckListRepository observationCheckListRepository;
	
	public List<ObservationsCheckList> findAll() {
		// TODO Auto-generated method stub
		return observationCheckListRepository.findAll();

	}

	public void save(ObservationsCheckList observationsCheckList) {
		observationCheckListRepository.save(observationsCheckList);
	}

	public Optional<ObservationsCheckList> findObservationCheckListById(Long id) {
		// TODO Auto-generated method stub
		return observationCheckListRepository.findById(id);

	}

	public void deleteObservationCheckListById(Long id) {
		// TODO Auto-generated method stub
		observationCheckListRepository.deleteById(id);
	}

}
