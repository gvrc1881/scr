package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.ProjectActivityInspection;

@Repository
public interface ProjectActivityInspectionRepository extends JpaRepository<ProjectActivityInspection, Long> {

}
