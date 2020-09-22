package com.scr.controller;

import java.util.List;
import java.util.Optional;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
import com.scr.model.Product;
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
	
	@RequestMapping(value="/findAllProducts", method=RequestMethod.GET, headers = "Accept=application/json")
	public List<Product> findAllProducts(){
		log.info("Enter into findAllProduct function");
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
	
	@RequestMapping(value = "/deleteProduct/{id}" ,method = RequestMethod.DELETE , headers = "Accept=application/json")
	public ResponseStatus deleteProductById(@PathVariable Long id) {
		log.info("Enter into deleteProductById function");
		log.info("Selected Product Id = "+id);
		try {
			productService.deleteProductById(id);
			return Helper.findResponseStatus("Product deleted successfully", Constants.SUCCESS_CODE);
		} catch (NullPointerException npe) {
			log.error("ERROR >> While deleting Product data"+npe.getMessage());
			return Helper.findResponseStatus("Product Deletion is Failed with "+npe.getMessage(), Constants.FAILURE_CODE);			
		} catch (Exception e) {
			log.error("ERROR >> While deleting Product data"+e.getMessage());
			return Helper.findResponseStatus("Product Deletion is Failed with "+e.getMessage(), Constants.FAILURE_CODE);			
		}
	}
}
