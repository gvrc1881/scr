package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{
	//repository
	List<ProductCategory> findAll();
	Boolean existsByProductCategoryId(String productCategoryId);
	
	Optional<ProductCategory> findByProductCategoryId(String productCategoryId);

	Boolean existsByCategoryName(String categoryName);
	
	Optional<ProductCategory> findByCategoryName(String categoryName);

	
}
