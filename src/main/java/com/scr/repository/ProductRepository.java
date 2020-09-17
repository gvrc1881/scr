package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.scr.model.Product;
import com.scr.model.ProductCategoryMember;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findByproductId(long parseLong);

	List<Product> findByProductTypeIdNotInAndCreatedStampLessThanEqualAndCreatedStampGreaterThan(
			List<String> productTypeList, Timestamp currenTimestamp, Timestamp previousTimestamp);

	List<Product> findByProductTypeIdNotInAndLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(
			List<String> productTypeList, Timestamp currenTimestamp, Timestamp previousTimestamp);
	
	@Query(value = "SELECT p.productId,p.description  FROM Product p")            
	List<Product> findByProductIdAndDescription();		
	
	@Query(value = "SELECT p.productId,p.description  FROM Product p  WHERE productId in (select productId from ProductCategoryMember where productCategoryId = :productCategoryId)")
	List<Product> findProducts(@Param("productCategoryId")String productCategoryId);
	

}