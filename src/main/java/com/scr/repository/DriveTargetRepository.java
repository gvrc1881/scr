package com.scr.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.scr.model.DriveTarget;
import com.scr.model.Drives;
import com.scr.model.Facility;
import com.scr.model.Make;

public interface DriveTargetRepository extends JpaRepository<DriveTarget, Long>{

	Optional<DriveTarget> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveTarget> findByStatusId(Integer statusId);
	
	
	/*@Query(value = "SELECT case when count(dt)> 0 then true else false  end  FROM DriveTarget dt WHERE dt.unitType = :unitType and dt.unitName = :unitName")
	Boolean findByUnitNameAndUnitType(@Param("unitType")String unitType, @Param("unitName") String unitName);
	*/
	Optional<DriveTarget> findByUnitTypeAndUnitName(String unitType,String unitName);

	List<DriveTarget> getByDriveIdAndStatusId(Drives driveId, int activeStatusId);

	@Query(value = "select distinct driveId from DriveTarget") 
	List<Drives> findDistinctByDriveId();

	List<DriveTarget> findByDriveId(Drives drives);	
	
/*	@Query(value = "select Division,subDivision from Facility") 
	List<Facility> findByUnitName();*/

	@Query(value = "select distinct unitName from DriveTarget")
	List<Facility> findDistinctByUnitName();	

	Optional<DriveTarget> findByDriveIdAndUnitName(Drives driveId, String unitName);

	

	DriveTarget getByDriveIdAndUnitName(Drives drives2, String zone);

	DriveTarget findByDriveIdAndUnitName(Optional<Drives> drive, String zone);
	
	@Query(value = "select sum(target),dt.drive_id,f.zone\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drives2 \r\n" + 
			"		and dt.unit_type='DIV'\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.zone =:zone \r\n" + 
			"		group by drive_id,f.zone",nativeQuery=true) 
	Double getDivAggregation(Drives drives2, String zone);
	
	@Query(value = "select sum(target),dt.drive_id,f.zone\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drives2 \r\n" + 
			"		and dt.unit_type='SUB_DIV'\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.zone =:zone \r\n" + 
			"		group by drive_id,f.zone",nativeQuery=true) 
	Double getSubDivAggregation(Drives drives2, String zone);
	
	@Query(value = "select sum(target),dt.drive_id,f.zone\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drives2 \r\n" + 
			"		and dt.unit_type in('OHE','TSS','SP','SSP','PSI') \r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.zone =:zone \r\n" + 
			"		group by drive_id,f.zone",nativeQuery=true)
	Double getDepotDivAggregation(Drives drives2, String zone);

	@Query(value = "select sum(target),dt.drive_id,f.zone\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drive \r\n" + 
			"		and dt.unit_type='DIV'\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.zone =:zone \r\n" + 
			"		group by drive_id,f.zone",nativeQuery=true) 
	Double getDivAggregation(Optional<Drives> drive, String zone);

	@Query(value = "select sum(target),dt.drive_id,f.zone\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drive \r\n" + 
			"		and dt.unit_type='SUB_DIV'\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.zone =:zone \r\n" + 
			"		group by drive_id,f.zone",nativeQuery=true)
	Double getSubDivAggregation(Optional<Drives> drive, String zone);

	@Query(value = "select sum(target),dt.drive_id,f.zone\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drive \r\n" + 
			"		and dt.unit_type in('OHE','TSS','SP','SSP','PSI') \r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.zone =:zone \r\n" + 
			"		group by drive_id,f.zone",nativeQuery=true)
	Double getDepotDivAggregation(Optional<Drives> drive, String zone);
	
	@Query(value ="select sum(target),dt.drive_id,f.division\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drives2 \r\n" + 
			"		and dt.unit_type='SUB_DIV'\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.division =:facilityName \r\n" + 
			"		group by drive_id,f.division",nativeQuery=true)
	Double getSubDiviAggregation(Drives drives2, String facilityName);

	@Query(value="select sum(target),dt.drive_id,f.division\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drives2 \r\n" + 
			"		and dt.unit_type in('OHE','TSS','SP','SSP','PSI')\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.division =:facilityName \r\n" + 
			"		group by drive_id,f.division\r\n" ,nativeQuery=true)
			Double getDepotAggregation(Drives drives2, String facilityName);

	@Query(value ="select sum(target),dt.drive_id,f.division\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drive \r\n" + 
			"		and dt.unit_type='SUB_DIV'\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.division =:facilityName \r\n" + 
			"		group by drive_id,f.division",nativeQuery=true)
	Double getSubDivsnAggregation(Optional<Drives> drive, String facilityName);

	@Query(value="select sum(target),dt.drive_id,f.division\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drive \r\n" + 
			"		and dt.unit_type in('OHE','TSS','SP','SSP','PSI')\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.division =:facilityName \r\n" + 
			"		group by drive_id,f.division\r\n" ,nativeQuery=true)
	Double getDepAggregation(Optional<Drives> drive, String facilityName);

	@Query(value="select sum(target),dt.drive_id,f.sub_division\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drives2 \r\n" + 
			"		and dt.unit_type in('OHE','TSS','SP','SSP','PSI')\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.sub_division =:facilityName \r\n" + 
			"		group by drive_id,f.sub_division\r\n",nativeQuery=true)
	Double getDeptAggregation(Drives drives2, String facilityName);

	@Query(value="select sum(target),dt.drive_id,f.sub_division\r\n" + 
			"		from drive_target  dt,facility f\r\n" + 
			"		where dt.drive_id =:drive \r\n" + 
			"		and dt.unit_type in('OHE','TSS','SP','SSP','PSI')\r\n" + 
			"		and dt.unit_name=f.facility_name\r\n" + 
			"		and f.sub_division =:facilityName \r\n" + 
			"		group by drive_id,f.sub_division\r\n",nativeQuery=true)
	Double getDepotsAggregation(Optional<Drives> drive, String facilityName);

	Optional<DriveTarget> findByDriveIdAndUnitTypeAndUnitName(Drives drives, String depotType, String facilityName);

	

	

	

	

	

	

	

	

	

	

	
	

	
}
