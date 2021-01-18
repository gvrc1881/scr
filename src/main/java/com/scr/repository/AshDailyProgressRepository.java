package com.scr.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.AshDailyProgress;
import com.scr.model.Facility;

@Repository
public interface AshDailyProgressRepository extends JpaRepository<AshDailyProgress, Long>{

	Optional<AshDailyProgress> findByDateAndFacility(Date date, Facility facility);

}
