package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.CrsEigInspections;

@Repository
public interface DriveInspectionRepository extends JpaRepository<CrsEigInspections, Long>{

	Optional<CrsEigInspections> findByIdAndStatusId(Long id, Integer statusId);

	List<CrsEigInspections> findByStatusId(Integer statusId);

}
