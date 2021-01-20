package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.scr.model.Facility;
import com.scr.model.ThermovisionCheckPoints;


@Repository
public interface ThermovisionCheckPointsRepository extends JpaRepository<ThermovisionCheckPoints, Long>{

	Optional<ThermovisionCheckPoints> findByCheckPointDescription(String tcpCheckPointDescription);
	
	List<ThermovisionCheckPoints> findByFacilityId(Facility facility);
	
	Optional<ThermovisionCheckPoints> findByFacilityIdAndCheckPointPart(Facility facilityId, String checkPointPart);
	List<ThermovisionCheckPoints> findByFacilityIdAndCheckPointDescription(Facility facilityId, String checkPointDescription);


}
