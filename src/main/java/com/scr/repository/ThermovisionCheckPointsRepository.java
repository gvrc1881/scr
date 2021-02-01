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

	Optional<ThermovisionCheckPoints> findByCheckPoint1Description(String tcpCheckPoint1Description);
	
	List<ThermovisionCheckPoints> findByFacilityIdOrderByDisplayOrderAsc(Facility facility);
	
	Optional<ThermovisionCheckPoints> findByCheckPointPartAndCheckPoint1Description(String checkPointPart, String checkPoint1Description);
	List<ThermovisionCheckPoints> findByFacilityIdAndIdNotIn(Facility facilityId, Long id);

	Optional<ThermovisionCheckPoints> findByCheckPoint1DescriptionAndCheckPoint2Description(
			String tcpCheckPoint1Description, String tcpCheckPoint2Description);


}
