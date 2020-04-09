package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
	
	List<Facility> findBySubDivision(String subDivision);

	List<Facility> findByDepotType(String depotType);
	

}

