package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.StandardPhases;
import com.scr.repository.StandardPhasesRepository;

@Service
public class StandardPhaseService {

	@Autowired
	private StandardPhasesRepository standardPhasesRepository;


	public List<StandardPhases> findAll(){
		return standardPhasesRepository.findAll();
	}

	public void save(StandardPhases standardPhases) {
		standardPhasesRepository.save(standardPhases);
	}
	public Optional<StandardPhases> findStandardPhasesById(Integer id) {
		// TODO Auto-generated method stub
		return standardPhasesRepository.findById(id);

	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		standardPhasesRepository.deleteById(id);
	}
	public Boolean existsByDescriptionAndTypeOfWork(String description, String typeOfWork) {
		return standardPhasesRepository.existsByDescriptionAndTypeOfWork(description,typeOfWork);
	}
	public Optional<StandardPhases> findByDescriptionAndTypeOfWork(String description,String typeOfWork) {
		return standardPhasesRepository.findByDescriptionAndTypeOfWork(description,typeOfWork);
	}
	public Boolean existsBySequenceAndTypeOfWork(Integer sequence, String typeOfWork) {
		return standardPhasesRepository.existsBySequenceAndTypeOfWork(sequence,typeOfWork);
	}
	public Optional<StandardPhases> findBySequenceAndTypeOfWork(Integer sequence,String typeOfWork) {
		return standardPhasesRepository.findBySequenceAndTypeOfWork(sequence,typeOfWork);
	}
	public Boolean existsByNameAndTypeOfWork(String name, String typeOfWork) {
		return standardPhasesRepository.existsByNameAndTypeOfWork(name,typeOfWork);
	}
	public Optional<StandardPhases> findByNameAndTypeOfWork(String name,String typeOfWork) {
		return standardPhasesRepository.findByNameAndTypeOfWork(name,typeOfWork);
	}
}