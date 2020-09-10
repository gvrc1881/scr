package com.scr.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.scr.app.dto.CompliancesDto;
import com.scr.app.dto.FacilityDto;
import com.scr.app.dto.FootPatrollingInspectionDto;
import com.scr.app.dto.FootPatrollingSectionsDto;
import com.scr.app.dto.FpAppMasterDto;
import com.scr.app.dto.FunctionalLocationHierarchyDto;
import com.scr.app.dto.InspectionTypeDto;
import com.scr.app.dto.ObservationCategoriesDto;
import com.scr.app.dto.ObservationsCheckListDto;
import com.scr.app.dto.ObservationsDto;
import com.scr.app.dto.OheLocationDto;
import com.scr.app.dto.ProductDto;
import com.scr.app.dto.ReportDto;
import com.scr.app.dto.ResponseCompliancesDto;
import com.scr.app.dto.ResponseFacilityDto;
import com.scr.app.dto.ResponseFootPatrollingInspectionDto;
import com.scr.app.dto.ResponseFootPatrollingSectionsDto;
import com.scr.app.dto.ResponseFunctionalLocationHierarchyDto;
import com.scr.app.dto.ResponseInspectionTypeDto;
import com.scr.app.dto.ResponseObservationCategoriesDto;
import com.scr.app.dto.ResponseObservationsCheckListDto;
import com.scr.app.dto.ResponseObservationsDto;
import com.scr.app.dto.ResponseOheLocationDto;
import com.scr.app.dto.ResponseProductDto;
import com.scr.app.dto.ResponseUserLoginDto;
import com.scr.app.dto.UserLoginDto;
import com.scr.model.AppDevice;
import com.scr.model.AppDeviceLogin;
import com.scr.model.AppDeviceUnit;
import com.scr.model.Compliance;
import com.scr.model.Facility;
import com.scr.model.FootPatrollingInspection;
import com.scr.model.FootPatrollingSection;
import com.scr.model.FunctionalLocationHierarchy;
import com.scr.model.InspectionType;
import com.scr.model.Observation;
import com.scr.model.ObservationCategory;
import com.scr.model.ObservationsCheckList;
import com.scr.model.OheLocation;
import com.scr.model.Product;
import com.scr.model.ReportRepository;
import com.scr.model.UserLogin;
import com.scr.repository.AppDeviceLoginRepository;
import com.scr.repository.AppDeviceRepository;
import com.scr.repository.AppDeviceUnitRepository;
import com.scr.repository.ComplianceRepository;
import com.scr.repository.FacilityRepository;
import com.scr.repository.FootPatrollingInspectionRepository;
import com.scr.repository.FootPatrollingSectionRepository;
import com.scr.repository.FunctionalLocationHierarchyRepository;
import com.scr.repository.InspectionTypeRepository;
import com.scr.repository.ObservationCategoryRepository;
import com.scr.repository.ObservationCheckListRepository;
import com.scr.repository.ObservationsRepository;
import com.scr.repository.OheLocationRepository;
import com.scr.repository.ProductRepository;
import com.scr.repository.ReportRepositoryRepository;
import com.scr.repository.UserLoginRepository;
import com.scr.util.CloseJDBCObjects;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Component
public class FootPatrollingRestService {
	
	static Logger log = Logger.getLogger(FootPatrollingRestService.class);
	
	@Autowired
	private AppDeviceRepository appDeviceRepository;
	
	@Autowired
	private AppDeviceUnitRepository appDeviceUnitRepository;
	
	@Autowired
	private AppDeviceLoginRepository appDeviceLoginRepository;
	
	@Autowired
	private UserLoginRepository userLoginRepository;
	
	@Autowired
	private FacilityRepository facilityRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private InspectionTypeRepository inspectionTypeRepository;
	
	@Autowired
	private OheLocationRepository oheLocationRepository;
	
	@Autowired
	private ObservationCategoryRepository observationCategoryRepository;
	
	@Autowired
	private ObservationCheckListRepository observationCheckListRepository;
	
	@Autowired
	private FootPatrollingSectionRepository footPatrollingSectionRepository;
	
	@Autowired
	private FunctionalLocationHierarchyRepository functionalLocationHierarchyRepository;
	
	@Autowired
	private FootPatrollingInspectionRepository footPatrollingInspectionRepository;
	
	@Autowired
	private ObservationsRepository observationsRepository;
	
	@Autowired
	private ComplianceRepository complianceRepository;
	
	@Autowired
	private ReportRepositoryRepository reportRepository;
	
	@Autowired
	CloseJDBCObjects closeJDBCObjects;
	
	@Autowired
	ResourceLoader resourceLoader;
	
	@Autowired
	private DataSource dataSource;
	
