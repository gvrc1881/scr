package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.ObservationsCheckList;

public interface ObservationCheckListRepository extends JpaRepository<ObservationsCheckList, Long> {
	List<ObservationsCheckList> findDistinctByObservationCategory(String observationCategory);
	List<ObservationsCheckList> findByObservationCategory(String observationCategory);
	List<ObservationsCheckList> findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);

	List<ObservationsCheckList> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(
			Timestamp currenTimestamp, Timestamp previousTimestamp);

}
