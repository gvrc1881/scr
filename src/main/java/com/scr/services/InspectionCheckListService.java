package com.scr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.InspectionCheckList;
import com.scr.model.TestInspection;
import com.scr.repository.InspectionCheckListRepository;

@Service
public class InspectionCheckListService {
	
	@Autowired
	private InspectionCheckListRepository inspectionCheckListRepository;

	public List<InspectionCheckList> findByTestInspectionId(TestInspection testInspection) {
		return inspectionCheckListRepository.findByTestInspectionId(testInspection);
	}

}
