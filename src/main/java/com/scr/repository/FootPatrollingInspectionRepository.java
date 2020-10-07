package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.FootPatrollingInspection;

@Repository
public interface FootPatrollingInspectionRepository extends JpaRepository<FootPatrollingInspection, Long> {
	// fetching from repository
	List<FootPatrollingInspection> findAll();
	Optional<FootPatrollingInspection> findById(Long id);

	List<FootPatrollingInspection> findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);

	List<FootPatrollingInspection> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(
			Timestamp currenTimestamp, Timestamp previousTimestamp);

	Optional<FootPatrollingInspection> findByDeviceIdAndDeviceSeqId(String deviceId, String deviceSeqId);

}
