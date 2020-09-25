package com.scr.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.EnergyMeter;
import com.scr.repository.EnergyMeterRepository;

@Service
public class EnergyMeterService {
	
	@Autowired
	private EnergyMeterRepository energyMeterRepository;

	public List<EnergyMeter> findAll() {
		// TODO Auto-generated method stub
		return energyMeterRepository.findAll();
	}

	public EnergyMeter save(EnergyMeter energyMeter) {
		// TODO Auto-generated method stub
		return energyMeterRepository.save(energyMeter);
	}

	public Optional<EnergyMeter> findById(Long id) {
		// TODO Auto-generated method stub
		return energyMeterRepository.findById(id);
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		energyMeterRepository.deleteById(id);
	}

	public Boolean existsByFeederAndStartDate(String feeder, Timestamp startDate) {
		// TODO Auto-generated method stub
		return energyMeterRepository.existsByFeederAndStartDate(feeder,startDate);
	}
	
	public Boolean existsByFeederAndEndDateIsNull(String feeder) {
		return energyMeterRepository.existsByFeederAndEndDateIsNull(feeder);
	}

}
