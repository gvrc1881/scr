package com.scr.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.GeographicState;

@Repository
public interface GeographicStateRepository extends JpaRepository<GeographicState, Integer> {

	List<GeographicState> findAll();
	
}
