package com.scr.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findByproductId(long parseLong);

	List<Product> findByProductTypeIdNotInAndCreatedStampLessThanEqualAndCreatedStampGreaterThan(
			List<String> productTypeList, Timestamp currenTimestamp, Timestamp previousTimestamp);

	List<Product> findByProductTypeIdNotInAndLastUpdatedStampLessThanEqualAndLastUpdatedStampGreaterThan(
			List<String> productTypeList, Timestamp currenTimestamp, Timestamp previousTimestamp);

	

}
