package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.ProductMakeModelAssociation;

public interface ProductMakeModelAssocRepository extends JpaRepository<ProductMakeModelAssociation, Long> {

	List<ProductMakeModelAssociation> findAll();
}
