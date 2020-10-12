package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.OheLocation;

@Repository
public interface OheLocationRepository extends JpaRepository<OheLocation, Long> {
	
	List<OheLocation> findAll();

	List<OheLocation> findByCreatedStampLessThanEqualAndCreatedStampGreaterThanAndOheMastIsNotNullAndLatitudeIsNotNullAndLongitudeIsNotNull(
			Timestamp currenTimestamp, Timestamp previousTimestamp);

	List<OheLocation> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThanAndOheMastIsNotNullAndLatitudeIsNotNullAndLongitudeIsNotNull(
			Timestamp currenTimestamp, Timestamp previousTimestamp);

}