	public FpAppMasterDto getMasterData(FpAppMasterDto fpMasterDto, Timestamp previousTimestamp, Timestamp currenTimestamp) {
		log.info("*** app name ***"+fpMasterDto.getAppName());
		log.info("*** previous timestamp ***"+previousTimestamp);
		log.info("*** current timestamp ***"+currenTimestamp);
		log.info("*** imei number ***"+fpMasterDto.getImeiNo());
		log.info("*** phone number ***"+fpMasterDto.getPhoneNum());
		List<String> facilities = new ArrayList<String>();
		List<String> userLogins = new ArrayList<String>();
		
		try {
			Optional<AppDevice> regWithPhoneNumber = appDeviceRepository.findBySecurityCodeAndAppNameAndActiveStatus(fpMasterDto.getPhoneNum(),fpMasterDto.getAppName(),"yes");
			if (regWithPhoneNumber.isPresent()) {
				log.info("** registration  completed ***");
				AppDevice appDevice = regWithPhoneNumber.get();
				if (appDevice.getDeviceId() == null) {
					appDevice.setDeviceId(fpMasterDto.getImeiNo());
					appDeviceRepository.save(appDevice);
				} else {
					Optional<AppDevice> regWithPhoneNumberAndDevId = appDeviceRepository.findBySecurityCodeAndAppNameAndActiveStatusAndDeviceId(fpMasterDto.getPhoneNum(),fpMasterDto.getAppName(),"yes",fpMasterDto.getImeiNo());
					if (!regWithPhoneNumberAndDevId.isPresent()) {
						fpMasterDto.setImeiAuth(false);
						fpMasterDto.setMessage("Please contact admin");
						return fpMasterDto;
					}
				}
				
			} else {
				log.info("** Registration not completed ***");
				fpMasterDto.setImeiAuth(false);
				fpMasterDto.setMessage("phone number not Registered.");
				return fpMasterDto;
			}
			
			List<AppDevice> regDeviceList = appDeviceRepository.findByDeviceIdAndSecurityCodeAndAppNameAndActiveStatus(fpMasterDto.getImeiNo(),fpMasterDto.getPhoneNum(),fpMasterDto.getAppName(),"yes");
				if (regDeviceList.size() == 0) {
					fpMasterDto.setImeiAuth(false);
					fpMasterDto.setMessage("Given phoneNumber and android id server not found record with app name");
					return fpMasterDto;
				} else if(regDeviceList.size() > 1){
					fpMasterDto.setImeiAuth(false);
					fpMasterDto.setMessage("Given phoneNumber and android id contains multiple records with app name ..contact server admin");
					return fpMasterDto;
				}else {
					AppDevice appDeviceList = regDeviceList.get(0);
					Optional<AppDeviceUnit> appDeviceUnitList = appDeviceUnitRepository.findByAppDeviceSeqId(appDeviceList.getSeqId());
					if (appDeviceUnitList.isPresent()) {
						AppDeviceUnit appDevUnit = appDeviceUnitList.get(); 
						facilities.add(appDevUnit.getUnitId());
					} else {
						fpMasterDto.setImeiAuth(false);
						fpMasterDto.setMessage("Device is not associated with any facility");
						return fpMasterDto;
					}
					List<AppDeviceLogin> appDevLoginList = appDeviceLoginRepository.findByAppDeviceSeqIdAndAppName(appDeviceList.getSeqId(),appDeviceList.getAppName());
					
					for (AppDeviceLogin appDeviceLogin : appDevLoginList) {
						log.info("** device associated with this user Login **"+appDeviceLogin.getUserLoginId());
						userLogins.add(appDeviceLogin.getUserLoginId());
					}
					fpMasterDto.setImeiAuth(true);
					fpMasterDto.setMessage("result is success with given app name ,phone number and android id");
				}
		}catch (Exception e) {
			log.info("error message >>>"+e.getMessage());
			// TODO: handle exception
		}
		
		try {
			ResponseFootPatrollingInspectionDto responseFootPatrollingInspectionDto = fpMasterDto.getAppToServerCreatedFootPatrollingInspectionDto();
			ResponseObservationsDto responseObservationsDto = fpMasterDto.getAppToServerCreatedResponseObservationsDto();
			ResponseCompliancesDto responseCompliancesDto = fpMasterDto.getAppToServerCreatedResponseCompliancesDto();
			fpMasterDto.getAppToServerCreatedResponseFpMovementDto();
			
			// user login
			log.info("preparing user login unsynch data");
			fpMasterDto.setCreatedResponseUserLoginDto(this.getNewUserLoginData(userLoginRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThanAndUserLoginIdIn(currenTimestamp,previousTimestamp,userLogins)));
			fpMasterDto.setUpdatedResponseUserLoginDto(this.getUpdatedUserLoginData(userLoginRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThanAndUserLoginIdIn(currenTimestamp,previousTimestamp,userLogins)));
			// facility
			log.info("preparing facility unsynch data");
			fpMasterDto.setCreatedResponseFacilityDto(this.getNewFacilityData(facilityRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			fpMasterDto.setUpdatedResponseFacilityDto(this.getUpdatedFacilityData(facilityRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			// product
			log.info("preparing product unsynch data");
			List<String> productTypeList = new ArrayList<String>();
			productTypeList.add("RAW_MATERIAL");
			productTypeList.add("FINISHED_GOOD");
			fpMasterDto.setCreatedResponseProductDto(this.getNewProductData(productRepository.findByProductTypeIdNotInAndCreatedStampLessThanEqualAndCreatedStampGreaterThan(productTypeList,currenTimestamp,previousTimestamp)));
			fpMasterDto.setUpdatedResponseProductDto(this.getUpdatedProductData(productRepository.findByProductTypeIdNotInAndLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(productTypeList,currenTimestamp,previousTimestamp)));
			// inspection type
			log.info("preparing inspection type unsynch data");
			fpMasterDto.setCreatedResponseInspectionTypeDto(this.getInspectionTypeData(inspectionTypeRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			fpMasterDto.setUpdatedResponseInspectionTypeDto(this.getInspectionTypeData(inspectionTypeRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			// observation categories
			log.info("preparing observation categories unsynch data");
			fpMasterDto.setCreatedObservationCategoriesDto(this.getObservationCategoriesData(observationCategoryRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			fpMasterDto.setUpdatedObservationCategoriesDto(this.getObservationCategoriesData(observationCategoryRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			// observations check list
			log.info("preparing observation check list unsynch data");
			fpMasterDto.setCreatedObservationsCheckListDto(this.getObservationsCheckListData(observationCheckListRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			fpMasterDto.setUpdatedObservationsCheckListDto(this.getObservationsCheckListData(observationCheckListRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			// foot patrolling sections
			log.info("preparing foot patrolling sections unsynch data");
			fpMasterDto.setCreatedFootPatrollingSectionsDto(this.getFootPatrollingSections(footPatrollingSectionRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			fpMasterDto.setUpdatedFootPatrollingSectionsDto(this.getFootPatrollingSections(footPatrollingSectionRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			// functional location hierarchy
			log.info("preparing functional location hierarchy unsynch data");
			fpMasterDto.setCreatedFunctionalLocationHierarchyDto(this.getFunctionalLocationHierarchy(functionalLocationHierarchyRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			fpMasterDto.setUpdatedFunctionalLocationHierarchyDto(this.getFunctionalLocationHierarchy(functionalLocationHierarchyRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			// ohe locations
			log.info("preparing ohe locations unsynch data");
			fpMasterDto.setCreatedResponseOheLocationDto(this.getOheLocationData(oheLocationRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThanAndOheMastIsNotNullAndLatitudeIsNotNullAndLongitudeIsNotNull(currenTimestamp,previousTimestamp)));
			fpMasterDto.setUpdatedResponseOheLocationDto(this.getOheLocationData(oheLocationRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThanAndOheMastIsNotNullAndLatitudeIsNotNullAndLongitudeIsNotNull(currenTimestamp,previousTimestamp)));
			// foot patrolling Inspection
			log.info("preparing foot patrolling  inspection unsynch data");
			fpMasterDto.setServerToAppCreatedFootPatrollingInspectionDto(this.getFootPatrollingInspectionData(footPatrollingInspectionRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			fpMasterDto.setServerToAppUpdatedFootPatrollingInspectionDto(this.getFootPatrollingInspectionData(footPatrollingInspectionRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			// observations	
			log.info("preparing observations unsynch data");
			fpMasterDto.setServerToAppCreatedResponseObservationsDto(this.getObservationsData(observationsRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			fpMasterDto.setServerToAppupdatedResponseObservationsDto(this.getObservationsData(observationsRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(currenTimestamp,previousTimestamp)));
			
			log.info("*** app to server insert process started ***");
			
			Integer reponseFootPatrollingInspectionDtoCount = 0;
			if ( responseFootPatrollingInspectionDto != null) {
				reponseFootPatrollingInspectionDtoCount = responseFootPatrollingInspectionDto.getCount();
				log.info("*** foot patrolling inspection record count ***"+reponseFootPatrollingInspectionDtoCount);
			}
			
			if(reponseFootPatrollingInspectionDtoCount != 0) {
				Map<String, String> map = new HashMap<String, String>();
				for (FootPatrollingInspectionDto fpInspectionDto : responseFootPatrollingInspectionDto.getFootPatrollingInspectionDtos()) {
						Timestamp startTime = null;
						Timestamp stopTime = null;
						DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSSSSS");
						if (fpInspectionDto.getStartTime() != null) {
							try {
								startTime = new Timestamp(dateFormat.parse(fpInspectionDto.getStartTime()).getTime());
							} catch (ParseException e) {
								
								e.printStackTrace();
							}
						}
						
						if (fpInspectionDto.getStopTime() != null) {
							try {
								stopTime = new Timestamp(dateFormat.parse(fpInspectionDto.getStopTime()).getTime());
							} catch (ParseException e) {
								
								e.printStackTrace();
							}
							
						}
					FootPatrollingInspection footPatrollingInspection = new FootPatrollingInspection();
					footPatrollingInspection.setDeviceId(fpInspectionDto.getDeviceId());
					footPatrollingInspection.setDeviceSeqId(fpInspectionDto.getDeviceSeqId());
					footPatrollingInspection.setInspectionType(fpInspectionDto.getInspectionType());
					footPatrollingInspection.setInspectionBy(fpInspectionDto.getInspectionBy());
					footPatrollingInspection.setSection(fpInspectionDto.getSection());
					footPatrollingInspection.setFacilityId(fpInspectionDto.getFacilityId());
					footPatrollingInspection.setKm(fpInspectionDto.getKm());
					footPatrollingInspection.setLocation(fpInspectionDto.getLocation());
					footPatrollingInspection.setStartTime(startTime);
					footPatrollingInspection.setStopTime(stopTime);
					FootPatrollingInspection savedFPI = footPatrollingInspectionRepository.save(footPatrollingInspection);

					map.put(fpInspectionDto.getDeviceSeqId()+"_"+fpInspectionDto.getDeviceId(), savedFPI.getId().toString());
				}
				fpMasterDto.setServerToAppFootPatrollingInspectionMap(map);
			}
			
			Integer responseObservationsDtoCount = 0;
			if (responseObservationsDto != null) {
				responseObservationsDtoCount = responseObservationsDto.getCount();
				log.info("*** Observations record  Count ***"+responseObservationsDtoCount);
			}
			if(responseObservationsDtoCount != 0) {
				Map<String, String> map = new HashMap<String, String>();
				for (ObservationsDto observationDto : responseObservationsDto.getObservationsDtos()) {
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSSSSS");
					
					Optional<FootPatrollingInspection> FPInspection = footPatrollingInspectionRepository.findByDeviceIdAndDeviceSeqId(observationDto.getDeviceId(),observationDto.getDeviceSeqId());
					if (FPInspection.isPresent()) {
						Observation observation = new Observation();
						observation.setDeviceId(observationDto.getDeviceId());
						observation.setDeviceSeqId(observationDto.getDeviceSeqId());
						observation.setCreatedBy(observationDto.getCreatedBy());
						observation.setAction(observationDto.getAction());
						observation.setActionBy(observationDto.getActionBy());
						observation.setObservationCategory(observationDto.getObservationCategory());
						observation.setObservationItem(observationDto.getObservationItem());
						observation.setObservation(observationDto.getObservation());
						observation.setDescription(observationDto.getDescription());
						observation.setLocation(observationDto.getLocation());
						if (observationDto.getCreatedDateTime() != null) {
							observation.setCreatedDateTime( new Timestamp(dateFormat.parse(observationDto.getCreatedDateTime()).getTime()));
						}
						Observation savedObservation = observationsRepository.save(observation);
					
						map.put(observationDto.getDeviceSeqId()+"_"+observationDto.getDeviceId(), savedObservation.getId().toString());
				  }
				}
				fpMasterDto.setServerToAppObservationMap(map);
			}
			
			Integer responseCompliancesDtoCount = 0;
			
			if (responseCompliancesDto != null) {
				responseCompliancesDtoCount = responseCompliancesDto.getCount();
				log.info("** compliance record count ****"+responseCompliancesDtoCount);
			}
			if (responseCompliancesDtoCount != 0) {
				Map<String, String> map = new HashMap<String, String>(); 
				for (CompliancesDto compliancesDto : responseCompliancesDto.getCompliancesDtos()) {
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSSSSS");
					
					Optional<Observation> observation = observationsRepository.findByDeviceIdAndDeviceSeqId(compliancesDto.getDeviceId(),compliancesDto.getDeviceSeqId());
					if (observation.isPresent()) {
						Compliance compliance = new Compliance();
						compliance.setAction(compliancesDto.getAction());
						compliance.setDeviceId(compliancesDto.getDeviceId());
						compliance.setDeviceSeqId(compliancesDto.getDeviceSeqId());
						compliance.setComplianceFullfilled(compliancesDto.getComplianceFulfilled());
						compliance.setDescription(compliancesDto.getDescription());
						compliance.setStatus(compliancesDto.getStatus());
						compliance.setComplianceBy(compliancesDto.getComplianceBy());
						compliance.setComplianceRemark(compliancesDto.getComplianceRemark());
						if (compliancesDto.getCompliedDateTime() != null) {
							compliance.setCompliedDateTime(new Timestamp(dateFormat.parse(compliancesDto.getCompliedDateTime()).getTime()));
						}
						Compliance savedComp = complianceRepository.save(compliance);
						map.put(compliancesDto.getDeviceSeqId()+"_"+compliancesDto.getDeviceId(), savedComp.getId().toString());
				   }
				}		
				fpMasterDto.setServerToAppCompliancesMap(map);
			}
			log.info("*** app to server insert process end ***");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("error message >>>>"+e.getMessage());
			e.printStackTrace();
		}
		return fpMasterDto;
	}
	
	public ResponseObservationsDto getObservationsData(List<Observation> observations){
		Integer size = observations.size();
		ResponseObservationsDto responseObservationsDto = new ResponseObservationsDto();
		if (size > 0) {
			List<ObservationsDto> observationsDtos = getObservations(observations);
			responseObservationsDto.setObservationsDtos(observationsDtos);
			responseObservationsDto.setCount(size);
			return responseObservationsDto;
		}
		return null;
	}
	
	public List<ObservationsDto> getObservations(List<Observation> observations) {
		// TODO Auto-generated method stub
		List<ObservationsDto> observationsDtos = new ArrayList<ObservationsDto>();
		for (Observation observation : observations) {
			ObservationsDto observationsDto = new ObservationsDto();
			observationsDto.setSeqId(observation.getSeqId());
			observationsDto.setDeviceId(observation.getDeviceId());
			observationsDto.setDeviceSeqId(observation.getDeviceSeqId());
			observationsDto.setInspectionSeqId(observation.getInspectionSeqId());
			observationsDto.setObservation(observation.getObservation());
			observationsDto.setObservationCategory(observation.getObservationCategory());
			observationsDto.setObservationItem(observation.getObservationItem());
			observationsDto.setAction(observation.getAction());
			observationsDto.setActionBy(observation.getActionBy());
			if (observation.getCreatedDateTime() != null) {
				observationsDto.setCreatedDateTime(observation.getCreatedDateTime().toString());
			}
			observationsDto.setCreatedBy(observation.getCreatedBy());
			observationsDto.setDescription(observation.getDescription());
			observationsDtos.add(observationsDto);
		}
		return observationsDtos;
	}
	
	public ResponseFootPatrollingInspectionDto getFootPatrollingInspectionData(List<FootPatrollingInspection> footPatrollingInspections){
		Integer size = footPatrollingInspections.size();
		ResponseFootPatrollingInspectionDto responseFootPatrollingInspectionDto = new ResponseFootPatrollingInspectionDto();
		if (size > 0) {
				List<FootPatrollingInspectionDto> footPatrollingInspectionDtos = getFootPatrollingInspections(footPatrollingInspections);
				responseFootPatrollingInspectionDto.setFootPatrollingInspectionDtos(footPatrollingInspectionDtos);
				responseFootPatrollingInspectionDto.setCount(size);
			return responseFootPatrollingInspectionDto;
		}
		return null;
	}
	public List<FootPatrollingInspectionDto> getFootPatrollingInspections(List<FootPatrollingInspection> footPatrollingInspections){
		List<FootPatrollingInspectionDto> footPatrollingInspectionDtos = new ArrayList<FootPatrollingInspectionDto>();
	
		for (FootPatrollingInspection footPatrollingInspection : footPatrollingInspections) {
			FootPatrollingInspectionDto footPatrollingInspectionDto = new FootPatrollingInspectionDto();
			footPatrollingInspectionDto.setSeqId(footPatrollingInspection.getSeqId());
			footPatrollingInspectionDto.setFacilityId(footPatrollingInspection.getFacilityId());
			footPatrollingInspectionDto.setInspectionType(footPatrollingInspection.getInspectionType());
			footPatrollingInspectionDto.setInspectionBy(footPatrollingInspection.getInspectionBy());
			footPatrollingInspectionDto.setSection(footPatrollingInspection.getSection());
			footPatrollingInspectionDto.setKm(footPatrollingInspection.getKm());
			footPatrollingInspectionDto.setLocation(footPatrollingInspection.getLocation());
			if (footPatrollingInspection.getStartTime() != null) {
				footPatrollingInspectionDto.setStartTime(footPatrollingInspection.getStartTime().toString());
			}
			if (footPatrollingInspection.getStopTime() != null ) {
				footPatrollingInspectionDto.setStartTime(footPatrollingInspection.getStopTime().toString());
			}
			footPatrollingInspectionDtos.add(footPatrollingInspectionDto);
		}
		return footPatrollingInspectionDtos;
	}
	
	public ResponseOheLocationDto getOheLocationData(List<OheLocation> oheLocations){
		   Integer size = oheLocations.size();
		   ResponseOheLocationDto responseOheLocationDto = new ResponseOheLocationDto();
			if (size > 0) {
				List<OheLocationDto> oheLocationDtos = getOheLocationDtos(oheLocations);
				responseOheLocationDto.setOheLocationDtos(oheLocationDtos);
				responseOheLocationDto.setCount(size);
				return responseOheLocationDto;
			}
			responseOheLocationDto.setCount(size);
			return responseOheLocationDto;
		   
	   }

	public  List<OheLocationDto> getOheLocationDtos(List<OheLocation> oheLocations) {
		List<OheLocationDto> oheLocationDtos = new ArrayList<OheLocationDto>();
		for(OheLocation oheLocation : oheLocations){
			OheLocationDto oheLocationDto = new OheLocationDto();
			oheLocationDto.setCreatedStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(oheLocation.getCreatedStamp()));
			oheLocationDto.setCreatedTxStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(oheLocation.getCreatedTxStamp()));
			if(oheLocation.getDate() != null){
			oheLocationDto.setDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.s").format(oheLocation.getDate()));
			}
			oheLocationDto.setLastUpdatedStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(oheLocation.getLastUpdatedStamp()));
			oheLocationDto.setLastUpdatedTxStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(oheLocation.getLastUpdatedTxStamp()));
			if(oheLocation.getLatitude() != null){
			oheLocationDto.setLatitude(oheLocation.getLatitude());
			}
			if(oheLocation.getLongitude() != null){
			oheLocationDto.setLongitude(oheLocation.getLongitude());
			}
			oheLocationDto.setKilometer(Double.toString((oheLocation.getKilometer())));
			oheLocationDto.setOheMast(oheLocation.getOheMast());
			oheLocationDto.setSeqId(oheLocation.getSeqId());
			oheLocationDto.setFacilityId(oheLocation.getFacilityId());
			
			oheLocationDtos.add(oheLocationDto);
			
		}
		return oheLocationDtos;
	}
	
	public ResponseFunctionalLocationHierarchyDto getFunctionalLocationHierarchy(List<FunctionalLocationHierarchy> functionalLocationHierarchies){
		Integer size = functionalLocationHierarchies.size(); 
		ResponseFunctionalLocationHierarchyDto responseFunctionalLocationHierarchyDto = new ResponseFunctionalLocationHierarchyDto();
		if (size > 0) {
			List<FunctionalLocationHierarchyDto> functionalLocationHierarchyDtos =getfunctionalLocationHierarchies(functionalLocationHierarchies);
			responseFunctionalLocationHierarchyDto.setFunctionalLocationHierarchyDtos(functionalLocationHierarchyDtos);
			responseFunctionalLocationHierarchyDto.setCount(size);
			return responseFunctionalLocationHierarchyDto;
		}
		responseFunctionalLocationHierarchyDto.setCount(size);
		return responseFunctionalLocationHierarchyDto;
	}
	
	
	public List<FunctionalLocationHierarchyDto> getfunctionalLocationHierarchies(List<FunctionalLocationHierarchy> functionalLocationHierarchies){
		List<FunctionalLocationHierarchyDto> functionalLocationHierarchyDtos = new ArrayList<FunctionalLocationHierarchyDto>();
		for (FunctionalLocationHierarchy functionalLocationHierarchy : functionalLocationHierarchies) {
			FunctionalLocationHierarchyDto functionalLocationHierarchyDto = new FunctionalLocationHierarchyDto();
			functionalLocationHierarchyDto.setGroupId(functionalLocationHierarchy.getGroupId());
			functionalLocationHierarchyDto.setHeadDesignation(functionalLocationHierarchy.getHeadDesignation());
			functionalLocationHierarchyDto.setHeadLoginId(functionalLocationHierarchy.getHeadLoginId());
			functionalLocationHierarchyDto.setId(functionalLocationHierarchy.getId().toString());
			functionalLocationHierarchyDto.setOrgLevel(functionalLocationHierarchy.getOrgLevel());
			functionalLocationHierarchyDto.setPartyId(functionalLocationHierarchy.getPartyId());
			functionalLocationHierarchyDto.setReportManager(functionalLocationHierarchy.getReportManager());
			functionalLocationHierarchyDto.setRmLoginId(functionalLocationHierarchy.getRmLoginId());
			functionalLocationHierarchyDto.setRmSeqId(functionalLocationHierarchy.getRmSeqId());
			functionalLocationHierarchyDto.setUnitCode(functionalLocationHierarchy.getUnitCode());
			functionalLocationHierarchyDto.setUnitName(functionalLocationHierarchy.getUnitName());
			functionalLocationHierarchyDto.setUnitStation(functionalLocationHierarchy.getUnitStation());
			functionalLocationHierarchyDto.setUnitType(functionalLocationHierarchy.getUnitType());
			functionalLocationHierarchyDtos.add(functionalLocationHierarchyDto);
		}
		return functionalLocationHierarchyDtos;
	}
	
	public ResponseFootPatrollingSectionsDto getFootPatrollingSections(List<FootPatrollingSection> footPatrollingSections){
		Integer size = footPatrollingSections.size(); 
		ResponseFootPatrollingSectionsDto responseFootPatrollingSectionsDto = new ResponseFootPatrollingSectionsDto();
		if (size > 0) {
			List<FootPatrollingSectionsDto> footPatrollingSectionsDtos = getFootPatrollingSectionsDtos(footPatrollingSections);
			responseFootPatrollingSectionsDto.setFootPatrollingSectionsDtos(footPatrollingSectionsDtos);
			responseFootPatrollingSectionsDto.setCount(size);
			return responseFootPatrollingSectionsDto;
		}
		responseFootPatrollingSectionsDto.setCount(size);
		return responseFootPatrollingSectionsDto;
	}
	
	public List<FootPatrollingSectionsDto> getFootPatrollingSectionsDtos(List<FootPatrollingSection> footPatrollingSections){
		List<FootPatrollingSectionsDto> footPatrollingSectionsDtos = new ArrayList<FootPatrollingSectionsDto>();
		for (FootPatrollingSection footPatrollingSection : footPatrollingSections) {
			FootPatrollingSectionsDto footPatrollingSectionsDto = new FootPatrollingSectionsDto();
			footPatrollingSectionsDto.setFacilityDepot(footPatrollingSection.getFacilityDepot());
			footPatrollingSectionsDto.setFpSection(footPatrollingSection.getFpSection());
			footPatrollingSectionsDto.setFromLocation(footPatrollingSection.getFromLocation());
			footPatrollingSectionsDto.setRemarks(footPatrollingSection.getRemarks());
			footPatrollingSectionsDto.setSeqId(footPatrollingSection.getSeqId());
			footPatrollingSectionsDto.setToLocation(footPatrollingSection.getToLocation());
			if(footPatrollingSection.getFromDate() != null){
			footPatrollingSectionsDto.setFromDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(footPatrollingSection.getFromDate()));
			}
			if(footPatrollingSection.getToDate() != null){
			footPatrollingSectionsDto.setToDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(footPatrollingSection.getToDate()));
			}
			footPatrollingSectionsDtos.add(footPatrollingSectionsDto);
		}
		return footPatrollingSectionsDtos;
	}
	
	public ResponseObservationsCheckListDto getObservationsCheckListData(List<ObservationsCheckList> observationsCheckLists){
		Integer size = observationsCheckLists.size(); 
		ResponseObservationsCheckListDto responseObservationsCheckListDto = new ResponseObservationsCheckListDto();
		if (size > 0) {
			List<ObservationsCheckListDto> observationsCheckListDtos = getObservationsCheckListDtos(observationsCheckLists);
			responseObservationsCheckListDto.setObservationsCheckListDtos(observationsCheckListDtos);
			responseObservationsCheckListDto.setCount(size);
			return responseObservationsCheckListDto;
		}
		responseObservationsCheckListDto.setCount(size);
		return responseObservationsCheckListDto;
	}
	
	public List<ObservationsCheckListDto> getObservationsCheckListDtos(List<ObservationsCheckList> observationsCheckLists){
		List<ObservationsCheckListDto> observationsCheckListDtos = new ArrayList<ObservationsCheckListDto>();
		for (ObservationsCheckList observationsCheckList : observationsCheckLists) {
			ObservationsCheckListDto observationsCheckListDto = new ObservationsCheckListDto();
			observationsCheckListDto.setDescription(observationsCheckList.getDescription());
			observationsCheckListDto.setInspectionType(observationsCheckList.getInspectionType());
			observationsCheckListDto.setObservationCategory(observationsCheckList.getObservationCategory());
			observationsCheckListDto.setObservationItem(observationsCheckList.getObservationItem());
			observationsCheckListDto.setSeqId(observationsCheckList.getSeqId());
			observationsCheckListDto.setPriority(observationsCheckList.getPriority());
			observationsCheckListDto.setDisplaySequence(observationsCheckList.getDisplaySequence());
			if(observationsCheckList.getFromDate() != null){
			observationsCheckListDto.setFromDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(observationsCheckList.getFromDate()));
			}
			if(observationsCheckList.getThruDate() != null){
			observationsCheckListDto.setThruDate(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(observationsCheckList.getThruDate()));
			}
			observationsCheckListDto.setSeverity(observationsCheckList.getSeverity());
			observationsCheckListDtos.add(observationsCheckListDto);
		}
		return observationsCheckListDtos;
	}
	
	public ResponseObservationCategoriesDto getObservationCategoriesData(List<ObservationCategory> observationCategories){
		Integer size = observationCategories.size(); 
		ResponseObservationCategoriesDto responseObservationCategoriesDto = new ResponseObservationCategoriesDto();
		if (size > 0) {
			List<ObservationCategoriesDto> observationCategoriesDtos = getObservationCategoriesDtos(observationCategories);
			responseObservationCategoriesDto.setObservationCategoriesDtos(observationCategoriesDtos);
			responseObservationCategoriesDto.setCount(size);
			return responseObservationCategoriesDto;
		}
		responseObservationCategoriesDto.setCount(size);
		return responseObservationCategoriesDto;
	}
	
	public List<ObservationCategoriesDto> getObservationCategoriesDtos(List<ObservationCategory> observationCategories) {
		List<ObservationCategoriesDto> observationCategoriesDtos = new ArrayList<ObservationCategoriesDto>();
		for (ObservationCategory observationCategory: observationCategories) {
			ObservationCategoriesDto observationCategoriesDto = new ObservationCategoriesDto();
			observationCategoriesDto.setSeqId(observationCategory.getSeqId());
			observationCategoriesDto.setInspectionType(observationCategory.getInspectionType());
			observationCategoriesDto.setDepartment(observationCategory.getDepartment());
			observationCategoriesDto.setDescription(observationCategory.getDescription());
			observationCategoriesDto.setObservationCategory(observationCategory.getObservationCategory());
			observationCategoriesDto.setRemark(observationCategory.getRemark());
			if (observationCategory.getFromDate() != null) {
				observationCategoriesDto
						.setFromDate(new SimpleDateFormat("yyyy-MM-dd").format(observationCategory.getFromDate()));
			}
			if (observationCategory.getThruDate() != null) {
				observationCategoriesDto
						.setThruDate(new SimpleDateFormat("yyyy-MM-dd").format(observationCategory.getThruDate()));
			}
			observationCategoriesDtos.add(observationCategoriesDto);
		}
		return observationCategoriesDtos;
	}
	
	public ResponseInspectionTypeDto getInspectionTypeData(List<InspectionType> inspectionTypes){
		Integer size = inspectionTypes.size(); 
		ResponseInspectionTypeDto responseInspectionTypeDto = new ResponseInspectionTypeDto();
		if (size > 0) {
			List<InspectionTypeDto> inspectionTypeDtos = getInspectionTypeDtos(inspectionTypes);
			responseInspectionTypeDto.setInspectionTypeDtos(inspectionTypeDtos);
			responseInspectionTypeDto.setCount(size);
			return responseInspectionTypeDto;
		}
		return null;
	}
	
	public List<InspectionTypeDto> getInspectionTypeDtos(List<InspectionType> inspectionTypes){
		List<InspectionTypeDto> inspectionTypeDtos = new ArrayList<InspectionTypeDto>();
		for (InspectionType inspectionType : inspectionTypes) {
			InspectionTypeDto inspectionTypeDto = new InspectionTypeDto();
			
			inspectionTypeDto.setSeqId(inspectionType.getSeqId());
			inspectionTypeDto.setDepartment(inspectionType.getDepartment());
			inspectionTypeDto.setInspectionType(inspectionType.getInspectionType());
			inspectionTypeDto.setDescription(inspectionType.getDescription());
			if(inspectionType.getFromDate() != null){
			inspectionTypeDto.setFromDate(new SimpleDateFormat("yyyy-MM-dd").format(inspectionType.getFromDate()));
			}
			if(inspectionType.getThruDate() != null){
			inspectionTypeDto.setThruDate(new SimpleDateFormat("yyyy-MM-dd").format(inspectionType.getThruDate()));
			}
			inspectionTypeDtos.add(inspectionTypeDto);
		}
		return inspectionTypeDtos;
	}
	
	public ResponseProductDto getNewProductData(List<Product> products){
		Integer size = products.size(); 
		log.info("........response...product..size.." + products.size());
		ResponseProductDto responseProductDto = new ResponseProductDto();
		if (size > 0) {
			List<ProductDto> productDtos = getProductDtos(products);
			responseProductDto.setProductDtos(productDtos);
			responseProductDto.setCount(size);
			return responseProductDto;
		}
		responseProductDto.setCount(size);
		return responseProductDto;
		
	}
	public ResponseProductDto getUpdatedProductData(List<Product> products){
		Integer size = products.size();
		ResponseProductDto responseProductDto = new ResponseProductDto();
		if (size > 0) {
			List<ProductDto> productDtos = getProductDtos(products);
			responseProductDto.setProductDtos(productDtos);
			responseProductDto.setCount(size);
			return responseProductDto;
		}
		responseProductDto.setCount(size);
		return responseProductDto;
		
	}
	public List<ProductDto> getProductDtos(List<Product> products){
		List<ProductDto> productDtos = new ArrayList<ProductDto>();
		for (Product product : products) {
			ProductDto productDto = new ProductDto();
			//productDto.setAmountUomTypeId(product.getString("amountUomTypeId"));
			//productDto.setAutoCreateKeywords(product.getString("autoCreateKeywords"));
			//productDto.setBillOfMaterialLevel(product.getString("billOfMaterialLevel"));
			//productDto.setBrandName(product.getString("brandName"));
			//productDto.setChargeShipping(product.getString("chargeShipping"));
			productDto.setComments(product.getComments());
			//productDto.setConfigId(product.getString("configId"));
			productDto.setCreatedByUserLogin(product.getCreatedByUserLogin());
			//productDto.setCreatedDate(product.getCreatedDate());
			productDto.setCreatedStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(product.getCreatedStamp()));
			productDto.setCreatedTxStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(product.getCreatedTxStamp()));
			//productDto.setDefaultShipmentBoxTypeId(product.getString("defaultShipmentBoxTypeId"));
			productDto.setDepthUomId(product.getDepthUomId());
			productDto.setDescription(product.getDescription());
			//productDto.setDetailImageUrl(product.getString("detailImageUrl"));
			//productDto.setDetailScreen(product.getString("detailScreen"));
			productDto.setDiameterUomId(product.getDiameterUomId());
			productDto.setFacilityId(product.getFacilityId());
			//productDto.setFixedAmount(product.getString("fixedAmount"));
			productDto.setHeightUomId(product.getHeightUomId());
			//productDto.setInShippingBox(product.getString("inShippingBox"));
			//productDto.setIncludeInPromotions(product.getString("includeInPromotions"));
			//productDto.setInternalName(product.getString("internalName"));
			//productDto.setIntroductionDate(product.getString("introductionDate"));
			//productDto.setInventoryMessage(product.getString("inventoryMessage"));
			productDto.setIsActive(product.getIsActive());
			productDto.setIsSerialized(product.getIsSerialized());
			//productDto.setIsVariant(product.getString("isVariant"));
			//productDto.setIsVirtual(product.getString("isVirtual"));
			//productDto.setLargeImageUrl(product.getString("largeImageUrl"));
			productDto.setLastModifiedByUserLogin(product.getLastModifiedByUserLogin());
			//productDto.setLastModifiedDate(product.getLastModifiedDate());
			productDto.setLastUpdatedStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(product.getLastUpdatedStamp()));
			productDto.setLastUpdatedTxStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(product.getLastUpdatedTxStamp()));
			productDto.setLongDescription(product.getLongDescription());
			//productDto.setManufacturerPartyId(product.getString("manufacturerPartyId"));
			productDto.setMaterialClassification(product.getMaterialClassification());
			//productDto.setMediumImageUrl(product.getString("mediumImageUrl"));
			//productDto.setOriginGeoId(product.getString("originGeoId"));
			//productDto.setOriginalImageUrl(product.getString("originalImageUrl"));
			//productDto.setPiecesIncluded(product.getString("piecesIncluded"));
			productDto.setPlNo(product.getPlNo());
			//productDto.setPriceDetailText(product.getString("priceDetailText"));
			productDto.setPrimaryProductCategoryId(product.getPrimaryProductCategoryId());
			productDto.setProductCodeTypeId(product.getProductCodeTypeId());
			if(product.getProductDepth() != null) {
			productDto.setProductDepth(product.getProductDepth().toString());
			}
			if(product.getProductDiameter() != null) {
			productDto.setProductDiameter(product.getProductDiameter().toString());
			}
			if(product.getProductHeight() != null) {
			productDto.setProductHeight(product.getProductHeight().toString());
			}
			productDto.setProductId(product.getProductId());
			//productDto.setProductMakeDetails(product.getString("productMakeDetails"));
			productDto.setProductName(product.getProductName());
			//productDto.setProductRating(product.getString("productRating"));
			productDto.setProductTypeId(product.getProductTypeId());
			if(product.getProductWeight() != null) {
			productDto.setProductWeight(product.getProductWeight().toString());
			}
			if(product.getQuantityIncluded() != null) {
			productDto.setQuantityIncluded(product.getQuantityIncluded().toString());
			}
			productDto.setQuantityUomId(product.getQuantityUomId());
			//productDto.setRatingTypeEnum(product.getString("ratingTypeEnum"));
			//productDto.setReleaseDate(product.getString("releaseDate"));
			//productDto.setRequireAmount(product.getString("requireAmount"));
			//productDto.setRequireInventory(product.getString("requireInventory"));
			//productDto.setRequirementMethodEnumId(product.getString("requirementMethodEnumId"));
			//productDto.setReservMaxPersons(product.getString("reservMaxPersons"));
			//productDto.setReservNthPPPerc(product.getString("reservNthPPPerc"));
			//productDto.setReserv2ndPPPerc(product.getString("reserv2ndPPPerc"));
			//productDto.setReturnable(product.getString("returnable"));
			productDto.setRlyId(product.getRlyId());
			//productDto.setSalesDiscWhenNotAvail(product.getString("salesDiscWhenNotAvail"));
			//productDto.setSalesDiscontinuationDate(product.getString("salesDiscontinuationDate"));
			//productDto.setShippingDepth(product.getString("shippingDepth"));
			//productDto.setShippingHeight(product.getString("shippingHeight"));
			//productDto.setShippingWidth(product.getString("shippingWidth"));
			//productDto.setSmallImageUrl(product.getString("smallImageUrl"));
			//productDto.setSupportDiscontinuationDate(product.getString("supportDiscontinuationDate"));
			//productDto.setTaxable(product.getString("taxable"));
			productDto.setTrdDivId(product.getTrdDivId());
			//productDto.setVirtualVariantMethodEnum(product.getString("virtualVariantMethodEnum"));
			if(product.getWeight() != null) {
			productDto.setWeight(product.getWeight().toString());
			}
			productDto.setWeightUomId(product.getWeightUomId());
			productDto.setWidthUomId(product.getWidthUomId());
			productDtos.add(productDto);
		}
		return productDtos;
	}
	
	public ResponseFacilityDto getNewFacilityData(List<Facility> facilities){
		Integer size = facilities.size(); 
		log.info("........response...facility..size.." + facilities.size());
		ResponseFacilityDto responseFacilityDto = new ResponseFacilityDto();
		if (size > 0) {
			List<FacilityDto> facilityDtos = getFacilityDtoGenValues(facilities);
			responseFacilityDto.setFacilityDtos(facilityDtos);
			responseFacilityDto.setCount(size);
			return responseFacilityDto;
		}
		responseFacilityDto.setCount(size);
		return responseFacilityDto;
		
	}
	
	public ResponseFacilityDto getUpdatedFacilityData(List<Facility> facilities){
		Integer size = facilities.size(); 
		ResponseFacilityDto responseFacilityDto = new ResponseFacilityDto();
		if (size > 0) {
			List<FacilityDto> facilityDtos = getFacilityDtoGenValues(facilities);
			responseFacilityDto.setFacilityDtos(facilityDtos);
			responseFacilityDto.setCount(size);
			return responseFacilityDto;
		}
		responseFacilityDto.setCount(size);
		return responseFacilityDto;
		
	}
	
	public List<FacilityDto> getFacilityDtoGenValues(List<Facility> facilities){
		List<FacilityDto> facilityDtos = new ArrayList<FacilityDto>();
		for (Facility facility : facilities) {
			FacilityDto facilityDto = new FacilityDto();
			//facilityDto.setClosedDate(facility.getClosedDate());
			facilityDto.setCreatedStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(facility.getCreatedStamp()));
			facilityDto.setCreatedTxStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(facility.getCreatedTxStamp()));
			// facilityDto.setDefaultDaysToShip(facility.getString("defaultDaysToShip"));
			// facilityDto.setDefaultDimensionUomId(facility.getString("defaultDimensionUomId"));
			facilityDto.setDefaultInventoryItemTypeId(facility.getDefaultInventoryItemTypeId());
			facilityDto.setDefaultWeightUomId(facility.getDefaultWeightUomId());
			facilityDto.setDepotType(facility.getDepotType());
			facilityDto.setDescription(facility.getDescription());
			facilityDto.setFacilityId(facility.getFacilityId());
			facilityDto.setFacilityName(facility.getFacilityName());
			//facilityDto.setFacilitySize(facility.getString("facilitySize"));
			//facilityDto.setFacilitySizeUomId(facility.getString("facilitySizeUomId"));
			facilityDto.setFacilityTypeId(facility.getFacilityTypeId());
			//facilityDto.setGeoPointId(facility.getString("geoPointId"));
			facilityDto.setIsDisable(facility.getIsDisable());
			facilityDto.setLastUpdatedStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(facility.getLastUpdatedStamp()));
			facilityDto.setLastUpdatedTxStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(facility.getLastUpdatedTxStamp()));
			//facilityDto.setManufAllocEnable(facility.getString("manufAllocEnable"));
			//facilityDto.setOpenedDate(facility.getOpenedDate());
			//facilityDto.setOrganized(facility.getString("organized"));
			//facilityDto.setOwnerPartyId(facility.getString("ownerPartyId"));
			facilityDto.setParentFacilityId(facility.getParentFacilityId());
			facilityDto.setPrimaryFacilityGroupId(facility.getPrimaryFacilityGroupId());
			//facilityDto.setProductStoreId(facility.getString("productStoreId"));
			facilityDto.setRemarks(facility.getRemarks());
			facilityDto.setReserveOrderEnumId(facility.getReserveOrderEnumId());
			//facilityDto.setSkipPackInvCheck(facility.getString("skipPackInvCheck"));
			//facilityDto.setSquareFootage(facility.getString("squareFootage"));
			facilityDto.setParentDepot(facility.getParentDepot());
			facilityDto.setDivision(facility.getDivision());
			facilityDto.setSubDivision(facility.getSubDivision());
			facilityDto.setFpTrackEnable(facility.getFpTrackEnable());
			if(facility.getFpTrackRecordFrequency() != null) {
			facilityDto.setFpTrackRecordFrequency(facility.getFpTrackRecordFrequency().toString());
			}
			facilityDtos.add(facilityDto);
		}
		return facilityDtos;
	}
	
	public ResponseUserLoginDto getNewUserLoginData(List<UserLogin> userLogins){
		Integer size = userLogins.size(); 
		log.info("User Login size:::"+size);
		ResponseUserLoginDto responseUserLoginDto = new ResponseUserLoginDto();
		if (size > 0) {
			List<UserLoginDto> responseUserLoginDtos  = getUserLogins(userLogins);
			
			responseUserLoginDto.setUserLoginDtos(responseUserLoginDtos);
			responseUserLoginDto.setCount(size);
			log.info("*** response userLogin Dtos count ****"+responseUserLoginDto.getCount());
			return responseUserLoginDto;
		}
		responseUserLoginDto.setCount(size);
		return responseUserLoginDto;
	}
	
	public ResponseUserLoginDto getUpdatedUserLoginData(List<UserLogin> userLogins){
		Integer size = userLogins.size(); 
		ResponseUserLoginDto responseUserLoginDto = new ResponseUserLoginDto();
		if (size > 0) {
			List<UserLoginDto> responseUserLoginDtos  = getUserLogins(userLogins);
			responseUserLoginDto.setUserLoginDtos(responseUserLoginDtos);
			responseUserLoginDto.setCount(size);
			return responseUserLoginDto;
		}
		responseUserLoginDto.setCount(size);
		return responseUserLoginDto;
	}
	
	public List<UserLoginDto> getUserLogins(List<UserLogin> userLoginList){
		List<UserLoginDto> userLoginDtos = new ArrayList<UserLoginDto>();
		for (UserLogin userLogin : userLoginList) {
			UserLoginDto userLoginDto = new UserLoginDto();
			if(userLogin.getCreatedStamp() != null){
				userLoginDto.setCreatedStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(userLogin.getCreatedStamp()));
			}
			if(userLogin.getCreatedTxStamp() != null){
				userLoginDto.setCreatedTxStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(userLogin.getCreatedTxStamp()));
			}
			userLoginDto.setCurrentPassword(userLogin.getCurrentPassword());
			if(userLogin.getDisabledDateTime() != null){
				userLoginDto.setDisabledDateTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(userLogin.getDisabledDateTime()));
			}
			log.info("user Login Id::::"+userLogin.getUserLoginId());
			userLoginDto.setEnabled(userLogin.getEnabled());
			userLoginDto.setExternalAuthId(userLogin.getExternalAuthId());
			userLoginDto.setHasLoggedOut(userLogin.getHasLoggedOut());
			userLoginDto.setIsSystem(userLogin.getIsSystem());
			userLoginDto.setLastCurrencyUom(userLogin.getLastCurrencyUom());
			userLoginDto.setLastLocale(userLogin.getLastLocale());
			userLoginDto.setLastTimeZone(userLogin.getLastTimeZone());
			if(userLogin.getLastUpdatedStamp() != null){
				userLoginDto.setLastUpdatedStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(userLogin.getLastUpdatedStamp()));
			}
			if(userLogin.getLastUpdatedTxStamp() != null){
				userLoginDto.setLastUpdatedTxStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.S").format(userLogin.getLastUpdatedTxStamp()));
			}
			userLoginDto.setPartyId(userLogin.getPartyId());
			userLoginDto.setPasswordHint(userLogin.getPasswordHint());
			userLoginDto.setRequirePasswordChange(userLogin.getRequirePasswordChange());
			//userLoginDto.setSuccessiveFailedLogins(userLogin.getSuccessiveFailedLogins());
			userLoginDto.setUserLdapDn(userLogin.getUserLdapDn());
			userLoginDto.setUserLoginId(userLogin.getUserLoginId());
			userLoginDtos.add(userLoginDto);
		}
		return userLoginDtos;
	}

	public FpAppMasterDto getRepotNames() {
		FpAppMasterDto FPMasterDto = new FpAppMasterDto();
		try {
			List<ReportRepository> reportRegistries = reportRepository.findByReportCategory("FootPatrolling");
			List<String> reportNameList = new ArrayList<String>();
			for (ReportRepository reportRepository : reportRegistries) {
				reportNameList.add(reportRepository.getReportId());
			}
			FPMasterDto.setReportNames(reportNameList);
			log.info("*** report names list ***"+FPMasterDto.getReportNames());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FPMasterDto;
		
	}

	public ReportDto reportExecution(ReportDto reportDto) {
		Connection connection = null;
		JasperPrint jasperPrint = null;
		String facilityName = null;
		String jrxmlFileName = null;
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (reportDto.getFacilityId() != null) {
			Optional<Facility> facilityList =facilityRepository.findByFacilityId(reportDto.getFacilityId());
			if (facilityList.isPresent()) {
				facilityName = facilityList.get().getFacilityName();
			}
		}
		Optional<ReportRepository> reportRep = reportRepository.findByReportId(reportDto.getReportId());
		if (reportRep.isPresent()) {
			jrxmlFileName = reportRep.get().getJrxmlName();
		}
		log.info("facilityName::"+facilityName);
		log.info("report Id::"+reportDto.getReportId());
		log.info("from Date::"+reportDto.getFromDate());
		log.info("thru Date::"+reportDto.getThruDate());
		if ("FP_Summary_Rept".equals(reportDto.getReportId() ) && facilityName == null) {
			facilityName = "All";
		}
		log.info("**jrxml file name**"+jrxmlFileName);
		parameters.put("facilityName", facilityName);
		parameters.put("subDivision", reportDto.getSubDivision());
		parameters.put("fromDate", reportDto.getFromDate());
		parameters.put("toDate", reportDto.getThruDate());
		log.info("map object values:::"+parameters.values());
		try {
			connection = dataSource.getConnection();
			Resource resource = resourceLoader.getResource("classpath:jrxml/" + jrxmlFileName);
			String tempFilePath = null;
			try {
				InputStream inputStream = resource.getInputStream();
				File somethingFile = File.createTempFile(resource.getFilename(), ".jrxml");
				try {
					FileUtils.copyInputStreamToFile(inputStream, somethingFile);
				} finally {
					IOUtils.closeQuietly(inputStream);
				}
				log.info("File Path is " + somethingFile.getAbsolutePath());
				tempFilePath = somethingFile.getAbsolutePath();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			log.info("Resource File Path = " + tempFilePath);
			
			/*
			 * String path = this.getClass().getClassLoader().getResource("").getPath();
			 * String reportsBasePath = reportResource.getBasePath();
			 * log.info("*** report base path***"+reportsBasePath); String absolutePath =
			 * reportResource.getAbsolutePath(path, jrxmlFileName, parameters,
			 * reportsBasePath); log.info("*** report absolute path ***"+absolutePath);
			 */
			JasperReport jasperReport = JasperCompileManager.compileReport(tempFilePath);
			log.info("*** jasper report object ***" + jasperReport);
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
			byte[] reportResult = JasperExportManager.exportReportToPdf(jasperPrint);
			log.info("*** report result length ***"+reportResult.length);
			reportDto.setReportResult(reportResult);
		} catch (JRException e) {			
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeJDBCObjects.closeConnection(connection);
		}
		return reportDto;
	}
	
}
