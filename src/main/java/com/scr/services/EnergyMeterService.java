package com.scr.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.controller.EnergyMeterController;
import com.scr.model.EnergyMeter;
import com.scr.model.Facility;
import com.scr.model.TssFeederMaster;
import com.scr.model.UserDefualtFacConsIndEtc;
import com.scr.repository.EnergyMeterRepository;
import com.scr.repository.FacilityRepository;
import com.scr.repository.TssFeederMasterRepository;
import com.scr.repository.UserDefualtFacConsIndEtcRepository;

@Service
public class EnergyMeterService {
	
	private static Logger log = Logger.getLogger(EnergyMeterService.class);
	
	@Autowired
	private EnergyMeterRepository energyMeterRepository;
	
	@Autowired
	private UserDefualtFacConsIndEtcRepository userDefualtFacConsIndEtcRepository;
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	@Autowired
	private TssFeederMasterRepository tssFeederMasterRepository;

	public List<EnergyMeter> findAll(String userName) {
		Optional<UserDefualtFacConsIndEtc> userDefault = userDefualtFacConsIndEtcRepository.findByUserLoginId(userName);
		List<String> divisions = new ArrayList<String>();
		List<EnergyMeter> eneMeterList = new ArrayList<>();
		if (userDefault.isPresent()) {
			Optional<Facility> facilityObj = facilityRepository.findByFacilityId(userDefault.get().getFacilityId());
			if (facilityObj.isPresent()) {
				if ("ZONE".equals(facilityObj.get().getDepotType())) {
					List<Facility> divisionList = facilityRepository.findByZoneAndDepotType(facilityObj.get().getFacilityName(),"DIV");
					for (Facility facility : divisionList) {
						divisions.add(facility.getDivision().toUpperCase());
					}
				}else if("DIV".equals(facilityObj.get().getDepotType())) {
					divisions.add(facilityObj.get().getDivision().toUpperCase());
				}
				List<EnergyMeter> eneList = energyMeterRepository.findByDataDivIn(divisions);
				for (EnergyMeter energyMeter : eneList) {
					Optional<TssFeederMaster> tssFeeder = tssFeederMasterRepository.findByFeederId(energyMeter.getFeederId());
					if (tssFeeder.isPresent()) {
						energyMeter.setFeederId(tssFeeder.get().getFeederName());
						eneMeterList.add(energyMeter);
					}
				}
			}
		}
		log.info("*** meters size ***"+eneMeterList.size());
		return eneMeterList;
	}

	public EnergyMeter save(EnergyMeter energyMeter) {
		// TODO Auto-generated method stub
		return energyMeterRepository.save(energyMeter);
	}

	public Optional<EnergyMeter> findById(Long id) {
		// TODO Auto-generated method stub
		return energyMeterRepository.findById(id);
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		energyMeterRepository.deleteById(id);
	}

	public Boolean existsByFeederAndStartDate(String feeder, Timestamp startDate) {
		// TODO Auto-generated method stub
		return energyMeterRepository.existsByFeederAndStartDate(feeder,startDate);
	}
	
	public Boolean existsByFeederAndEndDateIsNull(String feeder) {
		return energyMeterRepository.existsByFeederAndEndDateIsNull(feeder);
	}

}
