package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
	
	List<Facility> findBySubDivision(String subDivision);

	List<Facility> findByDepotType(String depotType);

	Optional<Facility> findByFacilityName(String facilityName);

	Optional<Facility> findByFacilityId(String facilityId);

	List<Facility> findByParentDepot(String facilityName);

	List<Facility> findByDivision(String facilityName);

	List<Facility> findByZone(String facilityName);

	List<Facility> findByParentFacilityId(String facilityName);
	

}

