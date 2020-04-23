package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scr.model.Model;

public interface ModelRepository extends JpaRepository<Model, Long>{

	List<Model> findAll();
}
