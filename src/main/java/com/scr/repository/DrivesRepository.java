package com.scr.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scr.model.Drives;
import com.scr.model.FunctionalLocationTypes;
import com.scr.model.Make;

@Repository
public interface DrivesRepository extends JpaRepository<Drives, Long> {

	Optional<Drives> findDriveById(Long id);

	Optional<Drives> findByIdAndStatusId(Long id, Integer statusId);

	List<Drives> findByStatusId(Integer statusId);

	Boolean existsByNameAndStatusId(String name, Integer statusId);

	Boolean existsByDescriptionAndStatusId(String description, Integer statusId);
	
	@Query("FROM Drives ORDER BY name ASC")
	List<Drives> findAllDrivesOrderByNameAsc();

	List<Drives> findByFromDateAndDepotType(Date fromDate, String depotType);

	List<Drives> findByFromDateGreaterThanEqualAndToDateGreaterThanEqualOrToDateIsNull(Date fromDate,Date toDate);

	List<Drives> findByFromDateGreaterThanEqualAndToDateGreaterThanEqualOrToDateIsNullAndDepotType(Date fromDate,
			Date toDate, FunctionalLocationTypes functionalLocationTypes);

}
