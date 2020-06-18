package com.scr.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scr.app.dto.FpAppMasterDto;
import com.scr.services.FootPatrollingRestService;
import com.scr.util.Helper;

@RestController
@RequestMapping("/warehouse/fpApp")
public class FootPatrollingRestController {
	
	static Logger log = Logger.getLogger(FootPatrollingRestController.class);
	
	@Autowired
	private FootPatrollingRestService footPatrollingRestService;
	
	@RequestMapping(value = "/hellow", method = RequestMethod.GET)
	public void helloWorld() {
		System.out.println("hellow world:::");
	}
	
	@RequestMapping(value = "/get-fp-data", method = RequestMethod.POST,  produces = "application/json")
	public FpAppMasterDto getData(@RequestBody FpAppMasterDto fpMasterDto) {
		
		String currentTimestampInString=fpMasterDto.getCurrentTimestamp();
		String previousTimestampInString =fpMasterDto.getPreviousTimestamp();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		try {
			Timestamp previousTimestamp = new Timestamp(dateFormat.parse(previousTimestampInString).getTime());
			Timestamp currenTimestamp = new Timestamp(dateFormat.parse(currentTimestampInString).getTime());
		fpMasterDto = footPatrollingRestService.getMasterData(fpMasterDto,previousTimestamp,currenTimestamp);
		}catch (Exception e) {
			// TODO: handle exception
		}
		return fpMasterDto;
	}

}
