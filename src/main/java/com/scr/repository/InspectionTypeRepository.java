package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.InspectionType;

@Repository
public interface InspectionTypeRepository extends JpaRepository<InspectionType, Long>{

}
