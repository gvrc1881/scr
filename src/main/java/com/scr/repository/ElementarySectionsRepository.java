package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scr.model.ElementarySection;


public interface ElementarySectionsRepository extends JpaRepository<ElementarySection, Long>{
	
	

	 /* @Query("select distinct elementarySectionCode from elementarySections  order by elementarySectionCode ASC ")*/
	  List<ElementarySection> findDistinctByElementarySectionCodeOrderByElementarySectionCodeAsc(String elementarySectionCode);

}
