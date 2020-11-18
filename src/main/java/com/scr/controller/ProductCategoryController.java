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
import com.scr.model.GantryMasterData;
import com.scr.model.ProductCategory;
import com.scr.services.ProductCategoryService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ProductCategoryController {
	
	static Logger log = LogManager.getLogger(ProductCategoryController.class);

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@RequestMapping(value="/findAllProductCategory", method=RequestMethod.GET, headers = "Accept=application/json")
	public List<ProductCategory> findAllProductCategory(){
		log.info("Enter into findAllProductCategory function");
		List<ProductCategory> productCategory=null;
		try {
			log.info("calling service for Product Category Data");
			productCategory=productCategoryService.findAll();
			log.info("fetched product Category Data"+productCategory.size());
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the productCategory data = "+npe.getMessage());
			
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the productCategory data = "+e.getMessage());
		}
		log.info("Exit from product Category function");
		return productCategory;
	}	
	@RequestMapping(value="/addProductCategory",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addProductCategory(@RequestBody ProductCategory productCategory) {
		log.info("Enter into addProductCategory function with below request parameters ");
		log.info("Request Parameters = "+productCategory.toString());
		try {
			log.info("Calling service with request parameters.");
			productCategoryService.save(productCategory);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("productCategory Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding ProductCategory data. "+npe.getMessage());
			return Helper.findResponseStatus("Product save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding ProductCategory data. "+e.getMessage());
			return Helper.findResponseStatus("ProductCategory save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findProductCategoryById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<ProductCategory> findProductCategoryById(@PathVariable Long id){
		Optional<ProductCategory> productCategory = null;
		try {
			log.info("Selected productCategory Id = "+id);
			productCategory = productCategoryService.findProductCategoryById(id);
			if(productCategory.isPresent()) {
				log.info("productCategory Data = "+productCategory.get());
				return new ResponseEntity<ProductCategory>(productCategory.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<ProductCategory>(productCategory.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find productCategory Details by id, "+e.getMessage());
			return new ResponseEntity<ProductCategory>(productCategory.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/updateProductCategory" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateProductCategory(@RequestBody ProductCategory productCategory) {
		log.info("Enter into updateProductCategory function with below request parameters ");
		log.info("Request Parameters = "+productCategory.toString());
		try {
			log.info("Calling service with request parameters.");
			productCategoryService.save(productCategory);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("productCategory Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating productCategory data. "+npe.getMessage());
			return Helper.findResponseStatus("productCategory update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating productCategory data. "+e.getMessage());
			return Helper.findResponseStatus("productCategory update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/deleteProductCategory/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteProductCategoryById(@PathVariable Long id) {
		log.info("Enter into deleteProductCategoryById function");
		log.info("Selected Product Category Id = "+id);
		try {
			productCategoryService.deleteProductCategoryById(id);
			return Helper.findResponseStatus("ProductCategory deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting ProductCategory data"+npe.getMessage());
			return Helper.findResponseStatus("ProductCategory Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting ProductCategory data"+e.getMessage());
			return Helper.findResponseStatus("ProductCategory Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
	@RequestMapping(value = "/existsProductCategoryId/{productCategoryId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsByProductCategoryId(@PathVariable("productCategoryId") String productCategoryId){		
		try {
			return productCategoryService.existsByProductCategoryId(productCategoryId);
		} catch (Exception e) {
			log.error("Error while checking exists productCategoryId.");
			return false;
		}
	}
	@RequestMapping(value = "/existProductCategoryIdById/{id}/{productCategoryId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existProductCategoryIdById(@PathVariable("id") Long id,@PathVariable("productCategoryId") String productCategoryId){
		
		log.info("id=="+id+"productCategoryId=="+productCategoryId);
		Boolean result;
		try {
			Optional<ProductCategory> productCategoryData = productCategoryService.findByProductCategoryId(productCategoryId);	
			if(productCategoryData.isPresent()) {
				ProductCategory productCategory = productCategoryData.get();
				log.info("***id ***"+productCategory.getId());
				if (id.equals(productCategory.getId())) {
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
	@RequestMapping(value = "/existsCategoryName/{categoryName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existsCategoryName(@PathVariable("categoryName") String categoryName){		
		try {
			return productCategoryService.existsCategoryName(categoryName);
		} catch (Exception e) {
			log.error("Error while checking exists categoryName.");
			return false;
		}
	}
	@RequestMapping(value = "/existCategoryNameById/{id}/{categoryName}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existCategoryNameById(@PathVariable("id") Long id,@PathVariable("categoryName") String categoryName){
		
		log.info("id=="+id+"categoryName=="+categoryName);
		Boolean result;
		try {
			Optional<ProductCategory> productCategoryData = productCategoryService.findByCategoryName(categoryName);	
			if(productCategoryData.isPresent()) {
				ProductCategory productCategory = productCategoryData.get();
				log.info("***id ***"+productCategory.getId());
				if (id.equals(productCategory.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and categoryName..."+e.getMessage());
			return false;
		}
	}
}
