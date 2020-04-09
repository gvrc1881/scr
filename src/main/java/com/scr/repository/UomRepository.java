package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Uom;

public interface UomRepository extends JpaRepository<Uom, Long>{
	List<Uom> findAll();

}
