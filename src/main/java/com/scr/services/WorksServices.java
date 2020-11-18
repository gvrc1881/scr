package com.scr.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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
import com.scr.model.WPASectionPopulation;
import com.scr.model.WPASectionTargets;
import com.scr.model.WorkGroup;
import com.scr.model.WorkPhaseActivity;
import com.scr.model.WorkPhases;
import com.scr.model.Works;
import com.scr.repository.WPADailyProgressRepository;
import com.scr.repository.WPASectionPopulationRepository;
import com.scr.repository.WPASectionTargetsRepository;
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
	
	@Autowired
	private WPASectionPopulationRepository wpaSectionPopulationRepository;
	
	@Autowired
	private WPASectionTargetsRepository wpaSectionTargetsRepository;

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

	public void saveWPAndWPA(CopyWPAndWPA copyWPAndWPA) {
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
					WorkPhaseActivity WPA = workPhaseActivityRepository.save(workPhaseActivity);
					List<WorkGroup> workGroupsList = workGroupRepository.findByWorkId(copyWPAndWPA.getWork());
					for (WorkGroup workGroup : workGroupsList) {
						WPASectionPopulation wpaSectionPopulation = new WPASectionPopulation();
						wpaSectionPopulation.setPopulation("0");
						wpaSectionPopulation.setWorkGroupId(workGroup);
						wpaSectionPopulation.setWorkPhaseActivityId(WPA);
						wpaSectionPopulationRepository.save(wpaSectionPopulation);
						Date expectedComp = copyWPAndWPA.getWork().getExpectedCompletion();
						
						LocalDate currentDate = LocalDate.now();
						int currentYear = currentDate.getYear();
						LocalDate expectedLocalDate = expectedComp.toInstant()
					      .atZone(ZoneId.systemDefault()).toLocalDate();
						int expectedYear = expectedLocalDate.getYear();
						WPASectionTargets wpaSectionTargets = new WPASectionTargets();
						Double defaultValue = new Double(0);
						
						if(currentYear < expectedYear) {
							log.info("*** in if condition current year "+currentYear+"*** expected year ***"+expectedYear);
							int diffYear = expectedYear - currentYear ;
							int nextYear = currentYear;
							for (int i = 1; i <= expectedYear - currentYear ; i++) {
								// checking more than one year then inserting record for (currentyear + 1)
								if (diffYear > 1) {
									WPASectionTargets yearFullTargets = new WPASectionTargets();
									for (int k = 1 ; k <= 12 ; k++) {
										yearFullTargets = prepareWPASectionTargets(k,yearFullTargets,defaultValue);
									}
									nextYear = nextYear + 1;
									diffYear = diffYear - 1 ;
									yearFullTargets.setYear(nextYear);
									yearFullTargets.setWorkGroupId(workGroup);
									yearFullTargets.setWorkPhaseActivityId(WPA);
									wpaSectionTargetsRepository.save(yearFullTargets);
								} else {
										for (int j = currentDate.getMonthValue() ; j <= 12 ; j++) {
											wpaSectionTargets = prepareWPASectionTargets(j,wpaSectionTargets,defaultValue);
										}
										wpaSectionTargets.setYear(currentYear);
										wpaSectionTargets.setWorkGroupId(workGroup);
										wpaSectionTargets.setWorkPhaseActivityId(WPA);
										wpaSectionTargetsRepository.save(wpaSectionTargets);
										WPASectionTargets wpaSectionTargets1 = new WPASectionTargets();
										nextYear = nextYear + 1;
										for (int k = 1 ; k <= expectedLocalDate.getMonthValue() ; k++) {
											wpaSectionTargets1 = prepareWPASectionTargets(k,wpaSectionTargets1,defaultValue);
										}
										wpaSectionTargets1.setYear(nextYear);
										wpaSectionTargets1.setWorkGroupId(workGroup);
										wpaSectionTargets1.setWorkPhaseActivityId(WPA);
										wpaSectionTargetsRepository.save(wpaSectionTargets1);
									}
								}
						} else if (currentYear == expectedYear ) {
							log.info("*** current moonth**"+currentDate.getMonthValue()+"*** expected month **"+expectedLocalDate.getMonthValue());
							for (int i = currentDate.getMonthValue() ; i <= expectedLocalDate.getMonthValue() ; i++) {
								
								wpaSectionTargets = prepareWPASectionTargets(i,wpaSectionTargets,defaultValue);
							}
							wpaSectionTargets.setYear(currentYear);
							wpaSectionTargets.setWorkGroupId(workGroup);
							wpaSectionTargets.setWorkPhaseActivityId(WPA);
							wpaSectionTargetsRepository.save(wpaSectionTargets);
						}
					}
				}
			}
		}
	}

	private WPASectionTargets prepareWPASectionTargets(int k, WPASectionTargets wpaSectionTargets, Double defaultValue) {
		switch (k) {
			case 1:
				wpaSectionTargets.setJan(defaultValue);
				break;
			case 2:
				wpaSectionTargets.setFeb(defaultValue);
				break;
			case 3:
				wpaSectionTargets.setMar(defaultValue);
				break;
			case 4:
				wpaSectionTargets.setApr(defaultValue);
				break;
			case 5:
				wpaSectionTargets.setMay(defaultValue);
				break;
			case 6:
				wpaSectionTargets.setJun(defaultValue);
				break;
			case 7:
				wpaSectionTargets.setJul(defaultValue);
				break;
			case 8:
				wpaSectionTargets.setAug(defaultValue);
				break;
			case 9:
				wpaSectionTargets.setSep(defaultValue);
				break;
			case 10:
				wpaSectionTargets.setOct(defaultValue);
				break;
			case 11:
				wpaSectionTargets.setNov(defaultValue);
				break;
			case 12:
				wpaSectionTargets.setDec(defaultValue);
				break;
			default:
				break;
		}
		return wpaSectionTargets;
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
