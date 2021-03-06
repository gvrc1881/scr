package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;
import com.scr.model.StationsSection;

@Repository
public interface StationsSectionsRepository extends JpaRepository<StationsSection, Long> {
	List<StationsSection> findAll();
	//exist coondition
	Boolean existsByStationCode(String stationCode);

	Boolean existsByStationName(String stationName);
	
	Optional<StationsSection> findByStationCode(String stationCode);
	
	Optional<StationsSection> findByStationName(String stationName);


}
