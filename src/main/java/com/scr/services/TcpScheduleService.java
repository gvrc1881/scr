package com.scr.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.Facility;
import com.scr.model.TcpSchedule;
import com.scr.repository.TcpScheduleRepository;

@Service
public class TcpScheduleService {
	
	@Autowired
	private TcpScheduleRepository tcpScheduleRepository;

	public Optional<TcpSchedule> findByFacilityIdAndDateTime(Long facId, Date tcpsDate) {
		return tcpScheduleRepository.findByFacilityIdAndDateTime(facId,tcpsDate);
	}

	public TcpSchedule save(TcpSchedule tcpSchedule) {
		return tcpScheduleRepository.save(tcpSchedule);
	}

}
