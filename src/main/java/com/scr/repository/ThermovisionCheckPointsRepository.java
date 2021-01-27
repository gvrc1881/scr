package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.Facility;
import com.scr.model.ThermovisionCheckPoints;

//check points
@Repository
public interface ThermovisionCheckPointsRepository extends JpaRepository<ThermovisionCheckPoints, Long>{

	Optional<ThermovisionCheckPoints> findByCheckPointDescription(String tcpCheckPointDescription);
	
	List<ThermovisionCheckPoints> findByFacilityIdOrderByDisplayOrderAsc(Facility facility);
	
	Optional<ThermovisionCheckPoints> findByCheckPointPartAndCheckPointDescription(String checkPointPart, String checkPointDescription);
	List<ThermovisionCheckPoints> findByFacilityIdAndIdNotIn(Facility facilityId, Long id);


}
