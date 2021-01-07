package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.TcpSchedule;
import com.scr.model.ThermovisionCheckPoints;
import com.scr.model.ThermovisionMeasures;

@Repository
public interface ThermovisionMeasuresRepository extends JpaRepository<ThermovisionMeasures, Long>{

	Optional<ThermovisionMeasures> findByTcpIdAndTcpScheduleId(ThermovisionCheckPoints thermovisionCheckPoints,
			TcpSchedule returnTcpSchedule);

}
