package com.scr.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scr.model.PrecautionaryMeasuresMaster;
import com.scr.model.Product;
import com.scr.repository.ProductRepository;
import com.scr.util.Constants;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public void save(Product product) {
		productRepository.save(product);
	}
	public Optional<Product> findProductById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id);

	}

   /*public String deleteProductById(Long id) {	
		
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
			 
			return Constants.JOB_SUCCESS_MESSAGE;
		}else {
			return "Invalid product Repository Id";
		}
		
	}*/
   public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
	   productRepository.deleteById(id);
	}
	public Boolean existsByProductId(String productId) {
		// TODO Auto-generated method stub
		return productRepository.existsByProductId(productId);
	}
	public Optional<Product> findByProductId(String productId) {
		// TODO Auto-generated method stub
		return productRepository.findByProductId(productId);
	}
	public Boolean existsByRlyId(String rlyId) {
		// TODO Auto-generated method stub
		return productRepository.existsByRlyId(rlyId);
	}
	public Optional<Product> findByRlyId(String rlyId) {
		return productRepository.findByRlyId(rlyId);
	}
}
