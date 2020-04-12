package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long>{
	List<Section>findAll();

}
