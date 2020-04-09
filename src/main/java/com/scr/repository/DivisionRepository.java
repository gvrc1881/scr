package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Division;
import com.scr.model.Zone;



public interface DivisionRepository extends JpaRepository<Division, Long>{
	List<Division> findByZoneId(Zone zoneId);

}
