package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.ObservationsCheckList;

public interface ObservationCheckListRepository extends JpaRepository<ObservationsCheckList, Long>{
	List<ObservationsCheckList> findDistinctByObservationCategory(String observationCategory);

}
