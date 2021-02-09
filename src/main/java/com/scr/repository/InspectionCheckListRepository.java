package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.InspectionCheckList;
import com.scr.model.TestInspection;

@Repository
public interface InspectionCheckListRepository extends JpaRepository<InspectionCheckList, Long>{

	List<InspectionCheckList> findByTestInspectionId(TestInspection testInspection);

}
