package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.scr.model.StatusItem;

public interface StatusItemRepository extends JpaRepository<StatusItem, Long> {
	List<StatusItem> findByStatusTypeId(String statusTypeId);

	List<StatusItem> findByStatusTypeIdIn(List<String> statusTypeList);
	
	@Query(value = "SELECT case when count(si)> 0 then true else false  end  FROM StatusItem si WHERE si.statusTypeId = :statusTypeId and si.statusId  = :statusId")
	Boolean existsByStatusTypeIdAndStatusId(@Param("statusTypeId") String statusTypeId,@Param("statusId") String statusId);
	
    Optional<StatusItem>findByStatusTypeIdAndStatusId(String statusTypeId,String statusId);
    
    @Query(value = "SELECT case when count(si)> 0 then true else false  end  FROM StatusItem si WHERE si.statusTypeId = :statusTypeId and si.statusCode  = :statusCode")
	Boolean existsByStatusTypeIdAndStatusCode(@Param("statusTypeId") String statusTypeId,@Param("statusCode") String statusCode);
	
    Optional<StatusItem>findByStatusTypeIdAndStatusCode(String statusTypeId,String statusCode);
    
    @Query(value = "SELECT case when count(si)> 0 then true else false  end  FROM StatusItem si WHERE si.statusTypeId = :statusTypeId and si.description  = :description")
	Boolean existsByStatusTypeIdAndDescription(@Param("statusTypeId") String statusTypeId,@Param("description") String description);
	
    Optional<StatusItem>findByStatusTypeIdAndDescription(String statusTypeId,String description);

}
