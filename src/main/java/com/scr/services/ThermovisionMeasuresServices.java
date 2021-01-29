package com.scr.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.response.ThermovisionMeasureResponse;
import com.scr.model.Facility;
import com.scr.model.TcpSchedule;
import com.scr.model.ThermovisionCheckPoints;
import com.scr.model.ThermovisionMeasures;
import com.scr.repository.FacilityRepository;
import com.scr.repository.ThermovisionMeasureUtilRepository;
import com.scr.repository.ThermovisionMeasuresRepository;

@Service
public class ThermovisionMeasuresServices {
	
	private Logger logger = Logger.getLogger(ThermovisionMeasuresServices.class);
	
	@Autowired
	private ThermovisionMeasuresRepository thermovisionMeasuresRepository;
	
	@Autowired
	private ThermovisionMeasureUtilRepository thermovisionMeasureUtilRepository;
	
	@Autowired
	private TcpScheduleService tcpScheduleService;
	
	@Autowired
	private ThermovisionCheckPointsService thermovisionCheckPointsService;
	
	@Autowired
	private FacilityRepository facilityRepository;
	

	public List<ThermovisionMeasureResponse> findThermovisionMeasure(String fromDate, String facilityId) {
		return thermovisionMeasureUtilRepository.findThermovisionMeasure(fromDate,facilityId);
	}


	public void saveThermovisionMeasures(List<ThermovisionMeasureResponse> thermovisionMeasureResponses) {
		
		for (ThermovisionMeasureResponse thermovisionMeasureResponse : thermovisionMeasureResponses) {
			//logger.info("*** object ***"+thermovisionMeasureResponse.toString());
			Optional<Facility> facilityObj = facilityRepository.findById(Long.valueOf(thermovisionMeasureResponse.getRsId()));
			if (facilityObj.isPresent()) {
				//Date date = thermovisionMeasureResponse.getTcpsDate();
				Optional<TcpSchedule> tcpScheduleObj = tcpScheduleService.findByFacilityIdAndDateTime(facilityObj.get().getId(),thermovisionMeasureResponse.getTcpsDate());
				TcpSchedule resultTcpSchedule = null;
				if (tcpScheduleObj.isPresent()) {
					tcpScheduleObj.get().setBy(thermovisionMeasureResponse.getTcpsBy());
					tcpScheduleObj.get().setGeneralRemark(thermovisionMeasureResponse.getTcpsGeneralRemark());
					resultTcpSchedule = tcpScheduleService.save(tcpScheduleObj.get());
				}else {
					TcpSchedule tcpSchedule = new TcpSchedule();
					tcpSchedule.setBy(thermovisionMeasureResponse.getTcpsBy());
					tcpSchedule.setGeneralRemark(thermovisionMeasureResponse.getTcpsGeneralRemark());
					tcpSchedule.setFacility(facilityObj.get());
					tcpSchedule.setDateTime(thermovisionMeasureResponse.getTcpsDate());
					resultTcpSchedule = tcpScheduleService.save(tcpSchedule);
				}
				if (resultTcpSchedule != null) {
					Optional<ThermovisionCheckPoints> thermoCheckPointObject = thermovisionCheckPointsService.findByCheckPoint1Description(thermovisionMeasureResponse.getTcpCheckPointDescription());
					if (thermoCheckPointObject.isPresent()) {
						//logger.info("*** finding measure objcet  ***");
						Optional<ThermovisionMeasures> thermoMeasureObject = thermovisionMeasuresRepository.findByTcpIdAndTcpScheduleId(thermoCheckPointObject.get(),resultTcpSchedule);
						if (thermoMeasureObject.isPresent()) {
							/*thermoMeasureObject.get().setAmbientTemp(thermovisionMeasureResponse.getTcpmAmbientTemp());
							thermoMeasureObject.get().setcClampMeasure(thermovisionMeasureResponse.getTcpmCClampMeasure());
							thermoMeasureObject.get().setCriticality(thermovisionMeasureResponse.getTcpmCriticality());
							thermoMeasureObject.get().setFixedMeasure(thermovisionMeasureResponse.getTcpmFixedMeasure());*/
							thermoMeasureObject.get().setRemark(thermovisionMeasureResponse.getTcpmRemark());
							//thermoMeasureObject.get().setVarianceWithOtherPoint(thermovisionMeasureResponse.getTcpmVarianceWithOtherPoint());
							thermovisionMeasuresRepository.save(thermoMeasureObject.get());
						}else {
							ThermovisionMeasures thermovisionMeasures = new ThermovisionMeasures();
							/*thermovisionMeasures.setAmbientTemp(thermovisionMeasureResponse.getTcpmAmbientTemp());
							thermovisionMeasures.setcClampMeasure(thermovisionMeasureResponse.getTcpmCClampMeasure());
							thermovisionMeasures.setCriticality(thermovisionMeasureResponse.getTcpmCriticality());
							thermovisionMeasures.setFixedMeasure(thermovisionMeasureResponse.getTcpmFixedMeasure());
							thermovisionMeasures.setRemark(thermovisionMeasureResponse.getTcpmRemark());
							thermovisionMeasures.setVarianceWithOtherPoint(thermovisionMeasureResponse.getTcpmVarianceWithOtherPoint());*/
							thermovisionMeasures.setTcpId(thermoCheckPointObject.get());
							thermovisionMeasures.setTcpScheduleId(resultTcpSchedule);
							thermovisionMeasuresRepository.save(thermovisionMeasures);
						}
					}
				}
			}
			
		}
		
	}

}
