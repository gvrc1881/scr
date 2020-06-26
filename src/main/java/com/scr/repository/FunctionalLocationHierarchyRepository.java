package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.FunctionalLocationHierarchy;

@Repository
public interface FunctionalLocationHierarchyRepository extends JpaRepository<FunctionalLocationHierarchy, Long> {

	List<FunctionalLocationHierarchy> findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(
			Timestamp currenTimestamp, Timestamp previousTimestamp);

	List<FunctionalLocationHierarchy> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(
			Timestamp currenTimestamp, Timestamp previousTimestamp);

}
