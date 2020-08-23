package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.EnergyConsumption;

@Repository
public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Long>{

	EnergyConsumption findTopByOrderByIdDesc();

}
