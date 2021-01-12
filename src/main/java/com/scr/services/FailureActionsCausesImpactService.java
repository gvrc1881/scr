
package com.scr.services;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Failure;
import com.scr.model.FailureActionsCausesImpact;
import com.scr.model.Make;
import com.scr.repository.FailureActionsCausesImpactRepository;
import com.scr.util.Constants;



@Service
public class FailureActionsCausesImpactService {
	

	
	@Autowired
	private FailureActionsCausesImpactRepository failureActionsRepository;
	
	public List<FailureActionsCausesImpact>findAll() {
		return failureActionsRepository.findAll();
	}
	
	public FailureActionsCausesImpact saveActions(FailureActionsCausesImpact failureRequest) {
		// TODO Auto-generated method stub
		return failureActionsRepository.save(failureRequest);
	}

	
	public void deleteActionById(Long id) {
		// TODO Auto-generated method stub
		failureActionsRepository.deleteById(id);
	}
	public Optional<FailureActionsCausesImpact> findActionsById(Long id) {
		return failureActionsRepository.findById(id);
	}

	public List<FailureActionsCausesImpact> findByFailureSeqId(Long failureSeqId) {
		
		return failureActionsRepository.findByFailureSeqId(failureSeqId);
	}

	

}
