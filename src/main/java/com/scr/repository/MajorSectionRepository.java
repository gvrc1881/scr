package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.MajorSections;

public interface MajorSectionRepository extends JpaRepository<MajorSections, Long>{
List <MajorSections> findAll();
}
