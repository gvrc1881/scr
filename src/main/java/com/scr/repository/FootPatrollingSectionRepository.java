package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.FootPatrollingSection;

public interface FootPatrollingSectionRepository extends JpaRepository<FootPatrollingSection, Long> {

	List<FootPatrollingSection> findAll();

}
