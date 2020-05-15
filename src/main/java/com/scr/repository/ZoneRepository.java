package com.scr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scr.model.Zone;



@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long>{
	 @Query("FROM Zone ORDER BY code ASC")
	List<Zone> findAllOrderByCodeAsc();

		

}
