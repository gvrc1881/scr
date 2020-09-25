package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{
	
	List<ProductCategory> findAll();
	Boolean existsByProductCategoryId(String productCategoryId);

	Boolean existsByCategoryName(String categoryName);
}
