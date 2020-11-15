package com.scr.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.message.response.WPADailyProgressResponse;
import com.scr.model.WPADailyProgress;
import com.scr.model.WPASectionPopulation;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
import com.scr.repository.WPADailyProgressRepository;
import com.scr.repository.WPASectionPopulationRepository;
import com.scr.repository.WorkGroupRepository;
import com.scr.services.DrivesService;

@Component
public class WorkMapper {
	
	static Logger logger = LogManager.getLogger(WorkMapper.class);
	
	@Autowired
	private WPADailyProgressRepository wpaDailyProgressRepository;
	
	@Autowired
	private WPADailyProgressResponse wpaDailyProgressResponse;
	
	@Autowired
	private WPASectionPopulationRepository wpaSectionPopulationRepository;
	
	@Autowired
	private WorkGroupRepository workGroupRepository;

	public  List<WPADailyProgressResponse> prepareWPADailyProgressData(Integer workId, List<WorkGroup> workGroups,
			List<WorkPhaseActivity> workPhaseActivities, Date date) {
		List<WPADailyProgressResponse> wpaDailyProgressResponseList = new ArrayList<WPADailyProgressResponse>();
		for (WorkGroup workGroup : workGroups) {
			for (WorkPhaseActivity workPhaseActivity : workPhaseActivities) {
				WPADailyProgressResponse wpaDailyProgressResponse = new WPADailyProgressResponse();
				Optional<WPASectionPopulation> wpaSectionPopulation = wpaSectionPopulationRepository.findByWorkGroupIdAndWorkPhaseActivityId(workGroup,workPhaseActivity);
				if (wpaSectionPopulation.isPresent()) {
					List<WPADailyProgress> wpaDailyProgressList = wpaDailyProgressRepository.findByWorkGroupIdAndWorkPhaseActivityIdAndDateLessThan(workGroup,workPhaseActivity,date);
					if (wpaDailyProgressList.size() > 0) {
						Double alreadyDoneCount = 0D;
						for (WPADailyProgress wpaDailyProgress : wpaDailyProgressList) {
							alreadyDoneCount = alreadyDoneCount + wpaDailyProgress.getPerformedCount();
						}
						wpaDailyProgressResponse.setAlreadyDoneCount(alreadyDoneCount);
					} 
					wpaDailyProgressResponse.setPerformedCount(new Double(0));
					wpaDailyProgressResponse.setPopulation(wpaSectionPopulation.get().getPopulation());
					wpaDailyProgressResponse.setWorkGroupId(workGroup);
					wpaDailyProgressResponse.setWorkPhaseActivityId(workPhaseActivity);
					wpaDailyProgressResponse.setDate(date);
					wpaDailyProgressResponseList.add(wpaDailyProgressResponse);
				}
				
			}
		}
		logger.info("*** in mapper ***"+wpaDailyProgressResponseList.size());
		return wpaDailyProgressResponseList;
	}

}
