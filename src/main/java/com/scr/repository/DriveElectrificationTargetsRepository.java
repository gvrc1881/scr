package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.ElectrificationTargets;

@Repository
public interface DriveElectrificationTargetsRepository extends JpaRepository<ElectrificationTargets, Long>{

	Optional<ElectrificationTargets> findByIdAndStatusId(Long id, Integer statusId);

	List<ElectrificationTargets> findByStatusId(Integer statusId);

}
