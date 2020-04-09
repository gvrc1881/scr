package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.ObservationCategory;

public interface ObservationCategoryRepository extends JpaRepository<ObservationCategory, Long>{
	List<ObservationCategory> findDistinctByDepartment(String department);

}
