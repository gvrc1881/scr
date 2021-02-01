package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.controller.ThermovisionCheckPointsController;
import com.scr.model.Facility;
import com.scr.model.ThermovisionCheckPoints;
import com.scr.model.WorkPhases;
import com.scr.repository.ThermovisionCheckPointsRepository;

@Service
public class ThermovisionCheckPointsService {
	
	private Logger logger = Logger.getLogger(ThermovisionCheckPointsService.class);


	@Autowired
	private ThermovisionCheckPointsRepository thermovisionCheckPointsRepository;

	public Optional<ThermovisionCheckPoints> findByCheckPoint1Description(String tcpCheckPoint1Description) {
		return thermovisionCheckPointsRepository.findByCheckPoint1Description(tcpCheckPoint1Description);
	}
	
     public ThermovisionCheckPoints save (ThermovisionCheckPoints checkPoints) {
		
		return thermovisionCheckPointsRepository.save(checkPoints);
	}
     public List<ThermovisionCheckPoints> getThermovisionCheckPointsBasedOnFacilityId(Facility facility) {
 		return thermovisionCheckPointsRepository.findByFacilityIdOrderByDisplayOrderAsc(facility);
 	}
     public void updateCheckPoints(List<ThermovisionCheckPoints> checkPointsData) {
 		
 		for (ThermovisionCheckPoints checkPoints : checkPointsData) {
 			
 			Optional<ThermovisionCheckPoints> cpData = thermovisionCheckPointsRepository.findByCheckPoint1Description(checkPoints.getCheckPoint1Description());
			if (cpData.isPresent()) {
 				ThermovisionCheckPoints updateCheckPointsData = cpData.get();
 				updateCheckPointsData.setCheckPointPart(checkPoints.getCheckPointPart());
 				updateCheckPointsData.setCheckPoint1Description(checkPoints.getCheckPoint1Description());
 				updateCheckPointsData.setCheckPoint2Description(checkPoints.getCheckPoint2Description());
 				logger.info("checkPointDesc"+updateCheckPointsData);
 				updateCheckPointsData.setDisplayOrder(checkPoints.getDisplayOrder());
 				updateCheckPointsData.setActive(checkPoints.getActive());
 				thermovisionCheckPointsRepository.save(updateCheckPointsData); 			
 		} 
 		
 		}
 	}
     
     public List<ThermovisionCheckPoints> findByFacilityIdAndIdNotIn(Facility facilityId,Long id) {
 		List<ThermovisionCheckPoints> checkPointList = thermovisionCheckPointsRepository.findByFacilityIdAndIdNotIn(facilityId,id);
 		return checkPointList;
 	}

	public Optional<ThermovisionCheckPoints> findByCheckPoint1DescriptionAndCheckPoint2Description(
			String tcpCheckPoint1Description, String tcpCheckPoint2Description) {
		return thermovisionCheckPointsRepository.findByCheckPoint1DescriptionAndCheckPoint2Description(tcpCheckPoint1Description,tcpCheckPoint2Description);
	}
}
