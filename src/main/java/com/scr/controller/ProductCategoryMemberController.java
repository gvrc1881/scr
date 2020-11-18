package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scr.message.response.ResponseStatus;
import com.scr.model.ProductCategoryMember;
import com.scr.services.ProductCategoryMemberService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ProductCategoryMemberController {
	
	static Logger log = LogManager.getLogger(ProductCategoryMemberController.class);

	@Autowired
	private ProductCategoryMemberService productCategoryMemberService;
	
	@RequestMapping(value="/findAllProductCategoryMember", method=RequestMethod.GET, headers = "Accept=application/json")
	public List<ProductCategoryMember> findAllProductCategoryMember(){
		log.info("Enter into findAll ProductCategoryMember function");
		List<ProductCategoryMember> productCategoryMember=null;
		try {
			log.info("calling service for productCategoryMember Data");
			productCategoryMember=productCategoryMemberService.findAll();
			log.info("fetched productCategoryMember Data"+productCategoryMember.size());
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the productCategoryMember data = "+npe.getMessage());
			
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the productCategoryMember data = "+e.getMessage());
		}
		log.info("Exit from product function");
		return productCategoryMember;
	}	
	@RequestMapping(value="/addProductCategoryMember",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addProductCategoryMember(@RequestBody ProductCategoryMember productCategoryMember) {
		log.info("Enter into addProductCategoryMember function with below request parameters ");
		log.info("Request Parameters = "+productCategoryMember.toString());
		try {
			log.info("Calling service with request parameters.");
			productCategoryMemberService.save(productCategoryMember);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("productCategoryMember Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding productCategoryMember data. "+npe.getMessage());
			return Helper.findResponseStatus("productCategoryMember save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding productCategoryMember data. "+e.getMessage());
			return Helper.findResponseStatus("productCategoryMember save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findProductCategoryMemberById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<ProductCategoryMember> findProductCategoryMemberById(@PathVariable Long id){
		Optional<ProductCategoryMember> productCategoryMember = null;
		try {
			log.info("Selected productCategoryMember Id = "+id);
			productCategoryMember = productCategoryMemberService.findProductCategoryMemberById(id);
			if(productCategoryMember.isPresent()) {
				log.info("productCategoryMember Data = "+productCategoryMember.get());
				return new ResponseEntity<ProductCategoryMember>(productCategoryMember.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<ProductCategoryMember>(productCategoryMember.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find productCategoryMember Details by id, "+e.getMessage());
			return new ResponseEntity<ProductCategoryMember>(productCategoryMember.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateProductCategoryMember" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateProductCategoryMember(@RequestBody ProductCategoryMember productCategoryMember) {
		log.info("Enter into updateProductCategoryMember function with below request parameters ");
		log.info("Request Parameters = "+productCategoryMember.toString());
		try {
			log.info("Calling service with request parameters.");
			productCategoryMemberService.save(productCategoryMember);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("productCategoryMember Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating productCategoryMember data. "+npe.getMessage());
			return Helper.findResponseStatus("productCategoryMember update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating productCategoryMember data. "+e.getMessage());
			return Helper.findResponseStatus("productCategoryMember update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteProductCategoryMember/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteProductCategoryMemberById(@PathVariable Long id) {
		log.info("Enter into deleteProductCategoryMemberById function");
		log.info("Selected ProductCategory Member Id = "+id);
		try {
			productCategoryMemberService.deleteProductCategoryMemberById(id);
			return Helper.findResponseStatus("ProductCategoryMember deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting ProductCategoryMember data"+npe.getMessage());
			return Helper.findResponseStatus("ProductCategoryMember Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting ProductCategoryMember data"+e.getMessage());
			return Helper.findResponseStatus("ProductCategoryMember Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	
	@RequestMapping(value = "/existsProductCategoryIdAndProductId/{productCategoryId}/{productId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsProductCategoryIdAndProductId(@PathVariable("productCategoryId") String productCategoryId ,@PathVariable("productId") String productId){
			
		try {
			log.info("Request for checking exists inspection Type and observationCategory.");
			return productCategoryMemberService.existsByProductCategoryIdAndProductId(productCategoryId,productId);	
		} catch (Exception e) {
			log.error("Error while checking exists productCategoryId and productId..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existProductCategoryIdProductIdAndId/{id}/{productCategoryId}/{productId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existProductCategoryIdProductIdAndId(@PathVariable("id") Long id,@PathVariable("productCategoryId") String productCategoryId,@PathVariable("productId") String productId){
		
		log.info("id=="+id+"productCategoryId=="+productCategoryId);
		Boolean result;
		try {
			Optional<ProductCategoryMember> productCategoryMemberData = productCategoryMemberService.findByProductCategoryIdAndProductId(productCategoryId,productId);
			//return makeService.existsByIdAndMakeCode(id,makeCode);
			if(productCategoryMemberData.isPresent()) {
				ProductCategoryMember ProductCategoryMember = productCategoryMemberData.get();
				log.info("***id ***"+ProductCategoryMember.getId());
				if (id.equals(ProductCategoryMember.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and productCategoryId..."+e.getMessage());
			return false;
		}
	}

}
