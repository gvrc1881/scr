package com.scr.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import com.scr.model.PowerBlock;




@Repository
public interface PowerBlockRepository extends JpaRepository<PowerBlock, Long> {
	

	 List<PowerBlock> findAll(); 
		
		
	}
	

