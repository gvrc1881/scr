package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.response.EnergyConsumptionResponse;
import com.scr.repository.EnergyConsumptionRepository;

@Service
public class EnergyConsumptionService {

	@Autowired
	private EnergyConsumptionRepository repository;
	
	public List<EnergyConsumptionResponse> findEnergyConsumption(String fromDate, String toDate, String feederId) {
		return repository.findEnergyConsumption(fromDate, toDate, feederId);
	}

}
