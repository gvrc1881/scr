package com.scr.services;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.message.request.OheThermovisionMeasureRequest;
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
					tcpScheduleObj.get().setLocation(thermovisionMeasureResponse.getTcpsLocation());
					tcpScheduleObj.get().setGeneralRemark(thermovisionMeasureResponse.getTcpsGeneralRemark());
					resultTcpSchedule = tcpScheduleService.save(tcpScheduleObj.get());
				}else {
					TcpSchedule tcpSchedule = new TcpSchedule();
					tcpSchedule.setBy(thermovisionMeasureResponse.getTcpsBy());
					tcpSchedule.setGeneralRemark(thermovisionMeasureResponse.getTcpsGeneralRemark());
					tcpSchedule.setLocation(thermovisionMeasureResponse.getTcpsLocation());
					tcpSchedule.setFacility(facilityObj.get());
					tcpSchedule.setDateTime(thermovisionMeasureResponse.getTcpsDate());
					resultTcpSchedule = tcpScheduleService.save(tcpSchedule);
				}
				if (resultTcpSchedule != null) {
					Optional<ThermovisionCheckPoints> thermoCheckPointObject = thermovisionCheckPointsService.findByCheckPoint1DescriptionAndCheckPoint2DescriptionAndFacilityId(thermovisionMeasureResponse.getTcpCheckPoint1Description(),thermovisionMeasureResponse.getTcpCheckPoint2Description(),facilityObj.get());
					if (thermoCheckPointObject.isPresent()) {
						logger.info("*** finding measure objcet  ***");
						Optional<ThermovisionMeasures> thermoMeasureObject = thermovisionMeasuresRepository.findByTcpIdAndTcpScheduleId(thermoCheckPointObject.get(),resultTcpSchedule);
						if (thermoMeasureObject.isPresent()) {
							/*thermoMeasureObject.get().setAmbientTemp(thermovisionMeasureResponse.getTcpmAmbientTemp());
							thermoMeasureObject.get().setcClampMeasure(thermovisionMeasureResponse.getTcpmCClampMeasure());
							thermoMeasureObject.get().setCriticality(thermovisionMeasureResponse.getTcpmCriticality());
							thermoMeasureObject.get().setFixedMeasure(thermovisionMeasureResponse.getTcpmFixedMeasure());*/
							thermoMeasureObject.get().setMeasurePoint1(thermovisionMeasureResponse.getTcpmMeasurePoint1());
							thermoMeasureObject.get().setMeasurePoint2(thermovisionMeasureResponse.getTcpmMeasurePoint2());
							thermoMeasureObject.get().setRemark(thermovisionMeasureResponse.getTcpmRemark());
							//thermoMeasureObject.get().setVarianceWithOtherPoint(thermovisionMeasureResponse.getTcpmVarianceWithOtherPoint());
							thermovisionMeasuresRepository.save(thermoMeasureObject.get());
						}else {
							ThermovisionMeasures thermovisionMeasures = new ThermovisionMeasures();
							/*thermovisionMeasures.setAmbientTemp(thermovisionMeasureResponse.getTcpmAmbientTemp());
							thermovisionMeasures.setcClampMeasure(thermovisionMeasureResponse.getTcpmCClampMeasure());
							thermovisionMeasures.setFixedMeasure(thermovisionMeasureResponse.getTcpmFixedMeasure());
							thermovisionMeasures.setVarianceWithOtherPoint(thermovisionMeasureResponse.getTcpmVarianceWithOtherPoint());*/
							thermovisionMeasures.setCriticality(thermovisionMeasureResponse.getTcpmCriticality());
							thermovisionMeasures.setRemark(thermovisionMeasureResponse.getTcpmRemark());
							thermovisionMeasures.setMeasurePoint1(thermovisionMeasureResponse.getTcpmMeasurePoint1());
							thermovisionMeasures.setMeasurePoint2(thermovisionMeasureResponse.getTcpmMeasurePoint2());
							thermovisionMeasures.setTcpId(thermoCheckPointObject.get());
							thermovisionMeasures.setTcpScheduleId(resultTcpSchedule);
							thermovisionMeasuresRepository.save(thermovisionMeasures);
						}
					}
				}
			}
			
		}
		
	}


	public void saveOheThermovisionMeasure(OheThermovisionMeasureRequest oheThermovisionMeasureRequest) {
		Optional<TcpSchedule> tcpScheduleObj = tcpScheduleService.findByFacilityIdAndDateTime(oheThermovisionMeasureRequest.getFacilityId().getId(),oheThermovisionMeasureRequest.getDateTime());
		TcpSchedule resultTcpSchedule = null;
		if (tcpScheduleObj.isPresent()) {
			tcpScheduleObj.get().setBy(oheThermovisionMeasureRequest.getBy());
			//tcpScheduleObj.get().setLocation(oheThermovisionMeasureRequest.getLocation());
			tcpScheduleObj.get().setGeneralRemark(oheThermovisionMeasureRequest.getGeneralRemark());
			resultTcpSchedule = tcpScheduleService.save(tcpScheduleObj.get());
		}else {
			TcpSchedule tcpSchedule = new TcpSchedule();
			tcpSchedule.setBy(oheThermovisionMeasureRequest.getBy());
			tcpSchedule.setGeneralRemark(oheThermovisionMeasureRequest.getGeneralRemark());
			tcpSchedule.setFacility(oheThermovisionMeasureRequest.getFacilityId());
			tcpSchedule.setDateTime(oheThermovisionMeasureRequest.getDateTime());
			resultTcpSchedule = tcpScheduleService.save(tcpSchedule);
		}
		if (resultTcpSchedule != null ) {
			Optional<ThermovisionMeasures> thermovisionMeasures = thermovisionMeasuresRepository.findByTcpScheduleIdAndConnectionPoint1AndLocation(resultTcpSchedule,oheThermovisionMeasureRequest.getConnectionPoint1(),oheThermovisionMeasureRequest.getLocation());
			if (thermovisionMeasures.isPresent()) {
				//thermovisionMeasures.get().setLocation(oheThermovisionMeasureRequest.getLocation());
				thermovisionMeasures.get().setAmbientTemp(oheThermovisionMeasureRequest.getAmbientTemp());
				thermovisionMeasures.get().setMeasurePoint1(oheThermovisionMeasureRequest.getMeasure1());
				thermovisionMeasures.get().setMeasurePoint2(oheThermovisionMeasureRequest.getMeasure2());
				thermovisionMeasures.get().setConnectionPoint1(oheThermovisionMeasureRequest.getConnectionPoint1());
				thermovisionMeasures.get().setConnectionPoint2(oheThermovisionMeasureRequest.getConnectionPoint2());
				thermovisionMeasures.get().setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				thermovisionMeasuresRepository.save(thermovisionMeasures.get());
			}else {
				ThermovisionMeasures thermovisionMeasure = new ThermovisionMeasures();
				thermovisionMeasure.setLocation(oheThermovisionMeasureRequest.getLocation());
				thermovisionMeasure.setAmbientTemp(oheThermovisionMeasureRequest.getAmbientTemp());
				thermovisionMeasure.setConnectionPoint1(oheThermovisionMeasureRequest.getConnectionPoint1());
				thermovisionMeasure.setConnectionPoint2(oheThermovisionMeasureRequest.getConnectionPoint2());
				thermovisionMeasure.setTcpScheduleId(resultTcpSchedule);
				thermovisionMeasure.setMeasurePoint1(oheThermovisionMeasureRequest.getMeasure1());
				thermovisionMeasure.setMeasurePoint2(oheThermovisionMeasureRequest.getMeasure2());
				thermovisionMeasure.setCreatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				thermovisionMeasure.setUpdatedOn(new Timestamp(Calendar.getInstance().getTime().getTime()));
				thermovisionMeasuresRepository.save(thermovisionMeasure);	
			}
			
		}
	}


	public List<ThermovisionMeasures> findOheThermovisionMeasure(Long facilityId) {
		// TODO Auto-generated method stub
		List<TcpSchedule> tcpSchs = tcpScheduleService.findByFacilityId(facilityId);
		List<ThermovisionMeasures> thermovisionMeasures = thermovisionMeasuresRepository.findByTcpScheduleIdInOrderByUpdatedOnDesc(tcpSchs);
		return thermovisionMeasures;
	}

}
