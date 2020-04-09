package com.scr.controller;


import java.util.List;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.scr.message.response.ResponseStatus;
import com.scr.model.EnergyBillPayment;
import com.scr.services.EnergyBillPaymentServices;
import com.scr.util.Constants;
import com.scr.util.Helper;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class EnergyBillPaymentController {
	
	static Logger log = LogManager.getLogger(EnergyBillPaymentController.class);
	
	@Autowired
	private EnergyBillPaymentServices energyBillPaymentServices;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllEnergyBillPayments", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<EnergyBillPayment> eneBillPaymentList() throws JSONException {
		List<EnergyBillPayment> energyBillPaymentList = null;
		try {
		log.info("Fetch energyBillPaymentList Started");	
		energyBillPaymentList = energyBillPaymentServices.findAll();
		log.info("Fetch energyBillPaymentList Ended");
		return energyBillPaymentList;
		} catch (NullPointerException e) {
			log.error(e);
		}
		catch (Exception e) {
			log.error(e);
		}
		return energyBillPaymentList;	
	}
	
	@RequestMapping(value = "/addEneBillPayment", method = RequestMethod.POST , headers = "Accept=application/json")
	@ResponseBody
	public ResponseStatus saveEneBillPayment(@RequestBody EnergyBillPayment energyBillPayment){
		EnergyBillPayment saveEneBillPayment = energyBillPaymentServices.saveEneBillPayment(energyBillPayment);
		if (saveEneBillPayment != null) {
			return Helper.findResponseStatus("Energy bill payment added successfully", Constants.SUCCESS_CODE);
		}
		return null;
	}
	
	
	@RequestMapping(value = "/findEneBillPayment/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<EnergyBillPayment> findById(@PathVariable("id") Integer id){
		Optional<EnergyBillPayment> ele = energyBillPaymentServices.findById(id);
		return new ResponseEntity<>(ele.get(),HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateEneBillPayment" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateEneBillPayment (@RequestBody EnergyBillPayment energyBillPayment) {
		energyBillPaymentServices.saveEneBillPayment(energyBillPayment);
		return Helper.findResponseStatus("Energy Bill Payment updated successfully", Constants.SUCCESS_CODE);
	}
	
	@RequestMapping(value = "/deleteEneBillPayment/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteEneBillPayment(@PathVariable Integer id) {
		energyBillPaymentServices.deleteEneBillPaymentById(id);
		return Helper.findResponseStatus("Energy Bill Payment Deleted successfully", Constants.SUCCESS_CODE);
	}

}
