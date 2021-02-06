package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.TestInspection;


@Repository
public interface TestInspectionRepository extends JpaRepository<TestInspection, Long>{

}
