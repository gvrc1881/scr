package com.scr.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scr.model.DriveDailyProgress;
import com.scr.model.Drives;

@Repository
public interface DriveProgressRecordRepository extends JpaRepository<DriveDailyProgress, Long>{

	Optional<DriveDailyProgress> findByIdAndStatusId(Long id, Integer statusId);

	List<DriveDailyProgress> findByStatusId(Integer statusId);

	//Optional<DriveDailyProgress> findByDriveId(Drives driveId);

	Optional<DriveDailyProgress> findByDriveIdAndPerformedDate(Drives drives, Date fromDate);

	List<DriveDailyProgress> findByDriveIdAndPerformedDateLessThan(Drives drives, Date fromDate);

	List<DriveDailyProgress> getByDriveIdAndStatusId(Drives driveId, int activeStatusId);
	
	List<DriveDailyProgress> findByDriveId(Drives driveId);
	
	List<DriveDailyProgress> findByPerformedDateAndDepotIn(Date fromDate, List<String> fac);	

	Optional<DriveDailyProgress> findByPerformedDateAndDepot(Date fromDate, String facilityId);

	List<DriveDailyProgress> findByPerformedDateAndDepotAndApprovedStatusIsNull(Date fromDate, String facilityId);

	List<DriveDailyProgress> findByPerformedDateLessThanEqualAndDepot(Date fromDate, String facilityId);

	List<DriveDailyProgress> findByPerformedDateAndDepotInAndApprovedStatusIsNull(Date fromDate, List<String> fac);

	List<DriveDailyProgress> findByDriveIdAndPerformedDateLessThanAndApprovedStatus(Drives drives, Date fromDate,
			String approveStatus);

}
