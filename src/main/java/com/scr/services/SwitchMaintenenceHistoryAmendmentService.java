package com.scr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.SwitchMaintenenceHistoryAmendment;
import com.scr.repository.SwitchMaintenenceHistoryAmendmentRepository;

@Service
public class SwitchMaintenenceHistoryAmendmentService {
	
	@Autowired
	private SwitchMaintenenceHistoryAmendmentRepository switchMaintenenceHistoryAmendmentRepository;

	public Optional<SwitchMaintenenceHistoryAmendment> findById(Long id) {
		return switchMaintenenceHistoryAmendmentRepository.findById(id);
	}

	public void save(SwitchMaintenenceHistoryAmendment switchMaintenenceHistoryAmendment) {
		switchMaintenenceHistoryAmendmentRepository.save(switchMaintenenceHistoryAmendment);
	}

	public Optional<SwitchMaintenenceHistoryAmendment> findBySeqId(String switchId) {
		return switchMaintenenceHistoryAmendmentRepository.findBySeqId(switchId);
	}

}
