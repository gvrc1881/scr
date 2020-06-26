package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Observation;

@Repository
public interface ObservationsRepository extends JpaRepository<Observation, Long> {

	List<Observation> findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);

	List<Observation> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);

	Optional<Observation> findByDeviceIdAndDeviceSeqId(String deviceId, String deviceSeqId);

}
