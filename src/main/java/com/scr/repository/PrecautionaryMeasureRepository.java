package com.scr.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.scr.model.PrecautionaryMeasure;
import com.scr.model.PrecautionaryMeasuresMaster;

@Repository
public interface PrecautionaryMeasureRepository extends JpaRepository<PrecautionaryMeasure, Long>{
	@Query(value = "SELECT case when count(pm)> 0 then true else false  end  FROM PrecautionaryMeasure pm WHERE pm.facilityId = :facilityId and pm.precautionaryMeasure = :precautionaryMeasure and CAST(pm.dateOfWork AS date ) = :dateOfWork ")
	Boolean existsByFacilityIdAndPrecautionaryMeasureAndDateOfWork(@Param("facilityId") String facilityId,@Param("precautionaryMeasure") PrecautionaryMeasuresMaster precautionaryMeasure,@Param("dateOfWork") Timestamp dateOfWork);	
	
	
	Optional<PrecautionaryMeasure> findByFacilityIdAndPrecautionaryMeasureAndDateOfWork(String facilityId,PrecautionaryMeasuresMaster precautionaryMeasure,Timestamp dateOfWork);
	
	List<PrecautionaryMeasure> getAllByDataDivIn(List<String> fac);
	
	List<PrecautionaryMeasure> getByPrecautionaryMeasure(PrecautionaryMeasuresMaster precautionaryMeasure);
	List<PrecautionaryMeasure> findByDateOfWorkAndFacilityIdAndApprovedStatusIsNull(Date dateOfWork,String facilityId);

}
