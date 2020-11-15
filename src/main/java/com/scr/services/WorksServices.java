package com.scr.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.mapper.WorkMapper;
import com.scr.message.request.CopyWPAndWPA;
import com.scr.message.response.WPADailyProgressResponse;
import com.scr.model.StandardPhaseActivity;
import com.scr.model.StandardPhases;
import com.scr.model.WPADailyProgress;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
import com.scr.model.Works;
import com.scr.repository.WPADailyProgressRepository;
import com.scr.repository.WorkGroupRepository;
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
	
	@Autowired
	private WorkGroupRepository workGroupRepository;
	
	@Autowired
	private WorkMapper workMapper;
	
	@Autowired
	private WPADailyProgressRepository wpaDailyProgressRepository;

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
					workPhaseActivity.setUom(standardPhaseActivity.getUom());
					workPhaseActivityRepository.save(workPhaseActivity);
				}
			}
		}
	}

	public List<WorkGroup> getWorkGroupsBasedOnWork(Works works) {
		return workGroupRepository.findByWorkId(works);
	}

	public List<WorkPhases> getWorkPhasesBasedOnWork(Works works) {
		return workPhaseRepository.findByWorkId(works);
	}

	public List<WPADailyProgressResponse> getWPADailyProgressBasedOnGroupActivity(Integer workId, Integer workGroupId,
			Integer workPhaseId, Date date) {
		List<WPADailyProgressResponse> wpaDailyProgresses = null;
		Optional<WorkGroup> workGroup = workGroupRepository.findById(workGroupId);
		Optional<WorkPhases> workPhase = workPhaseRepository.findById(workPhaseId);
		if (workGroup.isPresent() && workPhase.isPresent()) {
			List<WorkGroup> workGroups = workGroupRepository.findByCode(workGroup.get().getCode());
			List<WorkPhaseActivity> workPhaseActivities  = workPhaseActivityRepository.findByWorkPhaseId(workPhase.get());
			wpaDailyProgresses = workMapper.prepareWPADailyProgressData(workId,workGroups,workPhaseActivities,date);
		}
		//log.info("*** in service ***"+wpaDailyProgresses.size());
		return wpaDailyProgresses;
	}

	public void saveWPADailyProgress(List<WPADailyProgress> wpaDailyProgresses) {
		
		for (WPADailyProgress wpaDailyProgress : wpaDailyProgresses) {
			//log.info("*** goup id**"+wpaDailyProgress.getWorkGroupId().getId()+"*** activity id ***"+wpaDailyProgress.getWorkPhaseActivityId().getId()+"*** date ****"+wpaDailyProgress.getDate());
			Optional<WPADailyProgress> wpaDProgress = wpaDailyProgressRepository.findByWorkGroupIdAndWorkPhaseActivityIdAndDate(wpaDailyProgress.getWorkGroupId(),wpaDailyProgress.getWorkPhaseActivityId(),wpaDailyProgress.getDate());
			if (wpaDProgress.isPresent()) {
				WPADailyProgress updateWPADProgress = wpaDProgress.get();
				updateWPADProgress.setPerformedCount(wpaDailyProgress.getPerformedCount());
				wpaDailyProgressRepository.save(updateWPADProgress);
			} else {
				if(wpaDailyProgress.getPerformedCount() > 0 ) {
					wpaDailyProgressRepository.save(wpaDailyProgress);
				}
			}
			//log.info("*** to string ****"+wpaDailyProgress.toString());
		}
		
	}


}
