package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.scr.model.ObservationCategory;

public interface ObservationCategoryRepository extends JpaRepository<ObservationCategory, Long>{
	//observatin category in asscending order
	@Query("FROM ObservationCategory ORDER BY observationCategory ASC")
    List<ObservationCategory> findByInspectionTypeOrderByObservationCategoryAsc();
	List<ObservationCategory> findDistinctByDepartment(String department);

}
