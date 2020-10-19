package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scr.model.Schedule;
import com.scr.model.TssFeederMaster;

@Repository

public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
	
	@Query("FROM Schedule ORDER BY scheduleCode ASC")
	List<Schedule> findAllOrderByscheduleCodeAsc();
	
	Boolean existsByScheduleCode(String scheduleCode);
	
	Optional<Schedule> findByScheduleCode(String scheduleCode);
	
}