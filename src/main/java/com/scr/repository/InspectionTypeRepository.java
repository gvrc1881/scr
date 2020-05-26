package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.scr.model.InspectionType;

@Repository
public interface InspectionTypeRepository extends JpaRepository<InspectionType, Long>{
	List<InspectionType> findAll();
	@Query("FROM InspectionType ORDER BY inspectionType ASC")
	List<InspectionType> findAllOrderByInspectionTypeAsc();

}
