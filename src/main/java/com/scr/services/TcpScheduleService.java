package com.scr.services;

import java.util.Date;
import java.util.List;
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

	public List<TcpSchedule> findByFacilityId(Long facilityId) {
		return tcpScheduleRepository.findByFacilityId(facilityId);
	}

	public List<TcpSchedule> findByFacilityIdAndDateTimeGreaterThanAndDateTimeLessThan(Facility facility, Date fromDate,
			Date thruDate) {
		return tcpScheduleRepository.findByFacilityIdAndDateTimeGreaterThanAndDateTimeLessThan(facility,fromDate,thruDate);
	}

}
