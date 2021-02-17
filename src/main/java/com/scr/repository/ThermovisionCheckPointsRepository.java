package com.scr.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scr.model.Facility;
import com.scr.model.Make;
import com.scr.model.Model;
import com.scr.model.TestInspection;
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

	List<ThermovisionCheckPoints> findByActive(String active);

	Optional<ThermovisionCheckPoints> findByCheckPoint1DescriptionAndCheckPoint2DescriptionAndFacilityId(
			String tcpCheckPoint1Description, String tcpCheckPoint2Description, Facility facility);
	
	
	@Query(value = "SELECT case when count(ti)> 0 then true else false  end  FROM ThermovisionCheckPoints ti WHERE ti.facilityId = :facilityId and ti.checkPointPart = :checkPointPart and ti.checkPoint1Description = :checkPoint1Description")
	Boolean existsByFacilityIdAndCheckPointPartAndCheckPoint1Description(@Param("facilityId") Facility facilityId,@Param("checkPointPart") String checkPointPart,@Param("checkPoint1Description") String checkPoint1Description);	
	
	
	Optional<ThermovisionCheckPoints> findByFacilityIdAndCheckPointPartAndCheckPoint1Description(Facility facilityId,String checkPointPart,String checkPoint1Description);
	
	
	
	@Query(value = "SELECT case when count(ti)> 0 then true else false  end  FROM ThermovisionCheckPoints ti WHERE ti.facilityId = :facilityId and ti.checkPointPart = :checkPointPart and ti.checkPoint2Description = :checkPoint2Description")
	Boolean existsByFacilityIdAndCheckPointPartAndCheckPoint2Description(@Param("facilityId") Facility facilityId,@Param("checkPointPart") String checkPointPart,@Param("checkPoint2Description") String checkPoint2Description);	
	
	
	Optional<ThermovisionCheckPoints> findByFacilityIdAndCheckPointPartAndCheckPoint2Description(Facility facilityId,String checkPointPart,String checkPoint2Description);
}
