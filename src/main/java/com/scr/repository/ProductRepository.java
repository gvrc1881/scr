package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
