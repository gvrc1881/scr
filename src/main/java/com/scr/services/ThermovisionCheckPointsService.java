package com.scr.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.Facility;
import com.scr.model.ThermovisionCheckPoints;
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
  			logger.info("forLoop Service");
  			List<ThermovisionCheckPoints> cpData = thermovisionCheckPointsRepository.findByActive(checkPoints.getActive());
 				logger.info("Enter into if Service");
  				ThermovisionCheckPoints updateCheckPointsData = cpData.get(0);
  				updateCheckPointsData.setCheckPointPart(checkPoints.getCheckPointPart());
  				logger.info("checkPointPart"+checkPoints.getCheckPointPart());
  				updateCheckPointsData.setCheckPoint1Description(checkPoints.getCheckPoint1Description());
  				logger.info("CheckPoint1Description"+checkPoints.getCheckPoint1Description());
  				updateCheckPointsData.setCheckPoint2Description(checkPoints.getCheckPoint2Description());
  				logger.info("checkPointDesc2"+checkPoints.getCheckPoint2Description());
  				updateCheckPointsData.setDisplayOrder(checkPoints.getDisplayOrder());
  				thermovisionCheckPointsRepository.save(updateCheckPointsData); 			
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
	public void saveCopyCheckPointsData(List<ThermovisionCheckPoints> thermovisionCheckPoints) {
	       List<ThermovisionCheckPoints> checkPoints = new ArrayList<>();
	 		for (ThermovisionCheckPoints checkPoint:thermovisionCheckPoints) {
	 			ThermovisionCheckPoints  tcp = new ThermovisionCheckPoints();
	 			tcp.setFacilityId(checkPoint.getFacilityId());
	 			tcp.setActive(checkPoint.getActive());
	 			tcp.setCheckPoint1Description(checkPoint.getCheckPoint1Description());
	 			tcp.setCheckPoint2Description(checkPoint.getCheckPoint2Description());
	 			tcp.setCheckPointPart(checkPoint.getCheckPointPart());
	 			tcp.setDisplayOrder(checkPoint.getDisplayOrder());
	 			thermovisionCheckPointsRepository.save(tcp);
	 		}
	     }

	public Optional<ThermovisionCheckPoints> findByCheckPoint1DescriptionAndCheckPoint2DescriptionAndFacilityId(
			String tcpCheckPoint1Description, String tcpCheckPoint2Description, Facility facility) {
		return thermovisionCheckPointsRepository.findByCheckPoint1DescriptionAndCheckPoint2DescriptionAndFacilityId(tcpCheckPoint1Description,tcpCheckPoint2Description,facility);
	}
	
}
