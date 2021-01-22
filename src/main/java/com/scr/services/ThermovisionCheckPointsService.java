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
 		return thermovisionCheckPointsRepository.findByFacilityIdOrderByDisplayOrderAsc(facility);
 	}
     public void updateCheckPoints(List<ThermovisionCheckPoints> checkPointsData) {
 		
 		for (ThermovisionCheckPoints checkPoints : checkPointsData) {
 			
 			List<ThermovisionCheckPoints> cPData = thermovisionCheckPointsRepository.findByFacilityIdOrderByDisplayOrderAsc(checkPoints.getFacilityId());
 				ThermovisionCheckPoints updateCheckPointsData = cPData.get(0);
 				updateCheckPointsData.setCheckPointPart(checkPoints.getCheckPointPart());
 				updateCheckPointsData.setCheckPointDescription(checkPoints.getCheckPointDescription());
 				updateCheckPointsData.setCommparisonPoints(checkPoints.getCommparisonPoints());
 				updateCheckPointsData.setTypeOfCheckPoint(checkPoints.getTypeOfCheckPoint());
 				updateCheckPointsData.setDisplayOrder(checkPoints.getDisplayOrder());
 				updateCheckPointsData.setDisplayOfTempDiff(checkPoints.getDisplayOfTempDiff());
 				thermovisionCheckPointsRepository.save(updateCheckPointsData); 			
 		} 
 		
 		
 	}
     
     public List<ThermovisionCheckPoints> findByFacilityIdAndIdNotIn(Facility facilityId,Long id) {
 		List<ThermovisionCheckPoints> checkPointList = thermovisionCheckPointsRepository.findByFacilityIdAndIdNotIn(facilityId,id);
 		return checkPointList;
 	}
}
