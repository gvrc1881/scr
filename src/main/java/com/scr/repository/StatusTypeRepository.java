package com.scr.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.StatusType;

@Repository
public interface StatusTypeRepository extends JpaRepository<StatusType, Long>{
	
	
	Boolean existsByStatusTypeId(String statusTypeId);

	Boolean existsByDescription(String description);
	
	Optional<StatusType> findByStatusTypeId(String statusTypeId);
	
	Optional<StatusType> findByDescription(String description);

}
