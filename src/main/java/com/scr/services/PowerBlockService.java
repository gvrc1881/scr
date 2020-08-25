package com.scr.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.AssetsScheduleHistory;
import com.scr.model.PowerBlock;
import com.scr.repository.PowerBlockRepository;

@Service
public class PowerBlockService {

	static Logger logger = LogManager.getLogger(PowerBlockService.class);

	@Autowired
	private PowerBlockRepository powerBlockRepository;

	public List<PowerBlock> findAll() {
		return powerBlockRepository.findAll();
	}

	public void save(PowerBlock powerBlock) {
		powerBlockRepository.save(powerBlock);
	}

	public Optional<PowerBlock> findById(Long id) {
		return powerBlockRepository.findById(id);
	}

	public void deleteById(Long id) {
		powerBlockRepository.deleteById(id);
	}
	public List<PowerBlock> findPowerBlocks(String facilityId,Date createdDate) {
		List<PowerBlock> pbList = powerBlockRepository.findByFacilityIdAndCreatedDate(facilityId,createdDate);
		return pbList;
	}
}
