package com.scr.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.GantryMasterData;
import com.scr.model.Schedule;
import com.scr.model.TssFeederMaster;
import com.scr.repository.ScheduleRepository;
import com.scr.util.Constants;


@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	
	public List<Schedule> findAllOrderByscheduleCodeAsc() {
		// TODO Auto-generated method stub
		return scheduleRepository.findAllOrderByscheduleCodeAsc();
	}
	public Schedule save(Schedule scheduleData) {
		// TODO Auto-generated method stub
		return scheduleRepository.save(scheduleData);
	}

	public Optional<Schedule> findAssetScheduleById(Long id) {
		// TODO Auto-generated method stub
		return scheduleRepository.findById(id);
	}

	public void deleteAssetSchedule(Long id) {
		// TODO Auto-generated method stub
		scheduleRepository.deleteById(id);
	}

	public Boolean existsByScheduleCode(String scheduleCode) {
		// TODO Auto-generated method stub
		return scheduleRepository.existsByScheduleCode(scheduleCode);
	}
	
	public Optional<Schedule> findByScheduleCode(String scheduleCode) {
		// TODO Auto-generated method stub
		return scheduleRepository.findByScheduleCode(scheduleCode);
	}

}
