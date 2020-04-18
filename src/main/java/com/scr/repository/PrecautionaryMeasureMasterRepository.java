package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.PrecautionaryMeasuresMaster;

public interface PrecautionaryMeasureMasterRepository extends JpaRepository<PrecautionaryMeasuresMaster, Long>{
List<PrecautionaryMeasuresMaster> findAll();
}
