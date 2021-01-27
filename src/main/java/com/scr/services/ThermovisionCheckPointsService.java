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

	public Optional<ThermovisionCheckPoints> findByCheckPointDescription(String tcpCheckPointDescription) {
		return thermovisionCheckPointsRepository.findByCheckPointDescription(tcpCheckPointDescription);
	}
	
     public ThermovisionCheckPoints save (ThermovisionCheckPoints checkPoints) {
		
		return thermovisionCheckPointsRepository.save(checkPoints);
	}
     public List<ThermovisionCheckPoints> getThermovisionCheckPointsBasedOnFacilityId(Facility facility) {
 		return thermovisionCheckPointsRepository.findByFacilityIdOrderByDisplayOrderAsc(facility);
 	}
     public void updateCheckPoints(List<ThermovisionCheckPoints> checkPointsData) {
 		
 		for (ThermovisionCheckPoints checkPoints : checkPointsData) {
 			
 			Optional<ThermovisionCheckPoints> cpData = thermovisionCheckPointsRepository.findByCheckPointDescription(checkPoints.getCheckPointDescription());
			if (cpData.isPresent()) {
 				ThermovisionCheckPoints updateCheckPointsData = cpData.get();
 				updateCheckPointsData.setCheckPointPart(checkPoints.getCheckPointPart());
 				updateCheckPointsData.setCheckPointDescription(checkPoints.getCheckPointDescription());
 				updateCheckPointsData.setCommparisonPoints(checkPoints.getCommparisonPoints());
 				logger.info("checkPointDesc"+checkPoints.getCommparisonPoints());
 				logger.info("checkPointDesc"+updateCheckPointsData);
 				logger.info("checkPointDesc"+updateCheckPointsData.getCommparisonPoints());
 				updateCheckPointsData.setTypeOfCheckPoint(checkPoints.getTypeOfCheckPoint());
 				updateCheckPointsData.setDisplayOrder(checkPoints.getDisplayOrder());
 				updateCheckPointsData.setDisplayOfTempDiff(checkPoints.getDisplayOfTempDiff());
 				thermovisionCheckPointsRepository.save(updateCheckPointsData); 			
 		} 
 		
 		}
 	}
     
     public List<ThermovisionCheckPoints> findByFacilityIdAndIdNotIn(Facility facilityId,Long id) {
 		List<ThermovisionCheckPoints> checkPointList = thermovisionCheckPointsRepository.findByFacilityIdAndIdNotIn(facilityId,id);
 		return checkPointList;
 	}
}
