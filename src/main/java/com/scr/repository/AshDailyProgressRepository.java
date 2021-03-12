package com.scr.repository;

import java.util.Date;
import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.model.AshDailyProgress;
import com.scr.model.Facility;

@Repository
public interface AshDailyProgressRepository extends JpaRepository<AshDailyProgress, Long>{

	Optional<AshDailyProgress> findByDateAndFacility(Date date, Facility facility);
	
	@Query( value = "select case when sum(atdAoh) > 0 then sum(atdAoh) else 0 end, case when sum(atdPoh) > 0 then sum(atdPoh) else 0 end,case when sum(crossoverAoh) > 0 then sum(crossoverAoh) else 0 end,case when sum(gantryAoh) > 0 then sum(gantryAoh) else 0 end,case when sum(mclAoh) > 0 then sum(mclAoh) else 0 end,"
			+ "case when sum(mclPoh) > 0 then sum(mclPoh) else 0 end,case when sum(overlapAoh) > 0 then sum(overlapAoh) else 0 end,case when sum(ptfeAoh) > 0 then sum(ptfeAoh) else 0 end,case when sum(sclAoh) > 0 then sum(sclAoh) else 0 end," + 
			"case when sum(sclPoh) > 0 then sum(sclPoh) else 0 end,case when sum(siAoh) > 0 then sum(siAoh) else 0 end,case when sum(smAoh) > 0 then sum(smAoh) else 0 end,case when sum(turnoutAoh) > 0 then sum(turnoutAoh) else 0 end from AshDailyProgress where facility =:facility and date between :financialDate and :fromDate")
	String findBySumBasedOnFacilityAndDateBetween( @Param("facility") Facility facility,@Param("financialDate") Date financialDate,@Param("fromDate") Date fromDate);
	
	@Query( value = "select case when sum(atdAoh) > 0 then sum(atdAoh) else 0 end, case when sum(atdPoh) > 0 then sum(atdPoh) else 0 end,case when sum(crossoverAoh) > 0 then sum(crossoverAoh) else 0 end,case when sum(gantryAoh) > 0 then sum(gantryAoh) else 0 end,case when sum(mclAoh) > 0 then sum(mclAoh) else 0 end,"
			+ "case when sum(mclPoh) > 0 then sum(mclPoh) else 0 end,case when sum(overlapAoh) > 0 then sum(overlapAoh) else 0 end,case when sum(ptfeAoh) > 0 then sum(ptfeAoh) else 0 end,case when sum(sclAoh) > 0 then sum(sclAoh) else 0 end," + 
			"case when sum(sclPoh) > 0 then sum(sclPoh) else 0 end,case when sum(siAoh) > 0 then sum(siAoh) else 0 end,case when sum(smAoh) > 0 then sum(smAoh) else 0 end,case when sum(turnoutAoh) > 0 then sum(turnoutAoh) else 0 end from AshDailyProgress where facility =:facility and date between :financialDate and :fromDate")
	String monthSumBasedOnFacilityAndDateBetween( @Param("facility") Facility facility,@Param("financialDate") Date financialDate,@Param("fromDate") Date fromDate);

	
List<AshDailyProgress> findByDateAndApprovedStatusIsNull(Date fromDate);
	

	Optional<AshDailyProgress> findByDateAndFacilityAndApprovedStatusIsNull(Date fromDate, Optional<Facility> facility);

	

	Optional<AshDailyProgress> findByDateAndFacilityAndApprovedStatusIsNull(Date date, Facility facility);

	

	//List<AshDailyProgress> findByFacilityInAndDateAndApprovedStatusIsNull(Facility facility, Date fromDate);

	List<AshDailyProgress> findByFacilityInAndDateAndApprovedStatusIsNull(List<Facility> fac, Date fromDate);
}
