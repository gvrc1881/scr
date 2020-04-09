package com.scr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.ContentCategory;

@Repository
public interface ContentCategoryRepository extends JpaRepository<ContentCategory, Integer>{

}
