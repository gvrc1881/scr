package com.scr.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.response.AshDailyProgressResponse;
import com.scr.model.AshDailyProgress;
import com.scr.model.Facility;
import com.scr.repository.AshDailyProgressRepository;
import com.scr.repository.FacilityRepository;

@Service
public class AshDailyProgressService {

	static Logger logger = LogManager.getLogger(AshDailyProgressService.class);

	@Autowired
	private AshDailyProgressRepository ashDailyProgressRepository;
	
	@Autowired
	private FacilityRepository facilityRepository;

	public List<AshDailyProgressResponse> getAshDailyPrgress(Date fromDate, Long depotId) {
		// TODO Auto-generated method stub
		logger.info("**** in service preparing data***");
		List<AshDailyProgressResponse> ashDailyProgressList = new ArrayList();
		Field[] fields= AshDailyProgress.class.getDeclaredFields();
		for (Field field : fields) {
			AshDailyProgressResponse ashDailyProgressResponse = new AshDailyProgressResponse();
			Optional<Facility> facility = facilityRepository.findById(depotId);
			String assetType = null;
			ashDailyProgressResponse.setDate(fromDate);
			ashDailyProgressResponse.setDepotId(depotId);
			if ("int".equals(field.getType().getSimpleName())) {
				if (field.getName().toUpperCase().contains("AOH")) {
					assetType = field.getName().toUpperCase().substring(0,field.getName().length()-3) ;
					assetType = assetType +"-AOH";
				}else if (field.getName().toUpperCase().contains("POH")) {
					assetType = field.getName().toUpperCase().substring(0 ,field.getName().length()- 3);
					assetType = assetType +"-POH";
				}
				ashDailyProgressResponse.setColumnName(assetType);
				if (facility.isPresent()) {
					Optional<AshDailyProgress> ashDailyProgress = ashDailyProgressRepository.findByDateAndFacility(fromDate, facility.get());
					if (ashDailyProgress.isPresent()) {
						if("ATD-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getAtdAoh());
						}else if ("ATD-POH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getAtdPoh());
						}else if ("CROSSOVER-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getCrossoverAoh());
						}else if ("GANTRY-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getGantryAoh());
						}else if ("MCL-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getMclAoh());
						}else if ("MCL-POH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getMclPoh());
						}else if ("OVERLAP-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getOverlapAoh());
						}else if ("PTFE-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getPtfeAoh());
						}else if ("SCL-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getSclAoh());
						}else if ("SCL-POH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getSclPoh());
						}else if ("TURNOUT-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getTurnoutAoh());
						}else if ("SI-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getSiAoh());
						}else if ("SM-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getSmAoh());
						}
					}
				}
				
				ashDailyProgressList.add(ashDailyProgressResponse);
				
			}
			
		}
		return ashDailyProgressList;
	}

	public void saveAshDailyProgress(List<AshDailyProgressResponse> ashDailyProgressResponses) {
		// TODO Auto-generated method stub
		AshDailyProgress dailyProgress = new AshDailyProgress();
		for (AshDailyProgressResponse ashDailyProgressResponse : ashDailyProgressResponses) {
			Optional<Facility> facility = facilityRepository.findById(ashDailyProgressResponse.getDepotId());
			if (facility.isPresent()) {
				Optional<AshDailyProgress> ashDailyProgress = ashDailyProgressRepository.findByDateAndFacility(ashDailyProgressResponse.getDate(),facility.get());
				if (ashDailyProgress.isPresent()) {
					dailyProgress.setId(ashDailyProgress.get().getId());
				}
					if("ATD-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setAtdAoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("ATD-POH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setAtdPoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("CROSSOVER-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setCrossoverAoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("GANTRY-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setGantryAoh((ashDailyProgressResponse.getDailyProgress()));
					}else if ("MCL-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setMclAoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("MCL-POH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setMclPoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("OVERLAP-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setOverlapAoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("PTFE-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setPtfeAoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("SCL-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setSclAoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("SCL-POH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setSclPoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("TURNOUT-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setTurnoutAoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("SI-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setSiAoh(ashDailyProgressResponse.getDailyProgress());
					}else if ("SM-AOH".equals(ashDailyProgressResponse.getColumnName())) {
						dailyProgress.setSmAoh(ashDailyProgressResponse.getDailyProgress());
					}
					dailyProgress.setDate(ashDailyProgressResponse.getDate());
					dailyProgress.setFacility(facility.get());
			}
		}
		ashDailyProgressRepository.save(dailyProgress);
	}
}
