package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scr.model.Inspection;
import com.scr.model.Observation;

@Repository
public interface ObservationsRepository extends JpaRepository<Observation, Long> {

	List<Observation> findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);

	List<Observation> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);

	Optional<Observation> findByDeviceIdAndDeviceSeqId(String deviceId, String deviceSeqId);
	
	
	@Query(value="select ins.from_date_time , ins.section , ins.name_of_staff , ins.facility_id , obs.location , ins.inspection_seq_id , obs.seq_id " + 
			"from observations obs , inspection ins " + 
			"where obs.inspection_seq_id = ins.inspection_seq_id" + 
			"and type_of_work ='FOOT_PATROLLING'" + 
			"and ins.section=:section " + 
			"and ins.facility_id=:facilityId " + 
			"and ins.name_of_staff=:nameOfStaff " + 
			"and ins.from_date_time=:fromDateTime",nativeQuery = true)
	List<Inspection> findObservation(String section, String facilityId,String nameOfStaff,Timestamp fromDateTime);

	
	List<Observation> findByInspectionSeqId(String inspectionSeqId);
}
