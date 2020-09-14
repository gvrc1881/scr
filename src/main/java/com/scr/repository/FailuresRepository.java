package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.scr.model.Failure;

@Repository
public interface FailuresRepository extends JpaRepository<Failure, Long> {

	List<Failure> findAll();

	List<Failure> findByTypeOfFailureAndCurrentStatus(String typeOfFailure, String currentStatus);
	
	List<Failure> findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThan(String typeOfFailure, Timestamp fromDateTime,Timestamp thruDateTime);

	List<Failure> findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThanAndFacilityId(String typeOfFailure, Timestamp fromDateTime,
			Timestamp thruDateTime,String facilityId);
	List<Failure> findByTypeOfFailureAndFromDateTimeGreaterThanAndThruDateTimeLessThanAndDataDiv(String typeOfFailure, Timestamp fromDateTime,
			Timestamp thruDateTime,String dataDiv);
	
	}
	

