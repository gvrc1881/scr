package com.scr.controller;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.scr.model.Works;
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
		log.info("Enter into save function with below request parameters ");
		log.info("Request Parameters = "+energyBillPayment.toString());
		try {
			log.info("Calling service with request parameters.");
			EnergyBillPayment saveEneBillPayment = energyBillPaymentServices.saveEneBillPayment(energyBillPayment);
			if (saveEneBillPayment != null) {
				return Helper.findResponseStatus("Energy bill payment added successfully", Constants.SUCCESS_CODE);
			}
			return Helper.findResponseStatus("Energy Bill Payment added successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding  Energy Bill Payment  data. "+npe.getMessage());
			return Helper.findResponseStatus("Energy Bill Payment add is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Energy Bill Payment data. "+e.getMessage());
			return Helper.findResponseStatus("Energy Bill Payment add is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	
	@RequestMapping(value = "/findEneBillPayment/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<EnergyBillPayment> findById(@PathVariable("id") Integer id){
		Optional<EnergyBillPayment> ele = null;
		try {
			log.info("Selected Energy Bill Payment Id = "+id);
			ele =  energyBillPaymentServices.findById(id);
			if(ele.isPresent()) {
				log.info("Energy Bill Payment Data = "+ele.get());
				return new ResponseEntity<EnergyBillPayment>(ele.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<EnergyBillPayment>(ele.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Energy Bill Payment Details by id, "+e.getMessage());
			return new ResponseEntity<EnergyBillPayment>(ele.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "/updateEneBillPayment" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateEneBillPayment (@RequestBody EnergyBillPayment energyBillPayment) {
		log.info("Enter into update function with below request parameters ");
		log.info("Request Parameters = "+energyBillPayment.toString());
		try {
			log.info("Calling service with request parameters.");
			energyBillPaymentServices.saveEneBillPayment(energyBillPayment);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Energy Bill Payment updated successfully", Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating  Energy Bill Payment  data. "+npe.getMessage());
			return Helper.findResponseStatus("Energy Bill Payment update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating Energy Bill Payment data. "+e.getMessage());
			return Helper.findResponseStatus("Energy Bill Payment update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
		
	}
	
	@RequestMapping(value = "/deleteEneBillPayment/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteEneBillPayment(@PathVariable Integer id) {
		log.info("Enter into deleteById function");
		log.info("Selected eneBillPayment Id = "+id);
		try {
			energyBillPaymentServices.deleteEneBillPaymentById(id);
			return Helper.findResponseStatus("Energy Bill Payment Deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Energy Bill Payment data"+npe.getMessage());
			return Helper.findResponseStatus("Energy Bill Payment Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Energy Bill Payment data"+e.getMessage());
			return Helper.findResponseStatus("Energy Bill Payment Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existsReferenceAndToPaymentAndId/{id}/{reference}/{toPayment}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsReferenceAndToPaymentAndId(@PathVariable("id") Integer id,@PathVariable("reference") String reference ,@PathVariable("toPayment") String toPayment){
		log.info("Entered into existsReferenceAndToPaymentAndId function");
		Boolean result;
		try {
			Optional<EnergyBillPayment> eneBillPayment = energyBillPaymentServices.findByReferenceAndToPayment(reference,toPayment);
			if(eneBillPayment.isPresent()) {
				EnergyBillPayment eneBillPay = eneBillPayment.get();
				log.info("comparing with id's");
				if (id.equals(eneBillPay.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and reference and toPayment..."+e.getMessage());
			return false;
		}
		
	}

}
