package com.scr.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findByproductId(long parseLong);

}
