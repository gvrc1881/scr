package com.scr.repository;

import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;


@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
	
	List<Facility> findBySubDivision(String subDivision);
//facility names In asscending order
	List<Facility> findByDepotTypeOrderByFacilityNameAsc(String depotType);
	
	

	@Query("FROM Facility ORDER BY facilityName ASC")
    List<Facility> findAllOrderByFacilityNameAsc();
	Optional<Facility> findByFacilityName(String facilityName);

	Optional<Facility> findByFacilityId(String facilityId);

	List<Facility> findByParentDepot(String facilityName);

	List<Facility> findByDivision(String facilityName);

	List<Facility> findByZone(String facilityName);

	List<Facility> findByParentFacilityId(String facilityName);
	@Query(value = "select * from facility where depot_type in('OHE','PSI') order by facility_name ASC",
            nativeQuery=true
    )
    public List<Facility> findByDepotType(String depotType);
	List<Facility> findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);
	List<Facility> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);
	
	Boolean existsByFacilityName(String facilityName);
}

