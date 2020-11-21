package com.scr.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
import com.scr.model.Works;
import com.scr.repository.WorkPhaseActivityRepository;

@Service

public class WorkPhaseActivityService {
	
	private static Logger log = LogManager.getLogger(WorksServices.class);

	@Autowired
	private WorkPhaseActivityRepository workPhaseActivityRepository;
	
	
	public List<WorkPhaseActivity> getWorkPhaseActivityBasedOnWorkPhaseId(WorkPhases workPhases) {
		// TODO Auto-generated method stub
		return workPhaseActivityRepository.findByWorkPhaseId(workPhases);
	}


		public WorkPhaseActivity save(WorkPhaseActivity workPhaseActivity) {
		
		return workPhaseActivityRepository.save(workPhaseActivity);
	}
		
		public void updateWorkPhaseActivity(List<WorkPhaseActivity> workPhaseActivityData) {
			
			for (WorkPhaseActivity workPhaseActivity : workPhaseActivityData) {
				
				Optional<WorkPhaseActivity> wPAData = workPhaseActivityRepository.findByWorkPhaseIdAndName(workPhaseActivity.getWorkPhaseId(),workPhaseActivity.getName());
				if (wPAData.isPresent()) {
					
					WorkPhaseActivity updateWorkActivityData = wPAData.get();
			
					      
					updateWorkActivityData.setWorkPhaseId(workPhaseActivity.getWorkPhaseId());
					updateWorkActivityData.setName(workPhaseActivity.getName());
					updateWorkActivityData.setDescription(workPhaseActivity.getDescription());
					updateWorkActivityData.setSequence(workPhaseActivity.getSequence());
					updateWorkActivityData.setDependencyToStart(workPhaseActivity.getDependencyToStart());
					updateWorkActivityData.setUom(workPhaseActivity.getUom());
					updateWorkActivityData.setIsCheckList(workPhaseActivity.getIsCheckList());
					updateWorkActivityData.setIsObjectIdRequired(workPhaseActivity.getIsObjectIdRequired());
					updateWorkActivityData.setDepotType(workPhaseActivity.getDepotType());				
					updateWorkActivityData.setAssetType(workPhaseActivity.getAssetType());
									
					workPhaseActivityRepository.save(updateWorkActivityData);
				} /*else {
					 workPhaseRepository.save(workPhasesData);
				}*/
				
			} 
			
			
		}
		
		public void deleteById(Integer id) {
			
			workPhaseActivityRepository.deleteById(id);
		}

		public Boolean existsByWorkPhaseIdAndName(WorkPhases workPhase, String name) {
			// TODO Auto-generated method stub
			return  workPhaseActivityRepository.existsByWorkPhaseIdAndName(workPhase,name);
		}
		
		public Boolean existsByWorkPhaseIdAndSequence(WorkPhases workPhase, BigDecimal sequence) {
			// TODO Auto-generated method stub
			return  workPhaseActivityRepository.existsByWorkPhaseIdAndSequence(workPhase,sequence);
		}

}
