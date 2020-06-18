package com.scr.services;

import java.sql.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scr.app.dto.FpAppMasterDto;
import com.scr.app.dto.ResponseUserLoginDto;
import com.scr.app.dto.UserLoginDto;
import com.scr.model.AppDevice;
import com.scr.model.AppDeviceLogin;
import com.scr.model.AppDeviceUnit;
import com.scr.model.UserLogin;
import com.scr.repository.AppDeviceLoginRepository;
import com.scr.repository.AppDeviceRepository;
import com.scr.repository.AppDeviceUnitRepository;
import com.scr.repository.UserLoginRepository;

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
				if (appDevice.getDeviceId() == "null") {
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
			fpMasterDto.setCreatedResponseUserLoginDto(this.getNewUserLoginData(userLoginRepository.findByCreatedStampLessThanEqualAndCreatedStampGreaterThanAndUserLoginIdIn(currenTimestamp,previousTimestamp,userLogins)));
			fpMasterDto.setUpdatedResponseUserLoginDto(this.getUpdatedUserLoginData(userLoginRepository.findByLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThanAndUserLoginIdIn(currenTimestamp,previousTimestamp,userLogins)));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("error message >>>>"+e.getMessage());
		}
		return fpMasterDto;
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

	
}
