package com.scr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scr.model.ProductCategory;
import com.scr.repository.ProductCategoryRepository;

@Service
public class ProductCategoryService {
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	
	public List<ProductCategory> findAll(){
		return productCategoryRepository.findAll();
	}
	
	public void save(ProductCategory productCategory) {
		productCategoryRepository.save(productCategory);
	}
	public Optional<ProductCategory> findProductCategoryById(Long id) {
		// TODO Auto-generated method stub
		return productCategoryRepository.findById(id);

	}

	public void deleteProductCategoryById(Long id) {
		// TODO Auto-generated method stub
		productCategoryRepository.deleteById(id);
	}

}
