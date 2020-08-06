package com.scr.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Schedule;

import com.scr.repository.ScheduleRepository;
import com.scr.util.Constants;


@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	
	public List<Schedule> findAll() {
		// TODO Auto-generated method stub
		return scheduleRepository.findAll();
	}

}
