package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.controller.EnergyConsumptionController;
import com.scr.mapper.EnergyConsumptionMapper;
import com.scr.message.response.EnergyConsumptionResponse;
import com.scr.model.EnergyConsumption;
import com.scr.repository.EnergyConsumptionRepository;
import com.scr.repository.EnergyConsumptionUtilRepository;

@Service
public class EnergyConsumptionService {
	
	static Logger logger = LogManager.getLogger(EnergyConsumptionService.class);

	@Autowired
	private EnergyConsumptionUtilRepository utilRepository;
	
	@Autowired
	private EnergyConsumptionRepository repository;
	
	@Autowired
	private EnergyConsumptionMapper mapper;
	
	public List<EnergyConsumptionResponse> findEnergyConsumption(String fromDate, String toDate, String feederId, String division) {
		return utilRepository.findEnergyConsumption(fromDate, toDate, feederId, division);
	}

	public void saveEnergyConsumption(EnergyConsumptionResponse request) {
		Optional<EnergyConsumption> energyConsumptionOptional = null;
		if(request.getId() != null && request.getId() != 0) {
			energyConsumptionOptional = repository.findById(request.getId());
			if(energyConsumptionOptional.isPresent()) {
				EnergyConsumption energyConsumption = energyConsumptionOptional.get();
				energyConsumption = mapper.prepareEnergyConsumptionModel(request, energyConsumption);
				repository.save(energyConsumption);
			}
		}else {
			EnergyConsumption list = repository.findTopByOrderByIdDesc();
			EnergyConsumption energyConsumption = mapper.prepareEnergyConsumptionModelForSave(request, list);
			repository.save(energyConsumption);
		}
	}

}
