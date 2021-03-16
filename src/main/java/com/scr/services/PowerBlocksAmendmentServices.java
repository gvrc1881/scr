package com.scr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.PowerBlocksAmendment;
import com.scr.repository.PowerBlocksAmendmentRepository;

@Service
public class PowerBlocksAmendmentServices {
	
	@Autowired
	private PowerBlocksAmendmentRepository powerBlocksAmendmentRepository;

	public Optional<PowerBlocksAmendment> findById(Long id) {
		return powerBlocksAmendmentRepository.findById(id);
	}

	public void save(PowerBlocksAmendment powerBlocksAmendment) {
		 powerBlocksAmendmentRepository.save(powerBlocksAmendment);
	}

	public Optional<PowerBlocksAmendment> findByPbOperationSeqId(String pbOperationId) {
		return powerBlocksAmendmentRepository.findByPbOperationSeqId(pbOperationId);
	}	

}
