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
import com.scr.model.PrecautionaryMeasure;
import com.scr.model.Product;
import com.scr.model.ProductCategoryMember;
import com.scr.repository.ProductCategoryMemberRepository;
import com.scr.repository.ProductRepository;
import com.scr.services.ProductService;
import com.scr.util.Constants;
import com.scr.util.Helper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/scr/api")
public class ProductController {
	
	static Logger log = LogManager.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	
	@Autowired
	private ProductCategoryMemberRepository ProductCategoryMemberRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping(value="/findAllProducts", method=RequestMethod.GET, headers = "Accept=application/json")
	public List<Product> findAllProducts(){
		log.info("Enter into findAll Product function");
		List<Product> product=null;
		try {
			log.info("calling service for Product Data");
			product=productService.findAll();
			log.info("fetched product Data"+product.size());
		} catch (NullPointerException npe) {
			log.error("ERROR >>> while fetching the product data = "+npe.getMessage());
			
		}
		catch (Exception e) {
			log.error("ERROR >>> while fetching the product data = "+e.getMessage());
		}
		log.info("Exit from product function");
		return product;
	}	
	@RequestMapping(value="/addProduct",method=RequestMethod.POST,headers="Accept=application/json")
	public ResponseStatus addProduct(@RequestBody Product product) {
		log.info("Enter into addProduct function with below request parameters ");
		log.info("Request Parameters = "+product.toString());
		try {
			log.info("Calling service with request parameters.");
			productService.save(product);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Prduct Added successfully",Constants.SUCCESS_CODE);
		}catch(NullPointerException npe) {
			log.error("ERROR >> While adding Product data. "+npe.getMessage());
			return Helper.findResponseStatus("Product save is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While adding Product data. "+e.getMessage());
			return Helper.findResponseStatus("Product save is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	@RequestMapping(value = "/findProductById/{id}" , method = RequestMethod.GET , headers = "Accept=application/json")
	public ResponseEntity<Product> findProductById(@PathVariable Long id){
		Optional<Product> product = null;
		try {
			log.info("Selected Product Id = "+id);
			product = productService.findProductById(id);
			if(product.isPresent()) {
				log.info("product Data = "+product.get());
				return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
			}
			else
				return new ResponseEntity<Product>(product.get(), HttpStatus.CONFLICT);
		} catch (Exception e) {
			log.error("Error >>  while find Product Details by id, "+e.getMessage());
			return new ResponseEntity<Product>(product.get(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/updateProduct" ,method = RequestMethod.PUT , headers = "Accept=application/json")
	public ResponseStatus updateProduct(@RequestBody Product product) {
		log.info("Enter into updateProduct function with below request parameters ");
		log.info("Request Parameters = "+product.toString());
		try {
			log.info("Calling service with request parameters.");
			productService.save(product);
			log.info("Preparing the return response");
			return Helper.findResponseStatus("Product Updated successful", Constants.SUCCESS_CODE);	
		}catch(NullPointerException npe) {
			log.error("ERROR >> While updating product data. "+npe.getMessage());
			return Helper.findResponseStatus("product update is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);
		}
		catch (Exception e) {
			log.error("ERROR >> While updating product data. "+e.getMessage());
			return Helper.findResponseStatus("product update is Failed with "+e.getMessage(), Constants.FAILURE_CODE);
		}
	}
	
	@RequestMapping(value = "/existProductId/{productId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existProductId(@PathVariable("productId") String productId ){
			
		try {
            log.info("Request for checking exists product Id...");
			return productService.existsByProductId(productId);
		} catch (Exception e) {
			log.error("Error while checking exists product Id..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existProductIdById/{id}/{productId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existProductIdById(@PathVariable("id") Long id,@PathVariable("productId") String productId){
		
		log.info("id=="+id+"productId=="+productId);
		Boolean result;
		try {
			Optional<Product> productData = productService.findByProductId(productId);	
			if(productData.isPresent()) {
				Product product = productData.get();
				log.info("***id ***"+product.getId());
				if (id.equals(product.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and product..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existRlyId/{rlyId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existRlyId(@PathVariable("rlyId") String rlyId ){
			
		try {
            log.info("Request for checking exists rly Id...");
			return productService.existsByRlyId(rlyId);
		} catch (Exception e) {
			log.error("Error while checking exists rly Id..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/existRlyIdById/{id}/{rlyId}", method = RequestMethod.GET ,produces=MediaType.APPLICATION_JSON_VALUE)	
	public Boolean existRlyIdById(@PathVariable("id") Long id,@PathVariable("rlyId") String rlyId){
		
		log.info("id=="+id+"rlyId=="+rlyId);
		Boolean result;
		try {
			Optional<Product> productData = productService.findByRlyId(rlyId);	
			if(productData.isPresent()) {
				Product product = productData.get();
				log.info("***id ***"+product.getId());
				if (id.equals(product.getId())) {
					return result = false;
				} else {
					return result = true;
				}
			}
			else 
				return  result = false;
		} catch (Exception e) {
			log.error("Error while checking exists id and product..."+e.getMessage());
			return false;
		}
	}
	@RequestMapping(value = "/deleteProduct/{id}" ,method = RequestMethod.DELETE ,headers = "Accept=application/json")
	public ResponseStatus deleteProduct(@PathVariable Long id) {
		log.info("Enter into delete Product  function");
		log.info("Selected Product Id = "+id);
		List <ProductCategoryMember> productList = ProductCategoryMemberRepository.getByProductId(productRepository.findById(id).get());
		
		String result="";
		log.info("Delete function==");		
		if(  productList.size() == 0  )
		{
			log.info("preMeasureList=="+productList);
			
		try {
			String status = productService.deleteProductById(id);;
			if(status.equalsIgnoreCase(Constants.JOB_SUCCESS_MESSAGE))
				return Helper.findResponseStatus("product Deleted Successfully", Constants.SUCCESS_CODE);
		
	
		}
		catch (NullPointerException e) {
			log.error(e);
			return Helper.findResponseStatus("product Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error(e);
			return Helper.findResponseStatus("product Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
		}
		if(productList.size() > 0 )	
			 result="This product Id is Associated with Product Category Member";
		return Helper.findResponseStatus( result , Constants.FAILURE_CODE);	
	}
}
