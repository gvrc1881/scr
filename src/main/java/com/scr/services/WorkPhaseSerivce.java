package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.WPADailyProgress;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhases;
import com.scr.model.Works;
import com.scr.repository.WorkPhaseRepository;


@Service
public class WorkPhaseSerivce {
	
	private static Logger log = LogManager.getLogger(WorksServices.class);

	@Autowired
	private WorkPhaseRepository workPhaseRepository;
	
	
	public List<WorkPhases> getWorkPhasesBasedOnWorkId(Works works) {
		return workPhaseRepository.findByWorkId(works);
	}


	public WorkPhases save(WorkPhases workPhases) {
		
		return workPhaseRepository.save(workPhases);
	}
	
	public void updateWorkPhases(List<WorkPhases> workPhasesData) {
		
		for (WorkPhases workPhase : workPhasesData) {
			
			Optional<WorkPhases> wPData = workPhaseRepository.findByWorkIdAndPhaseName(workPhase.getWorkId(),workPhase.getPhaseName());
			if (wPData.isPresent()) {
				WorkPhases updateWorkPhaseData = wPData.get();
				updateWorkPhaseData.setWorkId(workPhase.getWorkId());
				updateWorkPhaseData.setPhaseName(workPhase.getPhaseName());
				updateWorkPhaseData.setDescription(workPhase.getDescription());
				updateWorkPhaseData.setSequence(workPhase.getSequence());
				updateWorkPhaseData.setDependencyToStart(workPhase.getDependencyToStart());
				updateWorkPhaseData.setWeightage(workPhase.getWeightage());
				updateWorkPhaseData.setStatus(workPhase.getStatus());
				updateWorkPhaseData.setPlannedStartDate(workPhase.getPlannedStartDate());
				updateWorkPhaseData.setTargetCompletionDate(workPhase.getTargetCompletionDate());				
				updateWorkPhaseData.setCommenceDate(workPhase.getCommenceDate());
				updateWorkPhaseData.setCompletionDate(workPhase.getCompletionDate());
				
				workPhaseRepository.save(updateWorkPhaseData);
			} /*else {
				 workPhaseRepository.save(workPhasesData);
			}*/
			
		} 
		
		
	}
	
	public List<WorkPhases> findAll() {
		// TODO Auto-generated method stub
		return workPhaseRepository.findAll();
	}


	public Optional<WorkPhases> findById(Integer workPhaseId) {
		// TODO Auto-generated method stub
		return workPhaseRepository.findById(workPhaseId);
	}

	public Boolean existsByWorkIdAndPhaseName(Works work, String phaseName) {
		// TODO Auto-generated method stub
		return  workPhaseRepository.existsByWorkIdAndPhaseName(work,phaseName);
	}
	
	public Boolean existByWorkIdAndSequence(Works work, Integer sequence) {
		// TODO Auto-generated method stub
		return  workPhaseRepository.existByWorkIdAndSequence(work,sequence);
	}

}
