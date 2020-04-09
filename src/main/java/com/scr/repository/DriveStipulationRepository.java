package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.Stipulations;

@Repository
public interface DriveStipulationRepository extends JpaRepository<Stipulations, Long>{

	Optional<Stipulations> findByIdAndStatusId(Long id, Integer statusId);

	List<Stipulations> findByStatusId(Integer statusId);

}
