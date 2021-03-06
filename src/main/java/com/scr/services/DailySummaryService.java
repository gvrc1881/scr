package com.scr.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.AssetScheduleAssoc;
import com.scr.model.DailyProgressSummery;
import com.scr.repository.DailySummaryRepository;

@Service
public class DailySummaryService {
	static Logger log = LogManager.getLogger(DailySummaryService.class);

	@Autowired
	private DailySummaryRepository dailySummaryRepository;
	
	public List<DailyProgressSummery> findAll() {
		// TODO Auto-generated method stub
		return dailySummaryRepository.findAll();
	}

	public void save(DailyProgressSummery dailyProgressSummery) {
		// TODO Auto-generated method stub
		dailySummaryRepository.save(dailyProgressSummery);
	}

	public Optional<DailyProgressSummery> findDailySummaryById(Long id) {
		// TODO Auto-generated method stub
		return dailySummaryRepository.findById(id);
	}

	public void deleteDailySummaryById(Long id) {
		// TODO Auto-generated method stub
		dailySummaryRepository.deleteById(id);
	}
	public Boolean existsByFacilityIdAndCreatedDate(String facilityId, Timestamp createdDate) {
		// TODO Auto-generated method stub
		return dailySummaryRepository.existsByFacilityIdAndCreatedDate(facilityId,createdDate);
	}
	public Optional<DailyProgressSummery> findByFacilityIdAndCreatedDate(String facilityId,Timestamp createdDate) {
		// TODO Auto-generated method stub
		return dailySummaryRepository.findByFacilityIdAndCreatedDate(facilityId,createdDate);
	}
	
}
