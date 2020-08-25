package com.scr.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Drives;
import com.scr.model.MeasureOrActivityList;
import com.scr.repository.MeasureOrActivityListRepository;

@Service
public class MeasureOrActivityListService {
	
	@Autowired
	private MeasureOrActivityListRepository measureRepository;

	public List<MeasureOrActivityList> findAll() {
		// TODO Auto-generated method stub
		return measureRepository.findAll();
	}

	public MeasureOrActivityList save(MeasureOrActivityList measure) {
		// TODO Auto-generated method stub
		return measureRepository.save(measure);
	}

	public Optional<MeasureOrActivityList> findById(Long id) {
		// TODO Auto-generated method stub
		return measureRepository.findById(id);
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		measureRepository.deleteById(id);
	}

	public Boolean existsByActivityId(String activityId) {
		// TODO Auto-generated method stub
		return measureRepository.existsByActivityId(activityId);
	}
	public Boolean existsByActivityName(String activityName) {
		// TODO Auto-generated method stub
		return measureRepository.existsByActivityName(activityName);
	}
	public Boolean existsByActivityNameAndUnitOfMeasure(String activityName, String unitOfMeasure) {
		 //TODO Auto-generated method stub
		return measureRepository.existsByActivityNameAndUnitOfMeasure(activityName,unitOfMeasure);
	}

	public Optional<MeasureOrActivityList> findByActivityId(String activityId) {
		// TODO Auto-generated method stub
		return measureRepository.findByActivityId(activityId);
	}

	

}
