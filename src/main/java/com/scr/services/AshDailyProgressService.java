package com.scr.services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.scr.repository.AssetMonthlyTargetRepository;
import com.scr.repository.FacilityRepository;
import com.scr.util.Helper;

@Service
public class AshDailyProgressService {

	static Logger logger = LogManager.getLogger(AshDailyProgressService.class);

	@Autowired
	private AshDailyProgressRepository ashDailyProgressRepository;
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	@Autowired
	private AssetMonthlyTargetRepository assetMonthlyTargetRepository;

	public List<AshDailyProgressResponse> getAshDailyPrgress(Date fromDate, Long depotId) {
		// TODO Auto-generated method stub
		logger.info("**** in service preparing data***");
		Optional<Facility> facility = facilityRepository.findById(depotId);
		Date financialDate = Helper.getFinancialDate(fromDate);
		Date monthStartDate = Helper.getMonthStartDate(fromDate);
		String financialYear = Helper.getFinancialYear(fromDate);
		String[] monthlyProgress = ashDailyProgressRepository.monthSumBasedOnFacilityAndDateBetween(facility.get(),monthStartDate,fromDate).split(",");
		String[] cumProgress = ashDailyProgressRepository.findBySumBasedOnFacilityAndDateBetween(facility.get(),financialDate,fromDate).split(",");
		List<AshDailyProgressResponse> ashDailyProgressList = new ArrayList();
		Field[] fields= AshDailyProgress.class.getDeclaredFields();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int targetMonth = cal.get(Calendar.MONTH);
		for (Field field : fields) {
			AshDailyProgressResponse ashDailyProgressResponse = new AshDailyProgressResponse();
			String assetTypeAndSchCode = null;
			String assetType = null;
			String schCode = null;
			ashDailyProgressResponse.setDate(fromDate);
			ashDailyProgressResponse.setDepotId(depotId);
			if ("int".equals(field.getType().getSimpleName())) {
				if (field.getName().toUpperCase().contains("AOH")) {
					schCode = "AOH";
					assetType = field.getName().toUpperCase().substring(0,field.getName().length()-3) ;
					assetTypeAndSchCode = assetType +"-AOH";
				}else if (field.getName().toUpperCase().contains("POH")) {
					schCode = "POH";
					assetType = field.getName().toUpperCase().substring(0 ,field.getName().length()- 3);
					assetTypeAndSchCode = assetType +"-POH";
				}
				ashDailyProgressResponse.setColumnName(assetTypeAndSchCode);
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
						}else if ("SI-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getSiAoh());
						}else if ("SM-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getSmAoh());
						}else if ("TURNOUT-AOH".equals(ashDailyProgressResponse.getColumnName())) {
							ashDailyProgressResponse.setDailyProgress(ashDailyProgress.get().getTurnoutAoh());
						}
					}
				}
				if("ATD-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[0]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[0]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("ATD-POH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[1]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[1]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("CROSSOVER-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[2]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[2]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("GANTRY-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[3]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[3]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("MCL-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[4]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[4]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("MCL-POH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[5]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[5]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("OVERLAP-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[6]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[6]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("PTFE-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[7]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[7]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("SCL-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[8]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[8]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("SCL-POH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[9]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[9]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("SI-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[10]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[10]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("SM-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[11]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[11]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}else if ("TURNOUT-AOH".equals(ashDailyProgressResponse.getColumnName())) {
					ashDailyProgressResponse.setCumProgress(cumProgress[12]);
					ashDailyProgressResponse.setMonthlyProgress(monthlyProgress[12]);
					this.prepareAssetMonthlyTargetsData(ashDailyProgressResponse,assetType,schCode,facility.get().getFacilityId(),financialYear ,targetMonth);
				}
				ashDailyProgressList.add(ashDailyProgressResponse);
				
			}
			
		}
		return ashDailyProgressList;
	}

	private void prepareAssetMonthlyTargetsData(AshDailyProgressResponse ashDailyProgressResponse, String assetType,
			String schCode, String facilityId, String financialYear, int targetMonth) {
		logger.info("preparing data asset type --" +assetType +"schCode--"+schCode+"facility--"+facilityId+"year--"+financialYear+"targetmonth--"+targetMonth);
			String totalTarget = assetMonthlyTargetRepository.cumTargetBasedOnAssetTypeScheduleCodeFacilityIdYear(assetType,schCode,facilityId,financialYear);
			String monthTotal = assetMonthlyTargetRepository.cumMonthTargetBasedOnAssetTypeScheduleCodeFacilityIdYear(assetType,schCode,facilityId,financialYear);
			ashDailyProgressResponse.setCumTarget(totalTarget);
			if (monthTotal != null) {
				ashDailyProgressResponse.setMonthlyTarget(monthTotal.split(",")[targetMonth]);
			}else
				ashDailyProgressResponse.setMonthlyTarget("0");
				
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
