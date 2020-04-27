package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Drives;

@Repository
public interface DrivesRepository extends JpaRepository<Drives, Long> {

	Optional<Drives> findDriveById(Long id);

	Optional<Drives> findByIdAndStatusId(Long id, Integer statusId);

	List<Drives> findByStatusId(Integer statusId);

	Boolean existsByNameAndStatusId(String name, Integer statusId);

	Boolean existsByDescriptionAndStatusId(String description, Integer statusId);

}
