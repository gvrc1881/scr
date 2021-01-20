package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.Facility;
import com.scr.model.ThermovisionCheckPoints;
import com.scr.repository.ThermovisionCheckPointsRepository;

@Service
public class ThermovisionCheckPointsService {

	@Autowired
	private ThermovisionCheckPointsRepository thermovisionCheckPointsRepository;

	public Optional<ThermovisionCheckPoints> findByCheckPointDescription(String tcpCheckPointDescription) {
		return thermovisionCheckPointsRepository.findByCheckPointDescription(tcpCheckPointDescription);
	}
	
     public ThermovisionCheckPoints save (ThermovisionCheckPoints checkPoints) {
		
		return thermovisionCheckPointsRepository.save(checkPoints);
	}
     public List<ThermovisionCheckPoints> getThermovisionCheckPointsBasedOnFacilityId(Facility facility) {
 		return thermovisionCheckPointsRepository.findByFacilityId(facility);
 	}
     public void updateCheckPoints(List<ThermovisionCheckPoints> checkPointsData) {
 		
 		for (ThermovisionCheckPoints checkPoints : checkPointsData) {
 			
 			Optional<ThermovisionCheckPoints> cPData = thermovisionCheckPointsRepository.findByFacilityIdAndCheckPointPart(checkPoints.getFacilityId(),checkPoints.getCheckPointPart());
 			if (cPData.isPresent()) {
 				ThermovisionCheckPoints updateCheckPointsData = cPData.get();
 				updateCheckPointsData.setCheckPointPart(checkPoints.getCheckPointPart());
 				updateCheckPointsData.setCheckPointDescription(checkPoints.getCheckPointDescription());
 				updateCheckPointsData.setCommparisonPoints(checkPoints.getCommparisonPoints());
 				updateCheckPointsData.setDisplayGroup(checkPoints.getDisplayGroup());
 				updateCheckPointsData.setDisplayOrder(checkPoints.getDisplayOrder());
 				
 				thermovisionCheckPointsRepository.save(updateCheckPointsData);
 			}
 			
 		} 
 		
 		
 	}
     
     public List<ThermovisionCheckPoints> findByFacilityIdAndCheckPointDescription(Facility facilityId,String checkPointDescription) {
 		List<ThermovisionCheckPoints> checkPointList = thermovisionCheckPointsRepository.findByFacilityIdAndCheckPointDescription(facilityId,checkPointDescription);
 		return checkPointList;
 	}
}
