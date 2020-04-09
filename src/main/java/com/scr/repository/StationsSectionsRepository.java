package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.StationsSection;

@Repository
public interface StationsSectionsRepository extends JpaRepository<StationsSection, Long>{

	List<StationsSection> findAll();
}
