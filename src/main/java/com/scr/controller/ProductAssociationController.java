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
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.ProductAssociation;
import com.scr.services.ProductAssociationService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ProductAssociationController {
	
	static Logger logger = LogManager.getLogger(ProductAssociationController.class);
	
	
	@Autowired
	private ProductAssociationService productAssociationService;
	
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/findAllProductAssociation", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<ProductAssociation> findAllProductAssociation() throws JSONException {
		logger.info("Enter into findAll ProductAssociation function");
		List<ProductAssociation> productAssociationList = null;
		try {
			logger.info("Calling service for productAssociation data");
			productAssociationList = productAssociationService.findAll();
			logger.info("Fetched precautionaryMeasure data = " + productAssociationList.size());
			return productAssociationList;
		} catch (NullPointerException npe) {
			logger.error("ERROR >>> while fetching the ProductAssociation data = " + npe.getMessage());
		} catch (Exception e) {
			logger.error("ERROR >>> while fetching the ProductAssociation data = " + e.getMessage());
		}
		logger.info("Exit from findAll PrecautionaryMeasure function");
		return productAssociationList;
	}
	
	@RequestMapping(value="/addProductAssociation",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addProductAssociation(@RequestBody ProductAssociation productAssociation) {
		logger.info("Enter into addProductAssociation function with below request parameters ");
		logger.info("Request Parameters = "+productAssociation.toString());
		try {
			logger.info("Calling service with request parameters.");
			productAssociationService.save(productAssociation);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("productAssociation Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While adding productAssociation data. "+npe.getMessage());
			return Helper.findResponseStatus("productAssociation save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While adding productAssociation data. "+e.getMessage());
			return Helper.findResponseStatus("productAssociation save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findProductAssociationById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<ProductAssociation> findProductAssociationById(@PathVariable Long id){
		Optional<ProductAssociation> productAssociation = null;
		try {
			logger.info("Selected productAssociation Id = "+id);
			productAssociation = productAssociationService.findProductAssociationById(id);
			if(productAssociation.isPresent()) {
				logger.info("product Association Data = "+productAssociation.get());
				return new ResponseEntity<ProductAssociation>(productAssociation.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<ProductAssociation>(productAssociation.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			logger.error("Error >>  while find Product Association Details by id, "+e.getMessage());
			return new ResponseEntity<ProductAssociation>(productAssociation.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateProductAssociation" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateProductAssociation(@RequestBody ProductAssociation productAssociation) {
		logger.info("Enter into updateProductAssociation function with below request parameters ");
		logger.info("Request Parameters = "+productAssociation.toString());
		try {
			logger.info("Calling service with request parameters.");
			productAssociationService.save(productAssociation);
			logger.info("Preparing the return response");
			return Helper.findResponseStatus("ProductAssociation Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			logger.error("ERROR >> While updating ProductAssociation data. "+npe.getMessage());
			return Helper.findResponseStatus("ProductAssociation update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			logger.error("ERROR >> While updating ProductAssociation data. "+e.getMessage());
			return Helper.findResponseStatus("ProductAssociation update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteProductAssociation/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteProductAssociationById(@PathVariable Long id) {
		logger.info("Enter into deleteProductAssociationById function");
		logger.info("Selected deleteProductAssociation Id = "+id);
		try {
			productAssociationService.deleteProductAssociationById(id);
			return Helper.findResponseStatus("Product Association deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			logger.error("ERROR >> While deleting Product Association data"+npe.getMessage());
			return Helper.findResponseStatus("Product Association Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			logger.error("ERROR >> While deleting Product Association data"+e.getMessage());
			return Helper.findResponseStatus("Product Association Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
}
