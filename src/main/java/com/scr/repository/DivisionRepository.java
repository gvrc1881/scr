package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.scr.model.Division;
import com.scr.model.Zone;



public interface DivisionRepository extends JpaRepository<Division, Long>{
	List<Division> findByZoneIdOrderByCodeAsc(Zone zoneId);
	
	@Query("FROM Division ORDER BY code ASC")
    List<Division> findAllOrderByCodeAsc();
	
}
