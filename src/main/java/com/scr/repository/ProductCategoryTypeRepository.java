package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scr.model.ProductCategoryType;

public interface ProductCategoryTypeRepository extends JpaRepository<ProductCategoryType, Long>{
	
	@Query("FROM ProductCategoryType ORDER BY productCategoryTypeId ASC")
	List<ProductCategoryType> findAllOrderByProductCategoryTypeIdAsc();

}
