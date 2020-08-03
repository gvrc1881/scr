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
import com.scr.app.dto.ReportDto;
import com.scr.services.FootPatrollingRestService;

@RestController
@RequestMapping("/warehouse/fpApp")
public class FootPatrollingRestController {
	
	static Logger log = Logger.getLogger(FootPatrollingRestController.class);
	
	@Autowired
	private FootPatrollingRestService footPatrollingRestService;
	
	@RequestMapping(value = "/hellow", method = RequestMethod.GET)
	public String helloWorld() {
		System.out.println("hellow world:::");
		return "*** HELLO WORLD ***";
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
	
	
	@RequestMapping(value = "/get-report-names", method = RequestMethod.GET,  produces = "application/json")
	public FpAppMasterDto getReportNames(){
		log.info("*** request for to get report names ***");
		return footPatrollingRestService.getRepotNames();
	}
	
	
	
	
	@RequestMapping(value = "/report-execution", method = RequestMethod.POST,  produces = "application/json")
	public ReportDto reportExecution(@RequestBody  ReportDto reportDto) {
		 log.info("*** request for report execution ***");
		 return footPatrollingRestService.reportExecution(reportDto);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
