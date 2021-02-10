package com.scr.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scr.message.response.DriveTargetResponse;
import com.scr.model.DriveCategoryAsso;
import com.scr.model.Drives;
import com.scr.model.FunctionalLocationTypes;
import com.scr.model.Make;
import com.scr.model.Product;

@Repository
public interface DrivesRepository extends JpaRepository<Drives, Long> {

	Optional<Drives> findDriveById(Long id);

	Optional<Drives> findByIdAndStatusId(Long id, Integer statusId);

	List<Drives> findByStatusId(Integer statusId);

	Boolean existsByNameAndStatusId(String name, Integer statusId);

	Boolean existsByDescriptionAndStatusId(String description, Integer statusId);
	
	@Query("FROM Drives ORDER BY name ASC")
	List<Drives> findAllDrivesOrderByNameAsc();

	List<Drives> findByFromDateAndDepotType(Date fromDate, String depotType);

	List<Drives> findByFromDateGreaterThanEqualAndToDateGreaterThanEqualOrToDateIsNull(Date fromDate,Date toDate);

	List<Drives> findByFromDateGreaterThanEqualAndToDateGreaterThanEqualOrToDateIsNullAndDepotType(Date fromDate,
			Date toDate, FunctionalLocationTypes functionalLocationTypes);


	@Query(value = " FROM Drives where checklist='Yes' and statusId=1")  	
	List<Drives> getDrives();
	



	

	List<Drives> findByDepotTypeAndFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNull(
			FunctionalLocationTypes functionalLocationTypes, Date fromDate, Date toDate);

	List<Drives> findByIdInAndDepotTypeAndFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNull(
			List<Long> drives, FunctionalLocationTypes functionalLocationTypes, Date fromDate, Date toDate);
	
	@Query(value = " select d from Drives d where d.depotType = :functionalLocationTypes and d.id not in (:drives) and fromDate <= :fromDate and toDate >= :toDate or toDate is null " )
	List<Drives> findByDepotTypeAndFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNullAndIdNotIn(@Param("functionalLocationTypes")
			FunctionalLocationTypes functionalLocationTypes,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate, @Param("drives")List<Long> drives);

	@Query(value = " select d from Drives d where d.depotType = :functionalLocationTypes and d.id in (:drives) and fromDate <= :fromDate and toDate >= :toDate or toDate is null " )
	List<Drives> findByDepotTypeAndIdInAndFromDateLessThanEqualAndToDateGreaterThanEqualOrToDateIsNull(@Param("functionalLocationTypes")
			FunctionalLocationTypes functionalLocationTypes, @Param("drives")List<Long> drives,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

	@Query("FROM Drives ORDER BY createdOn DESC")
	List<Drives> findByStatusIdAndOrderByCreatedOnDesc(int activeStatusId);	

	@Query(value="select d from Drives d where d.id=:id and( d.toDate >=:date or d.toDate is null)")
	Optional<Drives> findByIdAndToDateGreaterThanEqualOrToDateIsNull(Long id, Date date);

	List<Drives> findByFunctionalUnitInOrFunctionalUnitIsNullAndStatusId(List<String> fac, Integer activeStatusId);




	

	

	






	

	

	



	

	

	

	

	
	
}
