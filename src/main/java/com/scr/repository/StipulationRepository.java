package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Stipulations;

public interface StipulationRepository extends JpaRepository<Stipulations, Long>{
	List<Stipulations> findAll();


}
