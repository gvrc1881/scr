package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.scr.model.ObservationCategory;

public interface ObservationCategoryRepository extends JpaRepository<ObservationCategory, Long> {
	@Query("FROM ObservationCategory ORDER BY observationCategory ASC")
	List<ObservationCategory> findByInspectionTypeOrderByObservationCategoryAsc();

	List<ObservationCategory> findDistinctByDepartment(String department);

	@Query(value = "SELECT case when count(obc)> 0 then true else false  end  FROM ObservationCategory obc WHERE obc.inspectionType = :inspectionType and obc.observationCategory  = :observationCategory")
	Boolean existsByInspectionTypeAndObservationCategory(@Param("inspectionType") String inspectionType,
			@Param("observationCategory") String observationCategory);

	List<ObservationCategory> findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(Timestamp currenTimestamp,
			Timestamp previousTimestamp);

	List<ObservationCategory> findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(
			Timestamp currenTimestamp, Timestamp previousTimestamp);
	
    Optional<ObservationCategory>findByInspectionTypeAndObservationCategory(String inspectionType,String observationCategory);


}
