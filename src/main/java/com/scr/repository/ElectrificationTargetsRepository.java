package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.ElectrificationTargets;

public interface ElectrificationTargetsRepository extends JpaRepository<ElectrificationTargets, Long>{
	List<ElectrificationTargets> findAll();

}
