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
import com.scr.message.response.WPASectionTargetsResponse;
import com.scr.model.WPADailyProgress;
import com.scr.model.WPASectionPopulation;
import com.scr.model.WPASectionTargets;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
import com.scr.repository.WPADailyProgressRepository;
import com.scr.repository.WPASectionPopulationRepository;
import com.scr.repository.WPASectionTargetsRepository;
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
	
	@Autowired
	private WPASectionTargetsRepository wpaSectionTargetsRepository;
	
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
					Optional<WPADailyProgress> wpaDailyProgress = wpaDailyProgressRepository.findByWorkGroupIdAndWorkPhaseActivityIdAndDate(workGroup,workPhaseActivity,date);
					if (wpaDailyProgress.isPresent()) {
						wpaDailyProgressResponse.setPerformedCount(wpaDailyProgress.get().getPerformedCount());
						wpaDailyProgressResponse.setId(wpaDailyProgress.get().getId());
					} else {
						wpaDailyProgressResponse.setPerformedCount(new Double(0));
					}
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

	public List<WPASectionPopulation> prepareWPASectionPopulations(List<WorkGroup> workGroups,
			List<WorkPhaseActivity> workPhaseActivities) {
		List<WPASectionPopulation> wpaSectionPopulationList = new ArrayList<>();
		for (WorkGroup workGroup : workGroups) {
			for (WorkPhaseActivity workPhaseActivity : workPhaseActivities) {
				Optional<WPASectionPopulation> wpaSectionPopulation = wpaSectionPopulationRepository.findByWorkGroupIdAndWorkPhaseActivityId(workGroup, workPhaseActivity);
				if (wpaSectionPopulation.isPresent()) {
					wpaSectionPopulationList.add(wpaSectionPopulation.get());
				}
			}
		}
		return wpaSectionPopulationList;
	}

	public List<WPASectionTargetsResponse> prepareWPASectionTargets(List<WorkGroup> workGroups,
			List<WorkPhaseActivity> workPhaseActivities, Integer year) {
		List<WPASectionTargetsResponse> wpaSectionTargetsResponseList = new ArrayList<WPASectionTargetsResponse>();
		for (WorkGroup workGroup : workGroups) {
			for (WorkPhaseActivity workPhaseActivity : workPhaseActivities) {
				Optional<WPASectionPopulation> wpaSectionPopulation = wpaSectionPopulationRepository.findByWorkGroupIdAndWorkPhaseActivityId(workGroup,workPhaseActivity);
				if (wpaSectionPopulation.isPresent()) {
					Optional<WPASectionTargets> currentYearTarget =  wpaSectionTargetsRepository.findByWorkGroupIdAndWorkPhaseActivityIdAndYear(workGroup,workPhaseActivity,year);
					Integer nextYear = year + 1 ;
					Optional<WPASectionTargets> nextYearTarget =  wpaSectionTargetsRepository.findByWorkGroupIdAndWorkPhaseActivityIdAndYear(workGroup,workPhaseActivity,nextYear);
					if (currentYearTarget.isPresent()) {
						WPASectionTargetsResponse wpaSectionTargetsResponse = new WPASectionTargetsResponse();
						wpaSectionTargetsResponse.setApr(currentYearTarget.get().getApr());
						wpaSectionTargetsResponse.setAug(currentYearTarget.get().getAug());
						wpaSectionTargetsResponse.setDec(currentYearTarget.get().getDec());
						wpaSectionTargetsResponse.setId(currentYearTarget.get().getId());
						wpaSectionTargetsResponse.setJul(currentYearTarget.get().getJul());
						wpaSectionTargetsResponse.setJun(currentYearTarget.get().getJun());
						wpaSectionTargetsResponse.setMay(currentYearTarget.get().getMay());
						wpaSectionTargetsResponse.setNov(currentYearTarget.get().getNov());
						wpaSectionTargetsResponse.setOct(currentYearTarget.get().getOct());
						wpaSectionTargetsResponse.setPopulation(wpaSectionPopulation.get().getPopulation());
						wpaSectionTargetsResponse.setSep(currentYearTarget.get().getSep());
						wpaSectionTargetsResponse.setWorkGroupId(workGroup);
						wpaSectionTargetsResponse.setWorkPhaseActivityId(workPhaseActivity);
						wpaSectionTargetsResponse.setYear(currentYearTarget.get().getYear());
						wpaSectionTargetsResponse.setYearType(currentYearTarget.get().getYearType());
						if (nextYearTarget.isPresent()) {
							wpaSectionTargetsResponse.setJan(nextYearTarget.get().getJan());
							wpaSectionTargetsResponse.setFeb(nextYearTarget.get().getFeb());
							wpaSectionTargetsResponse.setMar(nextYearTarget.get().getMar());
						}
						wpaSectionTargetsResponseList.add(wpaSectionTargetsResponse);
					}
				}
			}
		}	
		return wpaSectionTargetsResponseList;
	}

}
