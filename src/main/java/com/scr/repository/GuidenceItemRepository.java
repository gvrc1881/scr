package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.GuidenceItem;

@Repository
public interface GuidenceItemRepository extends JpaRepository<GuidenceItem, Integer> {

	List<GuidenceItem> findAll();


	
}
