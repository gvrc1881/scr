package com.scr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.AlertGroup;

@Repository
public interface AlertGroupRepository extends JpaRepository<AlertGroup, Long>{
	
	Boolean existsByName(String name);

	Boolean existsByDescription(String description);
	
	Optional<AlertGroup> findByName(String name);
	
	Optional<AlertGroup> findByDescription(String description);

}
