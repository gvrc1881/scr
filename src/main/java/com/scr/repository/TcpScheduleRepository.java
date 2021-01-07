package com.scr.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;
import com.scr.model.TcpSchedule;

@Repository
public interface TcpScheduleRepository extends JpaRepository<TcpSchedule, Long>{

	Optional<TcpSchedule> findByFacilityIdAndDateTime(Long facId, Date tcpsDate);

}
