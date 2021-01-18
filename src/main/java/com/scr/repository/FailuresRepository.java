package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.scr.model.Facility;
import com.scr.model.Failure;
import com.scr.model.MeasureOrActivityList;

@Repository
public interface FailuresRepository extends JpaRepository<Failure, Long> {

	List<Failure> findAll();

	List<Failure> findByTypeOfFailureAndCurrentStatus(String typeOfFailure, String currentStatus);
	
	List<Failure> findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThan(String typeOfFailure, Timestamp fromDateTime,Timestamp thruDateTime);

	List<Failure> findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThanAndFacilityId(String typeOfFailure, Timestamp fromDateTime,
			Timestamp thruDateTime,String facilityId);
	List<Failure> findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThanAndDataDiv(String typeOfFailure, Timestamp fromDateTime,
			Timestamp thruDateTime,String dataDiv);
	
	@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM Failure tet WHERE tet.feedOf = :feedOf and tet.fromDateTime  = :fromDateTime")
	Boolean existsByFeedOfAndFromDateTime(@Param("feedOf")String feedOf, @Param("fromDateTime") Timestamp fromDateTime);
	
	@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM Failure tet WHERE tet.subStation = :subStation and tet.equipment = :equipment and tet.fromDateTime  = :fromDateTime")
	Boolean existsBySubStationAndEquipmentAndFromDateTime(@Param("subStation")String subStation,@Param("equipment")String equipment, @Param("fromDateTime") Timestamp fromDateTime);
	
	@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM Failure tet WHERE tet.subStation = :subStation and tet.fromDateTime = :fromDateTime")
	Boolean existsBySubStationAndOccurrence(@Param("subStation")String subStation,@Param("fromDateTime")Timestamp fromDateTime);
	
	@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM Failure tet WHERE tet.occurrence = :occurrence and tet.place = :place and tet.fromDateTime = :fromDateTime")
	Boolean existsByOccurrenceAndPlaceAndFromDateTime(@Param("occurrence")String occurrence,@Param("place")String place, @Param("fromDateTime") Timestamp fromDateTime);
	
	@Query(value = "SELECT case when count(tet)> 0 then true else false  end  FROM Failure tet WHERE tet.subStation = :subStation and tet.location = :location and tet.fromDateTime = :fromDateTime")
	Boolean existsBySubStationAndLocationAndFromDateTime(@Param("subStation")String subStation,@Param("location")String location, @Param("fromDateTime") Timestamp fromDateTime);
	
	Optional<Failure> findByFeedOfAndFromDateTime(String feedOf, Timestamp fromDateTime);
	
	Optional<Failure> findBySubStationAndEquipmentAndFromDateTime(String subStation,String equipment, Timestamp fromDateTime);
	
	Optional<Failure> findBySubStationAndOccurrence(String subStation,Timestamp fromDateTime);
	
	Optional<Failure> findByOccurrenceAndPlaceAndFromDateTime(String occurrence,String place,Timestamp fromDateTime);
	
	Optional<Failure> findBySubStationAndLocationAndFromDateTime(String subStation,String location,Timestamp fromDateTime);

	List<Failure> findByTypeOfFailureAndSubStationInAndCurrentStatus(String failureType, List<String> fac,String active);

	List<Failure> findByTypeOfFailureAndFeedOfInAndCurrentStatus(String failureType, List<String> fac, String active);

	List<Failure> findFailureByTypeOfFailureAndDataDivInAndCurrentStatus(String failureType, List<String> facilty,
			String active);

	

	

	

}
	

