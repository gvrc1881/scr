package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.request.CopyWPAndWPA;
import com.scr.model.StandardPhaseActivity;
import com.scr.model.StandardPhases;
import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
import com.scr.model.Works;
import com.scr.repository.WorkPhaseActivityRepository;
import com.scr.repository.WorkPhaseRepository;
import com.scr.repository.WorksRepository;

@Service
public class WorksServices {

	private static Logger log = LogManager.getLogger(WorksServices.class);

	@Autowired
	private WorksRepository worksRepository;
	
	@Autowired
	private WorkPhaseRepository workPhaseRepository;

	@Autowired
	private WorkPhaseActivityRepository workPhaseActivityRepository;

	public List<Works> findAll() {
		// TODO Auto-generated method stub
		return worksRepository.findAll();
	}

	public Works save(Works work) {
		// TODO Auto-generated method stub
		return worksRepository.save(work);
	}

	public Optional<Works> findById(Integer id) {
		// TODO Auto-generated method stub
		return worksRepository.findById(id);
	}

	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		worksRepository.deleteById(id);
		
	}

	public Boolean existsByWorkName(String workName) {
		// TODO Auto-generated method stub
		return worksRepository.existsByWorkName(workName);
	}

	public Optional<Works> findByWorkName(String workName) {
		// TODO Auto-generated method stub
		return worksRepository.findByWorkName(workName);
	}

	public void saveDrives(CopyWPAndWPA copyWPAndWPA) {
		List<StandardPhases> standardPhases = copyWPAndWPA.getStandardPhases();
		List<StandardPhaseActivity> standardPhaseActivities = copyWPAndWPA.getStandardPhaseActivities();
		for (StandardPhases standardPhase : standardPhases) {
			WorkPhases workPhase = new WorkPhases();
			workPhase.setPhaseName(standardPhase.getName());
			workPhase.setWorkId(copyWPAndWPA.getWork());
			workPhase = workPhaseRepository.save(workPhase);
			for (StandardPhaseActivity standardPhaseActivity : standardPhaseActivities) {
				if(standardPhase.getId() == standardPhaseActivity.getStandardPhaseId().getId()) {
					WorkPhaseActivity workPhaseActivity = new WorkPhaseActivity();
					workPhaseActivity.setWorkPhaseId(workPhase);
					workPhaseActivity.setAssetType(standardPhaseActivity.getAssetType());
					workPhaseActivity.setDepotType(standardPhaseActivity.getDepotType());
					workPhaseActivity.setIsCheckList(standardPhaseActivity.getIsCheckList());
					workPhaseActivity.setIsObjectIdRequired(standardPhaseActivity.getIsObjectIdRequired());
					workPhaseActivity.setName(standardPhaseActivity.getName());
					workPhaseActivityRepository.save(workPhaseActivity);
				}
			}
		}
	}

}
