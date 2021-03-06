package com.scr.repository;

import java.sql.Timestamp;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.scr.model.AssetMasterData;
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
	
	
	List<Facility> findByZone(String zone);

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
	
	Boolean existsByFacilityId(String facilityId);
	
	List<Facility> findByZoneAndDepotType(String zone,String depotType);
	List<Facility> findByDivisionAndDepotType(String division, String depotType);
	List<Facility> findBySubDivisionAndDepotType(String subDivision, String depotType);
	List<Facility> findByDivisionAndSubDivision(String div, String subDivision);
	Optional<Facility> findByFacilityNameAndZone(String division, String zone);
	Optional<Facility> findByFacilityNameAndDivision(String division, String subDivision);
	Optional<Facility> findByFacilityNameAndDivisionAndSubDivision(String division, String subDivision,
			String facility);
	Optional<Facility> findByFacilityNameAndSubDivision(String facility, String subDivision);
	List<Facility> findByZoneOrderByFacilityNameAsc(String facilityName);
	List<Facility> findByDivisionOrderByFacilityNameAsc(String facilityName);
	List<Facility> findBySubDivisionOrderByFacilityNameAsc(String facilityName);
	List<Facility> findByParentDepotOrderByFacilityNameAsc(String facilityName);
	List<Facility> findByParentFacilityIdOrderByFacilityNameAsc(String facilityName);
	List<Facility> findByfacilityId(List<String> fac);
	Optional<Facility> findByFacilityId(AssetMasterData assetMasterData);
	Optional<Facility> findById(String subStation);
	Optional<Facility> findById(Facility facility);
	
	
	
	
	
	//List<Facility> findByDivision(List<Facility> unitName);

}

